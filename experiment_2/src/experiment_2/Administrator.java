package experiment_2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Administrator extends User {

    public Administrator(String name, String password, String role){
        super(name, password, role);
    }

    String inputName;
    String inputPassword;
    Scanner sc = new Scanner(System.in);

    //修改用户信息
    public void changeUserInfo() {
        System.out.println("--------修改用户信息--------");
        //输入需修改的用户信息
        System.out.println("请输入要修改的用户名：");
        inputName = sc.next();
        System.out.println("请输入该用户的密码：");
        inputPassword =sc.next();

        //增加try-catch语句进行异常捕获
        try {
            //用户名匹配密码进行查找
            if(DataProcessing.search(inputName,inputPassword) != null){
                System.out.println("请输入用户身份：");
                String inputRole = sc.next();

                //修改用户信息
                if(DataProcessing.update(inputName,inputPassword,inputRole)) {
                    System.out.println("修改用户" + inputName + "成功 :)");
                }
                else System.out.println("修改失败，未查找到该用户名 :(");
            }
            else System.out.println("用户名或密码错误 :(");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除用户
    public void delUser(){
        System.out.println("-----------删除用户----------");
        System.out.println("请输入要删除的用户名：");
        inputName = sc.next();

        //增加try-catch语句进行异常捕获
        try {
            //删除用户数据
            if(DataProcessing.delete(inputName))
                System.out.println("删除用户成功 :)");
            else System.out.println("删除失败，请检查用户名是否输入正确 :(");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加用户
    public void addUser(){
        System.out.println("-----------添加用户----------");
        System.out.println("请输入要增加的用户名：");
        inputName = sc.next();
        System.out.println("请输入该用户的密码：");
        inputPassword =sc.next();
        System.out.println("请输入用户身份：");
        String inputRole = sc.next();

        //增加try-catch语句进行异常捕获
        try {
            //添加用户
            if(DataProcessing.insert(inputName,inputPassword,inputRole))
                System.out.println("添加用户成功 :)");
            else System.out.println("添加失败，该用户名已存在 :(");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //列出用户
    public void listUser(){
        System.out.println("-----------用户列表如下-----------");

        //枚举对象遍历全部用户
        //增加try-catch语句进行异常捕获
        try {
            Enumeration<User> u = DataProcessing.getAllUser();
            while(u.hasMoreElements()){
                System.out.println(u.nextElement());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改用户密码
    public void changePassword(){
        System.out.println("-----------修改密码----------");
        System.out.println("请输入新密码：");
        String newPassword = sc.next();

        //增加try-catch语句进行异常捕获
        try {
            //修改密码
            if(!changeSelfInfo(newPassword))
                System.out.println("修改失败 :(");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //下载文件
    public void downloadFile(){
        System.out.println("-----------下载文件----------");
        System.out.println("请输入文件名：");
        String fileName = sc.next();

        //增加try-catch语句进行异常捕获
        try {
            //进行下载
            super.downloadFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //重写父类User的showMain方法
    @Override
    public void showMenu() {

        String choice;

        while (true) {

            //菜单界面显示
            System.out.println("---------欢迎进入档案管理员界面---------");
            System.out.println("              1.修改用户");
            System.out.println("              2.删除用户");
            System.out.println("              3.新增用户");
            System.out.println("              4.列出用户");
            System.out.println("              5.下载文件");
            System.out.println("              6.文件列表");
            System.out.println("              7.修改密码");
            System.out.println("              8.退    出");
            System.out.println("-------------------------------------");
            System.out.print("请输入选项：");

            choice = sc.next();

            switch (choice){
                //1.修改用户
                case"1":
                    changeUserInfo();
                    break;

                //2.删除用户
                case"2":
                    delUser();
                    break;

                //3.新增用户
                case"3":
                    addUser();
                    break;

                //4.列出用户
                case"4":
                    //列出用户
                    listUser();
                    break;

                //5.下载文件
                case"5":
                    downloadFile();
                    break;

                //6.文件列表
                //增加try-catch语句进行异常捕获
                case"6":
                    try {
                        super.showFileList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                //7.修改密码
                case"7":
                   changePassword();
                    break;

                //8.退出
                case"8":
                    System.out.println("退出管理员菜单界面 +_+");
                    return;

               //其他情况：
                default:
                    System.out.println("要输入1~8的数字呀，请重新输入 =_=");
                    break;
            }
        }


    }
}
