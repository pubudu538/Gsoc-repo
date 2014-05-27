package org.wso2.bps.custom.event.listener.bpel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.AsyncDataPublisher;
import org.wso2.carbon.databridge.agent.thrift.conf.AgentConfiguration;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.base.ServerConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import org.wso2.carbon.utils.CarbonUtils;

public class DataPublisher {

	private static Log log = LogFactory.getLog(DataPublisher.class);
	public static String DATA_STREAM = "";
	public static final String VERSION = "1.0.0";

	@SuppressWarnings("deprecation")
	public static void setPublishingData(ArrayList<String> array,
			String category) {

		ServerConfiguration serverConfig = CarbonUtils.getServerConfiguration();
		String trustStorePath = serverConfig
				.getFirstProperty("Security.TrustStore.Location");
		String trustStorePassword = serverConfig
				.getFirstProperty("Security.TrustStore.Password");
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.trustStorePassword",
				trustStorePassword);
		String bamServerUrl = serverConfig.getFirstProperty("BamServerURL");         // Get BAM URL from carbon.xml
		String bamUserName = serverConfig.getFirstProperty("BamUserName");           // Get BAM username from carbon.xml
		String bamPassword = serverConfig.getFirstProperty("BamPassword");           // Get BAM password from carbon.xml
		
		// Using Asynchronous data publisher
		AsyncDataPublisher asyncDataPublisher = new AsyncDataPublisher(bamServerUrl,bamUserName,bamPassword);
		String streamDefinition = "";

		// According to the category, stream id is set and gets the stream definition
		if (category.equals("bpelProcessInfo")) {

			DATA_STREAM = "bpel_process_information";
			streamDefinition = getStreamDefinition(DATA_STREAM, VERSION,
					category);                 // get the stream definition
		} else if (category.equals("bpelProcessInstanceInfo")) {

			DATA_STREAM = "bpel_process_instance_information";
			streamDefinition = getStreamDefinition(DATA_STREAM, VERSION,
					category);

		} else if (category.equals("humanTaskInfo")) {

			DATA_STREAM = "human_task_information";
			streamDefinition = getStreamDefinition(DATA_STREAM, VERSION,
					category);
		}

		asyncDataPublisher.addStreamDefinition(streamDefinition, DATA_STREAM,
				VERSION);
		publishEvents(asyncDataPublisher, array);
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

	private static void publishEvents(AsyncDataPublisher asyncDataPublisher,
			ArrayList<String> values) {

		Object[] payload = new Object[values.size()];

		for (int i = 0; i < values.size(); i++) {
			payload[i] = values.get(i);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		Event event = eventObject(null, null, payload, map);

		try {
			asyncDataPublisher.publish(DATA_STREAM, VERSION, event);      // Publish events to BAM

			if (log.isDebugEnabled()) {
				log.debug(String.format(
						"Publishing Events to BAM: [Stream] %s [Version] %s",
						DATA_STREAM, VERSION));
			}

		} catch (AgentException e) {

			if (log.isErrorEnabled()) {
				log.error(String.format(
								"Could not publish events to BAM : [Stream] %s [Version] %s",
								DATA_STREAM, VERSION), e);
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
