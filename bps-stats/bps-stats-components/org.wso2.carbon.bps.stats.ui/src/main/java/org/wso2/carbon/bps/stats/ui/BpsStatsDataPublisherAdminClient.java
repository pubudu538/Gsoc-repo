package org.wso2.carbon.bps.stats.ui;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.bps.stats.stub.BpsStatsDataPublisherAdminStub;
import org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BpsStatsDataPublisherAdminClient {

	private static final Log log = LogFactory
			.getLog(BpsStatsDataPublisherAdminClient.class);
	private static final String BUNDLE = "org.wso2.carbon.bps.stats.ui.i18n.Resources";
	private BpsStatsDataPublisherAdminStub stub;
	private ResourceBundle bundle;

	public BpsStatsDataPublisherAdminClient(String cookie,
			String backendServerURL, ConfigurationContext configContext,
			Locale locale) throws AxisFault {

		String serviceURL = backendServerURL + "BpsStatsDataPublisherAdmin";
		bundle = ResourceBundle.getBundle(BUNDLE, locale);

		stub = new BpsStatsDataPublisherAdminStub(configContext, serviceURL);

		ServiceClient client = stub._getServiceClient();
		Options option = client.getOptions();
		option.setManageSession(true);
		option.setProperty(
				org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING,
				cookie);
	}

	public void setPublishingConfigData(PublishingConfigData publishingConfigData)
			throws RemoteException {
		try {
			log.info("set publishing config data in");
			stub.configurePublishingData(publishingConfigData);
			log.info("set publishing config data out");
		} catch (Exception e) {
			handleException(bundle.getString("cannot.set.publishing.config"), e);
		}
	}

	public PublishingConfigData getPublishingConfigData() throws RemoteException {
		try {
			log.info("stub get publishing config data in and out");
			return stub.getPublishingConfigData();
			
		} catch (RemoteException e) {
			log.error("get publishing config data didnt work");
			handleException(bundle.getString("cannot.get.publishing.config"), e);
		}
		return null;
	}

	private void handleException(String msg, java.lang.Exception e)
			throws RemoteException {
		log.error(msg, e);
		throw new RemoteException(msg, e);
	}

}
