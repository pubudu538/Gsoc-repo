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

	public void configurePublishingData(
			PublishingConfigData publishingConfigData) throws Exception {

		registryPersistenceManager.update(publishingConfigData);

	}

	public PublishingConfigData getPublishingConfigData() {

		return registryPersistenceManager.getPublishingConfigData();
	}

}