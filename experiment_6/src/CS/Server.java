package CS;

import java.io.*;
import java.net.*;

public class Server {

    //服务器的ServerSocket对象
    private static ServerSocket server;
    //网络字节输入输出流IO
    private static ObjectOutputStream os;
    private static ObjectInputStream is;

    //接收连接用户的Socket对象socket
    private static Socket socket;
    //从用户接收的信息
    private static String msgFromClient;
    //连接用户的数量
    private static int counter = 1;

    //等待用户连接
    public static void waitForConn() throws IOException {
        System.out.println("\n等待连接...\n");
        //接收用户
        socket = server.accept();
        System.out.println("Connection " + counter + " 已连接:"
                + socket.getInetAddress().getHostName());
    }

    //构造IO流
    public static void setStreams() throws IOException {
        os = new ObjectOutputStream(socket.getOutputStream());
        os.flush();
        is = new ObjectInputStream(socket.getInputStream());
        System.out.println("IO流构造完成\n");
    }

    //保持消息监听
    public static void processConn() throws Exception {
        do {
            //读入消息
            msgFromClient = (String) is.readObject();
            System.out.println("CLIENT>>> " + msgFromClient);
            //回传消息
            os.writeObject(msgFromClient);
            os.flush();
        } while (!msgFromClient.equals("登出")); //若消息不是登出，保持连接

        //最后传出登出消息
        os.writeObject(msgFromClient);
        os.flush();
    }

    //关闭服务器
    public static void closeConn() {
        try {
            os.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //服务器主函数
    public static void main(String[] args) {

        try {
            //ServerSocket构造函数参数为端口号
            server = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //保持循环，保证登出后再登录可再次连接
            while (true) {
                waitForConn();
                setStreams();
                processConn();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //出现异常强制关闭服务器
            closeConn();
        }

    }
}
