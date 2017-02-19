package tech.acodesigner.dao;

import tech.acodesigner.dto.AboutDto;
import tech.acodesigner.dto.ArticleDto;
import tech.acodesigner.dto.ArticleLiteDto;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.po.ArticlePo;
import tech.acodesigner.po.CategoryPo;
import tech.acodesigner.util.DBUtil;
import tech.acodesigner.util.DateUtil;
import tech.acodesigner.util.PageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 77239 on 2017/2/13/0013.
 */
public class ArticleDao {

    public static AboutDto getAbout() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "select content from blog_article where categoryId = 1;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        AboutDto about = null;
        if (rs.next()) {
            about = new AboutDto();
            about.setContent(rs.getString("content"));
        }
        DBUtil.closeCon(conn);
        return about;
    }

    public static void updateAbout(String content) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "update blog_article set content=? where categoryId=1;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, content);
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static ArrayList<ArticleDto> search(String key) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        ArrayList<ArticleDto> articles = new ArrayList<ArticleDto>();
        String sql = "select * from blog_article t1,blog_category t2,blog_user t3 where t1.categoryId=t2.categoryId and t1.userId = t3.userId "
				+ "and title like '%"
				+ key
				+ "%' and t1.categoryId>1 "
                + "ORDER BY pubDate DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            ArticleDto article = new ArticleDto();
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setPubDate(rs.getString("pubDate"));
            article.setImage(rs.getString("image"));
            article.setClicks(rs.getInt("clicks"));
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setImage(rs.getString("image"));
            article.setUser(userDto);
            CategoryPo category = new CategoryPo();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
            article.setCategory(category);
            articles.add(article);
        }
        DBUtil.closeCon(conn);
        return articles;
    }

    public static ArrayList<ArticleDto> pagination(PageUtil pageUtil) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        ArrayList<ArticleDto> articles = new ArrayList<ArticleDto>();
        String sql = "select * from blog_article t1,blog_category t2,blog_user t3 " +
                "where t1.categoryId=t2.categoryId and t1.userId = t3.userId and t1.categoryId>1 ORDER	BY pubDate DESC limit "
                + pageUtil.getStart() + "," + pageUtil.getPageSize();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            ArticleDto article = new ArticleDto();
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setPubDate(rs.getString("pubDate"));
            article.setImage(rs.getString("image"));
            article.setClicks(rs.getInt("clicks"));
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setUsername(rs.getString("username"));
            userDto.setImage(rs.getString("image"));
            article.setUser(userDto);
            CategoryPo category = new CategoryPo();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
            article.setCategory(category);
            articles.add(article);
        }
        DBUtil.closeCon(conn);
        return articles;
    }

    public static ArticleLiteDto getPreArticle(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT * FROM blog_article WHERE articleId = " +
                "(SELECT articleId FROM blog_article WHERE articleId < ? and categoryId > 1 ORDER BY articleId DESC LIMIT 1);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArticleLiteDto article = new ArticleLiteDto();
        if (rs.next()) {
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
        }
        DBUtil.closeCon(conn);
        return article;
    }

    public static ArticleLiteDto getNextArticle(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT * FROM blog_article WHERE articleId = " +
                "(SELECT articleId FROM blog_article WHERE articleId > ? and categoryId > 1 ORDER BY articleId ASC LIMIT 1);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArticleLiteDto article = new ArticleLiteDto();
        if (rs.next()) {
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
        }
        DBUtil.closeCon(conn);
        return article;
    }

    public static ArticleDto getArticleById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT * FROM blog_article t1,blog_category t2,blog_user t3" +
                " WHERE t1.categoryId=t2.categoryId AND t1.userId=t3.userId AND t1.articleId=?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArticleDto article = new ArticleDto();
        if (rs.next()) {
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setPubDate(rs.getString("pubDate"));
            article.setImage(rs.getString("image"));
            article.setClicks(rs.getInt("clicks"));
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("userId"));
            userDto.setImage(rs.getString("image"));
            article.setUser(userDto);

            CategoryPo category = new CategoryPo();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
            article.setCategory(category);
        }
        DBUtil.closeCon(conn);
        return article;
    }

    public static ArrayList<ArticleDto> getArticles() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT articleId,title,content,pubDate FROM blog_article WHERE categoryId>1 ORDER BY pubDate DESC;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<ArticleDto> articles = new ArrayList<ArticleDto>();
        while (rs.next()) {
            ArticleDto article = new ArticleDto();
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setPubDate(rs.getString("pubDate"));
            articles.add(article);
        }
        DBUtil.closeCon(conn);
        return articles;
    }

    public static ArrayList<ArticleLiteDto> getArticlesByCategoryId(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT articleId,title,pubDate FROM blog_article WHERE categoryId=? ORDER BY pubDate DESC;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<ArticleLiteDto> articles = new ArrayList<ArticleLiteDto>();
        while (rs.next()) {
            ArticleLiteDto article = new ArticleLiteDto();
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setPubDate(rs.getString("pubDate"));
            articles.add(article);
        }
        DBUtil.closeCon(conn);
        return articles;
    }

    public static ArrayList<ArticleLiteDto> getRecentArticles() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT articleId,title FROM blog_article WHERE categoryId>1 ORDER BY pubDate DESC LIMIT 0,5;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ArrayList<ArticleLiteDto> articles = new ArrayList<ArticleLiteDto>();
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            ArticleLiteDto article = new ArticleLiteDto();
            article.setId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            articles.add(article);
        }
        DBUtil.closeCon(conn);
        return articles;
    }

    public static void updateArticle(ArticlePo article) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "UPDATE blog_article set categoryId=?,title=?,content=?,image=? WHERE articleId=?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, article.getCategoryId());
        pstmt.setString(2, article.getTitle());
        pstmt.setString(3, article.getContent());
        pstmt.setString(4, article.getImage());
        pstmt.setInt(5, article.getArticleId());
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void saveArticle(ArticlePo article) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "INSERT INTO blog_article VALUES (null,?,1,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, article.getCategoryId());
        pstmt.setString(2, article.getTitle());
        pstmt.setString(3, article.getContent());
        pstmt.setString(4, DateUtil.formatLong(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        pstmt.setInt(5, article.getClicks());
        pstmt.setString(6, article.getImage());
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void deleteArticle(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "DELETE FROM blog_article WHERE articleId = ?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static void addClicks(int clicks, int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "UPDATE blog_article SET clicks=? WHERE articleId=?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,clicks);
        pstmt.setInt(2,id);
        pstmt.executeUpdate();
        DBUtil.closeCon(conn);
    }

    public static int count() throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getCon();
        String sql = "SELECT COUNT(*) AS total FROM blog_article WHERE categoryId>1;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt("total");
        }
        DBUtil.closeCon(conn);
        return count;
    }
}
