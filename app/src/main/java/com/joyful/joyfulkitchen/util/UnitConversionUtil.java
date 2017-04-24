package com.joyful.joyfulkitchen.util;


public class UnitConversionUtil {

    /**  克转换成安士*/
    public static int g2Oz(int g) {
        return (int) Math.round(g * 0.035274);
    }

    /** 克转换成毫升*/
    public static int g2Ml(int g) {
        return g;
    }

    /** 克转换成两 */
    public static int g2Two(int g) {
        return (int) Math.round(g * 0.2);
    }

    /** 克转换成磅*/
    public static int g2Lb(int g) {
        return (int) Math.round(g * 0.0022046);
    }


}
