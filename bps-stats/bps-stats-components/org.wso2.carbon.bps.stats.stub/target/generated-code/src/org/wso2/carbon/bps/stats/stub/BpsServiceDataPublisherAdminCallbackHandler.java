
/**
 * BpsServiceDataPublisherAdminCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

    package org.wso2.carbon.bps.stats.stub;

    /**
     *  BpsServiceDataPublisherAdminCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BpsServiceDataPublisherAdminCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BpsServiceDataPublisherAdminCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BpsServiceDataPublisherAdminCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
               // No methods generated for meps other than in-out
                
           /**
            * auto generated Axis2 call back method for getEventingConfigData method
            * override this method for handling normal response from getEventingConfigData operation
            */
           public void receiveResultgetEventingConfigData(
                    org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEventingConfigData operation
           */
            public void receiveErrorgetEventingConfigData(java.lang.Exception e) {
            }
                


    }
    