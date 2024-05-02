package experiment_3;

import java.util.Scanner;

public class Browser extends User {
    public Browser(String name, String password, String role) {
        super(name, password, role);
    }
    String inputFile;


    //下载文件
    public void downloadFiles(){
        System.out.println("-----------下载文件----------");
        if(super.downloadFile())
            System.out.println("下载成功 :)");
        else
            System.out.println("下载失败 :(");
    }

    //修改密码
    public void changePassword(){
        System.out.println("-----------修改密码----------");
        System.out.println("请输入新密码：");
        String newPassword = sc.next();
        if (!changeSelfInfo(newPassword))
            System.out.println("修改失败 :(");
        else
            System.out.println("修改成功 :)");
    }

    //重写父类User的showMain方法
    @Override
    public void showMenu() {

        while (true) {

            //菜单界面显示
            System.out.println("---------欢迎进入档案浏览员界面---------");
            System.out.println("              1.下载文件");
            System.out.println("              2.文件列表");
            System.out.println("              3.修改密码");
            System.out.println("              4.退    出");
            System.out.println("-------------------------------------");
            System.out.print("请输入选项：");

            String choice;
            Scanner sc = new Scanner(System.in);
            choice = sc.next();

            switch (choice) {
                //1.下载文件
                case "1":
                   downloadFiles();
                    break;

                //2.文件列表
                case "2":
                    super.showFileList();
                    break;

                //3.修改密码
                case "3":
                    changePassword();
                    break;

                //4.退出
                case "4":
                    System.out.println("退出浏览员菜单界面 +_+");
                    return;

                //其他情况：
                default:
                    System.out.println("要输入1~4的数字呀，请重新输入 =_=");
                    break;
            }
        }

    }
}
