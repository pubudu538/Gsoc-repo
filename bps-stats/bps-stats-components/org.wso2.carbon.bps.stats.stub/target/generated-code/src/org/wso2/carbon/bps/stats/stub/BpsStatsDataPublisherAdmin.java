

/**
 * BpsStatsDataPublisherAdmin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

    package org.wso2.carbon.bps.stats.stub;

    /*
     *  BpsStatsDataPublisherAdmin java interface
     */

    public interface BpsStatsDataPublisherAdmin {
          

        /**
          * Auto generated method signature
          * 
                    * @param getPublishingConfigData0
                
         */

         
                     public org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData getPublishingConfigData(

                        )
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getPublishingConfigData0
            
          */
        public void startgetPublishingConfigData(

            

            final org.wso2.carbon.bps.stats.stub.BpsStatsDataPublisherAdminCallbackHandler callback)

            throws java.rmi.RemoteException;

     
       /**
         * Auto generated method signature for Asynchronous Invocations
         * 
                 * @throws org.wso2.carbon.bps.stats.stub.BpsStatsDataPublisherAdminExceptionException : 
         */
        public void  configurePublishingData(
         org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData publishingConfigData4

        ) throws java.rmi.RemoteException
        
        
               ,org.wso2.carbon.bps.stats.stub.BpsStatsDataPublisherAdminExceptionException;

        

        
       //
       }
    