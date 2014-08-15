package org.wso2.carbon.bps.stats.internal;

import org.wso2.carbon.bpel.core.ode.integration.BPELServer;

public class BpsStatsComponentHolder {

	private static BpsStatsComponentHolder instance;

	private BPELServer bpelServer;

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

}
