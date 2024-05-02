package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.border.*;
import common.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

    private JPanel contentPanel;        //内容面板
    private JLabel usernameLabel;      //显示用户名标签
    private JLabel passwordLabel;      //显示密码标签
    private JTextField usernameTxt;    //用于输入用户名
    private JPasswordField passwordTxt;    //用于输入密码
    private JButton confirmButton;     //用户确认按钮
    private JButton cancelButton;      //用户取消按钮

    //界面启动
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);     //窗口可见
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建框架
    public LoginFrame() {

        //框架
        setTitle("\u7CFB\u7EDF\u767B\u5F55");   //使用Unicode确保在其他环境和平台上显示正确
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 565, 372);
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/frame/icon.png"));
        setIconImage(image);

        //中间容器
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);

        //用户名标签
        usernameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
        usernameLabel.setBounds(128, 89, 80, 40);
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        //密码标签
        passwordLabel = new JLabel("\u5BC6\u7801\uFF1A");
        passwordLabel.setBounds(148, 142, 60, 37);
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        //用户名文本域
        usernameTxt = new JTextField();
        usernameTxt.setBounds(222, 99, 176, 24);
        usernameTxt.setColumns(10);

        //密码文本域
        passwordTxt = new JPasswordField();
        passwordTxt.setBounds(222, 150, 176, 24);

        //确认按钮
        confirmButton = new JButton("\u786E\u5B9A");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //login event
                loginInAction(e);
            }
        });
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        confirmButton.setBounds(173, 216, 85, 27);

        //取消按钮
        cancelButton = new JButton("\u53D6\u6D88");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //cancel event
                resetValueAction(e);
            }
        });
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        cancelButton.setBounds(313, 216, 85, 27);

        //设置布局、添加部件
        contentPanel.setLayout(null);
        contentPanel.add(usernameLabel);
        contentPanel.add(passwordLabel);
        contentPanel.add(usernameTxt);
        contentPanel.add(passwordTxt);
        contentPanel.add(confirmButton);
        contentPanel.add(cancelButton);
    }

    //登录
    private void loginInAction(ActionEvent evt) {

        String username = this.usernameTxt.getText();
        String password = new String(this.passwordTxt.getPassword());   //获取输入内容

        if (isEmpty(username)) {
            JOptionPane.showMessageDialog(null, "未输入用户名 :("); //显示对话框
            return;
        }
        if (isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "未输入密码 :("); //显示对话框
            return;
        }

        try {
            if (DataProcessing.searchUser(username, password) == null) {
                JOptionPane.showMessageDialog(null, "用户名或密码错误 :("); //显示对话框
                return;
            } else {
                //导入用户
                User user = DataProcessing.searchUser(username, password);
                //令当前界面消失
                this.dispose();
                //跳转至主界面，新建对象并传入用户参数
                MainFrame mainframe = new MainFrame(user);
                mainframe.setVisible(true);
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    //重置文本
    private void resetValueAction(ActionEvent evt) {
        //设置为空
        this.usernameTxt.setText("");
        this.passwordTxt.setText("");
    }

    //判断字符串是否为空
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }
    
}




