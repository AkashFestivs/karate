package com.ama.karate.dao.userBasic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;

@Service
public class karateUserDao{

    @Autowired
    JdbcTemplate jt;

    ResponseDto response = new ResponseDto();
    public List<StudentDto> bringStudentDetails(String phoneNo, int studentId) {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    public ResponseDto sendStudentAdmissions(String studentObj, String phoneNo) {
        try {
            String sql = "SELECT student_creation(?, ?)";
    
            String responseObj = jt.queryForObject(sql, String.class,studentObj, phoneNo);
    
            response.setStatusCode(201);
            response.setMessage(responseObj);
            return response;
        } catch (DataAccessException e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ResponseDto upsertUserClasses(String classLid, String phoneNo, String beltLid) {
        try {
            String sql = "INSERT INTO user_class (user_lid, class_lid, belt_lid, created_by, updated_by)" +
                                " VALUES (" +
                                " (SELECT id FROM public.user WHERE phone_no = ?), " +
                                " ?, " +
                                " (SELECT belt_lid FROM user_belt_record WHERE user_lid = (SELECT id FROM public.user WHERE phone_no = ?) ORDER BY exam_date DESC LIMIT 1), " +
                                " ?, " +
                                " ?" +
                                " )" +
                                " ON CONFLICT (user_lid, class_lid, belt_lid)" +
                                " DO UPDATE SET " +
                                " updated_by = EXCLUDED.updated_by," +
                                " updated_at = CURRENT_TIMESTAMP;";
    
            int responseObj = jt.update(sql, phoneNo, classLid, phoneNo, phoneNo, phoneNo);
    
            response.setStatusCode(200);
            response.setMessage("success");
            return response;
        } catch (DataAccessException e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
    

}
