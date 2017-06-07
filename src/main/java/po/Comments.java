package po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by ywcrm on 2017/6/7.
 */
@Entity
public class Comments {
    private long id;
    private String userName;
    private String userPage;
    private String comefrom;
    private Timestamp time;
    private String content;
    private Long postId;
    private String contentHash;

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
    @Column(name = "comefrom")
    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "content_hash")
    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        if (id != comments.id) return false;
        if (userName != null ? !userName.equals(comments.userName) : comments.userName != null) return false;
        if (userPage != null ? !userPage.equals(comments.userPage) : comments.userPage != null) return false;
        if (comefrom != null ? !comefrom.equals(comments.comefrom) : comments.comefrom != null) return false;
        if (time != null ? !time.equals(comments.time) : comments.time != null) return false;
        if (content != null ? !content.equals(comments.content) : comments.content != null) return false;
        if (postId != null ? !postId.equals(comments.postId) : comments.postId != null) return false;
        if (contentHash != null ? !contentHash.equals(comments.contentHash) : comments.contentHash != null)
            return false;

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
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (contentHash != null ? contentHash.hashCode() : 0);
        return result;
    }
}
