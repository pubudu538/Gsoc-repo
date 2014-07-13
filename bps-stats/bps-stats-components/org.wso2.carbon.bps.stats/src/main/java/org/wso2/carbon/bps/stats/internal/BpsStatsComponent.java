package org.wso2.carbon.bps.stats.internal;

import org.osgi.service.component.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.bps.stats.conf.RegistryPersistenceManager;

/**
 * @scr.component name="org.wso2.carbon.bps.stats " immediate="true"
 * @scr.reference name="org.wso2.carbon.registry.service"
 *                interface="org.wso2.carbon.registry.core.service.RegistryService"
 *                cardinality="1..1" policy="dynamic" bind="setRegistryService"
 *                unbind="unsetRegistryService"
 */

public class BpsStatsComponent {

	private static Log log = LogFactory.getLog(BpsStatsComponent.class);

	protected void activate(ComponentContext context) {

		try {

			new RegistryPersistenceManager().load();

		} catch (Throwable t) {
			log.error("Failed to activate BPS Stats Bundle", t);
		}

	}

	protected void setRegistryService(RegistryService registryService) {
		try {

			RegistryPersistenceManager.setRegistryService(registryService);

		} catch (Exception e) {
			log.error("Cannot retrieve System Registry", e);
		}
	}

	protected void unsetRegistryService(RegistryService registryService) {
		RegistryPersistenceManager.setRegistryService(null);
	}

}
