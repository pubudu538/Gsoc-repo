package org.wso2.carbon.bps.stats.util;

import java.util.HashMap;
import java.util.Map;

import org.wso2.carbon.bps.stats.conf.PublishingConfigData;

public class TenantPublishingConfigData {

	private volatile static Map<Integer, PublishingConfigData> tenantPublishingConfigDataMap;

	public static Map<Integer, PublishingConfigData> getTenantSpecificPublishingConfigData() {
		if (tenantPublishingConfigDataMap == null) {
			synchronized (TenantPublishingConfigData.class) {
				if (tenantPublishingConfigDataMap == null) {
					tenantPublishingConfigDataMap = new HashMap<Integer, PublishingConfigData>();
				}
			}
		}
		return tenantPublishingConfigDataMap;
	}

}
