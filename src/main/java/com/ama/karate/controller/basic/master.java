package com.ama.karate.controller.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.BeltDto;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.MasterInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Master {

    @Autowired MasterInterfaceService mis;

    @Autowired ObjectMapper om;

    @PostMapping("/classes-list")
    public ResponseEntity<String> classesList() {
        try {
            List<ClassesDto> response = mis.bringClassList();
            String jsonResponse = om.writeValueAsString(response);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/belt-list")
    public ResponseEntity<String> beltList() {

        try {
            List<BeltDto> response = mis.bringBeltList();

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/all-students")
    public ResponseEntity<String> allStudent() {

        try {
            List<StudentDto> response = mis.bringAllStudents();

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("JsonProcessingException", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-belts")
    public ResponseEntity<String> addBelts(@RequestBody String JsonObj) {

        try {
            ResponseDto response = mis.insertBelt(JsonObj);

            return ResponseEntity.status(response.getStatusCode()).body(response.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/class-master")
    public ResponseEntity<String> classMaster(@RequestBody String classObj, @RequestAttribute String phoneNo) {

        try {
            ResponseDto response = mis.sendClassMaster(classObj, phoneNo);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
