package experiment_1;

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

                    //检查密码是否匹配
                    User user = DataProcessing.search(inputName,inputPassword);
                    if(user == null)
                        System.out.println("用户名或密码错误 :(");
                    else user.showMenu();
                    break;

                //2.退出
                case"2":
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