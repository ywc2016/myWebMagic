package spider;

import db.UserDao;
import po.User;
import us.codecraft.webmagic.Spider;
import util.MailUtils;

import java.util.List;

/**
 * Created by yuwc on 2017/5/15.
 *
 * @author yuwc
 */

public class BaseByUserProcessor extends BaseProcessor {
    private UserDao userDao = new UserDao();

    public BaseByUserProcessor() {
        this.setFirstSite("http://bbs.xiaomi.cn/f-487-566");
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
        this.setUserPostFirstPage(this.getDomain() + "/t-\\d+$");
        this.setUserPostSecondPage(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }

    @Override
    public void startSpider() {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待...");
        startTime = System.currentTimeMillis();
        // 开启5个线程，启动爬虫
        Spider.create(this).addUrl(getUserHomePage()).thread(1).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查看！");
        //抓取完发送邮件
        try {
            new MailUtils().sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getUserHomePage() {
        List<User> userList = userDao.findAll();
        String[] userHomePageArray = new String[5];
        for (int i = 0; i < 5; i++) {
            userHomePageArray[i] = userList.get(i).getHomePage();
        }
        return userHomePageArray;
    }

}
