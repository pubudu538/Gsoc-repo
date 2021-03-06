package org.wso2.carbon.bps.stats.conf;


import java.util.Map;

import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.bps.stats.util.CommonConstants;
import org.wso2.carbon.bps.stats.util.TenantPublishingConfigData;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.bam.data.publisher.util.BAMDataPublisherConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegistryPersistenceManager {

	private static RegistryService registryService;
	public static final String EMPTY_STRING = "";
	private static final Log log = LogFactory
			.getLog(RegistryPersistenceManager.class);

	public static void setRegistryService(RegistryService registryServiceParam) {
		registryService = registryServiceParam;
	}

	public void updateConfigurationProperty(String propertyName, Object value,
			String registryPath) throws RegistryException {

		String resourcePath = registryPath + propertyName;
		Registry registry = registryService
				.getConfigSystemRegistry(CarbonContext.getCurrentContext()
						.getTenantId());

		Resource resource;
		if (!registry.resourceExists(resourcePath)) {
			resource = registry.newResource();
			resource.addProperty(propertyName, String.valueOf(value));
			registry.put(resourcePath, resource);
		} else {
			resource = registry.get(resourcePath);
			resource.setProperty(propertyName, String.valueOf(value));
			registry.put(resourcePath, resource);
		}
	}

	public PublishingConfigData load() {

		PublishingConfigData publishingConfigData = new PublishingConfigData();

		// First set it to defaults, but do not persist
		publishingConfigData.setPublishingEnable(false);
		publishingConfigData.setUrl(EMPTY_STRING);
		publishingConfigData.setPassword(EMPTY_STRING);
		publishingConfigData.setUserName(EMPTY_STRING);

		// then load it from registry
		try {

			String serviceStatsStatus = getConfigurationProperty(
					CommonConstants.SERVICE_STATISTICS_REG_PATH,
					CommonConstants.ENABLE_BPS_STATS);

			String bamUrl = getConfigurationProperty(
					CommonConstants.SERVICE_COMMON_REG_PATH,
					BAMDataPublisherConstants.BAM_URL);

			String bamUserName = getConfigurationProperty(
					CommonConstants.SERVICE_COMMON_REG_PATH,
					BAMDataPublisherConstants.BAM_USER_NAME);
			String bamPassword = getConfigurationProperty(
					CommonConstants.SERVICE_COMMON_REG_PATH,
					BAMDataPublisherConstants.BAM_PASSWORD);

			if (serviceStatsStatus != null && bamUrl != null
					&& bamUserName != null && bamPassword != null) {

				publishingConfigData.setPublishingEnable(Boolean
						.parseBoolean(serviceStatsStatus));
				publishingConfigData.setUrl(bamUrl);
				publishingConfigData.setUserName(bamUserName);
				publishingConfigData.setPassword(bamPassword);

				int tenantId = PrivilegedCarbonContext
						.getThreadLocalCarbonContext().getTenantId(); // CarbonContext.getCurrentContext().getTenantId();
				Map<Integer, PublishingConfigData> tenantPublishingConfigData = TenantPublishingConfigData
						.getTenantSpecificPublishingConfigData();
				tenantPublishingConfigData.put(tenantId, publishingConfigData);

			} else { // Registry does not have publishing config. Set to
						// defaults.

				update(publishingConfigData);
			}
		} catch (Exception e) {

			log.error("Could not load values from registry", e);

			// If something went wrong, then we have the default, or whatever
			// loaded so far
		}
		return publishingConfigData;
	}

	public String getConfigurationProperty(String registryPath,
			String propertyName) throws RegistryException {

		String resourcePath = registryPath + propertyName;
		int id = CarbonContext.getCurrentContext().getTenantId();

		RegistryService rs = registryService;

		Registry registry = rs.getConfigSystemRegistry(id);

		String value = null;
		if (registry.resourceExists(resourcePath)) {
			Resource resource = registry.get(resourcePath);
			value = resource.getProperty(propertyName);
		}
		return value;
	}

	public void update(PublishingConfigData publishingConfigData)
			throws RegistryException {

		int tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext()
				.getTenantId(); // CarbonContext.getCurrentContext().getTenantId();

		Map<Integer, PublishingConfigData> tenantPublishingConfigData = TenantPublishingConfigData
				.getTenantSpecificPublishingConfigData();
		tenantPublishingConfigData.put(tenantId, publishingConfigData);

		updateConfigurationProperty(BAMDataPublisherConstants.BAM_USER_NAME,
				publishingConfigData.getUserName(),
				CommonConstants.SERVICE_COMMON_REG_PATH);

		updateConfigurationProperty(BAMDataPublisherConstants.BAM_URL,
				publishingConfigData.getUrl(),
				CommonConstants.SERVICE_COMMON_REG_PATH);

		updateConfigurationProperty(BAMDataPublisherConstants.BAM_PASSWORD,
				publishingConfigData.getPassword(),
				CommonConstants.SERVICE_COMMON_REG_PATH);

		updateConfigurationProperty(CommonConstants.ENABLE_BPS_STATS,
				publishingConfigData.isPublishingEnable(),
				CommonConstants.SERVICE_STATISTICS_REG_PATH);

	}

	public PublishingConfigData getPublishingConfigData() {
		return load();
	}

}
