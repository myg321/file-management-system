package experiment_3;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Operator extends User {
    public Operator(String name, String password, String role){
        super(name, password, role);
    }
    String inputFile;

    //上传文件
    public void uploadFile(){
        System.out.println("-----------上传文件----------");
        System.out.println("请输入要上传的文件名(路径)：");
        String fileName = sc.next();
        System.out.println("请输入该文件的档案号：");
        String fileID = sc.next();
        try {
            if(DataProcessing.searchUser(fileID) != null)
                System.out.println("档案号已存在 :(");
            else{
                System.out.println("请输入该文件的档案描述：");
                String fileDescrption = sc.next();

                // 输入文件对象
                File inputFile = new File(fileName);
                // 字节缓冲输入流，需封装原始文件流
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));

                // 输出文件对象
                File outputFile = new File(uploadpath+inputFile.getName());
                //outputFile.createNewFile();
                // 字节缓冲输出流，需封装原始文件流
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));

                // 用字节数组存取数据，选取1024字节作为适中大小
                byte[] bytes = new byte[1024];
                int len = 0;
                // 文件写入
                while ((len = input.read(bytes)) != -1) {
                    output.write(bytes, 0, len);
                }

                // 关闭流
                input.close();
                output.close();

                DataProcessing.docs.put(fileID, new Doc(fileID, this.getName(),
                        new Timestamp(System.currentTimeMillis()), fileDescrption, inputFile.getName()));
                System.out.println("上传成功 :)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            System.out.println("---------欢迎进入档案录入员界面---------");
            System.out.println("              1.上传文件");
            System.out.println("              2.下载文件");
            System.out.println("              3.文件列表");
            System.out.println("              4.修改密码");
            System.out.println("              5.退    出");
            System.out.println("-------------------------------------");
            System.out.print("请输入选项：");

            String choice;
            Scanner sc = new Scanner(System.in);
            choice = sc.next();
            switch (choice) {

                //1.上传文件
                case"1":
                    uploadFile();
                    break;

                //2.下载文件
                case "2":
                    downloadFiles();
                    break;

                //3.文件列表
                case "3":
                    super.showFileList();
                    break;

                //4.修改密码
                case "4":
                    changePassword();
                    break;

                //5.退出
                case "5":
                    System.out.println("退出录入员菜单界面 +_+");
                    return;

                //其他情况：
                default:
                    System.out.println("要输入1~5的数字呀，请重新输入 =_=");
                    break;
            }

        }

    }

}
