package spider;

import db.CommentsDao;
import db.CrawledUrlDao;
import db.PostsDao;
import db.UserDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import po.Posts;
import po.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yuwc on 2017/5/15.
 *
 * @author yuwc
 */

public class BaseByUserProcessor implements PageProcessor {
    //   抓取的论坛首页
    private String domain = "http://bbs.xiaomi.cn";
    private String firstSite = "http://bbs.xiaomi.cn/d-1";
    private String postListPageUrlPattern = domain + "/d-\\d+$";//domain + "/f\\-\\d+\\-\\d+$"
    private String firstPageOfPostUrlPattern = domain + "/t\\-\\d+$";//domain + "/t\\-\\d+$"
    //    private String secondPageOfPostUrlPattern = null;//this.domain + "/t-\\d+-\\d+-o1#comment_top"
    private String userHomePageUrlPattern = this.domain + "/u-detail-\\d+";
    private String firstUserPostsUrlPattern = this.domain + "/u-thread-\\d+";
    private String secondUserPostsUrlPattern = this.domain + "/u-thread-\\d+-\\d+#thread";

    //   抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private PostsDao postsDao = new PostsDao();
    private CommentsDao commentsDao = new CommentsDao();
    private CrawledUrlDao crawledUrlDao = new CrawledUrlDao();
    private UserDao userDao = new UserDao();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String patternWithYear = "\\s*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}\\s*";//2017-05-18 16:08:37
    private String patternWithoutYear = "\\s*\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}\\s*";
    private String patterOfSecondPageOfPost = "(" + this.domain + "/t-\\d+)-\\d+-o1#comment_top";
    private Calendar c = Calendar.getInstance();
    private int curYear = c.get(Calendar.YEAR);

    public String getDomain() {
        return domain;
    }

    public String getFirstSite() {
        return firstSite;
    }

    public String getPostListPageUrlPattern() {
        return postListPageUrlPattern;
    }

    public void setPostListPageUrlPattern(String postListPageUrlPattern) {
        this.postListPageUrlPattern = postListPageUrlPattern;
    }

    public void setFirstSite(String firstSite) {
        this.firstSite = firstSite;
    }


    public Site getSite() {

        return site;
    }

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(this.postListPageUrlPattern).match()) { //是帖子列表页面
            try {
                // 首先添加下一页
                String url = page.getHtml().xpath("//li[@class='next']/a/@href").all().get(0);
                crawledUrlDao.add(url);
                if (url == null || "".equals(url)) {
                    System.out.println("没有下一页帖子!");
                }
                page.addTargetRequest(url);
                crawledUrlDao.add(url);
            } catch (Exception e) {
                System.out.println("添加下一页帖子失败!");
                e.printStackTrace();
            }
            try {
                // 添加该页中所有的用户主页进入队列
                List<String> addUrlList = page.getHtml().xpath("//a[@class='headportrait']/@href").all();
                page.addTargetRequests(addUrlList);//
                crawledUrlDao.addAll(addUrlList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (page.getUrl().regex(this.userHomePageUrlPattern).match()) { // 用户主页

            try {
                //是第一页,添加一条帖子信息
                User user = new User();
                user.setMiid(Long.parseLong(page.getHtml()
                        .xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(0)));
                if (userDao.hasUserByMiid(user.getMiid())) {
                    return;
                }

                //添加个人帖子url
                try {
//                    System.out.println(page.getHtml().xpath("//li[@class='menu_item current']/a/@href").all().size());
                    String url1 = page.getHtml().xpath("//div[@class='nav_menu']//li/a/@href").all().get(1);
                    page.addTargetRequest(url1);
                    crawledUrlDao.add(url1);
                } catch (Exception e) {
                    System.out.println("个人帖子url有误!" + page.getHtml()
                            .xpath("//li[@class='menu_item current']/a/@href").all().toString());
                    e.printStackTrace();
                }
                user.setTopic_num(Integer.parseInt(page.getHtml()
                        .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(0)));
                user.setReply_num(Integer.parseInt(page.getHtml()
                        .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(1)));
                user.setExp(Integer.parseInt(page.getHtml()
                        .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(2)));
                user.setContribute(Integer.parseInt(page.getHtml()
                        .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(3)));
                user.setEmail(page.getHtml()
                        .xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(2));
                user.setUser_group(page.getHtml().xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(1));
                user.setHome_page(page.getUrl().all().get(0));
                user.setName(page.getHtml().xpath("//strong[@class='username']/text()").all().get(0));
                user.setVip_level(page.getHtml()
                        .xpath("//div[@class='score']//span/text()").all().get(0));
//                user.setContribute(Integer.parseInt(page.getHtml()
//                        .xpath("//div[@class='score']//span/text()").all().get(1)));
                try {
                    String s = page.getHtml()
                            .xpath("//div[@class='score']//span/text()").all().get(2);
                    if (s.equals("无")) {
                        user.setVip_rank(-1);
                    } else {
                        user.setVip_rank(Integer.parseInt(s));
                    }


                } catch (NumberFormatException e) {
                    System.out.println("无vip排位.");
                    e.printStackTrace();
                }

                user.setScore(Integer.parseInt(page.getHtml()
                        .xpath("//div[@class='score']//span/text()")
                        .all().get(3)));
                try {
                    user.setLast_access_time(sdf.parse(page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(0)));
                } catch (ParseException e) {
                    System.out.println("日期格式有误!" + page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(0));
                    e.printStackTrace();
                }
                try {
                    user.setLast_active_time(sdf.parse(page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(2)));
                } catch (ParseException e) {
                    System.out.println("日期格式有误!" + page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(2));
                    e.printStackTrace();
                }
                try {
                    user.setRegister_time(sdf.parse(page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(4)));
                } catch (ParseException e) {
                    System.out.println("日期格式有误!" + page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(4));
                    e.printStackTrace();
                }
                try {
                    user.setLast_deliver_time(sdf.parse(page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(5)));
                } catch (ParseException e) {
                    System.out.println("日期格式有误!" + page.getHtml()
                            .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(5));
                    e.printStackTrace();
                }

                userDao.add(user);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (page.getUrl().regex(this.firstUserPostsUrlPattern).match()) {//如果是个人帖子列表的第一页
            //添加下一页
            try {

                List<String> urls = page.getHtml().xpath("//li[@class='next']/a/@href").all();
                if (urls.size() != 0) {
                    page.addTargetRequests(urls);
                    crawledUrlDao.addAll(urls);
                } else {
                    System.out.println("没有下一页");
                }
            } catch (Exception e) {
                System.out.println("没有下一页");
                e.printStackTrace();
            }
            try {
                List<String> urls = page.getHtml()
                        .xpath("div[@class='theme_con_index current']/ul/li//div[@class='title']/a/@href")
                        .all();
                if (urls.size() != 0) {
                    page.addTargetRequests(urls);
                    crawledUrlDao.addAll(urls);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (page.getUrl().regex(this.secondUserPostsUrlPattern).match()) {
            //将帖子url加入队列
            try {
                List<String> urls = page.getHtml()
                        .xpath("div[@class='theme_con_index current']/ul/li//div[@class='title']/a/@href").all();
                page.addTargetRequests(urls);
                crawledUrlDao.addAll(urls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (page.getUrl().regex(this.firstPageOfPostUrlPattern).match()) {
            try {
                //是第一页,添加一条帖子信息

                Posts posts = new Posts();
                posts.setUrl(page.getUrl().toString());
                try {
                    posts.setTitle(page.getHtml().xpath("//div[@class='invitation_con']/h1/span[@class='name']/text()").all().get(0).toString());
                } catch (Exception e) {
                    System.out.println("获取标题失败!" + page.getUrl());
                    e.printStackTrace();
                }
                posts.setStamps(listToString(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='marktxt']/text()").all()));
                posts.setUserName(page.getHtml().xpath("//a[@class='user_name']/text()").all().get(0));
                posts.setUserPage(page.getHtml().xpath("//div[@class='personLayer_msg']/a[@class='user_head']/@href").all().get(0));
                posts.setPlate(page.getHtml().xpath("//div[@class='plateinfor']/a[@class='platename']/text()").all().get(0));
                posts.setSecondary_plate(page.getHtml().xpath("//div[@class='invitation_con']/p[@class='txt']/a[@class='name']/text()").all().get(0));
                String dateStr = null;
                try {
                    dateStr = page.getHtml()
                            .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='time']/text()").all().get(0);
                    if (Pattern.matches(patternWithoutYear, dateStr)) {
                        posts.setTime(sdf.parse(curYear + "-" + dateStr.substring(1)));
                    } else if (Pattern.matches(patternWithYear, dateStr)) {
                        posts.setTime(sdf.parse(dateStr));
                    }
                } catch (ParseException e) {
                    System.out.println("时间有误!" + dateStr);
                    e.printStackTrace();
                }
                posts.setComefrom(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='comefrom']/text()").all().get(0));
                posts.setCheckAmount(Integer.valueOf(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='f_r']/text()").all().get(1)));
                posts.setCommentsAmount(Integer.valueOf(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='f_r']/text()").all().get(0)));
                String contextHtml = page.getHtml().xpath("//div[@class='invitation_content']").all().get(0);
                Document doc = Jsoup.parse(contextHtml);
                posts.setContent(doc.text());
                postsDao.add(posts);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }


    // 把list转换为string，用,分割

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public void startSpider() {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待...");
        startTime = System.currentTimeMillis();
        // 开启5个线程，启动爬虫
        Spider.create(this).addUrl(this.getFirstSite()).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }

}
