package com.joyful.joyfulkitchen.util;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class StringUtil {

    /**
     * 一个字符串分割 ;,
     *
     * @param list 一个 盛装 分割后的 容器
     * @param str 要分割的字符串 按(;,)
     * @return
     */
    public static List<String>  StringSplit(List<String> list, String str){
        //List<String> lists = new ArrayList<String>();
        String[] arr = str.split(";");
        for (int i = 0; i < arr.length; i++) {
            String[] temp = arr[i].split(",");
            for (int j = 0; j < temp.length; j++) {
                list.add(temp[j]);
            }
        }
        return list;
    }

    /**
     * 把一个 List 中的 数据添加到另一个list中去
     * @param list
     * @param list2
     * @return
     */
    public static List<String> listAdd(List<String> list, List<String> list2) {
        for (int i = 0; i < list2.size(); i++) {
            list.add(list2.get(i));
        }
        return list;
    }

}
