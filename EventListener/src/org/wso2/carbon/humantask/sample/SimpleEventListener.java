package org.wso2.carbon.humantask.sample;

import org.wso2.carbon.humantask.core.api.event.HumanTaskEventListener;
import org.wso2.carbon.humantask.core.api.event.TaskEventInfo;
import org.wso2.carbon.humantask.core.dao.TaskType;
 
public class SimpleEventListener implements HumanTaskEventListener {
 
@Override
public void onEvent(TaskEventInfo taskEventInfo) {
	
System.out.println("[ Task ID : " + taskEventInfo.getTaskInfo().getId() + " ] [ Event : " + taskEventInfo.getEventType() + " ] at " + taskEventInfo.getTaskInfo().getModifiedDate());

if (taskEventInfo.getTaskInfo().getType() == TaskType.TASK) {
System.out.println("\tTask Name :" + taskEventInfo.getTaskInfo().getName());
System.out.println("\tTask Subject :" + taskEventInfo.getTaskInfo().getSubject());
System.out.println("\tTask Description :" + taskEventInfo.getTaskInfo().getDescription());
System.out.println("\tTask Owner : " + taskEventInfo.getTaskInfo().getOwner());
 
}
else if (taskEventInfo.getTaskInfo().getType() == TaskType.NOTIFICATION) {
	
System.out.println("\tNotification Name :" + taskEventInfo.getTaskInfo().getName());
System.out.println("\tNotification Subject :" + taskEventInfo.getTaskInfo().getSubject());
System.out.println("\tNotification Description :" + taskEventInfo.getTaskInfo().getDescription());
}

}

}
