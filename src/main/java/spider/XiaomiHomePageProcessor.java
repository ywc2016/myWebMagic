package spider;

import db.CommentsDao;
import db.CrawledUrlDao;
import db.PostsDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import po.Comments;
import po.Posts;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSDN博客爬虫
 *
 * @author steven
 * @describe 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 * @date 2016-4-30
 * @csdn qq598535550
 * @website lyf.soecode.com
 */
public class XiaomiHomePageProcessor implements PageProcessor {
    //   抓取的论坛首页
    private String firstSite = "http://bbs.xiaomi.cn/d-1";
    private String domain = "http://bbs.xiaomi.cn";
    //   抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private PostsDao postsDao = new PostsDao();
    private CommentsDao commentsDao = new CommentsDao();
    private CrawledUrlDao crawledUrlDao = new CrawledUrlDao();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String patternWithYear = "\\s*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}\\s*";
    private String patternWithoutYear = "\\s*\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}\\s*";
    private String patterOfSecondPageOfPost = "(" + this.domain + "/t-\\d+)-\\d+-o1#comment_top";
    private Calendar c = Calendar.getInstance();
    private int curYear = c.get(Calendar.YEAR);

    public String getFirstSite() {
        return firstSite;
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
        if (page.getUrl().regex(domain + "/d\\-\\d+$").match()) { //不是帖子且是帖子列表页面
            try {
                // 添加下一页
                String url = page.getHtml().xpath("//li[@class='next']/a/@href").all().get(0);
                page.addTargetRequest(url);
                crawledUrlDao.add(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                // 添加该页中所有的帖子进入队列
                List<String> addUrlList = page.getHtml().xpath("//div[@class='theme_list_con']/div[@class='title']/a/@href").all();
                page.addTargetRequests(addUrlList);// 限定文章列表获取区域
                crawledUrlDao.addAll(addUrlList);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            page.addTargetRequest(page.getHtml().xpath("//li[@class='theme_list clearfix']/div[@class='theme_list_con']/div[@class='title']/a/@href").all().get(0));


        } else if (page.getUrl().regex(domain + "/t\\-\\d+$").match()) { // 帖子首页
            try {
                //是第一页,添加一条帖子信息
                Posts posts = new Posts();
                posts.setUrl(page.getUrl().toString());
                posts.setTitle(page.getHtml().xpath("//div[@class='invitation_con']/h1/span[@class='name']/text()").all().get(0).toString());
                posts.setStamps(listToString(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='marktxt']/text()").all()));
                try {
                    posts.setUserName(page.getHtml().xpath("//a[@class='user_name']/text()").all().get(0));
                } catch (Exception e) {
                    System.out.println("用户名获取失败!");
                    e.printStackTrace();
                }
                posts.setUserPage(page.getHtml().xpath("//div[@class='personLayer_msg']/a[@class='user_head']/@href").all().get(0));
                posts.setPlate(page.getHtml().xpath("//div[@class='plateinfor']/a[@class='platename']/text()").all().get(0));
                posts.setSecondary_plate(page.getHtml().xpath("//div[@class='invitation_con']/p[@class='txt']/a[@class='name']/text()").all().get(0));
                try {
                    String dateStr = page.getHtml()
                            .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='time']/text()").all().get(0);
                    if (Pattern.matches(patternWithoutYear, dateStr)) {
                        posts.setTime(sdf.parse(curYear + "-" + dateStr.substring(1)));
                    } else if (Pattern.matches(patternWithYear, dateStr)) {
                        posts.setTime(sdf.parse(dateStr));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                posts.setComefrom(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='comefrom']/text()").all().get(0));
                posts.setCheckAmount(Integer.valueOf(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='f_r']/text()").all().get(1)));
                posts.setCommentsAmount(Integer.valueOf(page.getHtml()
                        .xpath("//div[@class='invitation_con']/p[@class='txt']/span[@class='f_r']/text()").all().get(0)));
                try {
                    String contextHtml = page.getHtml().xpath("//div[@class='invitation_content']").all().get(0);
                    Document doc = Jsoup.parse(contextHtml);
                    posts.setContent(doc.text());
                } catch (Exception e) {
                    System.out.println("没有帖子内容!");
                    e.printStackTrace();
                }
                postsDao.add(posts);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //抓取回复内容
            try {
                getComments(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (page.getUrl().regex(this.domain + "/t-\\d+-\\d+-o1#comment_top").match()) {//如果是帖子的第二页或者之后的页
            try {
                getComments(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            System.out.println("没有下一页评论!");
            e.printStackTrace();
        }
        String postUrl = null;
        if (page.getUrl().regex(domain + "/t\\-\\d+$").match()) {
            postUrl = page.getUrl().all().get(0);
        } else if (page.getUrl().regex(this.domain + "/t-\\d+-\\d+-o1#comment_top").match()) {
            Pattern pattern = Pattern.compile(this.patterOfSecondPageOfPost);
            Matcher m = pattern.matcher(page.getUrl().all().get(0));
            for (; m.find(); ) {
                postUrl = m.group(1);
            }
        }
        long postId = postsDao.findIdByField(String.class, "url", postUrl);
        List<String> list = page.getHtml().xpath("//ul[@class='reply_list']/").all();
        for (String html : list) {
            Document doc = Jsoup.parse(html);
            Comments comment = new Comments();
            comment.setUserName(doc.select("a.auth_name").get(0).text());
            comment.setUserPage(doc.select("a.auth_name").get(0).attr("href"));
            comment.setComefrom(doc.select("span.comefrom").get(0).text());
            try {
                String dateStr = doc.select("span.time").get(1).text();
                if (Pattern.matches(patternWithoutYear, dateStr)) {
                    comment.setTime(sdf.parse(curYear + "-" + dateStr.substring(1)));
                } else if (Pattern.matches(patternWithYear, dateStr)) {
                    comment.setTime(sdf.parse(dateStr));
                }
            } catch (ParseException e) {
                System.out.println("日期字符串有误! " + doc.select("span.time").get(1).text());
                e.printStackTrace();
            }
            try {
                comment.setContent(doc.select("div.reply_txt").get(0).text());
            } catch (Exception e) {
                System.out.println("没有评论内容!");
                e.printStackTrace();
            }
            comment.setPostId(postId);
            commentsDao.add(comment);
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


}