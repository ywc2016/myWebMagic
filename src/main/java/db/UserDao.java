package db;

import po.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    public UserDao() {

    }

    public int add(User user) {
        try {
            String sql = "INSERT INTO user (home_page,name,vip_level" +
                    ",contribute,vip_rank,score,miid,user_group,email" +
                    ",topic_num,reply_num,exp,last_active_time,register_time" +
                    ",last_access_time,last_deliver_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
            ps.setString(1, user.getHome_page());
            ps.setString(2, user.getName());
            ps.setString(3, user.getVip_level());
            ps.setInt(4, user.getContribute());
            ps.setInt(5, user.getVip_rank());
            ps.setInt(6, user.getScore());
            ps.setLong(7, user.getMiid());
            ps.setString(8, user.getUser_group());
            ps.setString(9, user.getEmail());
            ps.setInt(10, user.getTopic_num());
            ps.setInt(11, user.getReply_num());
            ps.setInt(12, user.getExp());
            try {
                ps.setDate(13, new java.sql.Date(user.getLast_active_time().getTime()));
                ps.setDate(14, new java.sql.Date(user.getRegister_time().getTime()));
                ps.setDate(15, new java.sql.Date(user.getLast_access_time().getTime()));
                ps.setDate(16, new java.sql.Date(user.getLast_deliver_time().getTime()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean hasUserByMiid(long miid) {
        try {
            String sql = "SELECT * FROM user where  miid=" + miid + ";";
            PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}