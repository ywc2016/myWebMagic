package po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;


@Entity
public class Comments {

    private long id;
    private String userName;
    private String userPage;
    private Date time;
    private String comefrom;
    private String content;
    private long postId;

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "post_id")
    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CsdnBlog [id=" + id + ", userName=" + userName + ", userPage=" + userPage + ", comefrom="
                + comefrom + ", time=" + time + ", content=" + content + ", postId=" + postId + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        if (id != comments.id) return false;
        if (postId != comments.postId) return false;
        if (userName != null ? !userName.equals(comments.userName) : comments.userName != null) return false;
        if (userPage != null ? !userPage.equals(comments.userPage) : comments.userPage != null) return false;
        if (time != null ? !time.equals(comments.time) : comments.time != null) return false;
        if (comefrom != null ? !comefrom.equals(comments.comefrom) : comments.comefrom != null) return false;
        if (content != null ? !content.equals(comments.content) : comments.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPage != null ? userPage.hashCode() : 0);
        result = 31 * result + (comefrom != null ? comefrom.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (postId ^ (postId >>> 32));
        return result;
    }
}