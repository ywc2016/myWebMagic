package po;

import java.util.Date;


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

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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


}