package spider;

import db.BadgeDao;
import db.MedalDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import po.Badge;
import po.Medal;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by ywcrm on 2017/6/7.
 */
public class MedalAndBadge implements PageProcessor {

    private String url = "http://bbs.xiaomi.cn/medal/list";

    //   抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private String medalPagePattern = "http://bbs.xiaomi.cn/medal/list";

    private BadgeDao badgeDao = new BadgeDao();
    private MedalDao medalDao = new MedalDao();

    /**
     * 抓取medal和badge信息 http://bbs.xiaomi.cn/medal/list
     */
    public void getMedalAndBadge() {
        startSpider();
    }

    public void process(Page page) {
        if (page.getUrl().regex(this.medalPagePattern).match()) { //奖牌页面
            processMedalPage(page);
        }
    }

    /**
     * 处理奖牌页面
     *
     * @param page
     */
    private void processMedalPage(Page page) {

        List<String> badgeHtmlList = page.getHtml()
                .xpath("//div[@class='con achievement clearfix']/dl").all();
        for (String badgeHtml : badgeHtmlList) {
            Document document = Jsoup.parse(badgeHtml);
            Badge badge = new Badge();
            badge.setImageUrl(document.select("img").attr("src"));

            List<Badge> list = badgeDao.findByPropertyEqual("imageUrl", badge.getImageUrl(), "String");
            if (list == null || list.size() == 0) {
                badge.setName(document.select("span.name").text());
                badge.setDescription(document.select("span.infor").text());
                badgeDao.save(badge);
            }

        }

        List<String> medalHtmlList = page.getHtml()
                .xpath("//div[@class='con clearfix']/dl").all();
        for (String medalHtml : medalHtmlList) {
            Document document = Jsoup.parse(medalHtml);
            Medal medal = new Medal();
            medal.setImageUrl(document.select("img").attr("src"));

            List<Medal> medalList = medalDao.findByPropertyEqual("imageUrl", medal.getImageUrl(), "String");
            if (medalList == null || medalList.size() == 0) {
                medal.setName(document.select("span").text());
                medal.setDetailUrl(document.select("a").attr("href"));
                medalDao.save(medal);
            }
        }
    }

    public Site getSite() {
        return site;
    }

    public void startSpider() {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待...");
        startTime = System.currentTimeMillis();
        // 开启5个线程，启动爬虫
        Spider.create(this).addUrl(this.url).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查看！");
    }

    public static void main(String[] args) {
        MedalAndBadge medalAndBadge = new MedalAndBadge();
        medalAndBadge.getMedalAndBadge();
    }
}
