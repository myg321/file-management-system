package frame;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import common.*;

@SuppressWarnings("serial")
public class SelfFrame extends JFrame {

    private JPanel contentPanel;
    private JLabel usernameLabel;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel roleLabel;

    private JTextField usernameTxt;
    private JPasswordField oldPasswordTxt;
    private JPasswordField newPasswordTxt;
    private JPasswordField confirmPasswordTxt;
    private JTextField roleTxt;

    private JButton confirmButton;
    private JButton returnButton;

    //启动界面
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelfFrame frame = new SelfFrame(new Administrator("kate", "123", "Administrator"));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建框架
    public SelfFrame(User user) {
        //传入用户参数
        //框架
        setResizable(false);
        setTitle("\u4E2A\u4EBA\u4FE1\u606F\u7BA1\u7406");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 502, 384);
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/frame/icon.png"));
        setIconImage(image);

        //中间容器
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        //用户名标签
        usernameLabel = new JLabel("\u7528\u6237\u540D:");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        usernameLabel.setBounds(100, 51, 72, 30);
        contentPanel.add(usernameLabel);

        //旧密码标签
        oldPasswordLabel = new JLabel("\u539F\u5BC6\u7801:");
        oldPasswordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        oldPasswordLabel.setBounds(100, 93, 72, 30);
        contentPanel.add(oldPasswordLabel);

        //新密码标签
        newPasswordLabel = new JLabel("\u65B0\u5BC6\u7801:");
        newPasswordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        newPasswordLabel.setBounds(100, 135, 72, 30);
        contentPanel.add(newPasswordLabel);

        //确认密码标签
        confirmPasswordLabel = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801:");
        confirmPasswordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        confirmPasswordLabel.setBounds(63, 178, 109, 30);
        contentPanel.add(confirmPasswordLabel);

        //角色标签
        roleLabel = new JLabel("\u89D2\u8272:");
        roleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        roleLabel.setBounds(118, 221, 57, 30);
        contentPanel.add(roleLabel);

        //用户名文本域
        usernameTxt = new JTextField();
        //自动设置文本为用户名
        usernameTxt.setText(user.getName());
        usernameTxt.setEditable(false);
        usernameTxt.setBounds(186, 56, 154, 24);
        contentPanel.add(usernameTxt);
        usernameTxt.setColumns(10);

        //旧密码文本域
        oldPasswordTxt = new JPasswordField();
        oldPasswordTxt.setBounds(186, 98, 154, 24);
        contentPanel.add(oldPasswordTxt);

        //新密码文本域
        newPasswordTxt = new JPasswordField();
        newPasswordTxt.setBounds(186, 140, 154, 24);
        contentPanel.add(newPasswordTxt);

        //确认密码文本域
        confirmPasswordTxt = new JPasswordField();
        confirmPasswordTxt.setBounds(186, 183, 154, 24);
        contentPanel.add(confirmPasswordTxt);

        //角色文本域
        roleTxt = new JTextField();
        //自动设置用户身份
        roleTxt.setText(user.getRole());
        roleTxt.setEditable(false);
        roleTxt.setColumns(10);
        roleTxt.setBounds(186, 226, 154, 24);
        contentPanel.add(roleTxt);

        //确认按钮
        confirmButton = new JButton("\u786E\u8BA4");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //修改密码事件
                ChangeSelfInfoActionPerformed(user, e);
            }
        });
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        confirmButton.setBounds(118, 288, 113, 27);
        contentPanel.add(confirmButton);

        //返回按钮
        returnButton = new JButton("\u8FD4\u56DE");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });
        returnButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        returnButton.setBounds(278, 288, 113, 27);
        contentPanel.add(returnButton);
    }

    //修改密码
    private void ChangeSelfInfoActionPerformed(User user, ActionEvent evt) {

        String oldpassword = new String(oldPasswordTxt.getPassword());
        String newpassword = new String(newPasswordTxt.getPassword());
        String confirmpassword = new String(confirmPasswordTxt.getPassword());

        //检查是否为空
        if (isEmpty(oldpassword)) {
            JOptionPane.showMessageDialog(null, "未输入旧密码 :(");
            return;
        }
        if (isEmpty(newpassword)) {
            JOptionPane.showMessageDialog(null, "未输入新密码 :(");
            return;
        }
        if (isEmpty(confirmpassword)) {
            JOptionPane.showMessageDialog(null, "请输入确认密码 :(");
            return;
        }

        //密码匹配
        try {

            if (DataProcessing.searchUser(user.getName(), oldpassword) == null) {
                JOptionPane.showMessageDialog(null, "用户名与原密码不匹配 :(");
                return;
            }
            if (!newpassword.equals(confirmpassword)) {
                JOptionPane.showMessageDialog(null, "两次输入的新密码不相同 :(");
                return;
            }

            //修改密码
            if (user.changeSelfInfo(newpassword)) {

                //清空
                this.oldPasswordTxt.setText("");
                this.newPasswordTxt.setText("");
                this.confirmPasswordTxt.setText("");

                JOptionPane.showMessageDialog(null, "修改成功 :)");
                return;

            } else {
                JOptionPane.showMessageDialog(null, "修改失败 :(");
                return;
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    //返回
    private void ReturnActionPerformed(ActionEvent evt) {
        this.dispose();
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


