package experiment_1;

import java.util.*;

public  class DataProcessing {
    static Hashtable<String, User> users;

    static {
        users = new Hashtable<String, User>();
        users.put("jack", new Operator("jack","123","operator"));
        users.put("rose", new Browser("rose","123","browser"));
        users.put("kate", new Administrator("kate","123","administrator"));
    }

    //查找用户数据
    //在哈希表中查找是否存在用户名为name的用户，有则返回User类型的用户，无则返回null
    public static User searchUser(String name){
        if (users.containsKey(name)) {
            return users.get(name);
        }
        return null;
    }

    //用户名、密码查找用户
    //若用户名密码匹配，则返回该用户，否则返回null
    public static User search(String name, String password){
        if (users.containsKey(name)) {
            User temp =users.get(name);
            if ((temp.getPassword()).equals(password))
                return temp;
        }
        return null;
    }

    // 获取一个用于遍历Hashtable中所有用户的Enumeration<User>对象
    public static Enumeration<User> getAllUser(){

        Enumeration<User> e  = users.elements();
        return e;
    }

    //更新用户信息
    public static boolean update(String name, String password, String role){
        User user;
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

    //向Hashtable中添加用户
    public static boolean insert(String name, String password, String role){
        User user;
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

    //Hashtable中删除用户名为name的用户
    public static boolean delete(String name){

        if (users.containsKey(name)){
            users.remove(name);
            return true;
        }else
            return false;

    }

}
