package org.wso2.carbon.bps.stats.util;

import org.wso2.carbon.core.RegistryResources;

public class CommonConstants {
	
    public static final String BAM_URL = "BAMUrl";
    public static final String BAM_USER_NAME = "BAMUserName";
    public static final String BAM_PASSWORD = "BAMPassword";
    
    public static final String ENABLE_BPS_STATS = "EnableBpsStats";


    public static final String SERVICE_COMMON_REG_PATH = RegistryResources.COMPONENTS
                                                   + "org.wso2.carbon.bps.stats/common/";

    public static final String SERVICE_PROPERTIES_REG_PATH = RegistryResources.COMPONENTS
                                                   + "org.wso2.carbon.bps.stats/properties";
    
    public static final String SERVICE_STATISTICS_REG_PATH = RegistryResources.COMPONENTS
            + "org.wso2.carbon.bps.stats/bpsStats/";


//    public static final String DEFAULT_BAM_SERVER_URL = "tcp://127.0.0.1:7611";

}
