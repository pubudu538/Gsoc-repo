package org.wso2.carbon.bps.stats.internal;

import org.osgi.service.component.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.bps.stats.conf.RegistryPersistenceManager;
import org.wso2.carbon.bps.stats.data.BpelCustomEventListener;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.bpel.core.BPELEngineService;
import org.wso2.carbon.bpel.core.ode.integration.BPELServer;

import org.wso2.carbon.humantask.core.HumanTaskEngineService;
import org.wso2.carbon.humantask.core.HumanTaskServer;

/**
 * @scr.component name="org.wso2.carbon.bps.stats " immediate="true" *
 * @scr.reference name="bpel.engine"
 *                interface="org.wso2.carbon.bpel.core.BPELEngineService"
 *                cardinality="1..1" policy="dynamic" bind="setBPELServer"
 *                unbind="unsetBPELServer" *
 * @scr.reference name="human.engine"
 *                interface="org.wso2.carbon.humantask.core.HumanTaskEngineService"
 *                cardinality="1..1" policy="dynamic" bind="setHumanTaskServer"
 *                unbind="unsetHumanTaskServer" *
 */

public class BpsStatsComponent {

	private static Log log = LogFactory.getLog(BpsStatsComponent.class);

	protected void activate(ComponentContext context) {

		try {

			// new RegistryPersistenceManager().load();
			// int tenantId = PrivilegedCarbonContext
			// .getThreadLocalCarbonContext().getTenantId();//
			// CarbonContext.getThreadLocalCarbonContext().getTenantId();
			// new BpelCustomEventListener().setTenantId(tenantId);

		} catch (Throwable t) {
			log.error("Failed to activate BPS Stats Bundle", t);
		}

	}

	protected void setRegistryService(RegistryService registryService) {
		try {

			log.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ^^ ^ ^^^^^  ^ ^^^^         - - ----- 999");

			RegistryPersistenceManager.setRegistryService(registryService);
			// int tenantId = PrivilegedCarbonContext
			// .getThreadLocalCarbonContext().getTenantId();//
			// CarbonContext.getThreadLocalCarbonContext().getTenantId();
			// new BpelCustomEventListener().setTenantId(tenantId);

		} catch (Exception e) {
			log.error("Cannot retrieve System Registry", e);
		}
	}

	protected void unsetRegistryService(RegistryService registryService) {
		RegistryPersistenceManager.setRegistryService(null);
	}

	protected void setBPELServer(BPELEngineService bpelEngineService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService bound to BEPL BAM Publisher component");
		}
		BpsStatsComponentHolder.getInstance().setBpelServer(
				bpelEngineService.getBPELServer());

		log.debug("set the bpel engine");
	}

	protected void unsetBPELServer(BPELEngineService bpelEngineService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService unbound from the BPEL Bam Publisher component");
		}
		BpsStatsComponentHolder.getInstance().setBpelServer(null);
	}

	protected void setHumanTaskServer(HumanTaskEngineService humanTaskService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService bound to BEPL BAM Publisher component");
		}
		// BpsStatsComponentHolder.getInstance().setBpelServer(
		// bpelEngineService.getBPELServer());
		BpsStatsComponentHolder.getInstance().setHumanTaskServer(
				humanTaskService.getHumanTaskServer());

		// humanTaskService.getHumanTaskServer().getTaskStoreManager()
		// .getHumanTaskStore(-1234).getTaskConfigurationInfoList();

		log.debug("set the bpel engine");
	}

	protected void unsetHumanTaskServer(HumanTaskEngineService humanTaskService) {
		if (log.isDebugEnabled()) {
			log.debug("BPELEngineService unbound from the BPEL Bam Publisher component");
		}
		BpsStatsComponentHolder.getInstance().setHumanTaskServer(null);

	}

	public static BPELServer getBPELServer() {
		return BpsStatsComponentHolder.getInstance().getBpelServer();
	}

	public static HumanTaskServer getHumanTaskServer() {
		return BpsStatsComponentHolder.getInstance().getHumanTaskServer();
	}

}
