package org.wso2.bps.custom.event.listener.bpel;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.ode.bpel.common.ProcessState;
import org.apache.ode.bpel.evt.BpelEvent;
//import org.apache.ode.bpel.evt.ProcessInstanceEvent;
//import org.apache.ode.bpel.evt.ProcessTerminationEvent;
import org.apache.ode.bpel.evt.ProcessInstanceStateChangeEvent;
import org.apache.ode.bpel.iapi.BpelEventListener;

public class BpelCustomEventListener implements BpelEventListener {

	public void onEvent(BpelEvent bpelEvent) {

		if (bpelEvent instanceof ProcessInstanceStateChangeEvent) {

			ProcessInstanceStateChangeEvent instanceStateChangeEvent = (ProcessInstanceStateChangeEvent) bpelEvent;
			String state = "";

			if (ProcessState.STATE_READY == instanceStateChangeEvent
					.getNewState()) {
				state = "Ready";
				setProcessDetails(instanceStateChangeEvent, state);

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
				System.out.println("$$$$$$$$$$$$ $$$ $$$ - Suspended &&&&&&&&&&");

			} else if (ProcessState.STATE_TERMINATED == instanceStateChangeEvent
					.getNewState()) {
				state = "Terminated";
				setProcessDetails(instanceStateChangeEvent, state);
				System.out.println("$$$$$$$$$$$$ $$$ $$$ - Terminated &&&&&&&&&&");

			}

		}

		if (bpelEvent.getType().toString() == "activityLifecycle") {

			ArrayList<String> instanceInfo = new ArrayList<String>();
			
//			System.out
//					.println("Pub noti for activity &&&& & & & & & &  & &&&&&&& : "
//							+ bpelEvent.getType().toString()
//							+ ", "
//							+ bpelEvent.getTimestamp()
//							+ ", event details "
//							+ bpelEvent.toString());

			String[] info = bpelEvent.toString().split("\n");
			String activity = info[1].trim();
			instanceInfo.add(activity.substring(0, activity.length() - 1));

			String[] orderedValues = setInstanceDetails(info);
			
			for (int k = 0; k < orderedValues.length; k++) {
				
				instanceInfo.add(orderedValues[k]);
			}

						
//			for (int i = 0; i < instanceInfo.size(); i++) {
//				System.out.println("Splitted event * " + i + " ---------# "
//						+ instanceInfo.get(i) + " - -  - - "
//						+ instanceInfo.size());
//			}
			
			String category = "bpelProcessInstanceInfo";
			DataPublisher.setPublishingData(instanceInfo, category);
		}
		
//		System.out
//		.println("&&&& & & & & & &  & &&&&&&& Separate %%%%%%%%%%%%%%%%%%%%%%%%%% %% %% % : "
//				+ bpelEvent.getType().toString()
//				+ ", "
//				+ bpelEvent.getTimestamp()
//				+ ", event details "
//				+ bpelEvent.toString());

	}

	public void startup(Properties properties) {
	}

	public void shutdown() {
	}

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

	public void setProcessDetails(ProcessInstanceStateChangeEvent process,
			String state) {

		ArrayList<String> processValues = new ArrayList<String>();
		String category = "bpelProcessInfo";

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

		

//		System.out.println("Package Name - " + packageName
//				+ ", Process Name - " + processName + ", Process Id - "
//				+ processId + ", Instance Id - " + processInstanceId
//				+ ", timestamp - " + timestamp + ", State - " + state);

		DataPublisher.setPublishingData(processValues, category);
	}

	// Need to add comments, logger class for logging, bam url to take from file
	// or stratos datapublisher
}
