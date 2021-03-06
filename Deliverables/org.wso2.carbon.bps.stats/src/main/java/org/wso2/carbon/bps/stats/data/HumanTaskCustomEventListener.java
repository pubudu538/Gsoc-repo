package org.wso2.carbon.bps.stats.data;

/*
 * This is used to capture Human Task events. All the 
 * information are sent to BAMDataPublisher in order to send to 
 * WSO2 BAM.
 * 
 * */

import java.util.ArrayList;

import org.wso2.carbon.humantask.core.api.event.HumanTaskEventListener;
import org.wso2.carbon.humantask.core.api.event.TaskEventInfo;
import org.wso2.carbon.humantask.core.dao.TaskType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HumanTaskCustomEventListener implements HumanTaskEventListener {

	private static Log log = LogFactory
			.getLog(HumanTaskCustomEventListener.class);

	private static int tenantId = -1234;

	public void onEvent(TaskEventInfo taskEventInfo) {

		// Only for Task type
		if (taskEventInfo.getTaskInfo().getType() == TaskType.TASK) {

			ArrayList<String> values = new ArrayList<String>();
			String category = "humanTaskInfo"; // set the category for the
												// publisher to identify the
												// stream id

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
			String tenant = String.valueOf(tenantId);

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
			values.add(tenant);

			if (log.isDebugEnabled()) {
				log.debug(String
						.format("******* Human Task Information : [Tenant Id] %s [Task Id] %s [Event Type] %s "
								+ "[Modified Date] %s [Task Name] %s [Task Subject] %s [Task Description] %s"
								+ " [Task Type] %s [Task Owner] %s [Task Status] %s [Task Created Time] %s",
								tenant, taskId, eventType, modifiedDate,
								taskName, taskSubject, taskDescription,
								taskType, taskOwner, taskStatus,
								taskCreatedTime));
			}

			BamDataPublisher publisher = new BamDataPublisher();
			publisher.setPublishingData(values, category, tenantId); // Publish
																		// events
																		// to
																		// BAM

		}

	}

	public static void setTenantId(int i) {
		tenantId = i;
	}

}