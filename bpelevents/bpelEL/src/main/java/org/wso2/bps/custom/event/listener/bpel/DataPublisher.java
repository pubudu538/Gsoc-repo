package org.wso2.bps.custom.event.listener.bpel;

import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.AsyncDataPublisher;
import org.wso2.carbon.databridge.agent.thrift.conf.AgentConfiguration;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.base.ServerConfiguration;
import java.util.HashMap;
import org.wso2.carbon.utils.CarbonUtils;

public class DataPublisher {

	private static Logger logger = Logger
			.getLogger(DataPublisher.class);
	public static final String CALL_CENTER_DATA_STREAM = "callcenter_stream";
	public static final String VERSION = "1.0.0";
	public static final String DATA = "Sophia,Mon,36,1,0,Colombo\n"
			+ "Jacob,Mon,55,0,1,Horana\n" + "Mason,Mon,150,0,1\n"
			+ "William,Mon,10,1,0,Colombo\n" + "Jayden,Mon,15,1,0,Galle\n"
			+ "Michael,Mon,25,1,0\n" + "Emma,Mon,40,1,0,Colombo";

	@SuppressWarnings("deprecation")
	public static void pubPublish() {

		ServerConfiguration serverConfig = CarbonUtils.getServerConfiguration();
		String trustStorePath = serverConfig.getFirstProperty("Security.TrustStore.Location");
		String trustStorePassword = serverConfig.getFirstProperty("Security.TrustStore.Password");
		
		System.setProperty("javax.net.ssl.trustStore",trustStorePath);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		// Using Asynchronous data publisher
		AsyncDataPublisher asyncDataPublisher = new AsyncDataPublisher(
				"tcp://localhost:7611", "admin", "admin");
		String streamDefinition = "{" + " 'name':'" + CALL_CENTER_DATA_STREAM
				+ "'," + " 'version':'" + VERSION + "',"
				+ " 'nickName': 'Phone_Retail_Shop',"
				+ " 'description': 'Phone Sales'," + " 'metaData':["
				+ " {'name':'publisherIP','type':'STRING'}" + " ],"
				+ " 'payloadData':[" + " {'name':'name','type':'STRING'},"
				+ " {'name':'day','type':'STRING'},"
				+ " {'name':'timeToRespond','type':'INT'},"
				+ " {'name':'answered','type':'INT'},"
				+ " {'name':'abandoned','type':'INT'}" + " ]" + "}";
		asyncDataPublisher.addStreamDefinition(streamDefinition,
				CALL_CENTER_DATA_STREAM, VERSION);
		publishEvents(asyncDataPublisher);
	}

	// public static void main(String[] args) {
	// System.setProperty("javax.net.ssl.trustStore",
	// "/mnt/windows/Users/chamith/Desktop/share/releases/bam2/230/wso2bam-2.3.0/repository/resources/security/client-truststore.jks");
	// System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
	//
	// //Using Asynchronous data publisher
	// AsyncDataPublisher asyncDataPublisher = new
	// AsyncDataPublisher("tcp://localhost:7611", "admin", "admin");
	// String streamDefinition = "{" +
	// " 'name':'" + CALL_CENTER_DATA_STREAM + "'," +
	// " 'version':'" + VERSION + "'," +
	// " 'nickName': 'Phone_Retail_Shop'," +
	// " 'description': 'Phone Sales'," +
	// " 'metaData':[" +
	// " {'name':'publisherIP','type':'STRING'}" +
	// " ]," +
	// " 'payloadData':[" +
	// " {'name':'name','type':'STRING'}," +
	// " {'name':'day','type':'STRING'}," +
	// " {'name':'timeToRespond','type':'INT'}," +
	// " {'name':'answered','type':'INT'}," +
	// " {'name':'abandoned','type':'INT'}" +
	// " ]" +
	// "}";
	// asyncDataPublisher.addStreamDefinition(streamDefinition,
	// CALL_CENTER_DATA_STREAM, VERSION);
	// publishEvents(asyncDataPublisher);
	// }

	private static void publishEvents(AsyncDataPublisher asyncDataPublisher) {
		String[] dataRow = DATA.split("\n");
		
		for (String row : dataRow) {
			String[] data = row.split(",");
			Object[] payload = new Object[] { data[0], data[1],
					Integer.parseInt(data[2]), Integer.parseInt(data[3]),
					Integer.parseInt(data[4]) };
			HashMap<String, String> map = new HashMap<String, String>();
			// Calling location information included.
			if (data.length == 6) {
				map.put("location", data[5]);
			}
			Event event = eventObject(null, new Object[] { "10.100.3.173" },
					payload, map);
			try {
				asyncDataPublisher.publish(CALL_CENTER_DATA_STREAM, VERSION,
						event);
				System.out.println("^^^^^^^^^^^^^^ Published Event &&&&&&&&&&&&&&&&& ^^^^^^^^^^^^");
				
			} catch (AgentException e) {
				logger.error("Failed to publish event", e);
				System.out.println("^^^^^^^^^^^^^^ Published Event Failed &&&&&&&&&&&&&&&&& ^^^^^^^^^^^^");
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
