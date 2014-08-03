package org.wso2.carbon.bps.stats.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.apache.ode.bpel.common.ProcessState;
import org.apache.ode.bpel.evt.BpelEvent;
import org.apache.ode.bpel.evt.ProcessInstanceStateChangeEvent;
import org.apache.ode.bpel.iapi.BpelEventListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.humantask.core.deployment.SimpleTaskDefinitionInfo;
import org.wso2.carbon.humantask.core.store.HumanTaskBaseConfiguration;
import org.wso2.carbon.bpel.b4p.internal.B4PServiceComponent;
import org.wso2.carbon.bpel.core.ode.integration.store.MultiTenantProcessStore;
import org.wso2.carbon.bps.stats.internal.BpsStatsComponent;
import org.wso2.carbon.bps.stats.data.HumanTaskCustomEventListener;

public class BpelCustomEventListener implements BpelEventListener {

	private static Log log = LogFactory.getLog(BpelCustomEventListener.class);
	// private static HashMap<String, BamDataPublisher> dataPubMap = new
	// HashMap<String, BamDataPublisher>();
	private static int tenantId = 4;

	// private static int count=0;

	public void onEvent(BpelEvent bpelEvent) {

		// Check for the BPEL instance state change
		if (bpelEvent instanceof ProcessInstanceStateChangeEvent) {

			ProcessInstanceStateChangeEvent instanceStateChangeEvent = (ProcessInstanceStateChangeEvent) bpelEvent;
			String state = "";

			// MultiTenantProcessStore tenan =
			// B4PServiceComponent.getBPELServer().getMultiTenantProcessStore();//.getTenantId(instanceStateChangeEvent.getProcessId());
			// tenan.getLocalDeploymentUnitRepo();

			int tenId = BpsStatsComponent.getBPELServer()
					.getMultiTenantProcessStore()
					.getTenantId(instanceStateChangeEvent.getProcessId());

			List<SimpleTaskDefinitionInfo> list = BpsStatsComponent
					.getHumanTaskServer().getTaskStoreManager()
					.getHumanTaskStore(tenId).getTaskConfigurationInfoList();

			List<HumanTaskBaseConfiguration> baseList = BpsStatsComponent
					.getHumanTaskServer().getTaskStoreManager()
					.getHumanTaskStore(tenId).getTaskConfigurations();
			
//			BpsStatsComponent
//			.getHumanTaskServer().getTaskStoreManager().getHumanTaskStore(tenId).getTaskConfigurationInfoListForPackage(pck);
			
			log.debug("list id - "+list.get(0));
			log.debug("lll - "+list.size()+" - "+list.get(0).getPackageName()+" 99999 - "+list.get(0).getTaskName());
			
			////////////////////////working fine returns appclaim-1
			
			log.debug(list.get(0).getTaskName());
			HumanTaskCustomEventListener.setTenantId(tenId);
			
			//////////////////////
			log.debug("list id 1 "+baseList.get(0).getPackageName());
			log.debug("list id - 2 "+baseList.get(0).getDefinitionName());
			log.debug("list id - 3 "+baseList.get(0).getServiceName());
			log.debug("list id - 4 "+baseList.get(0).getTargetNamespace());
			
			log.debug("tttttttttttttttt  -- - -   - "
					+ instanceStateChangeEvent.getProcessId());
			log.debug("tttttttttttttttt  -- - -   - " + tenId);

			// Check with every state in BPEL Process Instance
			if (ProcessState.STATE_READY == instanceStateChangeEvent
					.getNewState()) {
				state = "Ready";
				setProcessDetails(instanceStateChangeEvent, state); // When
																	// Process
																	// state
																	// changes,
																	// send to
																	// publish
																	// data

			} else if (ProcessState.STATE_ACTIVE == instanceStateChangeEvent
					.getNewState()) {
				state = "Active";
				setProcessDetails(instanceStateChangeEvent, state);

			} else if (ProcessState.STATE_COMPLETED_OK == instanceStateChangeEvent
					.getNewState()) {
				state = "Completed";
				setProcessDetails(instanceStateChangeEvent, state);

			} else if (ProcessState.STATE_COMPLETED_WITH_FAULT == instanceStateChangeEvent
					.getNewState()) {
				state = "Failed";
				setProcessDetails(instanceStateChangeEvent, state);

			} else if (ProcessState.STATE_SUSPENDED == instanceStateChangeEvent
					.getNewState()) {
				state = "Suspended";
				setProcessDetails(instanceStateChangeEvent, state);

			} else if (ProcessState.STATE_TERMINATED == instanceStateChangeEvent
					.getNewState()) {
				state = "Terminated";
				setProcessDetails(instanceStateChangeEvent, state);

			}

		}

		// For activityLifeCycle of the process instance
		if (bpelEvent.getType().toString() == "activityLifecycle") {

			ArrayList<String> instanceInfo = new ArrayList<String>();
			String category = "bpelProcessInstanceInfo"; // set the category for
															// the publisher to
															// identify the
															// stream id

			String[] info = bpelEvent.toString().split("\n");
			String activity = info[1].trim();
			instanceInfo.add(activity.substring(0, activity.length() - 1));

			String[] orderedValues = setInstanceDetails(info); // Get ordered
																// list of
																// attributes to
																// publish

			for (int k = 0; k < orderedValues.length; k++) {

				instanceInfo.add(orderedValues[k]);
			}

			if (log.isDebugEnabled()) {
				log.debug(String
						.format("BPEL Process Instance Information : [Activity] %s "
								+ "[Activity Id] %s [Activity Name] %s [Activity Type] %s [Activity Declaration Id] %s "
								+ "[Type] %s [Scope Declaration Id] %s [Parent Scopes Names] %s [Scope Name] %s [Scope Id] %s"
								+ " [Process Instance Id] %s [Process Name] %s [Process Id] %s [Line No] %s [Timestamp] %s [Class] %s",
								instanceInfo.get(0), instanceInfo.get(1),
								instanceInfo.get(2), instanceInfo.get(3),
								instanceInfo.get(4), instanceInfo.get(5),
								instanceInfo.get(6), instanceInfo.get(7),
								instanceInfo.get(8), instanceInfo.get(9),
								instanceInfo.get(10), instanceInfo.get(11),
								instanceInfo.get(12), instanceInfo.get(13),
								instanceInfo.get(14), instanceInfo.get(15)));

			}

			BamDataPublisher publisher = new BamDataPublisher();
			publisher.setPublishingData(instanceInfo, category); // Publish
																	// events to
																	// BAM
		}

	}

	public void startup(Properties properties) {
	}

	public void shutdown() {
	}

	// Outputs an ordered list of array which has process instance information
	public String[] setInstanceDetails(String[] array) {
		String[] newValues = new String[15];

		for (int j = 0; j < newValues.length; j++) {
			newValues[j] = "";
		}

		for (int i = 2; i < array.length; i++) {

			String[] values = array[i].split("=");

			if (values[0].trim().equals("ActivityId")) {
				newValues[0] = values[1].trim();

			} else if (values[0].trim().equals("ActivityName")) {
				newValues[1] = values[1].trim();

			} else if (values[0].trim().equals("ActivityType")) {
				newValues[2] = values[1].trim();

			} else if (values[0].trim().equals("ActivityDeclarationId")) {
				newValues[3] = values[1].trim();

			} else if (values[0].trim().equals("Type")) {
				newValues[4] = values[1].trim();

			} else if (values[0].trim().equals("ScopeDeclarationId")) {
				newValues[5] = values[1].trim();

			} else if (values[0].trim().equals("ParentScopesNames")) {
				newValues[6] = values[1].trim();

			} else if (values[0].trim().equals("ScopeName")) {
				newValues[7] = values[1].trim();

			} else if (values[0].trim().equals("ScopeId")) {
				newValues[8] = values[1].trim();

			} else if (values[0].trim().equals("ProcessInstanceId")) {
				newValues[9] = values[1].trim();

			} else if (values[0].trim().equals("ProcessName")) {
				newValues[10] = values[1].trim();

			} else if (values[0].trim().equals("ProcessId")) {
				newValues[11] = values[1].trim();

			} else if (values[0].trim().equals("LineNo")) {
				newValues[12] = values[1].trim();

			} else if (values[0].trim().equals("Timestamp")) {
				newValues[13] = values[1].trim();

			} else if (values[0].trim().equals("Class")) {
				newValues[14] = values[1].trim();

			}
		}

		return newValues;

	}

	// Add process details to an arraylist to send to the DataPublisher
	public void setProcessDetails(ProcessInstanceStateChangeEvent process,
			String state) {

		ArrayList<String> processValues = new ArrayList<String>();
		String category = "bpelProcessInfo"; // set the category for the
												// publisher to identify the
												// stream id

		String processInstanceId = process.getProcessInstanceId().toString();
		String processId = process.getProcessId().toString();
		String processName = process.getProcessName().toString();
		String timestamp = process.getTimestamp().toString();
		String[] results = processId.split("\\}");
		String packageName = results[results.length - 1];

		processValues.add(packageName);
		processValues.add(processName);
		processValues.add(processId);
		processValues.add(processInstanceId);
		processValues.add(timestamp);
		processValues.add(state);

		if (log.isDebugEnabled()) {
			log.debug(String
					.format("********* BPEL Process Information : [Package Name] %s [Process Name] %s [Process Id] %s"
							+ " [Process Instance Id] %s [TimeStamp] %s [State] %s",
							packageName, processName, processId,
							processInstanceId, timestamp, state));
		}

		BamDataPublisher publisher = new BamDataPublisher();
		publisher.setPublishingData(processValues, category); // Publish events
																// to the BAM

	}

	public static void setTenantId(int id) {
		tenantId = id;
	}

	public static int getTenantId() {
		return tenantId;
	}

}
