package com.ftn.nc.NCBackend.camunda.dto;

public class TaskDTO {

	private String taskId;
	
	private String processInstanceId;

	private String assignee;
	
	private String taskName;

	public TaskDTO() {
		super();
	}

	public TaskDTO(String taskId, String processInstanceId, String assignee, String taskName) {
		super();
		this.taskId = taskId;
		this.processInstanceId = processInstanceId;
		this.assignee = assignee;
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}
