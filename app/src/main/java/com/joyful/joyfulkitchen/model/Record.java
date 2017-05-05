package com.joyful.joyfulkitchen.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.joyful.joyfulkitchen.dao.DaoSession;
import com.joyful.joyfulkitchen.dao.FoodDao;
import com.joyful.joyfulkitchen.dao.RecordDao;
import com.joyful.joyfulkitchen.dao.UserDao;

/*记录称量*/
@Entity(nameInDb = "tb_record")
public class Record {

    @Id(autoincrement = true)
    @Property(nameInDb = "record_id")
    private Long record_id;         // INTEGER PRIMARY KEY AUTO_INCREMENT, -- 编号

    @Property(nameInDb = "total_weight")
    private double totalWeight;     // FLOAT ,                          -- 该食材 总重量

    @Property(nameInDb = "total_energy")
    private double totalEnergy;      // FLOAT,                           -- 该食材 总热量

    @Property(nameInDb = "meau_name")
    private String meauName;        // VARCHAR(30),                        -- 通过 菜名 查询 所具食材称量记录

    @Property(nameInDb = "create_time")
    private Date createTime;        // DATETIME,                           -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;        // DATETIME                          -- 更新时间

    @Property(nameInDb = "user_id")
    private Long userId;
    @ToOne(joinProperty = "userId")
    private User user;              // INTEGER ,                              -- 用户编号

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 765166123)
    private transient RecordDao myDao;
    @Generated(hash = 1855573095)
    public Record(Long record_id, double totalWeight, double totalEnergy, String meauName,
            Date createTime, Date updateTime, Long userId) {
        this.record_id = record_id;
        this.totalWeight = totalWeight;
        this.totalEnergy = totalEnergy;
        this.meauName = meauName;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userId = userId;
    }
    @Generated(hash = 477726293)
    public Record() {
    }
    public Long getRecord_id() {
        return this.record_id;
    }
    public void setRecord_id(Long record_id) {
        this.record_id = record_id;
    }
    public double getTotalWeight() {
        return this.totalWeight;
    }
    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }
    public double getTotalEnergy() {
        return this.totalEnergy;
    }
    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }
    public String getMeauName() {
        return this.meauName;
    }
    public void setMeauName(String meauName) {
        this.meauName = meauName;
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
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 496399742)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getUserId();
            user__resolvedKey = userId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1505145191)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecordDao() : null;
    }

    
}
