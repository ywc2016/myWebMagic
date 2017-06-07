package spider;

import db.CommentsDao;
import db.CrawledUrlDao;
import db.PostsDao;
import db.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import po.Comments;
import po.Posts;
import po.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import util.Utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuwc on 2017/5/15.
 *
 * @author yuwc
 */

public abstract class BaseProcessor implements PageProcessor {
    //   抓取的论坛首页
    private String domain = "http://bbs.xiaomi.cn";
    private String firstSite = null;
    private String postListPageUrlPattern = null;//domain + "/f\\-\\d+\\-\\d+$"
    private String firstPageOfPostUrlPattern = null;//domain + "/t\\-\\d+$"
    private String secondPageOfPostUrlPattern = null;//this.domain + "/t-\\d+-\\d+-o1#comment_top"
    private String userHomePageUrlPattern = this.domain + "/u-detail-\\d+$";


    //   抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private PostsDao postsDao = new PostsDao();
    private CommentsDao commentsDao = new CommentsDao();
    private CrawledUrlDao crawledUrlDao = new CrawledUrlDao();
    private UserDao userDao = new UserDao();
    private Utils utils = new Utils();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String patternWithYear = "\\s*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}\\s*";
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

    public String getFirstPageOfPostUrlPattern() {
        return firstPageOfPostUrlPattern;
    }

    public void setFirstPageOfPostUrlPattern(String firstPageOfPostUrlPattern) {
        this.firstPageOfPostUrlPattern = firstPageOfPostUrlPattern;
    }

    public String getSecondPageOfPostUrlPattern() {
        return secondPageOfPostUrlPattern;
    }

    public void setSecondPageOfPostUrlPattern(String secondPageOfPostUrlPattern) {
        this.secondPageOfPostUrlPattern = secondPageOfPostUrlPattern;
    }

    public Site getSite() {

        return site;
    }

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(this.postListPageUrlPattern).match()) { //不是帖子且是帖子列表页面

            processPostListPage(page);

        } else if (page.getUrl().regex(this.firstPageOfPostUrlPattern).match()) { // 帖子首页

            processFirstPostPage(page);

        } else if (page.getUrl().regex(this.secondPageOfPostUrlPattern).match()) {//如果是帖子的第二页或者之后的页
            processSecondPostPage(page);

        } else if (page.getUrl().regex(this.userHomePageUrlPattern).match()) { // 用户主页
            processUserHomePage(page);

        }
    }

    private void processUserHomePage(Page page) {
        try {
            User user = new User();
            user.setMiid(Long.parseLong(page.getHtml()
                    .xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(0)));
            if (userDao.hasUserByMiid(user.getMiid())) {
                return;
            }
//                //添加个人帖子url
//                try {
////                    System.out.println(page.getHtml().xpath("//li[@class='menu_item current']/a/@href").all().size());
//                    String url1 = page.getHtml().xpath("//div[@class='nav_menu']//li/a/@href").all().get(1);
//                    page.addTargetRequest(url1);
//                    crawledUrlDao.add(url1);
//                } catch (Exception e) {
//                    System.out.println("个人帖子url有误!" + page.getHtml()
//                            .xpath("//li[@class='menu_item current']/a/@href").all().toString());
//                    e.printStackTrace();
//                }
            user.setTopicNum(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(0)));
            user.setReplyNum(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(1)));
            user.setExp(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(2)));
            user.setContribute(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='wrap lively']/ul/li/span[@class='num']/text()").all().get(3)));
            user.setEmail(page.getHtml()
                    .xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(2));
            user.setUserGroup(page.getHtml().xpath("//ul[@class='msg']/li/span[@class='num']/text()").all().get(1));
            user.setHomePage(page.getUrl().all().get(0));
            user.setName(page.getHtml().xpath("//strong[@class='username']/text()").all().get(0));
            user.setVipLevel(page.getHtml()
                    .xpath("//div[@class='score']//span/text()").all().get(0));
//                user.setContribute(Integer.parseInt(page.getHtml()
//                        .xpath("//div[@class='score']//span/text()").all().get(1)));
            try {
                String s = page.getHtml()
                        .xpath("//div[@class='score']//span/text()").all().get(2);
                if (s.equals("无")) {
                    user.setVipRank(-1);
                } else {
                    user.setVipRank(Integer.parseInt(s));
                }

            } catch (NumberFormatException e) {
                System.out.println("无vip排位.");
                e.printStackTrace();
            }

            user.setScore(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='score']//span/text()")
                    .all().get(3)));
            try {
                user.setLastAccessTime(new Timestamp(sdf.parse(page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()")
                        .all().get(0)).getTime()));
            } catch (ParseException e) {
                System.out.println("日期格式有误!" + page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(0));
                e.printStackTrace();
            }

            try {
                user.setLastActiveTime(new Timestamp(sdf.parse(page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()")
                        .all().get(2)).getTime()));
            } catch (ParseException e) {
                System.out.println("日期格式有误!" + page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(2));
                e.printStackTrace();
            }

            try {
                user.setRegisterTime(new Timestamp(sdf.parse(page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(4)).getTime()));
            } catch (ParseException e) {
                System.out.println("日期格式有误!" + page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(4));
                e.printStackTrace();
            }

            try {
                user.setLastDeliverTime(new Timestamp(sdf.parse(page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(5)).getTime()));
            } catch (ParseException e) {
                System.out.println("日期格式有误!" + page.getHtml()
                        .xpath("//div[@class='wrap lively']/dl/dd/span/text()").all().get(5));
                e.printStackTrace();
            }

            //产品类图标
            List<String> medalList = page.getHtml().xpath("//div[@class='user clearfix']/div[@class='medal']/ul/li/a/@href").all();
            user.setMedalIds(utils.listToString(utils.convertMedalListToIdlist(medalList)));

            List<String> badgeList = page.getHtml().xpath("//p[@class='badge']/a/img/@src").all();
            userDao.save(user);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理帖子第二页及以后的页
     *
     * @param page
     */
    private void processSecondPostPage(Page page) {
        try {
            getComments(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理帖子首页
     *
     * @param page
     */
    private void processFirstPostPage(Page page) {
        try {

            List<Posts> postList = postsDao.findByPropertyEqual("url", page.getUrl().toString(), "String");
            if (postList != null && postList.size() != 0) {  //是第一页,如果是新的帖子,添加一条帖子信息
                Posts posts = new Posts();
                posts.setUrl(page.getUrl().toString());
                posts.setTitle(page.getHtml().xpath("//div[@class='invitation_con']/h1/span[@class='name']/text()").all().get(0).toString());
                posts.setStamps(utils.listToString(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='marktxt']/text()").all()));
                posts.setUserName(page.getHtml().xpath("//a[@class='user_name']/text()").all().get(0));
                posts.setUserPage(page.getHtml().xpath("//div[@class='personLayer_msg']/a[@class='user_head']/@href").all().get(0));
                posts.setPlate(page.getHtml().xpath("//div[@class='plateinfor']/a[@class='platename']/text()").all().get(0));
                posts.setSecondaryPlate(page.getHtml().xpath("//div[@class='invitation_con']/p[@class='txt']/a[@class='name']/text()").all().get(0));
                String dateStr = null;
                try {
                    dateStr = page.getHtml()
                            .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='time']/text()").all().get(0);
                    if (Pattern.matches(patternWithoutYear, dateStr)) {
                        posts.setTime(new Timestamp(sdf.parse(curYear + "-"
                                + dateStr.substring(1)).getTime()));
                    } else if (Pattern.matches(patternWithYear, dateStr)) {
                        posts.setTime(new Timestamp(sdf.parse(dateStr).getTime()));
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
            } else {
                System.out.println(page.getUrl().toString() + ":该帖已经入库!");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //抓取回复内容
        try {
            getComments(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理帖子列表页
     *
     * @param page
     */
    public void processPostListPage(Page page) {
        try {
            // 首先添加下一页
            String url = page.getHtml().xpath("//li[@class='next']/a/@href").all().get(0);

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

        try {
            // 添加该页中所有的帖子进入队列
            List<String> addUrlList = page.getHtml().xpath("//div[@class='theme_list_con']/div[@class='title']/a/@href").all();
            page.addTargetRequests(addUrlList);// 限定文章列表获取区域
//            page.addTargetRequest(page.getHtml().xpath("//li[@class='theme_list clearfix']/div[@class='theme_list_con']/div[@class='title']/a/@href").all().get(0));
            crawledUrlDao.addAll(addUrlList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 抓去回复内容并把下一页回复添加到队列
     *
     * @param page
     */
    private void getComments(Page page) {
        try {
            //把下一页评论加入队列
            List<String> urls = page.getHtml().xpath("//li[@class='next']/a/@href").all();
            page.addTargetRequests(urls);
            crawledUrlDao.addAll(urls);
        } catch (Exception e) {
            System.out.println("没有下一页");
            e.printStackTrace();
        }


        String postUrl = null;
        if (page.getUrl().regex(this.firstPageOfPostUrlPattern).match()) {//帖子第一页
            postUrl = page.getUrl().all().get(0);
        } else if (page.getUrl().regex(this.secondPageOfPostUrlPattern).match()) {//帖子第二页及以后
            Pattern pattern = Pattern.compile(this.patterOfSecondPageOfPost);
            Matcher m = pattern.matcher(page.getUrl().all().get(0));
            if (m.find()) {
                postUrl = m.group(1);
            }
        }
        long postId = postsDao.findIdByField("String", "url", postUrl);
        List<String> list = page.getHtml().xpath("//ul[@class='reply_list']/").all();
        for (String html : list) {
            Document doc = Jsoup.parse(html);
            Comments comment = new Comments();
            try {
                comment.setContent(doc.select("div.reply_txt").get(0).text());
                comment.setContentHash(DigestUtils.md5Hex(comment.getContent()));
            } catch (Exception e) {
                System.out.println("评论为空!");
                e.printStackTrace();
            }
            List<Comments> commentsList = commentsDao.findByPropertyEqual("contentHash",
                    comment.getContentHash(), "String");
            if (commentsList != null && commentsList.size() != 0) {
                comment.setUserName(doc.select("a.auth_name").get(0).text());
                comment.setUserPage(doc.select("a.auth_name").get(0).attr("href"));
                comment.setComefrom(doc.select("span.comefrom").get(0).text());
                try {
                    String dateStr = doc.select("span.time").get(1).text();
                    if (Pattern.matches(patternWithoutYear, dateStr)) {
                        comment.setTime(new Timestamp(sdf.parse(curYear + "-" + dateStr.substring(1)).getTime()));
                    } else if (Pattern.matches(patternWithYear, dateStr)) {
                        comment.setTime(new Timestamp(sdf.parse(dateStr).getTime()));
                    }
                } catch (ParseException e) {
                    System.out.println("日期字符串有误! " + doc.select("span.time").get(1).text());
                    e.printStackTrace();
                }

                comment.setPostId(postId);

                try {
                    commentsDao.save(comment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(page.getUrl().toString() + ":评论 <" + comment.getContent() + "> 已经入库");
            }
        }
    }

    public void startSpider() {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待...");
        startTime = System.currentTimeMillis();
        // 开启5个线程，启动爬虫
        Spider.create(this).addUrl(this.getFirstSite()).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查看！");
    }

}
