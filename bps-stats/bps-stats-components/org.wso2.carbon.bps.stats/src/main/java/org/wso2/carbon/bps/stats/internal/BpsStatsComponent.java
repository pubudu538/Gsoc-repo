package org.wso2.carbon.bps.stats.internal;

import org.osgi.service.component.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.bpel.core.BPELEngineService;
import org.wso2.carbon.bpel.core.ode.integration.BPELServer;

/**
 * @scr.component name="org.wso2.carbon.bps.stats " immediate="true" *
 * @scr.reference name="bpel.engine"
 *                interface="org.wso2.carbon.bpel.core.BPELEngineService"
 *                cardinality="1..1" policy="dynamic" bind="setBPELServer"
 *                unbind="unsetBPELServer" *
 */

public class BpsStatsComponent {

	private static Log log = LogFactory.getLog(BpsStatsComponent.class);

	protected void activate(ComponentContext context) {

		try {

		} catch (Throwable t) {
			log.error("Failed to activate BPS Stats Bundle", t);
		}

	}

	// protected void setRegistryService(RegistryService registryService) {
	// try {
	//
	// RegistryPersistenceManager.setRegistryService(registryService);
	// // int tenantId = PrivilegedCarbonContext
	// // .getThreadLocalCarbonContext().getTenantId();//
	// // CarbonContext.getThreadLocalCarbonContext().getTenantId();
	// // new BpelCustomEventListener().setTenantId(tenantId);
	//
	// } catch (Exception e) {
	// log.error("Cannot retrieve System Registry", e);
	// }
	// }
	//
	// protected void unsetRegistryService(RegistryService registryService) {
	// RegistryPersistenceManager.setRegistryService(null);
	// }

	protected void setBPELServer(BPELEngineService bpelEngineService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService bound to BPEL BPS STATS Publisher component");
		}
		BpsStatsComponentHolder.getInstance().setBpelServer(
				bpelEngineService.getBPELServer());

	}

	protected void unsetBPELServer(BPELEngineService bpelEngineService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService unbound from the BPEL BPS STATS Publisher component");
		}
		BpsStatsComponentHolder.getInstance().setBpelServer(null);
	}

	public static BPELServer getBPELServer() {
		return BpsStatsComponentHolder.getInstance().getBpelServer();
	}

}
