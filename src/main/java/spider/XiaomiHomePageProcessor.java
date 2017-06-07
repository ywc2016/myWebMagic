package spider;

/**
 *
 */
public class XiaomiHomePageProcessor extends BaseProcessor {


    public XiaomiHomePageProcessor() {
        this.setFirstSite("http://bbs.xiaomi.cn/d-1");
        this.setPostListPageUrlPattern(this.getDomain() + "/d\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }


}