package experiment_2;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public  class DataProcessing {
    private static boolean connectToDB=false;

    static Hashtable<String, User> users;

    static {
        users = new Hashtable<String, User>();
        users.put("jack", new Operator("jack","123","operator"));
        users.put("rose", new Browser("rose","123","browser"));
        users.put("kate", new Administrator("kate","123","administrator"));
        Init();
    }

    //数据库初始化
    public static  void Init(){
        // connect to database
        // update database connection status
        if (Math.random()>0.2)
            connectToDB = true;
        else
            connectToDB = false;

    }

    //查找用户数据
    public static User searchUser(String name) throws SQLException{
        //未连接到数据库则抛出异常
        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        //通过随机数模拟异常抛出
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Query" );

        if (users.containsKey(name)) {
            return users.get(name);
        }
        return null;
    }

    //用户名、密码查找用户
    //若用户名密码匹配，则返回该用户，否则返回null
    public static User search(String name, String password) throws SQLException {
        //未连接数据库抛出异常
        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        //通过随机数模拟异常抛出
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Query" );

        if (users.containsKey(name)) {
            User temp =users.get(name);
            if ((temp.getPassword()).equals(password))
                return temp;
        }
        return null;
    }

    // 获取一个用于遍历所有用户的Enumeration<User>对象
    public static Enumeration<User> getAllUser() throws SQLException{
        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Query" );

        Enumeration<User> e  = users.elements();
        return e;
    }

    //更新用户信息
    public static boolean update(String name, String password, String role) throws SQLException{
        User user;
        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Update" );

        if (users.containsKey(name)) {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name,password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name,password, role);
            else
                user = new Browser(name,password, role);
            users.put(name, user);
            return true;
        }else
            return false;
    }

    //添加用户
    public static boolean insert(String name, String password, String role) throws SQLException{
        User user;

        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Insert" );

        if (users.containsKey(name))
            return false;
        else{
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name,password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name,password, role);
            else
                user = new Browser(name,password, role);
            users.put(name, user);
            return true;
        }
    }

    //删除用户名为name的用户
    public static boolean delete(String name) throws SQLException{
        if ( !connectToDB )
            throw new SQLException( "Not Connected to Database" );

        double ranValue=Math.random();
        if (ranValue>0.5)
            throw new SQLException( "Error in excecuting Delete" );

        if (users.containsKey(name)){
            users.remove(name);
            return true;
        }else
            return false;

    }

    //数据库连接断开
    public static void disconnectFromDB() {
        if ( connectToDB ){
            // close Statement and Connection
            try{
                if (Math.random()>0.5)
                    throw new SQLException( "Error in disconnecting DB" );
            }catch ( SQLException sqlException ){
                sqlException.printStackTrace();
            }finally{
                connectToDB = false;
            }
        }
    }


}

