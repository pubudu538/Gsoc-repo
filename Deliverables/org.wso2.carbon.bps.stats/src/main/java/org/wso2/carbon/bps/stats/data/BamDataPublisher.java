package org.wso2.carbon.bps.stats.data;

/* 
 * This is used to Publish events to BAM. According to the
 * category receives from the event listeners, data stream 
 * will be selected.
 * 
 * bpel_process_information - for BPEL process information
 * bpel_process_instance_information - for BPEL instance information
 * human_task_information - for Human Task information 
 * 
 * WSO2 BAM credentials are taken from bps_stats.xml file in 
 * <BPS_HOME>/repository/conf/ directory. 
 * 
 * */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.agent.thrift.AsyncDataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.base.ServerConfiguration;
import org.wso2.carbon.bps.stats.conf.PublishingConfigData;
import org.wso2.carbon.bps.stats.util.CommonConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.axiom.om.OMElement;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.wso2.carbon.utils.CarbonUtils;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

public class BamDataPublisher {

	private static Log log = LogFactory.getLog(BamDataPublisher.class);
	private static HashMap<Integer, AsyncDataPublisher> dataPublisherMap = new HashMap<Integer, AsyncDataPublisher>();

	public void setPublishingData(ArrayList<String> array, String category,
			int tenantId) {

		String dataStream = "";
		String version = "1.0.0";

		// According to the category, stream id is set
		if (category.equals("bpelProcessInfo")) {

			dataStream = "bpel_process_information";

		} else if (category.equals("bpelProcessInstanceInfo")) {

			dataStream = "bpel_process_instance_information";

		} else if (category.equals("humanTaskInfo")) {

			dataStream = "human_task_information";

		}

		AsyncDataPublisher publisher = getPublisher(category, dataStream,
				version, tenantId);

		if (publisher != null) {
			publishEvents(publisher, array, dataStream, version); // send data
																	// to the
																	// publisher

		}

	}

	private AsyncDataPublisher getPublisher(String category, String dataStream,
			String version, int tenantId) {

		AsyncDataPublisher dataPublisher = null;

		PublishingConfigData publishingConfigData = getPublishingConfigData();

		// get the datapublisher instance if exist
		if (dataPublisherMap.get(tenantId) != null) {

			dataPublisher = dataPublisherMap.get(tenantId);
			String streamDefinition = getStreamDefinition(dataStream, version,
					category);
			dataPublisher.addStreamDefinition(streamDefinition, dataStream,
					version); // add stream definition to the publisher

		} else if (publishingConfigData.isPublishingEnable()) {

			String url = publishingConfigData.getUrl();
			String userName = publishingConfigData.getUserName();
			String password = publishingConfigData.getPassword();

			dataPublisher = createDataPublisher(url, userName, password); // create
																			// data
																			// publisher
			String streamDefinition = getStreamDefinition(dataStream, version,
					category);
			dataPublisher.addStreamDefinition(streamDefinition, dataStream,
					version); // add stream definition to the data publisher
			dataPublisherMap.put(tenantId, dataPublisher); // add data publisher
															// instance to
															// hashmap

		}

		return dataPublisher;

	}

	private PublishingConfigData getPublishingConfigData() {

		OMElement bamConfig = getPublishingConfig();
		PublishingConfigData publishingConfigData = new PublishingConfigData();
		boolean publishingEnabled;

		if (null != bamConfig) {

			OMElement servicePublishElement = bamConfig
					.getFirstChildWithName(new QName(
							CommonConstants.BPS_STATS_PUBLISH_OMELEMENT));

			if (null != servicePublishElement) {
				if (servicePublishElement
						.getText()
						.trim()
						.equalsIgnoreCase(
								CommonConstants.BPS_STATS_PUBLISH_ENABLED)) {
					publishingEnabled = true;
				} else {
					log.info("BAM Stat Publishing is disabled");
					publishingEnabled = false;
				}

			} else {
				publishingEnabled = false;
			}

			OMElement servicePublishUrl = bamConfig
					.getFirstChildWithName(new QName(CommonConstants.BAM_URL));
			OMElement servicePublishUserName = bamConfig
					.getFirstChildWithName(new QName(
							CommonConstants.BAM_USER_NAME));
			OMElement servicePublishPassword = bamConfig
					.getFirstChildWithName(new QName(
							CommonConstants.BAM_PASSWORD));

			publishingConfigData.setUrl(servicePublishUrl.getText().trim());
			publishingConfigData.setUserName(servicePublishUserName.getText()
					.trim());
			publishingConfigData.setPassword(servicePublishPassword.getText()
					.trim());

		} else {
			log.warn("Invalid " + CommonConstants.BAM_CONFIG_XML
					+ ". Disabling BPS Stats Publishing.");
			publishingEnabled = false;
		}

		publishingConfigData.setPublishingEnable(publishingEnabled);

		return publishingConfigData;
	}

	private OMElement getPublishingConfig() {
		String bamConfigPath = CarbonUtils.getCarbonConfigDirPath()
				+ File.separator + CommonConstants.BAM_CONFIG_XML;

		File bamConfigFile = new File(bamConfigPath);
		try {
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStream inputStream = new FileInputStream(bamConfigFile);
			XMLStreamReader reader = xif.createXMLStreamReader(inputStream);
			xif.setProperty("javax.xml.stream.isCoalescing", false);

			StAXOMBuilder builder = new StAXOMBuilder(reader);

			return builder.getDocument().getOMDocumentElement();
		} catch (FileNotFoundException e) {
			log.warn("No " + CommonConstants.BAM_CONFIG_XML + " found in "
					+ bamConfigPath);
			return null;
		} catch (XMLStreamException e) {
			log.error("Incorrect format " + CommonConstants.BAM_CONFIG_XML
					+ " file", e);
			return null;
		}
	}

	private AsyncDataPublisher createDataPublisher(String url, String userName,
			String password) {

		ServerConfiguration serverConfig = CarbonUtils.getServerConfiguration();
		String trustStorePath = serverConfig
				.getFirstProperty("Security.TrustStore.Location");
		String trustStorePassword = serverConfig
				.getFirstProperty("Security.TrustStore.Password");
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.trustStorePassword",
				trustStorePassword);

		// Using Asynchronous data publisher
		AsyncDataPublisher dataPublisher = new AsyncDataPublisher(url,
				userName, password);

		return dataPublisher;

	}

	// Setting up the stream definition for different categories
	private String getStreamDefinition(String dataStream, String version,
			String category) {
		String streamDefinition = "";

		if (category.equals("bpelProcessInfo")) {
			streamDefinition = "{" + " 'name':'" + dataStream + "',"
					+ " 'version':'" + version + "',"
					+ " 'nickName': 'BPEL_Process_Info',"
					+ " 'description': 'Process state changes info',"
					+ " 'payloadData':["
					+ " {'name':'packageName','type':'STRING'},"
					+ " {'name':'processName','type':'STRING'},"
					+ " {'name':'processId','type':'STRING'},"
					+ " {'name':'processInstanceId','type':'STRING'},"
					+ " {'name':'timestamp','type':'STRING'},"
					+ " {'name':'state','type':'STRING'},"
					+ " {'name':'tenantId','type':'STRING'}" + " ]" + "}";

		} else if (category.equals("bpelProcessInstanceInfo")) {

			streamDefinition = "{" + " 'name':'" + dataStream + "',"
					+ " 'version':'" + version + "',"
					+ " 'nickName': 'BPEL_Process_Instance_Info',"
					+ " 'description': 'Instance activity life cycle info',"
					+ " 'payloadData':["
					+ " {'name':'eventName','type':'STRING'},"
					+ " {'name':'activityId','type':'STRING'},"
					+ " {'name':'activityName','type':'STRING'},"
					+ " {'name':'activityType','type':'STRING'},"
					+ " {'name':'activityDeclarationId','type':'STRING'},"
					+ " {'name':'type','type':'STRING'},"
					+ " {'name':'scopeDeclarationId','type':'STRING'},"
					+ " {'name':'parentScopesNames','type':'STRING'},"
					+ " {'name':'scopeName','type':'STRING'},"
					+ " {'name':'scopeId','type':'STRING'},"
					+ " {'name':'processInstanceId','type':'STRING'},"
					+ " {'name':'processName','type':'STRING'},"
					+ " {'name':'processId','type':'STRING'},"
					+ " {'name':'lineNo','type':'STRING'},"
					+ " {'name':'timestamp','type':'STRING'},"
					+ " {'name':'class','type':'STRING'}" + " ]" + "}";

		} else if (category.equals("humanTaskInfo")) {

			streamDefinition = "{"
					+ " 'name':'"
					+ dataStream
					+ "',"
					+ " 'version':'"
					+ version
					+ "',"
					+ " 'nickName': 'Human_Task_Information',"
					+ " 'description': 'Human task instance state change info',"
					+ " 'payloadData':["
					+ " {'name':'taskId','type':'STRING'},"
					+ " {'name':'eventType','type':'STRING'},"
					+ " {'name':'modifiedDate','type':'STRING'},"
					+ " {'name':'taskName','type':'STRING'},"
					+ " {'name':'taskSubject','type':'STRING'},"
					+ " {'name':'taskDescription','type':'STRING'},"
					+ " {'name':'taskType','type':'STRING'},"
					+ " {'name':'taskOwner','type':'STRING'},"
					+ " {'name':'taskStatus','type':'STRING'},"
					+ " {'name':'taskCreatedTime','type':'STRING'},"
					+ " {'name':'tenantId','type':'STRING'}" + " ]" + "}";

		}

		return streamDefinition;
	}

	// publish events to BAM
	private void publishEvents(AsyncDataPublisher asyncDataPublisher,
			ArrayList<String> values, String dataStream, String version) {

		Object[] payload = new Object[values.size()];

		for (int i = 0; i < values.size(); i++) {
			payload[i] = values.get(i);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		Event event = eventObject(null, null, payload, map);

		try {
			asyncDataPublisher.publish(dataStream, version, event); // Publish
																	// events to
																	// BAM

			if (log.isDebugEnabled()) {
				log.debug(String.format(
						"Publishing Events to BAM: [Stream] %s [Version] %s",
						dataStream, version));
			}

		} catch (AgentException e) {

			if (log.isErrorEnabled()) {
				log.error(
						String.format(
								"Could not publish events to BAM : [Stream] %s [Version] %s",
								dataStream, version), e);
			}

		}

	}

	private Event eventObject(Object[] correlationData, Object[] metaData,
			Object[] payLoadData, HashMap<String, String> map) {

		Event event = new Event();
		event.setCorrelationData(correlationData);
		event.setMetaData(metaData);
		event.setPayloadData(payLoadData);
		event.setArbitraryDataMap(map);
		return event;
	}

}
