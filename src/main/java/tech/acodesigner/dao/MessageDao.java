package tech.acodesigner.dao;

import tech.acodesigner.dto.MessageDto;
import tech.acodesigner.dto.MessageReplyDto;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.po.MessagePo;
import tech.acodesigner.util.DBUtil;
import tech.acodesigner.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张秦遥 on 2017/2/20/0020.
 */
public class MessageDao {

    private static ArrayList<MessageReplyDto> getChildren(int type, int pid) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT * FROM blog_message t1,blog_user t2 WHERE t1.userId = t2.userId AND t1.messageType = ? AND t1.pid = ?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,type);
        pstmt.setInt(2,pid);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<MessageReplyDto> replyMessages = new ArrayList<MessageReplyDto>();
        while (rs.next()) {
            MessageReplyDto replyMessage = new MessageReplyDto();
            replyMessage.setId(rs.getInt("messageId"));
            replyMessage.setPid(rs.getInt("pid"));
            replyMessage.setPubDate(rs.getString("pubDate"));
            replyMessage.setContent(rs.getString("content"));
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setImage(rs.getString("image"));
            userDto.setUsername(rs.getString("username"));
            replyMessage.setUser(userDto);
            replyMessages.add(replyMessage);
        }
        DBUtil.closeCon(conn);
        return replyMessages;
    }

    public static ArrayList<MessageDto> getComments(int articleId) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        ArrayList<MessageDto> comments = new ArrayList<MessageDto>();
        String sql = "SELECT * FROM blog_message t1,blog_user t2 WHERE t1.userId = t2.userId AND messageType = 0 AND pid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, articleId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            MessageDto comment = new MessageDto();
            comment.setId(rs.getInt("messageId"));
            comment.setPid(rs.getInt("pid"));
            comment.setType(rs.getInt("messageType"));
            comment.setReplyType(2);
            comment.setPubDate(rs.getString("pubDate"));
            comment.setContent(rs.getString("content"));
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setImage(rs.getString("image"));
            userDto.setUsername(rs.getString("username"));
            comment.setUser(userDto);

            comment.setChirdren(getChildren(comment.getReplyType(), comment.getId()));

            comments.add(comment);
        }
        DBUtil.closeCon(conn);
        return comments;
    }

    public static ArrayList<MessageDto> getLeaveMessages() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        ArrayList<MessageDto> leaveMessages = new ArrayList<MessageDto>();
        String sql = "SELECT * FROM blog_message t1,blog_user t2 WHERE t1.userId = t2.userId AND t1.messageType = 1 order by pubDate desc ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            MessageDto leaveMessage = new MessageDto();
            leaveMessage.setId(rs.getInt("messageId"));
            leaveMessage.setPid(rs.getInt("pid"));
            leaveMessage.setType(rs.getInt("messageType"));
            leaveMessage.setReplyType(3);
            leaveMessage.setPubDate(rs.getString("pubDate"));
            leaveMessage.setContent(rs.getString("content"));

            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setImage(rs.getString("image"));
            userDto.setUsername(rs.getString("username"));
            leaveMessage.setUser(userDto);
            leaveMessage.setChirdren(getChildren(leaveMessage.getReplyType(), leaveMessage.getId()));

            leaveMessages.add(leaveMessage);
        }
        DBUtil.closeCon(conn);
        return leaveMessages;
    }

    public static ArrayList<MessageDto> getRecentMessages() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "select * from blog_message t1,blog_user t2 where t1.userId = t2.userId order by pubDate desc limit 0,5";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<MessageDto> msgs = new ArrayList<MessageDto>();
        while (rs.next()) {
            MessageDto msg = new MessageDto();
            UserDto user = new UserDto();
            msg.setContent(rs.getString("content"));
            user.setUsername(rs.getString("username"));
            msg.setUser(user);
            msgs.add(msg);
        }
        DBUtil.closeCon(conn);
        return msgs;
    }

        public static ArrayList<MessageDto> getAllMessages() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "select * from blog_message t1,blog_user t2 where t1.userId = t2.userId order by pubDate desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<MessageDto> msgs = new ArrayList<MessageDto>();
        while (rs.next()) {
            MessageDto msg = new MessageDto();
            UserDto user = new UserDto();
            msg.setId(rs.getInt("messageId"));
            msg.setType(rs.getInt("messageType"));
            msg.setPid(rs.getInt("pid"));
            msg.setPubDate(rs.getString("pubDate"));
            msg.setContent(rs.getString("content"));
            user.setUsername(rs.getString("username"));
            msg.setUser(user);
            msgs.add(msg);
        }
        DBUtil.closeCon(conn);
        return msgs;
    }

    public static void save(MessagePo message) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "insert into blog_message values(null,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, message.getUserId());
        pstmt.setInt(2, message.getMessageType());
        pstmt.setInt(3, message.getPid());
        pstmt.setString(4, message.getContent());
        pstmt.setString(5, DateUtil.formatLong(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void delete(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "delete from blog_message where messageId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
