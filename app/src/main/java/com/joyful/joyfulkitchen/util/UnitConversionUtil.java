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


    // 传入 g ，根据单位 转换
    public static int Conversion(int a, int index) {
        int rs = 0;
        switch (index){
            case 0:
                rs = a;
                break;
            case 1:
                rs = UnitConversionUtil.g2Two(a);
                break;
            case 2:
                rs = UnitConversionUtil.g2Lb(a);
                break;
            case 3:
                rs = UnitConversionUtil.g2Ml(a);
                break;
            case 4:
                rs = UnitConversionUtil.g2Oz(a);
                break;
        }
        return rs;
    }

}
