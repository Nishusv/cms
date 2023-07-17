package com.project.cms.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.project.cms.model.AttendanceInfo;
import com.project.cms.model.AttendanceResponseInfo;
import com.project.cms.model.DashboardInfo;
import com.project.cms.model.LeaveDetails;
import com.project.cms.model.LeaveResponseDetails;
import com.project.cms.model.SalaryInfo;
import com.project.cms.model.SalaryResponseInfo;
import com.project.cms.model.SemInfo;
import com.project.cms.model.SemResponseInfo;
import com.project.cms.model.Student;
import com.project.cms.model.StudentAttendanceInfo;
import com.project.cms.model.StudentAttendanceResponseInfo;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;
import com.project.cms.repository.AttendanceInfoRepository;
import com.project.cms.repository.LeaveDetailsRepository;
import com.project.cms.repository.SalariInfoRepository;
import com.project.cms.repository.StudentAttendanceInfoRepository;
import com.project.cms.repository.StudentRepository;
import com.project.cms.repository.StudentSemInfoRepository;
import com.project.cms.repository.TeacherRepository;
import com.project.cms.repository.UserRepository;

@Service
public class CmsServiceImpl implements CmsService {

	@Autowired
	private UserRepository userRepository;

	private Encoder encode = Base64.getEncoder();

	private Decoder decoder = Base64.getDecoder();

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private AttendanceInfoRepository attendanceRepo;

	@Autowired
	private SalariInfoRepository salaryInfoRepo;

	@Autowired
	private LeaveDetailsRepository leaveDetailsRepository;
	
	@Autowired
	private StudentAttendanceInfoRepository studentAttendanceInfoRepository;
	
	@Autowired
	private StudentSemInfoRepository semInfoRepository;

	@Override
	public UserRegistration saveUser(User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			UserRegistration userRegistration = new UserRegistration();
			userRegistration.setEmail(user.getEmail());
			userRegistration.setFullName(user.getFullName());
			userRegistration.setPassword(encode.encodeToString(user.getPassword().getBytes()));
			userRegistration.setIsActive(true);
			String concatCode = user.getFullName() + user.getEmail();
			String authorization = encode.encodeToString(concatCode.getBytes());
			userRegistration.setAuthorization(authorization);
			if (user.getRole().equals("teacher") || user.getRole().equals("student")
					|| user.getRole().equals("admin")) {
				userRegistration.setRole(user.getRole());
			} else {
				throw new ApplicationContextException("role should be either teacher or student");
			}
			return userRepository.save(userRegistration);
		} else {
			throw new ApplicationContextException("password and confirm password should be identical");
		}

	}

	public UserRegistration getUser(String emailId) {
		return userRepository.findByEmail(emailId);
	}

	public List<UserRegistration> getAllUser() {
		return userRepository.findAll();
	}

	public String deleteUser(String emailId) {
		UserRegistration dbResponse = userRepository.findByEmail(emailId);
		userRepository.delete(dbResponse);
		return "user removed successfully";

	}

	public UserRegistration changePassword(User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			UserRegistration dbResponse = userRepository.findByEmail(user.getEmail());
			String dbPassword = new String(decoder.decode(dbResponse.getPassword()));
			if (dbPassword.equals(user.getOldPassword())) {
				Encoder encode = Base64.getEncoder();
				String password = encode.encodeToString(user.getPassword().getBytes());
				dbResponse.setPassword(password);
				return userRepository.save(dbResponse);
			} else {
				throw new ApplicationContextException("Old password is not matching with existing password");
			}
		} else {
			throw new ApplicationContextException("password and confirm password should be identical");
		}

	}

	@Override
	public UserRegistration loginUser(String email, String password) {
		UserRegistration user = getUser(email);
		String decodePassword = new String(decoder.decode(user.getPassword()));
		if (decodePassword.equals(password)) {
			if (user.getIsActive().equals(Boolean.TRUE)) {
				return user;
			} else {
				throw new ApplicationContextException("User is inactive please Signup again");
			}
		} else {
			throw new ApplicationContextException("Password is incorrect");
		}
	}

	public TeacherInfo addTeacher(TeacherInfo teacherInfo, HttpServletRequest httpServletRequest) {

		List<String> allUser = getAllUser().stream().map(x -> x.getAuthorization()).collect(Collectors.toList());

		String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		Boolean authorized = allUser.contains(header);

		if (authorized) {

			AttendanceResponseInfo attendanceResponseInfo = teacherInfo.getAttendanceInfo().get(0);
			
			AttendanceInfo attendanceInfoLocal = AttendanceInfo.builder().jan(attendanceResponseInfo.getJan())
					.feb(attendanceResponseInfo.getFeb()).mar(attendanceResponseInfo.getMar())
					.apr(attendanceResponseInfo.getApr()).may(attendanceResponseInfo.getMay())
					.june(attendanceResponseInfo.getJune()).build();

			Teacher teacher = Teacher.builder().name(teacherInfo.getDashboardInfo().getName())
					.dob(teacherInfo.getDashboardInfo().getDob()).gender(teacherInfo.getDashboardInfo().getGender())
					.qualification(teacherInfo.getDashboardInfo().getQualification())
					.designation(teacherInfo.getDashboardInfo().getDesignation())
					.mobile(teacherInfo.getDashboardInfo().getMobile()).email(teacherInfo.getDashboardInfo().getEmail())
					.emergencyNumber(teacherInfo.getDashboardInfo().getEmergencyNumber())
					.address(teacherInfo.getDashboardInfo().getAddress())
					.lastWorkingCompany(teacherInfo.getDashboardInfo().getLastWorkingCompany())
					.presentCompany(teacherInfo.getDashboardInfo().getPresentWorkingCompany())
					.joiningDate(teacherInfo.getDashboardInfo().getJoiningDate())
					.fatherName(teacherInfo.getDashboardInfo().getFatherName()).build();

			AttendanceInfo attendanceInfo = AttendanceInfo.builder().jan(attendanceInfoLocal.getJan())
					.feb(attendanceInfoLocal.getFeb()).mar(attendanceInfoLocal.getMar())
					.apr(attendanceInfoLocal.getApr()).may(attendanceInfoLocal.getMay())
					.june(attendanceInfoLocal.getJune()).teacher(teacher).build();

			Teacher teacherResponse = teacherRepo.save(teacher);

			AttendanceInfo attendanceInfoResponse = attendanceRepo.save(attendanceInfo);

			List<SalaryInfo> salary = teacherInfo.getSalaryInfo().stream()
					.map(x -> SalaryInfo.builder().bankAccount(x.getBankAccount()).bankName(x.getBankName())
							.deduction(x.getDeduction()).earnings(x.getEarnings()).grossSalary(x.getGrossSalary())
							.month(x.getMonth()).salaryDate(x.getSalaryDate()).netSalary(x.getNetSalary())
							.totalDeduction(x.getDeduction()).teacher(teacher).build())
					.collect(Collectors.toList());

			List<SalaryInfo> salaryInfoResponse = salaryInfoRepo.saveAll(salary);

			LeaveDetails leaveDetails = LeaveDetails.builder().annualLeave(teacherInfo.getLeaveInfo().getAnnualLeave())
					.annualLeaveUsed(teacherInfo.getLeaveInfo().getAnnualLeaveUsed())
					.otherLeave(teacherInfo.getLeaveInfo().getOtherLeave())
					.otherLeaveUsed(teacherInfo.getLeaveInfo().getOtherLeaveUsed())
					.sickLeave(teacherInfo.getLeaveInfo().getSickLeave())
					.sickLeaveUsed(teacherInfo.getLeaveInfo().getSickLeaveUsed()).teacher(teacher).build();

			LeaveDetails leaveResponse = leaveDetailsRepository.save(leaveDetails);

			return getTeacherDetail(teacherResponse, attendanceInfoResponse, salaryInfoResponse, leaveResponse);

		} else {
			throw new ApplicationContextException("User is not authorized to do the transaction");
		}
	}

	private TeacherInfo getTeacherDetail(Teacher teacherResponse, AttendanceInfo attendanceInfoResponse,
			List<SalaryInfo> salaryInfoResponse, LeaveDetails leaveResponse) {

		DashboardInfo dashBoard = DashboardInfo.builder().name(teacherResponse.getName()).dob(teacherResponse.getDob())
				.gender(teacherResponse.getGender()).qualification(teacherResponse.getQualification())
				.designation(teacherResponse.getDesignation()).mobile(teacherResponse.getMobile())
				.email(teacherResponse.getEmail()).emergencyNumber(teacherResponse.getEmergencyNumber())
				.address(teacherResponse.getAddress()).lastWorkingCompany(teacherResponse.getLastWorkingCompany())
				.presentWorkingCompany(teacherResponse.getPresentCompany())
				.joiningDate(teacherResponse.getJoiningDate()).fatherName(teacherResponse.getFatherName()).build();
		
		LeaveResponseDetails leaveDetails = LeaveResponseDetails.builder().annualLeave(leaveResponse.getAnnualLeave())
				.annualLeaveUsed(leaveResponse.getAnnualLeaveUsed()).otherLeave(leaveResponse.getOtherLeave())
				.otherLeaveUsed(leaveResponse.getOtherLeaveUsed()).sickLeave(leaveResponse.getSickLeave())
				.sickLeaveUsed(leaveResponse.getSickLeaveUsed()).build();
		
		AttendanceResponseInfo attendanceInfo = AttendanceResponseInfo.builder().jan(attendanceInfoResponse.getJan())
				.feb(attendanceInfoResponse.getFeb()).mar(attendanceInfoResponse.getMar())
				.apr(attendanceInfoResponse.getApr()).may(attendanceInfoResponse.getMay())
				.june(attendanceInfoResponse.getJune()).build();
		
		List<SalaryResponseInfo> salaryResponseInfo = salaryInfoResponse.stream().map(x -> SalaryResponseInfo.builder()
				.bankAccount(x.getBankAccount())
				.bankName(x.getBankName()).deduction(x.getDeduction()).earnings(x.getEarnings()).grossSalary(x.getGrossSalary())
				.month(x.getMonth()).name(x.getName()).netSalary(x.getNetSalary()).salaryDate(x.getSalaryDate()).netSalary(x.getNetSalary()).build()).collect(Collectors.toList());
				
		

		return TeacherInfo.builder().id(teacherResponse.getId().toString()).dashboardInfo(dashBoard)
				.leaveInfo(leaveDetails).attendanceInfo(Arrays.asList(attendanceInfo))
				.salaryInfo(salaryResponseInfo).build();

	}

	public TeacherInfo getTeacher(String email) {
		
		Teacher teachResponse = teacherRepo.findByEmail(email);
		AttendanceInfo attendanceInfo = attendanceRepo.findByTeacher(teachResponse.getId());
		List<SalaryInfo> salaryInfo = salaryInfoRepo.findByTeacher(teachResponse.getId());
		LeaveDetails leaveDetails = leaveDetailsRepository.findByTeacher(teachResponse.getId());
		return getTeacherDetail(teachResponse, attendanceInfo, salaryInfo, leaveDetails);
		
	}

	public StudentInfo addStudent(StudentInfo studentInfo, HttpServletRequest httpServletRequest) {

		List<String> allUser = getAllUser().stream().map(x -> x.getAuthorization()).collect(Collectors.toList());

		String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		Boolean authorized = allUser.contains(header);

		if (authorized) {
			
			StudentAttendanceResponseInfo attendanceInfoLocal = studentInfo.getAttendanceInfo().get(0);

			StudentAttendanceInfo attendanceInfo = StudentAttendanceInfo.builder().jan(attendanceInfoLocal.getJan())
					.feb(attendanceInfoLocal.getFeb()).mar(attendanceInfoLocal.getMar())
					.apr(attendanceInfoLocal.getApr()).may(attendanceInfoLocal.getMay())
					.june(attendanceInfoLocal.getJune()).build();

			SemResponseInfo semInfo = studentInfo.getSemDetails().get(0);

			SemInfo sem = SemInfo.builder().sem1(semInfo.getSem1()).sem2(semInfo.getSem2()).sem3(semInfo.getSem3())
					.sem4(semInfo.getSem4()).sem5(semInfo.getSem5()).sem6(semInfo.getSem6()).build();

			Student teacher = Student.builder().name(studentInfo.getDashboardInfo().getName())
					.dob(studentInfo.getDashboardInfo().getDob()).gender(studentInfo.getDashboardInfo().getGender())
					.mobile(studentInfo.getDashboardInfo().getMobile()).email(studentInfo.getDashboardInfo().getEmail())
					.address(studentInfo.getDashboardInfo().getAddress())
					.fatherName(studentInfo.getDashboardInfo().getFatherName()).build();

			 Student studentRecord = studentRepo.save(teacher);
			 StudentAttendanceInfo attendanceResponse = studentAttendanceInfoRepository.save(attendanceInfo);
			 SemInfo semInfoResponse = semInfoRepository.save(sem);
			 
			 return getStudentInfo(studentRecord,attendanceResponse,semInfoResponse);

		} else {
			throw new ApplicationContextException("User is not authorized to do the transaction");
		}

	}

	private StudentInfo getStudentInfo(Student studentRecord, StudentAttendanceInfo attendanceResponse,
			SemInfo semInfoResponse) {
		StudentAttendanceResponseInfo attendanceInfo = StudentAttendanceResponseInfo.builder().jan(attendanceResponse.getJan())
				.feb(attendanceResponse.getFeb()).mar(attendanceResponse.getMar()).apr(attendanceResponse.getApr())
				.may(attendanceResponse.getMay()).june(attendanceResponse.getJune()).build();
		
		DashboardInfo dashBoard = DashboardInfo.builder().name(studentRecord.getName()).dob(studentRecord.getDob())
				.gender(studentRecord.getGender()).mobile(studentRecord.getMobile())
				.email(studentRecord.getEmail())
				.address(studentRecord.getAddress()).fatherName(studentRecord.getFatherName()).build(); 
		
		SemResponseInfo semInfo = SemResponseInfo.builder().sem1(semInfoResponse.getSem1())
				.sem2(semInfoResponse.getSem2()).sem3(semInfoResponse.getSem3()).sem4(semInfoResponse.getSem4()).sem5(semInfoResponse.getSem5())
				.sem6(semInfoResponse.getSem6()).build();
		
		return StudentInfo.builder().attendanceInfo(Arrays.asList(attendanceInfo)).dashboardInfo(dashBoard)
				.id(studentRecord.getId().toString()).semDetails(Arrays.asList(semInfo)).build();
		}

	public StudentInfo getStudent(String email) {
		
		Student studentRecord = studentRepo.findByEmail(email);
		 StudentAttendanceInfo attendanceResponse = studentAttendanceInfoRepository.findByStudent(studentRecord.getId());
		 SemInfo semInfoResponse = semInfoRepository.findByStudent(studentRecord.getId());
		 
		 return getStudentInfo(studentRecord,attendanceResponse,semInfoResponse);

	}

	@Override
	public List<UserRegistration> getAll() {
		return userRepository.findAll();
	}

}
