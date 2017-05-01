package com.joyful.joyfulkitchen.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import java.util.Date;

//@Entity 用于标识这是一个需要Greendao帮我们生成代码的bean
//@Id 标明主键，括号里可以指定是否自增
//@Property 用于设置属性在数据库中的列名（默认不写就是保持一致）
//@NotNull 非空
//@Transient 标识这个字段是自定义的不会创建到数据库表里
//@Unique 添加唯一约束
//@ToOne 是将自己的一个属性与另一个表建立关联
//@ToMany的属性referencedJoinProperty，类似于外键约束。
//@JoinProperty 对于更复杂的关系，可以使用这个注解标明目标属性的源属性。

@Entity(nameInDb = "tb_user")
@Keep
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    @Property(nameInDb = "user_id")
    private Long userId;                   // INTEGER PRIMARY KEY AUTO_INCREMENT,

    @Property(nameInDb = "nickname")
    private String nickName;                //  VARCHAR(30) 昵称

    private String phone;                   // VARCHAR(20)  ,                   -- 电话号码
    private String email;                   // VARCHAR(20)                      -- email
    private String pwd;                     //VARCHAR(20),                      -- 密码
    private String img;                     //VARCHAR(50),                      -- 头像
    private Date birth;                     // Date ,                 		     -- 年龄
    private int sex;                        //BIT ,                             -- 性别
    private String city;                    // VARCHAR(20),                     -- 城市
    private String country;                 // VARCHAR(20) ,                    -- 国家
    private double weight;                  // FLOAT ,                           -- 体重
    private double heigth;                  //FLOAT ,                            -- 身高
    private int target;                    //INTEGER,                           -- 目标
    private int power;                     //INTEGER  ,                         -- 工作强度
    private int active;                    //INTEGER   ,                        -- 是否激活
    private String token;                  //VARCHAR(50) ,                       -- token 码


    @Property(nameInDb = "create_time")
    private Date createTime;             // DATETIME,                 -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;             // DATETIME                  -- 更新时间

    @Generated(hash = 2112966527)
    public User(Long userId, String nickName, String phone, String email, String pwd,
            String img, Date birth, int sex, String city, String country, double weight,
            double heigth, int target, int power, int active, String token, Date createTime,
            Date updateTime) {
        this.userId = userId;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.pwd = pwd;
        this.img = img;
        this.birth = birth;
        this.sex = sex;
        this.city = city;
        this.country = country;
        this.weight = weight;
        this.heigth = heigth;
        this.target = target;
        this.power = power;
        this.active = active;
        this.token = token;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeigth() {
        return this.heigth;
    }

    public void setHeigth(double heigth) {
        this.heigth = heigth;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", img='" + img + '\'' +
                ", birth=" + birth +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", weight=" + weight +
                ", heigth=" + heigth +
                ", target=" + target +
                ", power=" + power +
                ", active=" + active +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}