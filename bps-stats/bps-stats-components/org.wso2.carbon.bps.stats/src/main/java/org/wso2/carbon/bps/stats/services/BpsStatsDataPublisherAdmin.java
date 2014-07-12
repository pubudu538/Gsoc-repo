package org.wso2.carbon.bps.stats.services;

import org.wso2.carbon.bps.stats.conf.PublishingConfigData;
import org.wso2.carbon.bps.stats.conf.RegistryPersistenceManager;
import org.wso2.carbon.core.AbstractAdmin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BpsStatsDataPublisherAdmin extends AbstractAdmin {

	private RegistryPersistenceManager registryPersistenceManager;
	private static final Log log = LogFactory
			.getLog(BpsStatsDataPublisherAdmin.class);

	public BpsStatsDataPublisherAdmin() {
		registryPersistenceManager = new RegistryPersistenceManager();
	}

	public void configurePublishingData(PublishingConfigData publishingConfigData)
			throws Exception {
		log.info("Admin - in configurePublshingdata");
		registryPersistenceManager.update(publishingConfigData);
		log.info("Admin - out configurePublshingdata");
	}

	public PublishingConfigData getPublishingConfigData() {
		log.info("Admin - in and out getPublshingconfigdata");
		return registryPersistenceManager.getPublishingConfigData();
	}

}