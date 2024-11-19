package com.ama.karate.dao.userBasic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.StudentDto;

@Service
public class ClassesDao{

    @Autowired
    JdbcTemplate jt;

    ResponseDto response = new ResponseDto();

    //  Get List of classes for particular instructors
    public List<ClassesDto> bringUserClasses(String phoneNo) {
        try {
            
            String SQL = "SELECT " + 
                            "     cm.name AS classesName," + 
                            "     ARRAY_AGG(DISTINCT instructor.full_name) AS instructorName," + 
                            "     cm.address AS classesAddress," + 
                            "     cm.timing AS classesTiming, " + 
                            "     COUNT(DISTINCT student.id) AS totalStudents," + 
                            "     cm.id AS classesLid" + 
                            " FROM " + 
                            "     user_class uc" + 
                            " JOIN " + 
                            "     class_master cm ON uc.class_lid = cm.id" + 
                            " JOIN " + 
                            "     public.user instructor ON uc.user_lid = instructor.id" + 
                            " JOIN " + 
                            "     user_class student_class ON student_class.class_lid = cm.id" + 
                            " JOIN " + 
                            "     public.user student ON student_class.user_lid = student.id" + 
                            " WHERE " + 
                            "     instructor.phone_no = ?" + 
                            " GROUP BY " + 
                            "     cm.name, cm.address, cm.id;";

            return jt.query(SQL, new BeanPropertyRowMapper<>(ClassesDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<StudentDto> bringClassStudents(String phoneNo, int classId) {
        try {
            String SQL = "SELECT student.full_name AS studentName, bm.colour AS studentBelt, student.profile_url AS studentProfileUrl, student.id AS student_lid " + 
                                " FROM public.user student" + 
                                " INNER JOIN user_class uc ON uc.user_lid = student.id" + 
                                " INNER JOIN belt_master bm ON uc.belt_lid = bm.id" + 
                                " INNER JOIN roles ur ON student.role_id = ur.id" + 
                                " WHERE uc.class_lid = ? AND ur.abbr = 'STD';";
            return jt.query(SQL, new BeanPropertyRowMapper<>(StudentDto.class), classId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<StudentDto> bringClassInstructor(String phoneNo, int classId) {
        try {
            String SQL = "SELECT instructor.full_name AS instructorName, bm.colour AS instructorBelt, instructor.profile_url AS instructorProfileUrl, instructor.id AS instructorlid "
                            + " FROM public.user instructor"
                            + " INNER JOIN user_class uc ON uc.user_lid = instructor.id"
                            + " INNER JOIN belt_master bm ON uc.belt_lid = bm.id"
                            + " INNER JOIN roles ur ON instructor.role_id = ur.id"
                            + " WHERE uc.class_lid = ? AND ur.abbr = 'INS';";
            return jt.query(SQL, new BeanPropertyRowMapper<>(StudentDto.class), classId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
