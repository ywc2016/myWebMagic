package po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;


@Entity
public class User {

    private long id;
    private String home_page;
    private String name;
    private String vip_level;
    private int contribute;
    private int vip_rank;
    private int score;
    private long miid;
    private String user_group;
    private String email;
    private int topic_num;
    private int reply_num;
    private int exp;
    private Date last_active_time;
    private Date register_time;
    private Date last_access_time;
    private Date last_deliver_time;
    private String homePage;
    private String vipLevel;
    private Integer vipRank;
    private String userGroup;
    private Integer topicNum;
    private Integer replyNum;
    private Timestamp lastActiveTime;
    private Timestamp registerTime;
    private Timestamp lastAccessTime;
    private Timestamp lastDeliverTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContribute(Integer contribute) {
        this.contribute = contribute;
    }

    public void setMiid(Long miid) {
        this.miid = miid;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    @Basic
    @Column(name = "contribute")
    public int getContribute() {
        return contribute;
    }

    public void setContribute(int contribute) {
        this.contribute = contribute;
    }

    public int getVip_rank() {
        return vip_rank;
    }

    public void setVip_rank(int vip_rank) {
        this.vip_rank = vip_rank;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "miid")
    public long getMiid() {
        return miid;
    }

    public void setMiid(long miid) {
        this.miid = miid;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }

    public int getReply_num() {
        return reply_num;
    }

    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }

    @Basic
    @Column(name = "exp")
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Date getLast_active_time() {
        return last_active_time;
    }

    public void setLast_active_time(Date last_active_time) {
        this.last_active_time = last_active_time;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public Date getLast_access_time() {
        return last_access_time;
    }

    public void setLast_access_time(Date last_access_time) {
        this.last_access_time = last_access_time;
    }

    public Date getLast_deliver_time() {
        return last_deliver_time;
    }

    public void setLast_deliver_time(Date last_deliver_time) {
        this.last_deliver_time = last_deliver_time;
    }

    @Basic
    @Column(name = "home_page")
    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    @Basic
    @Column(name = "vip_level")
    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Basic
    @Column(name = "vip_rank")
    public Integer getVipRank() {
        return vipRank;
    }

    public void setVipRank(Integer vipRank) {
        this.vipRank = vipRank;
    }

    @Basic
    @Column(name = "user_group")
    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @Basic
    @Column(name = "topic_num")
    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }

    @Basic
    @Column(name = "reply_num")
    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    @Basic
    @Column(name = "last_active_time")
    public Timestamp getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Timestamp lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @Basic
    @Column(name = "register_time")
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "last_access_time")
    public Timestamp getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Timestamp lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Basic
    @Column(name = "last_deliver_time")
    public Timestamp getLastDeliverTime() {
        return lastDeliverTime;
    }

    public void setLastDeliverTime(Timestamp lastDeliverTime) {
        this.lastDeliverTime = lastDeliverTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (contribute != user.contribute) return false;
        if (score != user.score) return false;
        if (miid != user.miid) return false;
        if (exp != user.exp) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (homePage != null ? !homePage.equals(user.homePage) : user.homePage != null) return false;
        if (vipLevel != null ? !vipLevel.equals(user.vipLevel) : user.vipLevel != null) return false;
        if (vipRank != null ? !vipRank.equals(user.vipRank) : user.vipRank != null) return false;
        if (userGroup != null ? !userGroup.equals(user.userGroup) : user.userGroup != null) return false;
        if (topicNum != null ? !topicNum.equals(user.topicNum) : user.topicNum != null) return false;
        if (replyNum != null ? !replyNum.equals(user.replyNum) : user.replyNum != null) return false;
        if (lastActiveTime != null ? !lastActiveTime.equals(user.lastActiveTime) : user.lastActiveTime != null)
            return false;
        if (registerTime != null ? !registerTime.equals(user.registerTime) : user.registerTime != null) return false;
        if (lastAccessTime != null ? !lastAccessTime.equals(user.lastAccessTime) : user.lastAccessTime != null)
            return false;
        if (lastDeliverTime != null ? !lastDeliverTime.equals(user.lastDeliverTime) : user.lastDeliverTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (homePage != null ? homePage.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (vipLevel != null ? vipLevel.hashCode() : 0);
        result = 31 * result + contribute;
        result = 31 * result + (vipRank != null ? vipRank.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (int) (miid ^ (miid >>> 32));
        result = 31 * result + (userGroup != null ? userGroup.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (topicNum != null ? topicNum.hashCode() : 0);
        result = 31 * result + (replyNum != null ? replyNum.hashCode() : 0);
        result = 31 * result + exp;
        result = 31 * result + (lastActiveTime != null ? lastActiveTime.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + (lastAccessTime != null ? lastAccessTime.hashCode() : 0);
        result = 31 * result + (lastDeliverTime != null ? lastDeliverTime.hashCode() : 0);
        return result;
    }
}