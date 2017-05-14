package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CrawledUrlDao {

    public CrawledUrlDao() {

    }

    public int add(String crawledUrl) {
        try {
            String sql = "INSERT INTO crawled_url (url) VALUES (?);";
            PreparedStatement ps = MysqlCon.getConn().prepareStatement(sql);
            ps.setString(1, crawledUrl);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addAll(List<String> addUrlList) {
        for (String url : addUrlList) {
            add(url);
        }
    }

    public long findIdByField(Class clazz, String fieldName, String value) {
        if (clazz.equals(String.class)) {
            try {
                String sql = "SELECT id FROM crawled_url where " + fieldName + "='" + value + "';";
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