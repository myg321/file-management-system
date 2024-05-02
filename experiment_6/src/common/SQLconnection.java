package common;

import java.sql.SQLException;

public class SQLconnection {

    //加载数据库驱动类
    static String DriverName = "com.mysql.cj.jdbc.Driver"; //我的数据库版本为8.2.0，其写法要加“cj.”

    //声明数据库的URL
    //新版本显式关闭useSSL，添加时区
    static String Url = "jdbc:mysql://localhost:3306/document?useSSL=false&serverTimezone=UTC";

    //数据库用户和密码
    static String User = "root";
    static String Password = "123456";

    //连接数据库
    public static void connect() throws Exception {
        DataProcessing.connectToDB(DriverName, Url, User, Password);
    }

    //断开数据库
    public static void disconnect() throws SQLException {
        DataProcessing.disconnectFromDB();
    }

}

