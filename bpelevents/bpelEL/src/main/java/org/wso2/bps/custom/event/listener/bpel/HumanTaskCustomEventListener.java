package org.wso2.bps.custom.event.listener.bpel;

import java.util.ArrayList;
import org.wso2.carbon.humantask.core.api.event.HumanTaskEventListener;
import org.wso2.carbon.humantask.core.api.event.TaskEventInfo;
import org.wso2.carbon.humantask.core.dao.TaskType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HumanTaskCustomEventListener implements HumanTaskEventListener {

	private static Log log = LogFactory
			.getLog(HumanTaskCustomEventListener.class);
	;
	@Override
	public void onEvent(TaskEventInfo taskEventInfo) {

		// Only for Task type 
		if (taskEventInfo.getTaskInfo().getType() == TaskType.TASK) {

			ArrayList<String> values = new ArrayList<String>();
			String category = "humanTaskInfo";         // set the category for the publisher to identify the stream id

			String taskId = String.valueOf(taskEventInfo.getTaskInfo().getId());
			String eventType = String.valueOf(taskEventInfo.getEventType());
			String modifiedDate = String.valueOf(taskEventInfo.getTaskInfo()
					.getModifiedDate());
			String taskName = taskEventInfo.getTaskInfo().getName();
			String taskSubject = taskEventInfo.getTaskInfo().getSubject();
			String taskDescription = taskEventInfo.getTaskInfo()
					.getDescription();
			String taskType = String.valueOf(taskEventInfo.getTaskInfo()
					.getType());
			String taskOwner = taskEventInfo.getTaskInfo().getOwner();
			String taskStatus = String.valueOf(taskEventInfo.getTaskInfo()
					.getStatus());
			String taskCreatedTime = String.valueOf(taskEventInfo.getTaskInfo()
					.getCreatedDate());

			values.add(taskId);
			values.add(eventType);
			values.add(modifiedDate);
			values.add(taskName);
			values.add(taskSubject);
			values.add(taskDescription);
			values.add(taskType);
			values.add(taskOwner);
			values.add(taskStatus);
			values.add(taskCreatedTime);

			if (log.isDebugEnabled()) {
				log.debug(String
						.format("******* Human Task Information : [Task Id] %s [Event Type] %s " +
								"[Modified Date] %s [Task Name] %s [Task Subject] %s [Task Description] %s" +
								" [Task Type] %s [Task Owner] %s [Task Status] %s [Task Created Time] %s",
								taskId, eventType, modifiedDate, taskName,
								taskSubject, taskDescription, taskType,
								taskOwner, taskStatus, taskCreatedTime));
			}

			BamDataPublisher.setPublishingData(values, category);  // Publish events to BAM
			
		}

	}
}
