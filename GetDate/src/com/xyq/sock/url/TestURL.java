package com.xyq.sock.url;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TestURL {

    public static void main(String[] args) {
        //城市代码
        List<String> city = new ArrayList<String>();
        Citycode(city);
        //获取天气预报
        int i=0;
        String data="";
        while(i<city.size())
        {
            String url = "https://restapi.amap.com/v3/weather/weatherInfo?city="+city.get(i)+"&key=b71f9cd07dd4193dbbe72f9f90fe3790&extensions=all&output=JSON";
            data=sendGet(url);
            JSONObject json= JSONObject.fromObject(data);
            save(json,i);
            i++;
            System.out.println(data);
        }
    }
    //城市代码
    public static void Citycode(List<String> city)
    {
        city.add("110000");
        city.add("120000");
        city.add("310000");
        city.add("500000");
        city.add("230100");
        city.add("220100");
        city.add("210100");
        city.add("150100");
        city.add("130100");
        city.add("410100");
        city.add("370100");
        city.add("140100");
        city.add("340100");
        city.add("420100");
        city.add("320100");
        city.add("330100");
        city.add("430100");
        city.add("360100");
        city.add("350100");
        city.add("440100");
        city.add("450100");
        city.add("460100");
        city.add("510100");
        city.add("520100");
        city.add("530100");
        city.add("620100");
        city.add("640100");
        city.add("650100");
        city.add("630100");
        city.add("540100");
    }
    //get获取数据
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            //建立连接
            // 打开和URL之间的连接
            String urlName = url;
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(4000);
            conn.connect();
            //获取响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result+=line;
            }
            } catch (Exception e) {
                System.out.println("发送GET请求出现异常！" + e);
            } finally {// 使用finally块来关闭输入流
            try {
                if (in != null) in.close();
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        //返回结果
        return result;
    }
    //json储存到本地文件
    public static void save(JSONObject data,int name)
    {
        try {

            File writeName = new File("C:\\DATA\\data"+name+".json"); // 相对路径，如果没有则要建立一个新的output.txt文件
            if(!writeName.exists()) {
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            }
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(String.valueOf(data));
            out.flush(); // 把缓存区内容压入文件
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}