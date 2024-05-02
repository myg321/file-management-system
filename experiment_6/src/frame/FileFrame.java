package frame;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import CS.Client;
import common.*;

@SuppressWarnings("serial")
public class FileFrame extends JFrame {

    //中间容器
    private JPanel contentPanel;
    //多页面容器
    private JTabbedPane tabbedPanel;

    //上传文件页面及组件
    private JPanel uploadPanel;
    private JLabel fileIDLabel;
    private JLabel filedescriptionLabel;
    private JLabel filenameLabel;
    private JTextField fileidTxt;
    private JTextArea filedescriptionTxt;
    private JTextField filepathTxt;
    private JButton uploadButton;
    private JButton openFileButton;
    private JButton returnButton1;

    //下载文件页面及组件
    private JPanel downloadPanel;
    private JButton downloadButton;
    private JButton returnButton2;
    private JScrollPane scrollPanel;
    private JTable filesTable;

    //启动界面
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    //单独调用连接数据库
                    try {
                        SQLconnection.connect();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    }

                    FileFrame frame = new FileFrame(new Administrator("jack", "123", "operator"), 0);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建框架
    public FileFrame(User user, int choice) {
        //传入用户及页面选项: 0上传文件 1下载文件
        //框架
        setTitle("\u6587\u4EF6\u7BA1\u7406\u754C\u9762");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 802, 581);
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/frame/icon.png"));
        setIconImage(image);

        //中间容器
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        //多页面容器
        tabbedPanel = new JTabbedPane(JTabbedPane.TOP);
        tabbedPanel.setBounds(38, 35, 713, 469);
        contentPanel.add(tabbedPanel);

        //上传页面
        uploadPanel = new JPanel();
        tabbedPanel.addTab("\u6587\u4EF6\u4E0A\u4F20", null, uploadPanel, null);
        uploadPanel.setLayout(null);

        //档案号标签
        fileIDLabel = new JLabel("\u6863\u6848\u53F7");
        fileIDLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        fileIDLabel.setBounds(125, 33, 60, 36);
        uploadPanel.add(fileIDLabel);

        //文件描述标签
        filedescriptionLabel = new JLabel("\u6863\u6848\u63CF\u8FF0");
        filedescriptionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        filedescriptionLabel.setBounds(105, 90, 80, 36);
        uploadPanel.add(filedescriptionLabel);

        //文件名标签
        filenameLabel = new JLabel("\u6863\u6848\u6587\u4EF6\u540D");
        filenameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        filenameLabel.setBounds(85, 314, 100, 36);
        uploadPanel.add(filenameLabel);

        //档案号文本域
        fileidTxt = new JTextField();
        fileidTxt.setBounds(215, 40, 272, 27);
        uploadPanel.add(fileidTxt);
        fileidTxt.setColumns(10);

        //文件描述文本域
        filedescriptionTxt = new JTextArea();
        filedescriptionTxt.setBounds(215, 96, 272, 199);
        uploadPanel.add(filedescriptionTxt);
        filedescriptionTxt.setColumns(10);

        //文件名文本域
        filepathTxt = new JTextField();
        filepathTxt.setColumns(10);
        filepathTxt.setBounds(215, 321, 272, 27);
        uploadPanel.add(filepathTxt);

        //上传按钮
        uploadButton = new JButton("\u4E0A\u4F20");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //上传文件事件
                UploadActionPerformed(user, e);
            }
        });
        uploadButton.setBounds(215, 380, 95, 27);
        uploadButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        uploadPanel.add(uploadButton);

        //返回按钮
        returnButton1 = new JButton("\u8FD4\u56DE");
        returnButton1.setBounds(395, 380, 95, 27);
        returnButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });

        //打开按钮
        openFileButton = new JButton("\u6253\u5F00");
        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //打开文件事件
                OpenFileActionPerformed(e);
            }
        });
        openFileButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        openFileButton.setBounds(532, 319, 95, 27);
        uploadPanel.add(openFileButton);
        returnButton1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        uploadPanel.add(returnButton1);

        //下载页面
        downloadPanel = new JPanel();
        tabbedPanel.addTab("\u6587\u4EF6\u4E0B\u8F7D", null, downloadPanel, null);
        tabbedPanel.setEnabledAt(1, true);
        downloadPanel.setLayout(null);

        //下载按钮
        downloadButton = new JButton("\u4E0B\u8F7D");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //下载文件事件
                DownloadActionPerformed(user, e);
            }
        });
        downloadButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        downloadButton.setBounds(215, 380, 95, 27);
        downloadPanel.add(downloadButton);

        //返回按钮
        returnButton2 = new JButton("\u8FD4\u56DE");
        returnButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //返回事件
                ReturnActionPerformed(e);
            }
        });
        returnButton2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        returnButton2.setBounds(395, 380, 95, 27);
        downloadPanel.add(returnButton2);

        //可下拉容器
        scrollPanel = new JScrollPane();
        scrollPanel.setBounds(35, 32, 637, 322);
        downloadPanel.add(scrollPanel);

        //下载文件列表
        filesTable = new JTable();
        //构造表格
        ConstructFileTable();
        //加入可下拉区域
        scrollPanel.setViewportView(filesTable);

        //设置权限及页面
        setPane(user, choice);
    }

    //表格构造
    private void ConstructFileTable() {

        //表头数据
        String[] columnNames = { "\u6863\u6848\u53F7", "\u521B\u5EFA\u8005", "\u65F6\u95F4", "\u6587\u4EF6\u540D",
                "\u6587\u4EF6\u63CF\u8FF0" };
        //表格数据
        String[][] rowData = new String[20][5];

        Enumeration<Doc> f;
        try {
            //获取哈希表信息
            f = DataProcessing.getAllDocs();

            //行数
            int row = 0;
            //将哈希表信息导入至表格
            while (f.hasMoreElements()) {
                Doc doc = f.nextElement();
                rowData[row][0] = doc.getID();
                rowData[row][1] = doc.getCreator();
                rowData[row][2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(doc.getTimestamp()); //Time转String
                rowData[row][3] = doc.getFilename();
                rowData[row][4] = doc.getDescription();
                row++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        //构造表格
        filesTable.setModel(new DefaultTableModel(rowData, columnNames) {

            boolean[] columnEditables = new boolean[] { false, false, false, false, false };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }

        });

    }

    //打开文件
    private void OpenFileActionPerformed(ActionEvent evt) {

        //弹出文件选择框
        FileDialog OpenFileDialog = new FileDialog(this, "选择上传文件");
        OpenFileDialog.setVisible(true);

        //获取文件路径
        String filepath = OpenFileDialog.getDirectory() + OpenFileDialog.getFile();
        filepathTxt.setText(filepath);

    }

    //上传文件
    private void UploadActionPerformed(User user, ActionEvent evt) {

        String filepath = filepathTxt.getText();
        String fileID = fileidTxt.getText();
        String filedescription = filedescriptionTxt.getText();

        if (isEmpty(filepath)) {
            JOptionPane.showMessageDialog(null, "未选择文件 :(");
            return;
        }
        if (isEmpty(fileID)) {
            JOptionPane.showMessageDialog(null, "未输入档案号 :(");
            return;
        }
        if (isEmpty(filedescription)) {
            JOptionPane.showMessageDialog(null, "未输入文件描述 :(");
            return;
        }

        if (user.uploadFile(fileID, filepath, filedescription)) {

            // 发送上传文件消息
            try {
                Client.sendMsg("上传文件");
                Client.receiveMsg();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                return;
            }

            // 更新表格数据
            ConstructFileTable();
            JOptionPane.showMessageDialog(null, "上传成功 :)");
            return;

        } else {
            JOptionPane.showMessageDialog(null, "上传失败 :(");
            return;
        }

    }

    //下载文件
    private void DownloadActionPerformed(User user, ActionEvent evt) {

        //获取所选行序号, 若未选择其值为-1
        int selectedrow = filesTable.getSelectedRow();

        if (selectedrow == -1) { //未选择文件的情况
            JOptionPane.showMessageDialog(null, "未选择文件 :(");
            return;
        } else {

            //获取档案号
            String fileID = (String) filesTable.getValueAt(selectedrow, 0);
            //若选择空行
            if (isEmpty(fileID)) {
                return;
            }

            //显示确认界面: 信息, 标题, 选项个数
            int value = JOptionPane.showConfirmDialog(null,
                    "确定要下载文件吗？", "文件下载确认界面", 2);
            //Yes=0 No=1
            if (value == 0) {

                if (user.downloadFile(fileID)) {

                    // 发送下载文件消息
                    try {
                        Client.sendMsg("下载文件");
                        Client.receiveMsg();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "下载成功 :)");
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "下载失败 :(");
                    return;
                }

            } else if (value == 1) {
                return;
            }
        }
    }

    //设置页面
    private void setPane(User user, int choice) {

        if (!user.getRole().equalsIgnoreCase("operator")) {
            fileidTxt.setEditable(false);
            filedescriptionTxt.setEditable(false);
            filepathTxt.setEditable(false);
            uploadButton.setEnabled(false);
            openFileButton.setEnabled(false);
        }

        if (choice == 0) {
            tabbedPanel.setSelectedComponent(uploadPanel);
        } else if (choice == 1) {
            tabbedPanel.setSelectedComponent(downloadPanel);
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
