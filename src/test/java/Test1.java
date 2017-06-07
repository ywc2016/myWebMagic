import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by ywcrm on 2017/5/12.
 */
public class Test1 {
    @Test
    public void test1() {
    }

    @Test
    public void test2() {
        String s = DigestUtils.md5Hex("123");
        System.out.println(s);
    }
}
