package org.wso2.bps.custom.event.listener.bpel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.AsyncDataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.base.ServerConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import org.wso2.carbon.utils.CarbonUtils;

public class BamDataPublisher {

	private static Log log = LogFactory.getLog(BamDataPublisher.class);
	private static HashMap<String, AsyncDataPublisher> dataPublisherMap = new HashMap<String, AsyncDataPublisher>();
	private static String key = "publisher";

	@SuppressWarnings("deprecation")
	public static void setPublishingData(ArrayList<String> array,
			String category) {

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
		
		//send data to the publisher
		publishEvents(getPublisher(category,dataStream,version), array,dataStream,version);
	}


	private static AsyncDataPublisher getPublisher(String category,String dataStream,String version) {

		AsyncDataPublisher dataPublisher = null;

		//get the datapublisher instance if exist
		if (dataPublisherMap.get(key) != null) {

			dataPublisher = dataPublisherMap.get(key);
			String streamDefinition = getStreamDefinition(dataStream, version,
					category);
			dataPublisher.addStreamDefinition(streamDefinition, dataStream,
					version);       // add stream definition to the publisher


		} else {

			dataPublisher = createDataPublisher(); 		// create data publisher
			String streamDefinition = getStreamDefinition(dataStream, version,
					category);
			dataPublisher.addStreamDefinition(streamDefinition, dataStream,
					version);		 // add stream definition to the data publisher
			dataPublisherMap.put(key, dataPublisher);         // add data publisher instance to hashmap

		}

		return dataPublisher;

	}

	
	private static AsyncDataPublisher createDataPublisher() {

		ServerConfiguration serverConfig = CarbonUtils.getServerConfiguration();
		String trustStorePath = serverConfig
				.getFirstProperty("Security.TrustStore.Location");
		String trustStorePassword = serverConfig
				.getFirstProperty("Security.TrustStore.Password");
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.trustStorePassword",
				trustStorePassword);
		
		String bamServerUrl = serverConfig.getFirstProperty("BamServerURL"); // Get BAM URL from carbon.xml
																				
		String bamUserName = serverConfig.getFirstProperty("BamUserName"); // Get BAM username from carbon.xml
																			
		String bamPassword = serverConfig.getFirstProperty("BamPassword"); // Get BAM password from carbon.xml

		// Using Asynchronous data publisher
		AsyncDataPublisher dataPublisher = new AsyncDataPublisher(bamServerUrl,
				bamUserName, bamPassword);

		return dataPublisher;

	}
	
	
	// Setting up the stream definition for different categories
	private static String getStreamDefinition(String dataStream,
			String version, String category) {
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
					+ " {'name':'state','type':'String'}" + " ]" + "}";

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
					+ " {'name':'class','type':'String'}" + " ]" + "}";

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
					+ " {'name':'taskCreatedTime','type':'String'}" + " ]"
					+ "}";

		}

		return streamDefinition;
	}

	// publish events to BAM
	private static void publishEvents(AsyncDataPublisher asyncDataPublisher,
			ArrayList<String> values,String dataStream,String version) {

		Object[] payload = new Object[values.size()];

		for (int i = 0; i < values.size(); i++) {
			payload[i] = values.get(i);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		Event event = eventObject(null, null, payload, map);

		try {
			asyncDataPublisher.publish(dataStream, version, event); // Publish events to BAM

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

	private static Event eventObject(Object[] correlationData,
			Object[] metaData, Object[] payLoadData, HashMap<String, String> map) {

		Event event = new Event();
		event.setCorrelationData(correlationData);
		event.setMetaData(metaData);
		event.setPayloadData(payLoadData);
		event.setArbitraryDataMap(map);
		return event;
	}

}
