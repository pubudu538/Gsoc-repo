package org.wso2.carbon.bps.stats.internal;

import org.wso2.carbon.bpel.core.ode.integration.BPELServer;
import org.wso2.carbon.humantask.core.HumanTaskServer;
import org.wso2.carbon.registry.core.service.RegistryService;

public class BpsStatsComponentHolder {

	private static BpsStatsComponentHolder instance;

	private BPELServer bpelServer;
	private RegistryService registryService;
	private HumanTaskServer humanTaskServer;

	private BpsStatsComponentHolder() {
	}

	public static BpsStatsComponentHolder getInstance() {
		if (null == instance) {
			instance = new BpsStatsComponentHolder();
		}
		return instance;
	}

	public BPELServer getBpelServer() {
		return bpelServer;
	}

	public void setBpelServer(BPELServer bpelServer) {
		this.bpelServer = bpelServer;
	}

	public void setRegistryService(RegistryService registryService) {
		this.registryService = registryService;
	}

	public RegistryService getRegistryService() {
		return registryService;
	}

	public void setHumanTaskServer(HumanTaskServer humanTaskServer) {
		this.humanTaskServer = humanTaskServer;
	}

	public HumanTaskServer getHumanTaskServer() {
		return humanTaskServer;
	}

}
