package com.joyful.joyfulkitchen.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.joyful.joyfulkitchen.dao.DaoSession;
import com.joyful.joyfulkitchen.dao.PostImgDao;
import com.joyful.joyfulkitchen.dao.PostDao;
import com.joyful.joyfulkitchen.dao.UserDao;

/*帖子*/
@Entity(nameInDb = "tb_post")
public class Post {

    @Id(autoincrement = true)
    @Property(nameInDb = "post_id")
    private Long postId;                // INTEGER PRIMARY KEY AUTO_INCREMENT,

    private String message;             // VARCHAR(255) ,
    private int  browse;                // INTEGER ,                     -- 浏览数
    private int comments;               // INTEGER ,                     -- 评论数
    private int praise;                 // INTEGER,                      -- 点赞数

    @Property(nameInDb = "user_id")
    private Long userId;                // INTEGER,                      -- 用户(外键)
    @ToOne(joinProperty = "userId")
    private User user;

    @Property(nameInDb = "create_time")
    private Date createTime;            // DATETIME,                    -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;            // DATETIME                     -- 更新时间

    @ToMany(referencedJoinProperty = "postId")
    private List<PostImg> postImgList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 572315894)
    private transient PostDao myDao;

    @Generated(hash = 1610809834)
    public Post(Long postId, String message, int browse, int comments, int praise,
            Long userId, Date createTime, Date updateTime) {
        this.postId = postId;
        this.message = message;
        this.browse = browse;
        this.comments = comments;
        this.praise = praise;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 1782702645)
    public Post() {
    }

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBrowse() {
        return this.browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public int getComments() {
        return this.comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getPraise() {
        return this.praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 944512365)
    public List<PostImg> getPostImgList() {
        if (postImgList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PostImgDao targetDao = daoSession.getPostImgDao();
            List<PostImg> postImgListNew = targetDao._queryPost_PostImgList(postId);
            synchronized (this) {
                if (postImgList == null) {
                    postImgList = postImgListNew;
                }
            }
        }
        return postImgList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1451246721)
    public synchronized void resetPostImgList() {
        postImgList = null;
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
    @Generated(hash = 1915117241)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPostDao() : null;
    }


}
