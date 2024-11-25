package com.ama.karate.dao.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.SessionDto;

@Service
public class loginDao {

    @Autowired JdbcTemplate jt;

    //  To fetch the password by phone number
    public AuthDto userPassword(String phoneNo) {
        System.out.println("user phone in dao : 0"+phoneNo);
        try {
            String SQL = "SELECT password, phone_no AS phoneNo FROM public.user WHERE phone_no = ? AND active = TRUE";
    
            return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(AuthDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new AuthDto();
        }
    }

    public SessionDto sessionData(String phoneNo){
        try {
            String roleNameSQL = "SELECT rr.abbr FROM public.user pu INNER JOIN roles rr ON rr.id = pu.role_id WHERE pu.phone_no = ? ";
            String roleName = jt.queryForObject(roleNameSQL, String.class, phoneNo);

            System.out.println("roleName: " + roleName);
            if(roleName != null && roleName.equalsIgnoreCase("ADM")){

                String SQL = "SELECT DISTINCT   " + 
                                "     pu.id AS userLid,   " + 
                                "     pu.phone_no AS phoneNo,   " + 
                                "     rr.name AS userRole,   " + 
                                "     ut.title,   " + 
                                "     pu.profile_url AS profileUrl,  " + 
                                "     'ADMIN BELT' AS userBelt,  " + 
                                "     pu.email,  " + 
                                "     pu.full_name AS fullName,  " + 
                                "     pu.address," + 
                                "     (SELECT COUNT(cm.*) FROM class_master cm ) AS total_classes," + 
                                "     (SELECT COUNT(pu_inner.id) " + 
                                "      FROM public.user pu_inner" + 
                                "      INNER JOIN roles rr_inner ON rr_inner.id = pu_inner.role_id" + 
                                "      WHERE pu_inner.active = TRUE AND rr_inner.abbr = 'INS') AS totalInstructors," + 
                                "     (SELECT COUNT(pu_inner.id) " + 
                                "      FROM public.user pu_inner" + 
                                "      INNER JOIN roles rr_inner ON rr_inner.id = pu_inner.role_id" + 
                                "      WHERE pu_inner.active = TRUE AND rr_inner.abbr = 'STD') AS totalStudents" +
                                " FROM   " + 
                                "     public.user pu   " + 
                                " INNER JOIN   " + 
                                "     roles rr ON rr.id = pu.role_id   " + 
                                " LEFT JOIN   " + 
                                "     user_title ut ON ut.user_lid = pu.id" + 
                                " WHERE pu.phone_no = ? ;";
                return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(SessionDto.class), phoneNo);

            }else{
                String SQL = "WITH user_details AS (" + 
                            "     SELECT DISTINCT" + 
                            "         pu.id AS userLid," + 
                            "         pu.phone_no AS phoneNo," + 
                            "         rr.name AS userRole," + 
                            "         ut.title AS title," + 
                            "         pu.profile_url AS profileUrl," + 
                            "         bm.colour AS userBelt," + 
                            "         pu.email," + 
                            "         pu.full_name AS fullName," + 
                            "         pu.address" + 
                            "     FROM" + 
                            "         public.user pu" + 
                            "     INNER JOIN" + 
                            "         roles rr ON rr.id = pu.role_id" + 
                            "     INNER JOIN" + 
                            "         user_class uc ON uc.user_lid = pu.id" + 
                            "     INNER JOIN" + 
                            "         class_master cm ON cm.id = uc.class_lid" + 
                            "     INNER JOIN" + 
                            "         belt_master bm ON bm.id = uc.belt_lid" + 
                            "     LEFT JOIN" + 
                            "         user_title ut ON ut.user_lid = pu.id" + 
                            "     WHERE" + 
                            "         pu.phone_no = ? " + 
                            " )," + 
                            " aggregated_data AS (" + 
                            "     SELECT" + 
                            "         COUNT(DISTINCT uc.class_lid) AS total_classes," + 
                            "         COUNT(CASE WHEN rr.abbr = 'STD' THEN pu.id END) AS totalStudents," + 
                            "         COUNT(CASE WHEN rr.abbr = 'INS' THEN pu.id END) AS totalInstructors" + 
                            "     FROM" + 
                            "         user_class uc" + 
                            "     INNER JOIN" + 
                            "         public.user pu ON pu.id = uc.user_lid" + 
                            "     INNER JOIN" + 
                            "         roles rr ON rr.id = pu.role_id" + 
                            "     WHERE" + 
                            "         uc.class_lid IN (" + 
                            "             SELECT uc.class_lid" + 
                            "             FROM user_class uc" + 
                            "             INNER JOIN public.user pu ON uc.user_lid = pu.id" + 
                            "             WHERE pu.phone_no = ? " + 
                            "         )" + 
                            " )" + 
                            " SELECT" + 
                            "     ud.*," + 
                            "     ad.total_classes," + 
                            "     ad.totalStudents, " + 
                            "     ad.totalInstructors " + 
                            " FROM" + 
                            "     user_details ud," + 
                            "     aggregated_data ad;";
                return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(SessionDto.class), phoneNo, phoneNo);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            return new SessionDto();
        }
    }

    public AuthDto bringUserEmail(String phoneNo) {
        try {
            String SQL = "SELECT email FROM public.user WHERE phone_no = ? AND active = TRUE";
    
            return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(AuthDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new AuthDto();
        }
    }

    public boolean insertOtp(String phoneNo, String otp, String otpFor) {
        String sql = "INSERT INTO otp (phone_no, otp, otp_for, created_by) " +
                        "VALUES (?, ?, ?, ?)";

        try {
            jt.update(sql, phoneNo, otp, otpFor, phoneNo);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isOtpValid(String phoneNo, String otp) {
        String sql = "UPDATE otp" + 
                        " SET is_valid = TRUE" + 
                        " WHERE id = (" + 
                        "     SELECT id" + 
                        "     FROM otp" + 
                        "     WHERE phone_no = ?" + 
                        "       AND otp = ?" + 
                        "       AND valid_till > CURRENT_TIMESTAMP" + 
                        "       AND is_valid = FALSE" + 
                        "     ORDER BY id DESC" + 
                        "     LIMIT 1" + 
                        " )" + 
                        " RETURNING is_valid;";

        try {
            Boolean isValid = jt.queryForObject(sql, Boolean.class, phoneNo, otp);
            return isValid != null && isValid;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String password, String phoneNo) {
        String sql = "UPDATE public.user pu "
                        + " SET password = ? " 
                        + " FROM otp " 
                        + " WHERE pu.phone_no = otp.phone_no "
                        + " AND otp.is_valid = true "
                        + " AND otp.valid_till > CURRENT_TIMESTAMP"
                        + " AND pu.phone_no = ? RETURNING TRUE;";

        try {
            Boolean isChanges = jt.queryForObject(sql, Boolean.class, password, phoneNo);
            return isChanges != null && isChanges;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}
