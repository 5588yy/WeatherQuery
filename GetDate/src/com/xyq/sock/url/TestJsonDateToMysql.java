package com.xyq.sock.url;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestJsonDateToMysql {

    private static final String url = "jdbc:mysql://localhost:3306/homework?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "yuejianing";
    private static Connection con;
    //连接数据库
    static Connection getconnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    //插入数据进入数据库中
    public static void moreinsertdata(Connection con,int name) {
        //创建解析器
        JsonParser parser = new JsonParser();
        JsonObject object;
        try {
            //forecast： i
            object = (JsonObject) parser.parse(new FileReader("C:\\DATA\\data"+name+".json"));
            JsonArray array1 = object.get("forecasts").getAsJsonArray();
            for (int i = 0; i < array1.size(); i++) {
                //城市
                JsonObject arrayObject1 = array1.get(i).getAsJsonObject();
                String citystr = arrayObject1.get("city").toString();
                //casts：天气 j
                JsonArray array2 = arrayObject1.get("casts").getAsJsonArray();
                for (int j = 0; j < array2.size(); j++) {
                    JsonObject arrayObject2 = array2.get(j).getAsJsonObject();
                    PreparedStatement psql2 = con.prepareStatement("insert into weatherdata (city,date,dayweather,daywind,week,daypower,daytemp,nighttemp,nightweather,nightpower) values(?,?,?,?,?,?,?,?,?,?)");
                    //省份
                    psql2.setString(1, citystr);
                    //日期
                    String date = arrayObject2.get("date").getAsString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date myDate = dateFormat.parse(date);
                    Format format = new SimpleDateFormat("yyyy-MM-dd");
                    String str = format.format(myDate);
                    psql2.setString(2, str);
                    //other
                    psql2.setString(3, arrayObject2.get("dayweather").getAsString());
                    psql2.setString(4, arrayObject2.get("daywind").getAsString());
                    psql2.setString(5, arrayObject2.get("week").getAsString());
                    psql2.setString(6, arrayObject2.get("daypower").getAsString());
                    psql2.setString(7, arrayObject2.get("daytemp").getAsString());
                    psql2.setString(8, arrayObject2.get("nighttemp").getAsString());
                    psql2.setString(9, arrayObject2.get("nightweather").getAsString());
                    psql2.setString(10, arrayObject2.get("nightpower").getAsString());
                    //执行和关闭
                    psql2.executeUpdate();
                    psql2.close();
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            } catch(ParseException e){
                e.printStackTrace();
            } catch(FileNotFoundException e){
                e.printStackTrace();
            }
    }
    //main
    public static void main(String[] args) {
        Connection con = TestJsonDateToMysql.getconnect();
        for(int i=0;i<=29;i++)
        {
            moreinsertdata(con,i);
        }
    }
}