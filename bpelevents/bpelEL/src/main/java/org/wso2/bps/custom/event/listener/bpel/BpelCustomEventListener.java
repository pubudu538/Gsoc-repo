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

			} else if (ProcessState.STATE_TERMINATED == instanceStateChangeEvent
					.getNewState()) {
				state = "Terminated";
				setProcessDetails(instanceStateChangeEvent, state);

			}

		}

		if (bpelEvent.getType().toString() == "activityLifecycle") {

			ArrayList<String> instanceInfo = new ArrayList<String>();
			//DataPublisher publisher = new DataPublisher();
			
			System.out
					.println("Pub noti for activity &&&& & & & & & &  & &&&&&&& : "
							+ bpelEvent.getType().toString()
							+ ", "
							+ bpelEvent.getTimestamp()
							+ ", event details "
							+ bpelEvent.toString());

			String[] info = bpelEvent.toString().split("\n");
			String activity = info[1].trim();
			instanceInfo.add(activity.substring(0,activity.length()-1));

			for (int k = 2; k < info.length; k++) {
				String[] values = info[k].split("=");
				instanceInfo.add(values[1].trim());
			}

			ArrayList<String> arr=new ArrayList<String>();
			String st="fdsalf fdsfds";
			
			for (int j = 0; j < 17; j++) {
			
				arr.add(st);
				
			}
			
			String category = "bpelProcessInstanceInfo";
			DataPublisher.setPublishingData(arr, category);
			
			// arraylist is ready to publish
			for (int i = 0; i < instanceInfo.size(); i++) {
				System.out.println("Splitted event * "+i+" ---------# "
						+ instanceInfo.get(i)+" - -  - - "+instanceInfo.size());
			}
		}

	}

	public void startup(Properties properties) {
	}

	public void shutdown() {
	}

	public void setProcessDetails(ProcessInstanceStateChangeEvent process,
			String state) {
		
		//DataPublisher publisher = new DataPublisher();
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
		
		DataPublisher.setPublishingData(processValues, category);		

		System.out.println("Package Name - " + packageName
				+ ", Process Name - " + processName + ", Process Id - "
				+ processId + ", Instance Id - " + processInstanceId
				+ ", timestamp - " + timestamp + ", State - " + state);

	}

	//Need to add comments, logger class for logging, bam url to take from file or stratos datapublisher
}
