
/**
 * BpsStatsDataPublisherAdminExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

package org.wso2.carbon.bps.stats.stub;

public class BpsStatsDataPublisherAdminExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1405187974688L;
    
    private org.wso2.carbon.bps.stats.services.BpsStatsDataPublisherAdminException faultMessage;

    
        public BpsStatsDataPublisherAdminExceptionException() {
            super("BpsStatsDataPublisherAdminExceptionException");
        }

        public BpsStatsDataPublisherAdminExceptionException(java.lang.String s) {
           super(s);
        }

        public BpsStatsDataPublisherAdminExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public BpsStatsDataPublisherAdminExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.bps.stats.services.BpsStatsDataPublisherAdminException msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.bps.stats.services.BpsStatsDataPublisherAdminException getFaultMessage(){
       return faultMessage;
    }
}
    