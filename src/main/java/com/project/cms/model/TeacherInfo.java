package com.project.cms.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherInfo {

	private String id;
	
	private DashboardInfo dashboardInfo;
	
	private LeaveDetails leaveInfo;
	
	private List<AttendanceInfo> attendanceInfo;
	
	private List<SalaryInfo> salaryInfo;
	
}
