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

public class Xiaomi6PageProcessor extends BaseProcessor {

    public Xiaomi6PageProcessor() {
        this.setFirstSite("http://bbs.xiaomi.cn/f-487-1");
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }

}