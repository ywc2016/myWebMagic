package po;

import java.util.Date;


public class Posts {

    private long id;
    private String title;
    private String stamps;
    private String userName;
    private String userPage;
    private String plate;
    private String secondary_plate;
    private Date time;
    private String comefrom;
    private int checkAmount;
    private int commentsAmount;
    private String content;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStamps() {
        return stamps;
    }

    public void setStamps(String stamps) {
        this.stamps = stamps;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPage() {
        return userPage;
    }

    public void setUserPage(String userPage) {
        this.userPage = userPage;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getSecondary_plate() {
        return secondary_plate;
    }

    public void setSecondary_plate(String secondary_plate) {
        this.secondary_plate = secondary_plate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public int getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(int checkAmount) {
        this.checkAmount = checkAmount;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CsdnBlog [id=" + id + ", title=" + title + ", stamps=" + stamps + ", user_name=" + userName + ", userPage="
                + userPage + ", plate=" + plate + ", secondaryPlate=" + secondary_plate + ", time=" + time.toString()
                + ", comefrom=" + comefrom + ", checkAmount=" + checkAmount + ", commentsAmount=" + commentsAmount + ", content="
                + content + "]";
    }

}