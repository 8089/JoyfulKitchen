package com.joyful.joyfulkitchen.util;

import com.joyful.joyfulkitchen.model.User;

public interface BaseHeatUtil {
    /**  根据用户信息， 获取每日所需要的热量 */
    double getDailyHeat(User user);

    // 1千卡=1大卡=1000卡=1000卡路里 =4186焦耳=4.186千焦。
    double kcal2Joule(double kcal);

}
