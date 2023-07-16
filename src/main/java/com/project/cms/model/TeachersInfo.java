package com.project.cms.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeachersInfo {
	
	private DashboardInfo2 dashboardInfo;
	
	private LeaveDetails leaveInfo;
	
	private List<AttendanceInfo> attendanceInfo;
	
	private List<SalaryInfo> salaryInfo;

}
