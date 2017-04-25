package com.joyful.joyfulkitchen.util;

import com.joyful.joyfulkitchen.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/18.
 */

public class HeatUtilImpl implements BaseHeatUtil {


    /**  根据用户信息， 获取每日所需要的热量 */
    @Override
    public float getDailyHeat(User user) {

        int age = getAge(user.getBirth());
        float BMR = getBMR(user.getSex(), user.getHeigth(), user.getWeight(), age);
        return getHeatByPower(user.getPower(), BMR);

    }

    // 1千卡=1大卡=1000卡=1000卡路里 =4186焦耳=4.186千焦。
    //  1千卡转换成 焦耳
    @Override
    public float kcal2Joule(float kcal) {
        return kcal * 1000 * 4.184f;
    }


    /**
     * 使用Harris Benedict Formula，将你的BMR乘以活动系数（如下）：
     几乎不动 Calorie-Calculation = BMR x 1.2
     稍微运动（每周1-3次）总需 = BMR x 1.375
     中度运动（每周3-5次）总需 = BMR x 1.55
     积极运动（每周6-7次）总需 = BMR x 1.725
     专业运动（2倍运动量）总需 = BMR x 1.9
     * */
    private float getHeatByPower(int power, float bmr) {
        float heat = 0;
        // power 0 是较轻， 1 是轻体， 2 是中等体
        switch (power){
            case 0:
                heat = bmr * 1.2f;
                break;
            case 1:
                heat = bmr * 1.375f;
                break;
            case 2:
                heat = bmr * 1.55f;
                break;
        }
        return heat;
    }




    /**
     * 一、人每天至少需要多少热量公式一：
     女: BMR = 65.5 + ( 9.6 x 体重kg ) + ( 1.8 x 身高cm ) - ( 4.7 x 年龄years )
     男: BMR = 66 + ( 13.7 x 体重kg ) + ( 5 x 身高cm ) - ( 6.8 x 年龄years )
     *
     * */
    private float getBMR(int sex, float heigth, float weight, int age) {
        float BMR = 0;
        // 0 代表女的，  1代表男的
        if (sex == 0){
            BMR = 65.5f + (9.6f * weight) + (1.8f * heigth * 100) - (4.7f * age);
        }else if (sex == 1){
            BMR = 66f + (13.7f * weight) + (5f * heigth * 100) - (6.8f * age);
        }
        return BMR;
    }


    // 根据 出生年月获取 年龄
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException("年龄不能超过当前日期");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            if (nowDayOfYear < bornDayOfYear) {
                age -= 1;
            }
        }
        return age;
    }



    /**
     *
      (   一） 较轻体⼒力力活动━━平均每⼩小时消耗约为95千卡， 包括坐着时间较多的⼯工作和活动， 如阅
     读、写字、开会、吃饭、看电视或电影、听⼴广播、缝纫、打字、办公室⼯工作等等。
     （ ⼆二） 轻体⼒力力劳动━━平均每⼩小时消耗为120千卡， 包括站⽴立时间较多的⼯工作如做饭、切菜、擦
     桌⼦子、洗⼩小件⾐衣物、烫⾐衣服、缓步慢⾏行行、讲课、实验室⼯工作、快速打字、售货等。
     （ 三） 中等体⼒力力劳动━━平均每⼩小时消耗约为170千卡， 包括站着⼯工作需要⼿手臂动作较多的（ 如
     交通警值勤勤、乐队指挥） 或坐着⼯工作但⼿手臂激烈烈动作的（ 如重型机械操作， 驾驶拖拉机） ， 擦地、
     扫地、铺床、刷漆、⽤用洗⾐衣机洗⾐衣、园艺⼯工作、中等速度步⾏行行等。
     * */
    private float getPhysicalStrength(int power) {
        float physicalStrength = 0;
        // power 0 是较轻， 1 是轻体， 2 是中等体
        switch (power){
            case 0:
                // 假设 每天 8 小时
                physicalStrength = 95 * 8;
                break;
            case 1:
                // 假设 每天 8 小时
                physicalStrength = 120 * 8;
                break;
            case 2:
                // 假设 每天 8 小时
                physicalStrength = 170 * 8;
                break;
        }

        return physicalStrength;
    }

    /**
     * ⼈人体基础代谢的需要基本热量量 简单算法
     ⼥  女女子 ： 基本热量量（ 千卡） = 体重( ⽄斤） X 9
        男⼦子 ： 基本热量量（ 千卡） = 体重( ⽄斤） X 1 0
     * */
    private float getBaseHeat(float weight, int sex){
        // 0 代表女的，  1代表男的
        return sex == 0 ? weight * 2 * 9 : weight * 2 * 10;
    }
}
