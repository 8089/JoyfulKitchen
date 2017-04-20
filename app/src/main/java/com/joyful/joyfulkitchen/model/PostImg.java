package com.joyful.joyfulkitchen.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.joyful.joyfulkitchen.dao.DaoSession;
import com.joyful.joyfulkitchen.dao.PostDao;
import com.joyful.joyfulkitchen.dao.PostImgDao;

/*帖子图片*/
@Entity(nameInDb = "tb_post_img")
public class PostImg {

    @Id(autoincrement = true)
    @Property(nameInDb = "post_img_id")
    private Long postImgId;                     // INTEGER PRIMARY KEY AUTO_INCREMENT,
    @Property(nameInDb = "img_url")
    private String imgUrl;                      // VARCHAR(50),                  -- 图片地址

    @Property(nameInDb = "post_id")
    private Long postId;                        // INTEGER,                      -- post (外键)
    @ToOne(joinProperty = "postId")
    private Post post;

    @Property(nameInDb = "create_time")
    private Date createTime;                    // DATETIME,                 -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;                    // DATETIME                  -- 更新时间
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1646232939)
    private transient PostImgDao myDao;

    @Generated(hash = 1259191568)
    public PostImg(Long postImgId, String imgUrl, Long postId, Date createTime,
            Date updateTime) {
        this.postImgId = postImgId;
        this.imgUrl = imgUrl;
        this.postId = postId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 434105540)
    public PostImg() {
    }

    public Long getPostImgId() {
        return this.postImgId;
    }

    public void setPostImgId(Long postImgId) {
        this.postImgId = postImgId;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    @Generated(hash = 1690682906)
    private transient Long post__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1638466291)
    public Post getPost() {
        Long __key = this.postId;
        if (post__resolvedKey == null || !post__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PostDao targetDao = daoSession.getPostDao();
            Post postNew = targetDao.load(__key);
            synchronized (this) {
                post = postNew;
                post__resolvedKey = __key;
            }
        }
        return post;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 47794615)
    public void setPost(Post post) {
        synchronized (this) {
            this.post = post;
            postId = post == null ? null : post.getPostId();
            post__resolvedKey = postId;
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
    @Generated(hash = 1337617567)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPostImgDao() : null;
    }


}
