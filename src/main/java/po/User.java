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
public class User {
    private long id;
    private String homePage;
    private String name;
    private String vipLevel;
    private Integer contribute;
    private Integer vipRank;
    private int score;
    private Long miid;
    private String userGroup;
    private String email;
    private Integer topicNum;
    private Integer replyNum;
    private Integer exp;
    private Timestamp lastActiveTime;
    private Timestamp registerTime;
    private Timestamp lastAccessTime;
    private Timestamp lastDeliverTime;
    private String medalIds;
    private String badgeIds;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "contribute")
    public Integer getContribute() {
        return contribute;
    }

    public void setContribute(Integer contribute) {
        this.contribute = contribute;
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
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "miid")
    public Long getMiid() {
        return miid;
    }

    public void setMiid(Long miid) {
        this.miid = miid;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "exp")
    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
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

    @Basic
    @Column(name = "medal_ids")
    public String getMedalIds() {
        return medalIds;
    }

    public void setMedalIds(String medalIds) {
        this.medalIds = medalIds;
    }

    @Basic
    @Column(name = "badge_ids")
    public String getBadgeIds() {
        return badgeIds;
    }

    public void setBadgeIds(String badgeIds) {
        this.badgeIds = badgeIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (score != user.score) return false;
        if (homePage != null ? !homePage.equals(user.homePage) : user.homePage != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (vipLevel != null ? !vipLevel.equals(user.vipLevel) : user.vipLevel != null) return false;
        if (contribute != null ? !contribute.equals(user.contribute) : user.contribute != null) return false;
        if (vipRank != null ? !vipRank.equals(user.vipRank) : user.vipRank != null) return false;
        if (miid != null ? !miid.equals(user.miid) : user.miid != null) return false;
        if (userGroup != null ? !userGroup.equals(user.userGroup) : user.userGroup != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (topicNum != null ? !topicNum.equals(user.topicNum) : user.topicNum != null) return false;
        if (replyNum != null ? !replyNum.equals(user.replyNum) : user.replyNum != null) return false;
        if (exp != null ? !exp.equals(user.exp) : user.exp != null) return false;
        if (lastActiveTime != null ? !lastActiveTime.equals(user.lastActiveTime) : user.lastActiveTime != null)
            return false;
        if (registerTime != null ? !registerTime.equals(user.registerTime) : user.registerTime != null) return false;
        if (lastAccessTime != null ? !lastAccessTime.equals(user.lastAccessTime) : user.lastAccessTime != null)
            return false;
        if (lastDeliverTime != null ? !lastDeliverTime.equals(user.lastDeliverTime) : user.lastDeliverTime != null)
            return false;
        if (medalIds != null ? !medalIds.equals(user.medalIds) : user.medalIds != null) return false;
        if (badgeIds != null ? !badgeIds.equals(user.badgeIds) : user.badgeIds != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (homePage != null ? homePage.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (vipLevel != null ? vipLevel.hashCode() : 0);
        result = 31 * result + (contribute != null ? contribute.hashCode() : 0);
        result = 31 * result + (vipRank != null ? vipRank.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (miid != null ? miid.hashCode() : 0);
        result = 31 * result + (userGroup != null ? userGroup.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (topicNum != null ? topicNum.hashCode() : 0);
        result = 31 * result + (replyNum != null ? replyNum.hashCode() : 0);
        result = 31 * result + (exp != null ? exp.hashCode() : 0);
        result = 31 * result + (lastActiveTime != null ? lastActiveTime.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + (lastAccessTime != null ? lastAccessTime.hashCode() : 0);
        result = 31 * result + (lastDeliverTime != null ? lastDeliverTime.hashCode() : 0);
        result = 31 * result + (medalIds != null ? medalIds.hashCode() : 0);
        result = 31 * result + (badgeIds != null ? badgeIds.hashCode() : 0);
        return result;
    }
}
