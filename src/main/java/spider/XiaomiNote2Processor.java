package spider;

/**
 *
 */
public class XiaomiNote2Processor extends BaseProcessor {

    public XiaomiNote2Processor() {
        this.setFirstSite("http://bbs.xiaomi.cn/f-385-1");
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }
}