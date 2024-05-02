package frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import common.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    private JPanel contentPanel;  //内容面板
    private JMenuBar menuBar;    //菜单栏

    private JMenu userManagerMenu;    //用户管理菜单
    private JMenu fileManagerMenu;    //档案管理菜单
    private JMenu selfInfoMenu;       //个人信息管理菜单
    private JMenu otherMenu;          //其他菜单

    private JButton addUserButton;         //添加用户按钮
    private JButton delUserButton;         //删除用户按钮
    private JButton updateUserButton;      //修改用户按钮
    private JButton uploadFileButton;      //上传文件按钮
    private JButton downloadFileButton;    //下载文件按钮
    private JButton changeSelfInfoButton;  //修改信息按钮
    private JButton exitButton;            //退出按钮

    //启动界面
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //启动默认Administrator
                    MainFrame frame = new MainFrame(new Administrator("kate", "123", "Administrator"));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建框架
    public MainFrame(User user) {
        //传入角色参数
        //框架
        setResizable(false);
        //根据角色设置标题
        SetTitle(user.getRole());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1171, 699);
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/frame/icon.png"));
        setIconImage(image);

        //中间容器
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        //菜单栏
        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1165, 33);
        contentPanel.add(menuBar);

        //用户管理下拉菜单
        userManagerMenu = new JMenu("用户管理");
        userManagerMenu.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        menuBar.add(userManagerMenu);

        //增添用户按钮
        addUserButton = new JButton("增添用户");
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //增添用户事件
                addUserAction(user, e);
            }
        });
        addUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userManagerMenu.add(addUserButton);

        //删除用户按钮
        delUserButton = new JButton("删除用户");
        delUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //删除用户事件
                delUserAction(user, e);
            }
        });
        delUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userManagerMenu.add(delUserButton);

        //修改用户按钮
        updateUserButton = new JButton("修改用户");
        updateUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //修改用户事件
                updateUserAction(user, e);
            }
        });
        updateUserButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userManagerMenu.add(updateUserButton);

        //档案管理下拉菜单
        fileManagerMenu = new JMenu("档案管理");
        fileManagerMenu.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        menuBar.add(fileManagerMenu);

        //上传文件按钮
        uploadFileButton = new JButton("上传文件");
        uploadFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //上传文件事件
                uploadFileAction(user, e);
            }
        });
        uploadFileButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        fileManagerMenu.add(uploadFileButton);

        //下载文件按钮
        downloadFileButton = new JButton("下载文件");
        downloadFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //下载文件事件
                downloadFileAction(user, e);
            }
        });
        downloadFileButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        fileManagerMenu.add(downloadFileButton);

        //个人信息管理下拉菜单
        selfInfoMenu = new JMenu("个人信息管理");
        selfInfoMenu.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        menuBar.add(selfInfoMenu);

        //修改密码按钮
        changeSelfInfoButton = new JButton("密码修改");
        changeSelfInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //修改密码事件
                changeSelfAction(user, e);
            }
        });
        changeSelfInfoButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        selfInfoMenu.add(changeSelfInfoButton);

        //其他类下拉菜单
        otherMenu = new JMenu("其他");
        otherMenu.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        menuBar.add(otherMenu);

        //退出按钮
        exitButton = new JButton("退出");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //退出事件
                exitAction(e);
            }
        });
        exitButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        otherMenu.add(exitButton);

        //设置各按钮权限
        SetRights(user.getRole());
    }

    //增加用户
    private void addUserAction(User user, ActionEvent evt) {
        //选项编号 0
        UserFrame userframe = new UserFrame(user, 0);
        userframe.setVisible(true);
    }

    //删除用户
    private void delUserAction(User user, ActionEvent evt) {
        //选项编号 1
        UserFrame userframe = new UserFrame(user, 1);
        userframe.setVisible(true);
    }

    //修改用户
    private void updateUserAction(User user, ActionEvent evt) {
        //选项编号 2
        UserFrame userframe = new UserFrame(user, 2);
        userframe.setVisible(true);
    }

    //上传文件
    private void uploadFileAction(User user, ActionEvent evt) {
        //选项编号 0
        FileFrame fileframe = new FileFrame(user, 0);
        fileframe.setVisible(true);
    }

    //下载文件
    private void downloadFileAction(User user, ActionEvent evt) {
        //选项编号 1
        FileFrame fileframe = new FileFrame(user, 1);
        fileframe.setVisible(true);
    }

    //修改密码
    private void changeSelfAction(User user, ActionEvent evt) {
        SelfFrame selfframe = new SelfFrame(user);
        selfframe.setVisible(true);
    }

    //设置标题
    private void SetTitle(String role) {
        if (role.equalsIgnoreCase("administrator")) {
            setTitle("档案管理员界面");
        } else if (role.equalsIgnoreCase("browser")) {
            setTitle("档案浏览员界面");
        } else if (role.equalsIgnoreCase("operator")) {
            setTitle("档案录入员界面");
        }
    }

    //设置用户权限
    private void SetRights(String role) {
        if (role.equalsIgnoreCase("administrator")) {
            addUserButton.setEnabled(true);
            delUserButton.setEnabled(true);
            updateUserButton.setEnabled(true);
            downloadFileButton.setEnabled(true);
            uploadFileButton.setEnabled(false);
            changeSelfInfoButton.setEnabled(true);
            exitButton.setEnabled(true);
        } else if (role.equalsIgnoreCase("browser")) {
            addUserButton.setEnabled(false);
            delUserButton.setEnabled(false);
            updateUserButton.setEnabled(false);
            downloadFileButton.setEnabled(true);
            uploadFileButton.setEnabled(false);
            changeSelfInfoButton.setEnabled(true);
            exitButton.setEnabled(true);
        } else if (role.equalsIgnoreCase("operator")) {
            addUserButton.setEnabled(false);
            delUserButton.setEnabled(false);
            updateUserButton.setEnabled(false);
            downloadFileButton.setEnabled(true);
            uploadFileButton.setEnabled(true);
            changeSelfInfoButton.setEnabled(true);
            exitButton.setEnabled(true);
        }
    }

    //退出
    private void exitAction(ActionEvent evt) {
        this.dispose();
        LoginFrame loginframe = new LoginFrame();
        loginframe.setVisible(true);
    }

}
