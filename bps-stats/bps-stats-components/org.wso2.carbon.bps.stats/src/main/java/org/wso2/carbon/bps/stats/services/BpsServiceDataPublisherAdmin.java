package org.wso2.carbon.bps.stats.services;

import org.wso2.carbon.bps.stats.conf.PublishingConfigData;
import org.wso2.carbon.bps.stats.conf.RegistryPersistenceManager;
import org.wso2.carbon.core.AbstractAdmin;

public class BpsServiceDataPublisherAdmin extends AbstractAdmin {

	private RegistryPersistenceManager registryPersistenceManager;

	public BpsServiceDataPublisherAdmin() {
		registryPersistenceManager = new RegistryPersistenceManager();
	}

	public void configurePublishin(PublishingConfigData publishingConfigData)
			throws Exception {
		registryPersistenceManager.update(publishingConfigData);
	}

	public PublishingConfigData getEventingConfigData() {
		return registryPersistenceManager.getPublishingConfigData();
	}

}