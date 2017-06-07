package db;

import org.hibernate.Session;
import org.hibernate.query.Query;
import po.Comments;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentsDao extends BaseDao<Comments> {


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

    public List<Comments> findByUserNameAndTime(String userName, Timestamp timestamp) {
        try {
            String queryString = "from " + typeClass().getCanonicalName()
                    + " as model where model." + "userName" + "= :userName and model.time = :time";
            Session session = Client.getSessionFactory().openSession();
            Query query = session.createQuery(queryString);
            query.setParameter("userName", userName);
            query.setParameter("time", timestamp);
            List<Comments> pojos = query.list();
            return pojos;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public static void main(String[] args) {
        CommentsDao commentsDao = new CommentsDao();
        try {
            List<Comments> list = commentsDao.findByUserNameAndTime("Mi_961284213",
                    new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse("2017-06-06 18:04:20").getTime()));
            System.out.println(list.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}