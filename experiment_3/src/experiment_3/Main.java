package experiment_3;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("-----------欢迎进入档案系统-----------");
            System.out.println("              1.登  录");
            System.out.println("              2.退  出");
            System.out.println("---------------- ;-) ---------------");
            System.out.println("请输入选项：");
            choice = sc.next();
            switch (choice){

                //1.登录
                case"1":
                    System.out.println("请输入用户名：");
                    String inputName = sc.next();
                    System.out.println("请输入密码：");
                    String inputPassword = sc.next();

                    try {
                        //检查密码是否匹配
                        User user = DataProcessing.searchUser(inputName,inputPassword);
                        if(user == null)
                            System.out.println("用户名或密码错误 :(");
                        else user.showMenu();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                //2.退出
                case"2":
                    try {
                        DataProcessing.disconnectFromDB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("档案系统已退出，感谢使用 *_*");
                    return;

                //3.其他情况
                default:
                    System.out.println("要输入1~2的数字呀，请重新输入:");
                    break;
            }
        }

    }
}