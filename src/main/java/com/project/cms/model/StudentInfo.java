package com.project.cms.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentInfo {

	private String id;
	
	private DashboardInfo dashboardInfo;
	
	private List<SemInfo> semDetails;
	
	private List<StudentAttendanceInfo> attendanceInfo;
	
}
