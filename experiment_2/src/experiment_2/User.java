package experiment_2;

import java.io.IOException;
import java.sql.SQLException;

public abstract class User {
    private String name;
    private String password;
    private String role;

    User(String name,String password,String role){
        this.name=name;
        this.password=password;
        this.role=role;
    }

    //修改用户信息
    public boolean changeSelfInfo(String password) throws SQLException{
        //写用户信息到存储
        if (DataProcessing.update(name, password, role)){
            this.password=password;
            System.out.println("修改成功 :)");
            return true;
        }else
            return false;
    }

    //下载文件
    public boolean downloadFile(String filename) throws IOException{
        double ranValue=Math.random();
        //随机数模拟抛出异常
        if (ranValue>0.5)
            throw new IOException( "Error in accessing file" );
        System.out.println("下载文件... ...");
        return true;
    }

    //显示文件列表
    public void showFileList() throws SQLException{
        double ranValue=Math.random();
        //随机数模拟抛出异常
        if (ranValue>0.5)
            throw new SQLException( "Error in accessing file DB" );
        System.out.println("-----------文件列表如下-----------");
        System.out.println("列表... ...");
    }

    //重写toSting方法，方便打印用户信息
    public String toString(){
        return ("用户名："+name+"，密码："+password+"，用户类型："+role);
    }

    //显示菜单
    public abstract void showMenu();

    //退出系统
    public void exitSystem(){
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    //getter & setter
    public String getName() {
        return name;
    }

    //getter ＆ setter
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
