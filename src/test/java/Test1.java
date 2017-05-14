import db.PostsDao;
import org.junit.Test;

/**
 * Created by ywcrm on 2017/5/12.
 */
public class Test1 {
    @Test
    public void test1() {
        System.out.println(new PostsDao().findIdByField(String.class, "url", "http://bbs.xiaomi.cn/t-13501785"));
    }
}
