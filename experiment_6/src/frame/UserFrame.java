package frame;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.Enumeration;

import CS.Client;
import common.*;

@SuppressWarnings("serial")
public class UserFrame extends JFrame {

    //中间容器
    private JPanel contentPanel;
    //多页面容器
    private JTabbedPane tabbedPanel;

    //增添用户页面及组件
    private JPanel addUserPanel;
    private JLabel usernameLabel1;  //用户名标签
    private JLabel passwordLabel1;  //密码标签
    private JLabel roleLabel1;      //角色标签
    private JTextField usernameTxt1; //用户名文本框
    private JPasswordField passwordTxt1; //密码文本框
    @SuppressWarnings("rawtypes")
    private JComboBox roleComboBox1;      //角色下拉框
    private JButton confirmButton1;       //确认按钮
    private JButton returnButton1;        //返回按钮

    //删除用户页面及组件
    private JPanel delUserPanel;
    private JScrollPane scrollPane;
    private JTable usersTable;     //用户表格
    private JButton confirmButton2; //确认按钮
    private JButton returnButton2;  //返回按钮

    //修改用户页面及组件
    private JPanel updateUserPanel;
    private JLabel usernameLabel2;  //用户名标签
    private JLabel passwordLabel2;  //密码标签
    private JLabel roleLabel2;      //角色标签
    private JTextField usernameTxt2; //用户名文本框
    private JPasswordField passwordTxt2; //密码文本框
    @SuppressWarnings("rawtypes")
    private JComboBox roleComboBox2;      //角色下拉框
    private JButton confirmButton3;       //确认按钮
    private JButton returnButton3;        //返回按钮

    //启动界面
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 实验6网络编程，无法实现单独调用界面, 需从登录界面启动跳转

                    // 单独调用连接数据库
                    try {
                        SQLconnection.connect();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    }

                    UserFrame frame = new UserFrame(new Administrator("kate", "123", "Administrator"), 0);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建框架
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public UserFrame(User user, int choice) {
        //传入用户及页面选项: 0 增添用户 1 删除用户 2 修改用户
        //框架
        setResizable(false);
        setTitle("\u7528\u6237\u7BA1\u7406\u754C\u9762");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 587, 441);
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/frame/icon.png"));
        setIconImage(image);

        //中间容器
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        //多页面容器
        tabbedPanel = new JTabbedPane(JTabbedPane.TOP);
        tabbedPanel.setBounds(14, 49, 553, 309);
        contentPanel.add(tabbedPanel);

        //添加用户页面
        addUserPanel = new JPanel();
        //增添选项卡
        tabbedPanel.addTab("\u65B0\u589E\u7528\u6237", null, addUserPanel, null);
        addUserPanel.setLayout(null);

        //用户名标签
        usernameLabel1 = new JLabel("\u7528\u6237\u540D");
        usernameLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        usernameLabel1.setBounds(114, 38, 61, 32);
        addUserPanel.add(usernameLabel1);

        //密码标签
        passwordLabel1 = new JLabel("\u5BC6\u7801");
        passwordLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passwordLabel1.setBounds(132, 93, 43, 32);
        addUserPanel.add(passwordLabel1);

        //角色标签
        roleLabel1 = new JLabel("\u89D2\u8272");
        roleLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        roleLabel1.setBounds(132, 150, 43, 32);
        addUserPanel.add(roleLabel1);

        //用户名文本域
        usernameTxt1 = new JTextField();
        usernameTxt1.setBounds(197, 44, 181, 24);
        usernameTxt1.setColumns(10);
        addUserPanel.add(usernameTxt1);

        //密码文本域
        passwordTxt1 = new JPasswordField();
        passwordTxt1.setBounds(197, 99, 181, 24);
        addUserPanel.add(passwordTxt1);

        //角色选项栏
        roleComboBox1 = new JComboBox();
        roleComboBox1.setEditable(true);
        roleComboBox1.setModel(new DefaultComboBoxModel(new String[] { "", "administrator", "browser", "operator" }));
        roleComboBox1.setBounds(197, 156, 181, 24);
        addUserPanel.add(roleComboBox1);

        //增添按钮
        confirmButton1 = new JButton("\u589E\u6DFB");
        confirmButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //增添用户事件
                AddUserActionPerformed(user, e);
            }
        });
        confirmButton1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        confirmButton1.setBounds(132, 222, 113, 27);
        addUserPanel.add(confirmButton1);

        //返回按钮
        returnButton1 = new JButton("\u8FD4\u56DE");
        returnButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });
        returnButton1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        returnButton1.setBounds(329, 222, 113, 27);
        addUserPanel.add(returnButton1);

        //删除用户页面
        delUserPanel = new JPanel();
        tabbedPanel.addTab("\u5220\u9664\u7528\u6237", null, delUserPanel, null);
        delUserPanel.setLayout(null);

        //删除按钮
        confirmButton2 = new JButton("\u5220\u9664");
        confirmButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //删除事件
                DelUserActionPerformed(user, e);
            }
        });
        confirmButton2.setBounds(132, 222, 113, 27);
        confirmButton2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        delUserPanel.add(confirmButton2);

        //返回按钮
        returnButton2 = new JButton("\u8FD4\u56DE");
        returnButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });
        returnButton2.setBounds(329, 222, 113, 27);
        returnButton2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        delUserPanel.add(returnButton2);

        //可下拉容器域
        scrollPane = new JScrollPane();
        scrollPane.setBounds(62, 37, 432, 159);
        delUserPanel.add(scrollPane);

        //用户列表
        usersTable = new JTable();
        //构造表格
        ConstructUserTable();
        //加入可下拉区域
        scrollPane.setViewportView(usersTable);

        //修改用户页面
        updateUserPanel = new JPanel();
        updateUserPanel.setLayout(null);
        tabbedPanel.addTab("\u4FEE\u6539\u7528\u6237", null, updateUserPanel, null);

        //用户名标签
        usernameLabel2 = new JLabel("\u7528\u6237\u540D");
        usernameLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        usernameLabel2.setBounds(114, 38, 61, 32);
        updateUserPanel.add(usernameLabel2);

        //密码标签
        passwordLabel2 = new JLabel("\u5BC6\u7801");
        passwordLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passwordLabel2.setBounds(132, 93, 43, 32);
        updateUserPanel.add(passwordLabel2);

        //角色标签
        roleLabel2 = new JLabel("\u89D2\u8272");
        roleLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        roleLabel2.setBounds(132, 150, 43, 32);
        updateUserPanel.add(roleLabel2);

        //用户名文本域
        usernameTxt2 = new JTextField();
        usernameTxt2.setColumns(10);
        usernameTxt2.setBounds(197, 44, 181, 24);
        updateUserPanel.add(usernameTxt2);

        //密码文本域
        passwordTxt2 = new JPasswordField();
        passwordTxt2.setBounds(197, 99, 181, 24);
        updateUserPanel.add(passwordTxt2);

        //角色选项栏
        roleComboBox2 = new JComboBox();
        roleComboBox2.setModel(new DefaultComboBoxModel(new String[] { "", "administrator", "browser", "operator" }));
        roleComboBox2.setEditable(true);
        roleComboBox2.setBounds(197, 156, 181, 24);
        updateUserPanel.add(roleComboBox2);

        //修改按钮
        confirmButton3 = new JButton("\u4FEE\u6539");
        confirmButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //修改用户事件
                UpdateUserActionPerformed(user, e);
            }
        });
        confirmButton3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        confirmButton3.setBounds(132, 222, 113, 27);
        updateUserPanel.add(confirmButton3);

        //返回按钮
        returnButton3 = new JButton("\u8FD4\u56DE");
        returnButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });
        returnButton3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        returnButton3.setBounds(329, 222, 113, 27);
        updateUserPanel.add(returnButton3);

        //设置页面
        SetPane(choice);
    }

    //用户表格构造
    private void ConstructUserTable() {

        //表头数据
        String[] columnNames = { "\u7528\u6237\u540D", "\u5BC6\u7801", "\u89D2\u8272" };
        //表格数据
        String[][] rowData = new String[20][3];

        Enumeration<User> u;
        try {
            //获取哈希表信息
            u = DataProcessing.getAllUser();
            //行数
            int row = 0;
            //将哈希表信息导入至表格数据
            while (u.hasMoreElements()) {
                User user = u.nextElement();
                rowData[row][0] = user.getName();
                rowData[row][1] = user.getPassword();
                rowData[row][2] = user.getRole();
                row++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        //构造表格
        usersTable.setModel(new DefaultTableModel(rowData, columnNames) {

            boolean[] columnEditables = new boolean[] { false, false, false };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }

        });

    }

    //增添
    private void AddUserActionPerformed(User user, ActionEvent evt) {

        //获取选项内容
        String username = this.usernameTxt1.getText();
        String password = new String(this.passwordTxt1.getPassword());
        String role = (String) this.roleComboBox1.getSelectedItem();

        if (isEmpty(username)) {
            JOptionPane.showMessageDialog(null, "未输入用户名 :(");
            return;
        }
        if (isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "未输入密码 :(");
            return;
        }
        if (isEmpty(role)) {
            JOptionPane.showMessageDialog(null, "未选择身份 :(");
            return;
        }

        try {

            if (DataProcessing.insertUser(username, password, role)) {
                // 发送增添用户的消息
                try {
                    Client.sendMsg("增添用户");
                    Client.receiveMsg();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    return;
                }
                // 更新表格数据
                ConstructUserTable();
                JOptionPane.showMessageDialog(null, "添加成功 :)");
                return;
            } else {
                JOptionPane.showMessageDialog(null, "添加失败，用户名已存在 :(");
                return;
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    //删除
    private void DelUserActionPerformed(User user, ActionEvent evt) {

        //获取所选行序号,若未选择其值为-1
        int selectedrow = usersTable.getSelectedRow();

        if (selectedrow == -1) { //未选择用户的情况
            JOptionPane.showMessageDialog(null, "未选择用户 :(");
            return;
        } else {

            //获取所选行的用户名
            String username = (String) usersTable.getValueAt(selectedrow, 0);
            //若选择空行
            if (isEmpty(username)) {
                return;
            }
            //选择自身用户的情况
            if (username.equals(user.getName())) {
                JOptionPane.showMessageDialog(null, "不能删除自身用户 :(");
                return;
            }

            //显示确认界面: 信息, 标题, 选项个数
            int value = JOptionPane.showConfirmDialog(null, "确定要删除用户吗？", "用户删除确认界面", 2);
            //Yes=0 No=1
            if (value == 0) {

                try {

                    if (DataProcessing.deleteUser(username)) {
                        // 发送删除用户消息
                        try {
                            Client.sendMsg("删除用户");
                            Client.receiveMsg();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                            return;
                        }
                        // 更新表格数据
                        ConstructUserTable();
                        JOptionPane.showMessageDialog(null, "删除成功 :)");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败 :(");
                        return;
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                }

            } else if (value == 1) {
                return;
            }
        }
    }

    //修改
    private void UpdateUserActionPerformed(User user, ActionEvent evt) {

        String username = this.usernameTxt2.getText();
        String password = new String(this.passwordTxt2.getPassword());
        String role = (String) this.roleComboBox2.getSelectedItem();

        if (isEmpty(username)) {
            JOptionPane.showMessageDialog(null, "未输入用户名 :(");
            return;
        }
        if (isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "未输入密码 :(");
            return;
        }
        if (isEmpty(role)) {
            JOptionPane.showMessageDialog(null, "未选择身份 :(");
            return;
        }

        try {

            if (DataProcessing.searchUser(username, password) == null) {
                JOptionPane.showMessageDialog(null, "用户名与密码不匹配 :(");
                return;
            } else {

                //显示确认界面：信息，标题，选项个数
                int value = JOptionPane.showConfirmDialog(null, "确定要修改信息吗？", "信息修改确认界面", 2);

                //Yes=0 No=1
                if (value == 0) {
                    if (DataProcessing.updateUser(username, password, role)) {
                        // 发送修改用户信息
                        try {
                            Client.sendMsg("修改用户");
                            Client.receiveMsg();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                            return;
                        }
                        // 更新表格数据
                        ConstructUserTable();
                        JOptionPane.showMessageDialog(null, "修改成功！");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败 :(");
                        return;
                    }
                } else if (value == 1) {
                    return;
                }
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    //设置页面
    private void SetPane(int value) {
        if (value == 0) {
            tabbedPanel.setSelectedComponent(addUserPanel);
        } else if (value == 1) {
            tabbedPanel.setSelectedComponent(delUserPanel);
        } else if (value == 2) {
            tabbedPanel.setSelectedComponent(updateUserPanel);
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
