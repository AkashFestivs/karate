package com.ama.karate.dao.basic;

import java.util.ArrayList;
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

    ResponseDto response = new ResponseDto();

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
            String SQL = "SELECT DISTINCT pu.id AS student_lid, pu.full_name AS studentName, bm.colour AS studentBelt, pu.profile_url AS studentProfileUrl, " + 
                            " cm.name AS className, pu.phone_no AS phoneNo " + 
                            " FROM public.user pu " + 
                            " INNER JOIN user_class uc ON pu.id = uc.user_lid " + 
                            " INNER JOIN roles rr ON rr.id = pu.role_id " + 
                            " INNER JOIN class_master cm ON uc.class_lid = cm.id " + 
                            " INNER JOIN belt_master bm ON bm.id = uc.belt_lid " + 
                            " WHERE rr.abbr = 'STD';";

            return jt.query(SQL, new BeanPropertyRowMapper<>(StudentDto.class));
        } catch (DataAccessException e) {
            return new ArrayList<StudentDto>();
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
            response.setStatusCode(200);
            response.setMessage("Insert succeefully");
            return response;
        } catch (DataAccessException | JsonProcessingException e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    public ResponseDto sendClassMaster(String classObj, String phoneNo) {
        try {
            String sql = "SELECT upsert_class_master(?, ?)";
    
            String responseObj = jt.queryForObject(sql, String.class,classObj, phoneNo);
    
            response.setStatusCode(201);
            response.setMessage(responseObj);
            return response;
        } catch (DataAccessException e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}

