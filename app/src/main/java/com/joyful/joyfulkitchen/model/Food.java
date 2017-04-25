package com.joyful.joyfulkitchen.model;


import com.joyful.joyfulkitchen.dao.DaoSession;
import com.joyful.joyfulkitchen.dao.FoodDao;
import com.joyful.joyfulkitchen.dao.FoodTypeDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

@Entity(nameInDb = "tb_food")
public class Food {

    @Id(autoincrement = true)
    @Property(nameInDb = "food_id")
    private Long foodId;                        // INTEGER PRIMARY KEY AUTO_INCREMENT,

    @Property(nameInDb = "food_name")
    private String foodName;                    // VARCHAR(30),

    @Property(nameInDb = "food_img")
    private String foodImg;                    // VARCHAR(50),   -- 食物图片

    private double energy;                       // FLOAT,        -- 热量(每一百克)(Kcal 千卡)
    private double protein;                      //   FLOAT,      -- 蛋白(g)
    private double fat;                          // FLOAT ,       -- 脂肪(g)
    private double carbohydrate;                // FLOAT,        --  碳水化合物(g)
    private double fiber;                        // FLOAT ,       -- 纤维(g)
    private double cholesterol;                  // FLOAT,       -- 胆固醇(毫克)(g)

    @Property(nameInDb = "food_type_id")
    private Long foodTypeId;
    @ToOne(joinProperty = "foodTypeId")
    private FoodType foodType;                  //  INTEGER  ,   -- 食材类型


    @Property(nameInDb = "create_time")
    private Date createTime;                    // DATETIME,     -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;                    // DATETIME      -- 更新时间

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1296197325)
    private transient FoodDao myDao;

    @Generated(hash = 2122643115)
    public Food(Long foodId, String foodName, String foodImg, double energy, double protein,
            double fat, double carbohydrate, double fiber, double cholesterol,
            Long foodTypeId, Date createTime, Date updateTime) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImg = foodImg;
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.fiber = fiber;
        this.cholesterol = cholesterol;
        this.foodTypeId = foodTypeId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 866324199)
    public Food() {
    }

    public Long getFoodId() {
        return this.foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImg() {
        return this.foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public double getEnergy() {
        return this.energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getProtein() {
        return this.protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return this.fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return this.carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getFiber() {
        return this.fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getCholesterol() {
        return this.cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Long getFoodTypeId() {
        return this.foodTypeId;
    }

    public void setFoodTypeId(Long foodTypeId) {
        this.foodTypeId = foodTypeId;
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

    @Generated(hash = 107578111)
    private transient Long foodType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1851720693)
    public FoodType getFoodType() {
        Long __key = this.foodTypeId;
        if (foodType__resolvedKey == null || !foodType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FoodTypeDao targetDao = daoSession.getFoodTypeDao();
            FoodType foodTypeNew = targetDao.load(__key);
            synchronized (this) {
                foodType = foodTypeNew;
                foodType__resolvedKey = __key;
            }
        }
        return foodType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 329501633)
    public void setFoodType(FoodType foodType) {
        synchronized (this) {
            this.foodType = foodType;
            foodTypeId = foodType == null ? null : foodType.getFoodTypeId();
            foodType__resolvedKey = foodTypeId;
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
    @Generated(hash = 505459956)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFoodDao() : null;
    }


}
