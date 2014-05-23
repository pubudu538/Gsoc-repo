package bpelEL;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.ode.bpel.common.ProcessState;
import org.apache.ode.bpel.evt.BpelEvent;
//import org.apache.ode.bpel.evt.ProcessInstanceEvent;
//import org.apache.ode.bpel.evt.ProcessTerminationEvent;
import org.apache.ode.bpel.evt.ProcessInstanceStateChangeEvent;
import org.apache.ode.bpel.iapi.BpelEventListener;

public class App implements BpelEventListener {

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

			System.out
					.println("Pub noti for activity &&&& & & & & & &  & &&&&&&& : "
							+ bpelEvent.getType().toString()
							+ ", "
							+ bpelEvent.getTimestamp()
							+ ", event details "
							+ bpelEvent.toString());

			String[] info = bpelEvent.toString().split("\n");
			instanceInfo.add(info[1].trim());

			for (int k = 2; k < info.length; k++) {
				String[] values = info[k].split("=");
				instanceInfo.add(values[1].trim());
			}

			// arraylist is ready to publish
			for (int i = 0; i < instanceInfo.size(); i++) {
				System.out.println("Splitted event ----------# "
						+ instanceInfo.get(i));
			}
		}

	}

	public void startup(Properties properties) {

	}

	public void shutdown() {
	}

	public void setProcessDetails(ProcessInstanceStateChangeEvent process,
			String state) {
		String processInstanceId = process.getProcessInstanceId().toString();
		;
		String processId = process.getProcessId().toString();
		String processName = process.getProcessName().toString();
		String timestamp = process.getTimestamp().toString();
		String[] results = processId.split("\\}");
		String packageName = results[results.length - 1];

		System.out.println("Package Name - " + packageName
				+ ", Process Name - " + processName + ", Process Id - "
				+ processId + ", Instance Id - " + processInstanceId
				+ ", timestamp - " + timestamp + ", State - " + state);

	}

}
