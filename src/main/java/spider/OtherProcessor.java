package spider;

/**
 * @author yuwc
 */
public class OtherProcessor extends BaseProcessor {

    public OtherProcessor() {
        this.setFirstSite("http://bbs.xiaomi.cn/f-451-1");//小米5/5c/5s/Plus
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }

    public OtherProcessor(String firstPlateUrl) {
        this.setFirstSite(firstPlateUrl);//小米5/5c/5s/Plus
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }
}