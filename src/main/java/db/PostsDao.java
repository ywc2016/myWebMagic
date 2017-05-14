package db;

import po.Posts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostsDao {

    public PostsDao() {

    }

    public int add(Posts posts) {
        try {
            String sql = "INSERT INTO posts (id,url,title,stamps" +
                    ",user_name,user_page,plate,secondary_plate,time,comefrom" +
                    ",check_amount,comments_amount,content) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
            ps.setLong(1, posts.getId());
            ps.setString(2, posts.getUrl());
            ps.setString(3, posts.getTitle());
            ps.setString(4, posts.getStamps());
            ps.setString(5, posts.getUserName());
            ps.setString(6, posts.getUserPage());
            ps.setString(7, posts.getPlate());
            ps.setString(8, posts.getSecondary_plate());
            ps.setDate(9, new java.sql.Date(posts.getTime().getTime()));
            ps.setString(10, posts.getComefrom());
            ps.setInt(11, posts.getCheckAmount());
            ps.setInt(12, posts.getCommentsAmount());
            ps.setString(13, posts.getContent());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long findIdByField(Class clazz, String fieldName, String value) {
        if (clazz.equals(String.class)) {
            try {
                String sql = "SELECT id FROM posts where " + fieldName + "='" + value + "';";
                PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getLong("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

}