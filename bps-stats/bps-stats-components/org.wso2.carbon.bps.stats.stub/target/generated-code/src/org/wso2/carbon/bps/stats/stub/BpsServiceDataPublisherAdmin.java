

/**
 * BpsServiceDataPublisherAdmin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

    package org.wso2.carbon.bps.stats.stub;

    /*
     *  BpsServiceDataPublisherAdmin java interface
     */

    public interface BpsServiceDataPublisherAdmin {
          
       /**
         * Auto generated method signature for Asynchronous Invocations
         * 
                 * @throws org.wso2.carbon.bps.stats.stub.BpsServiceDataPublisherAdminExceptionException : 
         */
        public void  configurePublishin(
         org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData publishingConfigData1

        ) throws java.rmi.RemoteException
        
        
               ,org.wso2.carbon.bps.stats.stub.BpsServiceDataPublisherAdminExceptionException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getEventingConfigData2
                
         */

         
                     public org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData getEventingConfigData(

                        )
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getEventingConfigData2
            
          */
        public void startgetEventingConfigData(

            

            final org.wso2.carbon.bps.stats.stub.BpsServiceDataPublisherAdminCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    