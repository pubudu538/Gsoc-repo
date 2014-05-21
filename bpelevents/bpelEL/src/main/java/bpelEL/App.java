package bpelEL;


import java.util.Properties;

import org.apache.ode.bpel.common.ProcessState;
import org.apache.ode.bpel.evt.BpelEvent;
import org.apache.ode.bpel.evt.ProcessInstanceEvent;
import org.apache.ode.bpel.evt.ProcessTerminationEvent;
import org.apache.ode.bpel.evt.ProcessInstanceStateChangeEvent;
import org.apache.ode.bpel.iapi.BpelEventListener;
//import org.wso2.carbon.bpel.b4p.coordination.configuration.CoordinationConfiguration;
//import org.wso2.carbon.bpel.b4p.internal.B4PContentHolder;
//import org.wso2.carbon.bpel.b4p.internal.B4PServiceComponent;
//import org.wso2.carbon.bpel.core.ode.integration.store.ProcessConfigurationImpl;

public class App implements BpelEventListener
{
	public void onEvent(BpelEvent bpelEvent) {
		
           if (bpelEvent instanceof ProcessTerminationEvent) {

                ProcessTerminationEvent event = (ProcessTerminationEvent) bpelEvent;
              //  ProcessConfigurationImpl processConf = getProcessConfiguration(event);
                
                System.out.println("Event Notification for termination ****** : " +
    	                bpelEvent.getType().toString() +
    	                ", " + bpelEvent.getTimestamp()+
    	                ", event details " + bpelEvent.toString());
                
//                if (processConf.isB4PTaskIncluded()) {
//                                       
                	System.out.println("process id **** "+event.getProcessInstanceId());
//                    
//                    TerminationTask terminationTask = new TerminationTask(Long.toString(event.getProcessInstanceId()));
//                    B4PContentHolder.getInstance().getCoordinationController().runTask(terminationTask);
//                }

            } else if (bpelEvent instanceof ProcessInstanceStateChangeEvent) {
            	
            	System.out.println("Event Notification for statechange ############# : " +
    	                bpelEvent.getType().toString() +
    	                ", " + bpelEvent.getTimestamp()+
    	                ", event details " + bpelEvent.toString());
            	
                ProcessInstanceStateChangeEvent instanceStateChangeEvent = (ProcessInstanceStateChangeEvent) bpelEvent;
                
                             
                if(ProcessState.STATE_COMPLETED_OK == instanceStateChangeEvent.getNewState())
                {
                	System.out.println("Event Notification for AllStates ############# : " +
        	                bpelEvent.getType().toString() +
        	                ", " + bpelEvent.getTimestamp()+
        	                ", event details " + bpelEvent.toString());
                	
                	System.out.println("Process Instance Id - " +
                	instanceStateChangeEvent.getProcessInstanceId()+" & Process id - " +
                			instanceStateChangeEvent.getProcessId()+ " & Process Name " +
                			instanceStateChangeEvent.getProcessName()+ " & time - "+
                			instanceStateChangeEvent.getTimestamp() + " & type - "+
                			instanceStateChangeEvent.getType() + " & toString - "+ 
                			instanceStateChangeEvent.toString());
                }
            }
        
    }

    public void startup(Properties properties) {
       // this.isCoordinationEnabled = CoordinationConfiguration.getInstance().isEnable();
    }

    public void shutdown() {
    }

//    private ProcessConfigurationImpl getProcessConfiguration(ProcessInstanceEvent event) {
//        int tenantId = B4PServiceComponent.getBPELServer().getMultiTenantProcessStore().getTenantId(event.getProcessId());
//        return (ProcessConfigurationImpl) B4PServiceComponent.
//                getBPELServer().getMultiTenantProcessStore().getTenantsProcessStore(tenantId).
//                getProcessConfiguration(event.getProcessId());
//    }
	
	
}
