package com.ama.karate.interfaceService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;

@Service
public interface UserInterfaceService {

    List<ClassesDto> bringUserClasses(String phoneNo);

    List<StudentDto> bringClassStudents(String phoneNo, int classId);

    List<StudentDto> bringStudentDetails(String phoneNo, int studentId);

    ResponseDto sendStudentAdmissions(String StudentObj, String phoneNo); 
    
}
