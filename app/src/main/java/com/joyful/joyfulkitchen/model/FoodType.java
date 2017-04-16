package com.joyful.joyfulkitchen.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;


/*食物类型*/
@Entity(nameInDb = "tb_food_type")
public class FoodType {

    @Id(autoincrement = true)
    @Property(nameInDb = "food_type_id")
    private Long foodTypeId;                // INTEGER PRIMARY KEY ,   -- 编号

    @Property(nameInDb = "type_name")
    private String typeName;                // VARCHAR(30) ,           -- 食物类型名称

    @Property(nameInDb = "food_type_img")
    private String foodTypeImg;             // VARCHAR(50),            -- 食物类型图片

    @Property(nameInDb = "create_time")
    private Date createTime;                // DATETIME,                 -- 创建时间

    @Property(nameInDb = "update_time")
    private Date updateTime;                // DATETIME                  -- 更新时间

    @Generated(hash = 828776444)
    public FoodType(Long foodTypeId, String typeName, String foodTypeImg,
            Date createTime, Date updateTime) {
        this.foodTypeId = foodTypeId;
        this.typeName = typeName;
        this.foodTypeImg = foodTypeImg;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 2140850097)
    public FoodType() {
    }

    public Long getFoodTypeId() {
        return this.foodTypeId;
    }

    public void setFoodTypeId(Long foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFoodTypeImg() {
        return this.foodTypeImg;
    }

    public void setFoodTypeImg(String foodTypeImg) {
        this.foodTypeImg = foodTypeImg;
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

}
