package com.ama.karate.dao.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.BeltDto;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.MasterInterfaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MasterDao implements MasterInterfaceService{

    @Autowired JdbcTemplate jt;

    @Autowired ObjectMapper om;

    ResponseDto responseDto = new ResponseDto();

    //Get List of classes
    @Override
    public List<ClassesDto> bringClassList() {
        try {
            String SQL = "SELECT id AS classesLid, name AS classesName, address AS classesAddress, city AS classCity, fees AS classesFees, " +
                         "admission_fees AS admissionFees, is_main AS isMain " +
                         "FROM class_master cm WHERE cm.active = TRUE;";

            return jt.query(SQL, new BeanPropertyRowMapper<>(ClassesDto.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<BeltDto> bringBeltList() {
        try {
            String SQL = "SELECT colour, fees, level, portion, description FROM belt_master WHERE active = true;";
    
            return jt.query(SQL, new BeanPropertyRowMapper<>(BeltDto.class));
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<StudentDto> bringAllStudents() {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<StudentDto>();
        }
    }

    @Override
    public ResponseDto insertClasses(String jsonObj) {

        try {
            Map<String, Object> classMap = om.readValue(jsonObj, Map.class);

            // Prepare SQL and parameters
            String sql = "INSERT INTO class_master (name, address, city, is_main, start_date, timing, fees, admission_fees) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?,)";
            
            Object[] params = new Object[]{
                classMap.get("name"),
                classMap.get("address"),
                classMap.get("city"),
                classMap.get("isMain"),
                classMap.get("startDate"),
                classMap.get("timing"),
                classMap.get("fees"),
                classMap.get("admissionFees")
            };

            jt.update(sql, params);
            responseDto.setStatusCode(200);
            responseDto.setMessage("Insert succeefully");
            return responseDto;
        } catch (DataAccessException | JsonProcessingException e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage(e.getMessage());
            e.printStackTrace();
            return responseDto;
        }
    }

    public ResponseDto insertBelt(String jsonObj) {
        try {
            Map<String, Object> beltMap = om.readValue(jsonObj, Map.class);
        

            String sql = "INSERT INTO belt_master (colour, fees, level, portion, description) " +
                         "VALUES (?, ?, ?, ?, ?)";
            
            Object[] params = new Object[]{
                beltMap.get("colour"),
                beltMap.get("fees"),
                beltMap.get("level"),
                beltMap.get("portion"),
                beltMap.get("description"),
            };

            jt.update(sql, params);
            responseDto.setStatusCode(200);
            responseDto.setMessage("Insert succeefully");
            return responseDto;
        } catch (DataAccessException | JsonProcessingException e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage(e.getMessage());
            e.printStackTrace();
            return responseDto;
        }
    }
}

