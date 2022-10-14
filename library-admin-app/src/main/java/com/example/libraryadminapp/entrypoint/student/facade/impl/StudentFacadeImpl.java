package com.example.libraryadminapp.entrypoint.student.facade.impl;

import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.service.StudentService;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.CoursePaymentInfoResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import com.example.libraryadminapp.entrypoint.student.facade.StudentFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;


    @Override
    public void createStudent(final StudentCreationRequestDTO studentCreationRequestDTO) {

        final StudentCreationRequestModel studentCreationRequestModel = buildStudentCreationRequestModel(studentCreationRequestDTO);
        studentService.createStudent(studentCreationRequestModel);
    }

    @Override
    public void verifyStudentMobileNumber(final String studentMobileNumber) throws IOException {

        studentService.verifyStudentMobileNumber(studentMobileNumber);
    }

    @Override
    public Integer assignCourseToStudent(final String courseName, final String studentName, final Long courseSlotId) {

        return studentService.assignCourseToStudent(courseName, studentName, courseSlotId);
    }

    @Override
    public Integer assignCoursePaperToStudent(final String coursePaperName, final String studentName) {

        return studentService.assignCoursePaperToStudent(coursePaperName, studentName);
    }

    @Override
    public List<StudentCourseResponseDTO> getAllCoursesBookings(final String studentName) {

        return studentService.getAllCoursesBookings(studentName)
                .stream()
                .map(studentCourseResponseModel ->
                    StudentCourseResponseDTO
                            .builder()
                            .courseName(studentCourseResponseModel.getCourseName())
                            .subjectName(studentCourseResponseModel.getSubjectName())
                            .professorName(studentCourseResponseModel.getProfessorName())
                            .pricePerSemester(studentCourseResponseModel.getPricePerSemester())
                            .pricePerMonth(studentCourseResponseModel.getPricePerMonth())
                            .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(final String studentName) {

        return studentService.getAllCoursePapersBookings(studentName)
                .stream()
                .map(studentCourseResponseModel ->
                        StudentCoursePaperResponseDTO
                                .builder()
                                .coursePaperName(studentCourseResponseModel.getCoursePaperName())
                                .subjectName(studentCourseResponseModel.getSubjectName())
                                .professorName(studentCourseResponseModel.getProfessorName())
                                .price(studentCourseResponseModel.getPrice())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> getAllCoursesPaymentInfo() {
        return studentService.getAllCoursesPaymentInfo()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                        .studentName(paymentInfoResponseModel.getStudentName())
                        .courseName(paymentInfoResponseModel.getCourseName())
                        .build());
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> getAllCoursePapersPaymentInfo() {

        return studentService.getAllCoursePapersPaymentInfo()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                        .studentName(paymentInfoResponseModel.getStudentName())
                        .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                        .build());
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> searchByPaymentInfoNumber(Integer paymentInfoNumber) {

        return studentService.searchByPaymentInfoNumber(paymentInfoNumber)
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                        .studentName(paymentInfoResponseModel.getStudentName())
                        .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                        .courseName(paymentInfoResponseModel.getCourseName())
                        .build());
    }

    private StudentCreationRequestModel buildStudentCreationRequestModel(final StudentCreationRequestDTO studentCreationRequestDTO) {

        return StudentCreationRequestModel
                .builder()
                .studentName(studentCreationRequestDTO.getStudentName())
                .mobileNumber(studentCreationRequestDTO.getMobileNumber())
                .academicYear(studentCreationRequestDTO.getAcademicYear())
                .facultyName(studentCreationRequestDTO.getFacultyName())
                .build();
    }
}
