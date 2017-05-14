package po;

import java.util.Date;


public class Comments {

    private long id;
    private String userName;
    private String userPage;
    private Date time;
    private String comefrom;
    private String content;
    private long postId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

}