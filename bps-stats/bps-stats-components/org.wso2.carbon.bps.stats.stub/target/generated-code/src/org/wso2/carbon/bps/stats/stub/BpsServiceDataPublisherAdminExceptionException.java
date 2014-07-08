
/**
 * BpsServiceDataPublisherAdminExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

package org.wso2.carbon.bps.stats.stub;

public class BpsServiceDataPublisherAdminExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1404836554967L;
    
    private org.wso2.carbon.bps.stats.services.BpsServiceDataPublisherAdminException faultMessage;

    
        public BpsServiceDataPublisherAdminExceptionException() {
            super("BpsServiceDataPublisherAdminExceptionException");
        }

        public BpsServiceDataPublisherAdminExceptionException(java.lang.String s) {
           super(s);
        }

        public BpsServiceDataPublisherAdminExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public BpsServiceDataPublisherAdminExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.bps.stats.services.BpsServiceDataPublisherAdminException msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.bps.stats.services.BpsServiceDataPublisherAdminException getFaultMessage(){
       return faultMessage;
    }
}
    