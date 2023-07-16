package com.project.cms.service;

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
import com.project.cms.model.LeaveDetails;
import com.project.cms.model.SalaryInfo;
import com.project.cms.model.SemInfo;
import com.project.cms.model.Student;
import com.project.cms.model.StudentAttendanceInfo;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeachersInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;
import com.project.cms.repository.StudentRepository;
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
			if (user.getRole().equals("teacher") || user.getRole().equals("student")) {
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

	public Teacher addTeacher(TeachersInfo teacherInfo, HttpServletRequest httpServletRequest) {

		List<String> allUser = getAllUser().stream().map(x -> x.getAuthorization()).collect(Collectors.toList());

		String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		Boolean authorized = allUser.contains(header);
		
		if (authorized) {
			
			List<SalaryInfo> salary = teacherInfo.getSalaryInfo().stream()
					.map(x -> SalaryInfo.builder().bankAccount(x.getBankAccount()).bankName(x.getBankName())
							.deduction(x.getDeduction()).earnings(x.getEarnings()).grossSalary(x.getGrossSalary())
							.month(x.getMonth()).salaryDate(x.getSalaryDate()).netSalary(x.getNetSalary())
							.totalDeduction(x.getDeduction()).build())
					.collect(Collectors.toList());

			AttendanceInfo attendanceInfoLocal = teacherInfo.getAttendanceInfo().get(0);

			LeaveDetails leaveDetails = LeaveDetails.builder().annualLeave(teacherInfo.getLeaveInfo().getAnnualLeave())
					.annualLeaveUsed(teacherInfo.getLeaveInfo().getAnnualLeaveUsed())
					.otherLeave(teacherInfo.getLeaveInfo().getOtherLeave())
					.otherLeaveUsed(teacherInfo.getLeaveInfo().getOtherLeaveUsed())
					.sickLeave(teacherInfo.getLeaveInfo().getSickLeave())
					.sickLeaveUsed(teacherInfo.getLeaveInfo().getSickLeaveUsed()).build();

			AttendanceInfo attendanceInfo = AttendanceInfo.builder().jan(attendanceInfoLocal.getJan())
					.feb(attendanceInfoLocal.getFeb()).mar(attendanceInfoLocal.getMar())
					.apr(attendanceInfoLocal.getApr()).may(attendanceInfoLocal.getMay())
					.june(attendanceInfoLocal.getJune()).build();


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
					.fatherName(teacherInfo.getDashboardInfo().getFatherName()).leaveDetails(leaveDetails)
					.attendanceInfo(attendanceInfo).salaryInfo(salary).build();

			return teacherRepo.save(teacher);

		} else {
			throw new ApplicationContextException("User is not authorized to do the transaction");
		}
	}

	public Teacher getTeacher(String email) {
		return teacherRepo.findByEmail(email);
	}

	public Student addStudent(StudentInfo studentInfo, HttpHeaders httpHeaders) {

		UserRegistration user = getUser(studentInfo.getDashboardInfo().getEmail());

		String auth = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);

		if (auth.equals(user.getAuthorization())) {

			StudentAttendanceInfo attendanceInfoLocal = studentInfo.getAttendanceInfo().get(0);

			StudentAttendanceInfo attendanceInfo = StudentAttendanceInfo.builder().jan(attendanceInfoLocal.getJan())
					.feb(attendanceInfoLocal.getFeb()).mar(attendanceInfoLocal.getMar())
					.apr(attendanceInfoLocal.getApr()).may(attendanceInfoLocal.getMay())
					.june(attendanceInfoLocal.getJune()).build();

			SemInfo semInfo = studentInfo.getSemDetails().get(0);

			SemInfo sem = SemInfo.builder().sem1(semInfo.getSem1()).sem2(semInfo.getSem2()).sem3(semInfo.getSem3())
					.sem4(semInfo.getSem4()).sem5(semInfo.getSem5()).sem6(semInfo.getSem6()).build();

			Student teacher = Student.builder().name(studentInfo.getDashboardInfo().getName())
					.dob(studentInfo.getDashboardInfo().getDob()).gender(studentInfo.getDashboardInfo().getGender())
					.mobile(studentInfo.getDashboardInfo().getMobile()).email(studentInfo.getDashboardInfo().getEmail())
					.address(studentInfo.getDashboardInfo().getAddress())
					.fatherName(studentInfo.getDashboardInfo().getFatherName()).attendanceInfo(attendanceInfo)
					.semInfo(sem).build();

			return studentRepo.save(teacher);

		} else {
			throw new ApplicationContextException("User is not authorized to do the transaction");
		}

	}

	public Student getStudent(String email) {
		return studentRepo.findByEmail(email);
	}

}
