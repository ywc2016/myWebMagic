package db;

import po.Comments;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentsDao {


    public CommentsDao() {

    }

    public int add(Comments comments) {
        try {
            String sql = "INSERT INTO comments (user_name,user_page,comefrom" +
                    ",time,content,post_id) VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
            ps.setString(1, comments.getUserName());
            ps.setString(2, comments.getUserPage());
            ps.setString(3, comments.getComefrom());
            try {
                ps.setDate(4, new java.sql.Date(comments.getTime().getTime()));
            } catch (SQLException e) {
                System.out.println("时间格式有误! " + comments.getTime());
                e.printStackTrace();
            }
            ps.setString(5, comments.getContent());
            ps.setLong(6, comments.getPostId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}