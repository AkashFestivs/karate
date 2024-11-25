package com.ama.karate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.karate.dao.userBasic.ClassesDao;
import com.ama.karate.dao.userBasic.karateUserDao;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.UserInterfaceService;

@Service
public class UserService implements UserInterfaceService{

    @Autowired ClassesDao icd;
    
    @Autowired karateUserDao isd;

    @Override
    public List<ClassesDto> bringUserClasses(String phoneNo) {
        return icd.bringUserClasses(phoneNo);
    }

    @Override
    public List<StudentDto> bringClassStudents(String phoneNo, int classId) {
        return icd.bringClassStudents(phoneNo, classId);
    }

    @Override
    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        return isd.bringStudentDetails(phoneNo, studentId);
    }

    @Override
    public ResponseDto sendStudentAdmissions(String StudentObj, String phoneNo) {
        return isd.sendStudentAdmissions(StudentObj, phoneNo);
    }

    @Override
    public ResponseDto upsertUserClasses(String classLid, String phoneNo, String beltLid) {
        return isd.upsertUserClasses(classLid, phoneNo, beltLid);
    }
}
