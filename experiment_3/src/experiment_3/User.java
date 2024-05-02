package experiment_3;
import java.io.*;
import java.sql.SQLException;
import java.util.*;


public abstract class User {
    private String name;
    private String password;
    private String role;

    Scanner sc = new Scanner(System.in);

    // 上传与下载路径
    String uploadpath = "d:\\OOP\\uploadfile\\";
    String downloadpath = "d:\\OOP\\downloadfile\\";

    User(String name, String password, String role) {
        this.setName(name);
        this.setPassword(password);
        this.setRole(role);
    }

    public String getName() {
        return name;
    }

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

    public abstract void showMenu();

    public String toString() {
        return "Name: " + this.name + " Password: " + this.password + " Role: " + this.role;
    }

    // 下载文件
    public boolean downloadFile() {

        System.out.print("请输入档案号：");
        String downloadId = sc.next();

        Doc downloadDoc = null;
        try {
            downloadDoc = DataProcessing.searchDoc(downloadId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (downloadDoc == null) {
            return false;
        } else {

            // 输入文件对象
            File inputFile = new File(uploadpath + downloadDoc.getFilename());

            try {
                // 字节缓冲输入流，需封装原始文件流
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFile));
                // 输出文件对象
                File outputFile = new File(downloadpath + downloadDoc.getFilename());
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

                return true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    // 获取文件列表
    public void showFileList() {
        try {
            Enumeration<Doc> e = DataProcessing.getAllDocs();
            while (e.hasMoreElements()) {
                System.out.println(e.nextElement());
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    // 更改用户信息
    public boolean changeSelfInfo(String password) {
        try {
            if (DataProcessing.updateUser(name, password, role)) {
                this.password = password;
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 退出系统
    public void exitSystem() {
        System.out.println("系统退出，谢谢使用 :)");
        System.exit(0);
    }

}
