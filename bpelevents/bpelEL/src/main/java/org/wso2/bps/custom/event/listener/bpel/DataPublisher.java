package org.wso2.bps.custom.event.listener.bpel;

import org.apache.log4j.Logger;
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

	private static Logger logger = Logger.getLogger(DataPublisher.class);
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

		// Using Asynchronous data publisher
		AsyncDataPublisher asyncDataPublisher = new AsyncDataPublisher(
				"tcp://localhost:7611", "admin", "admin");
		String streamDefinition = "";
		
		if (category.equals("bpelProcessInfo")) {
			
			DATA_STREAM = "bpel_process_information";
			streamDefinition = getStreamDefinition(DATA_STREAM, VERSION,
					category);
		}else if(category.equals("bpelProcessInstanceInfo")){
		
			DATA_STREAM = "bpel_process_instance_information";
			streamDefinition = getStreamDefinition(DATA_STREAM, VERSION,
					category);
			
		}

		asyncDataPublisher.addStreamDefinition(streamDefinition, DATA_STREAM,
				VERSION);
		publishEvents(asyncDataPublisher, array);
	}

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
						
		}

		return streamDefinition;
	}

	private static void publishEvents(AsyncDataPublisher asyncDataPublisher,ArrayList<String> values) {
	
		Object[] payload = new Object[values.size()];
		
		for(int i=0;i<values.size();i++)
		{
			payload[i] = values.get(i);
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		Event event = eventObject(null,null,payload, map);
		
		try {
			asyncDataPublisher.publish(DATA_STREAM, VERSION,
					event);
			//System.out.println("^^^^^^^^^^^^^^ Published Event &&&&&&&&&&&&&&&&& ^^^^^^^^^^^^");

		} catch (AgentException e) {
			logger.error("Failed to publish event", e);
			
			//System.out.println("^^^^^^^^^^^^^^ Published Event Failed &&&&&&&&&&&&&&&&& ^^^^^^^^^^^^");
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
