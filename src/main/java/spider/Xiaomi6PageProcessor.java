package spider;

public class Xiaomi6PageProcessor extends BaseProcessor {

    public Xiaomi6PageProcessor() {
        this.setFirstSite("http://bbs.xiaomi.cn/f-487-1");
        this.setPostListPageUrlPattern(this.getDomain() + "/f\\-\\d+\\-\\d+$");
        this.setFirstPageOfPostUrlPattern(this.getDomain() + "/t\\-\\d+$");
        this.setSecondPageOfPostUrlPattern(this.getDomain() + "/t-\\d+-\\d+-o1#comment_top");
    }


}