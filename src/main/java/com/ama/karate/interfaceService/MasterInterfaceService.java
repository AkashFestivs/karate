package com.ama.karate.interfaceService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ama.karate.dto.BeltDto;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;

@Service
public interface  MasterInterfaceService {

    List<ClassesDto> bringClassList();

    List<BeltDto> bringBeltList();

    List<StudentDto> bringAllStudents();

    ResponseDto insertClasses(String JsonObj);

    ResponseDto insertBelt(String jsonObj);


}
