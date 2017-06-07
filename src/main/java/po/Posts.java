package po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;


@Entity
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
    private String secondaryPlate;

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setCheckAmount(Integer checkAmount) {
        this.checkAmount = checkAmount;
    }

    public void setCommentsAmount(Integer commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "stamps")
    public String getStamps() {
        return stamps;
    }

    public void setStamps(String stamps) {
        this.stamps = stamps;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_page")
    public String getUserPage() {
        return userPage;
    }

    public void setUserPage(String userPage) {
        this.userPage = userPage;
    }

    @Basic
    @Column(name = "plate")
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

    @Basic
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "comefrom")
    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    @Basic
    @Column(name = "check_amount")
    public int getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(int checkAmount) {
        this.checkAmount = checkAmount;
    }

    @Basic
    @Column(name = "comments_amount")
    public int getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "url")
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

    @Basic
    @Column(name = "secondary_plate")
    public String getSecondaryPlate() {
        return secondaryPlate;
    }

    public void setSecondaryPlate(String secondaryPlate) {
        this.secondaryPlate = secondaryPlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posts posts = (Posts) o;

        if (id != posts.id) return false;
        if (checkAmount != posts.checkAmount) return false;
        if (commentsAmount != posts.commentsAmount) return false;
        if (title != null ? !title.equals(posts.title) : posts.title != null) return false;
        if (stamps != null ? !stamps.equals(posts.stamps) : posts.stamps != null) return false;
        if (userName != null ? !userName.equals(posts.userName) : posts.userName != null) return false;
        if (userPage != null ? !userPage.equals(posts.userPage) : posts.userPage != null) return false;
        if (plate != null ? !plate.equals(posts.plate) : posts.plate != null) return false;
        if (time != null ? !time.equals(posts.time) : posts.time != null) return false;
        if (comefrom != null ? !comefrom.equals(posts.comefrom) : posts.comefrom != null) return false;
        if (content != null ? !content.equals(posts.content) : posts.content != null) return false;
        if (url != null ? !url.equals(posts.url) : posts.url != null) return false;
        if (secondaryPlate != null ? !secondaryPlate.equals(posts.secondaryPlate) : posts.secondaryPlate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (stamps != null ? stamps.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPage != null ? userPage.hashCode() : 0);
        result = 31 * result + (plate != null ? plate.hashCode() : 0);
        result = 31 * result + (secondaryPlate != null ? secondaryPlate.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (comefrom != null ? comefrom.hashCode() : 0);
        result = 31 * result + checkAmount;
        result = 31 * result + commentsAmount;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}