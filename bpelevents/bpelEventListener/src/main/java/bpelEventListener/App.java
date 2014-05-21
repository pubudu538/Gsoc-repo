package bpelEventListener;

//import org.apache.ode.bpel.common.ProcessState;
import org.apache.ode.bpel.evt.BpelEvent;
//import org.apache.ode.bpel.evt.ProcessInstanceEvent;
//import org.apache.ode.bpel.evt.ProcessTerminationEvent;
//import org.apache.ode.bpel.evt.ProcessInstanceStateChangeEvent;
import org.apache.ode.bpel.iapi.BpelEventListener;
//import org.wso2.carbon.bpel.core.ode.integration.store.ProcessConfigurationImpl;
//import org.wso2.carbon.bpel.b4p.coordination.configuration.CoordinationConfiguration;
//import org.wso2.carbon.bpel.b4p.internal.B4PContentHolder;
//import org.wso2.carbon.bpel.b4p.internal.B4PServiceComponent;
import java.util.Properties;

public class App implements BpelEventListener
{

	@Override
	public void onEvent(BpelEvent bpelEvent) {
		
		
//            if (bpelEvent instanceof ProcessTerminationEvent) {
//
//                ProcessTerminationEvent event = (ProcessTerminationEvent) bpelEvent;
//
//                ProcessConfigurationImpl processConf = getProcessConfiguration(event);
//                
//                if (processConf.isB4PTaskIncluded()) {
//                    if (log.isDebugEnabled()) {
//                        log.debug("TERMINATED Process instance " + event.getProcessInstanceId()
//                                + " has a B4P activity. Initiating Exit Protocol Messages to task(s).");
//                    }
//                    TerminationTask terminationTask = new TerminationTask(Long.toString(event.getProcessInstanceId()));
//                    B4PContentHolder.getInstance().getCoordinationController().runTask(terminationTask);
//                }
//
//            } else if (bpelEvent instanceof ProcessInstanceStateChangeEvent) {
//                ProcessInstanceStateChangeEvent instanceStateChangeEvent = (ProcessInstanceStateChangeEvent) bpelEvent;
//                if (ProcessState.STATE_COMPLETED_WITH_FAULT == instanceStateChangeEvent.getNewState()) {
//
//                    ProcessConfigurationImpl processConf = getProcessConfiguration(instanceStateChangeEvent);
//                    if (processConf.isB4PTaskIncluded()) {
//                        if (log.isDebugEnabled()) {
//                            log.debug("Process Instance, COMPLETED WITH FAULT " + instanceStateChangeEvent.getProcessInstanceId()
//                                    + " has a B4P activity. Initiating Exit Protocol Messages to task(s)");
//                        }
//                        TerminationTask terminationTask = new TerminationTask(Long.toString(instanceStateChangeEvent.getProcessInstanceId()));
//                        B4PContentHolder.getInstance().getCoordinationController().runTask(terminationTask);
//                    }
//                }
//            }
        
		 System.out.println("Event Notification: " +
	                bpelEvent.getType().toString() +
	                ", " + bpelEvent.getTimestamp()+
	                ", event details " + bpelEvent.toString());
		
		
//	System.out.println("[ Task ID : " + taskEventInfo.getTaskInfo().getId() + " ] [ ********* Event **** : " + taskEventInfo.getEventType() + " ] at ***** " + taskEventInfo.getTaskInfo().getModifiedDate());
//	
//	if (taskEventInfo.getTaskInfo().getType() == TaskType.TASK) {
//		
//	System.out.println("\tTask Name :" + taskEventInfo.getTaskInfo().getName());
//	System.out.println("\tTask Subject :" + taskEventInfo.getTaskInfo().getSubject());
//	System.out.println("\tTask Description :" + taskEventInfo.getTaskInfo().getDescription());
//	System.out.println("\tTask type :" + taskEventInfo.getTaskInfo().getType());
//	System.out.println("\tTask Owner : " + taskEventInfo.getTaskInfo().getOwner());
//	System.out.println("\tTask status : " + taskEventInfo.getTaskInfo().getStatus());
//	
//	 
//	}
//	

	}
	
	public void startup(Properties properties) {
       // log.info("Custom Event Listner:"+ CustomEventListener.class +" startup!");
        System.out.println("Custom Event Listner:"+ App.class +" startup!");
    }

    public void shutdown() {
       // log.info("Custom Event Listner:"+ CustomEventListener.class +" shutdown!");
        System.out.println("Custom Event Listner:"+ App.class +" shutdown!");
    }
	
}
