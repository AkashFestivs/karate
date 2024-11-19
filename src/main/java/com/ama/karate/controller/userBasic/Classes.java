package com.ama.karate.controller.userBasic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.UserInterfaceService;
import static com.ama.karate.utils.JsonKeyService.getJsonKeys;

@RestController
public class Classes {

    @Autowired UserInterfaceService iis;

    @PostMapping("/user-classes")
    public ResponseEntity<String> userClasses(@RequestAttribute String phoneNo) {

        try {
            List<ClassesDto> response = iis.bringUserClasses(phoneNo);
            System.out.println("Phone number: " + phoneNo);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/class-studnets")
    public ResponseEntity<String> classStudnets(@RequestParam int classId, @RequestAttribute String phoneNo) {

        try {
            List<StudentDto> response = iis.bringClassStudents(phoneNo, classId);

            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upsert-user-class")
    public ResponseEntity<String> upsertUserClass(@RequestBody String josnBody, @RequestAttribute String phoneNo) {

        try {

            List<Map<String, String>> map = getJsonKeys(josnBody, "class_lid", "belt_lid");

            map.forEach(m -> {

                iis.upsertUserClasses(m.get("class_lid"), phoneNo, m.get("belt_lid"));

            });

            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
