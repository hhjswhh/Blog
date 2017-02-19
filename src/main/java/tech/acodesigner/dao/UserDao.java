package tech.acodesigner.dao;

import tech.acodesigner.dto.UserDto;
import tech.acodesigner.po.UserPo;
import tech.acodesigner.util.DBUtil;
import tech.acodesigner.util.MD5Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 77239 on 2017/2/11/0011.
 */
public class UserDao {

    public static UserDto login(String username, String password) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "select * from blog_user where username=? and password=?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        UserDto result = null;
        if (rs.next()) {
            result = new UserDto();
            result.setId(rs.getInt("userId"));
            result.setType(rs.getInt("userType"));
            result.setUsername(rs.getString("username"));
            result.setImage(rs.getString("image"));
        }
        DBUtil.closeCon(conn);
        return result;
    }

    public static void save(UserPo user) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "insert into blog_user values(null,0,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getImage());
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        UserPo user = new UserPo();
        user.setUsername("root");
        user.setPassword(MD5Util.encoderPassword("123"));
        user.setImage("root");
        UserDao.save(user);
    }
}
