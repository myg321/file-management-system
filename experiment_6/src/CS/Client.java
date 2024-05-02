package CS;

import java.io.*;
import java.net.*;

public class Client {

    //用户Socket对象
    private static Socket client;
    //网络字符输入输出流IO
    private static ObjectInputStream is;
    private static ObjectOutputStream os;
    //从服务端接收的信息
    private static String msgFromSever;

    //连接服务器
    public static void connectToServer() throws Exception {
        System.out.println("\n正在尝试连接服务器...\n");
        //Socket构造函数参数为IP地址与端口号
        client = new Socket("127.0.0.1", 6666);
        System.out.println("已连接至:" + client.getInetAddress().getHostName());
    }

    //构造IO流
    public static void setStreams() throws IOException {
        os = new ObjectOutputStream(client.getOutputStream());
        os.flush();
        is = new ObjectInputStream(client.getInputStream());
        System.out.println("IO流构造完成\n");
    }

    //断开连接
    public static void closeConn() throws IOException {
        os.close();
        is.close();
        client.close();
    }

    //用户向服务器发送消息
    public static void sendMsg(String message) throws IOException {
        //写入输出流
        os.writeObject(message);
        os.flush();
    }

    //接收服务器回传的消息
    public static void receiveMsg() throws Exception {
        //读入输入流
        msgFromSever = (String) is.readObject();
        System.out.println("SERVER>>> " + msgFromSever);
    }

}
