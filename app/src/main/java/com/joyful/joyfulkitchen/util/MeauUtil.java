package com.joyful.joyfulkitchen.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class MeauUtil {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
//    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY = "a8af9da2c78c4de58b6a74a759b80a06";

    //1.菜谱大全
    public static String getFood(String menuName, int fristIndex, int row) {
        String result = null;
        String url = "http://apis.juhe.cn/cook/query.php";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("menu", menuName);//需要查询的菜谱名
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json
        if (fristIndex >= 0) {
            params.put("pn", fristIndex);//数据返回起始下标
        } else {
            params.put("pn", "");//数据返回起始下标
        }

        if (row != 0) {
            params.put("rn", row);//数据返回起始下标
        } else {
            params.put("rn", "");//数据返回条数，最大30
        }

        params.put("albums", "");//albums字段类型，1字符串，默认数组

        try {
            result = net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //2.分类标签列表
    public static String getCategory(int parentid) {
        String result = null;
        String url = "http://apis.juhe.cn/cook/category";//请求接口地址
        Map params = new HashMap();//请求参数

        if (parentid != 0) {
            params.put("parentid", parentid);
        } else {
            params.put("parentid", "");//分类ID，默认全部
        }
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json

        try {
            result = net(url, params, "GET");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //3.按标签检索菜谱
    public static String getTag() {
        String result = null;
        String url = "http://apis.juhe.cn/cook/index";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("cid", "");//标签ID
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json
        params.put("pn", "");//数据返回起始下标，默认0
        params.put("rn", "");//数据返回条数，最大30，默认10
        params.put("format", "");//steps字段屏蔽，默认显示，format=1时屏蔽

        try {
            result = net(url, params, "GET");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //4.按菜谱ID查看详细
    public static String getMeauById(int id) {
        String result = null;
        String url = "http://apis.juhe.cn/cook/queryid";//请求接口地址
        Map params = new HashMap();//请求参数
        if (id != 0) {
            params.put("id", id);//菜谱的ID
        } else {
            params.put("id", "");//菜谱的ID
        }
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json

        try {
            result = net(url, params, "GET");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
//            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}