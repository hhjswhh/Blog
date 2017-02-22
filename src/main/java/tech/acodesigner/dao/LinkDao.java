package tech.acodesigner.dao;

import tech.acodesigner.po.LinkPo;
import tech.acodesigner.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 77239 on 2017/2/22/0022.
 */
public class LinkDao {

    public static LinkPo getLink(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT * FROM blog_link WHERE linkId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        LinkPo link = new LinkPo();
        if (rs.next()) {
            link.setLinkId(rs.getInt("linkId"));
            link.setLinkName(rs.getString("linkName"));
            link.setUrl(rs.getString("url"));
        }
        DBUtil.closeCon(conn);
        return link;
    }

    public static ArrayList<LinkPo> getAllLinks() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        ArrayList<LinkPo> links = new ArrayList<LinkPo>();
        String sql = "SELECT * FROM blog_link";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            LinkPo link = new LinkPo();
            link.setLinkId(rs.getInt("linkId"));
            link.setLinkName(rs.getString("linkName"));
            link.setUrl(rs.getString("url"));
            links.add(link);
        }
        DBUtil.closeCon(conn);
        return links;
    }

    public static void saveLink(LinkPo link) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "INSERT INTO blog_link VALUES (null,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, link.getLinkName());
        pstmt.setString(2, link.getUrl());
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void updateLink(LinkPo link) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "UPDATE blog_link SET linkName=?,url=? WHERE linkId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, link.getLinkName());
        pstmt.setString(2, link.getUrl());
        pstmt.setInt(3, link.getLinkId());
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void deleteLink(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "DELETE FROM blog_link where linkId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
