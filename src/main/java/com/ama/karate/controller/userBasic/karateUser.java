package com.ama.karate.controller.userBasic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.UserInterfaceService;

@RestController
public class karateUser {

    @Autowired UserInterfaceService iis;
    
    @PostMapping("/studnet-details")
    public ResponseEntity<String> studentDetails(@RequestParam int studentId, @RequestAttribute String phoneNo) {

        try {
            List<StudentDto> response = iis.bringStudentDetails(phoneNo, studentId);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-studnet")
    public ResponseEntity<String> addStudent(@RequestBody String jsonObj, @RequestAttribute String phoneNo) {

        try {

            ResponseDto response = iis.sendStudentAdmissions(jsonObj, phoneNo);
            return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
