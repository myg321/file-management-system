//package CS;
//
//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//
//public class Server extends Thread {
//    //将Server继承于Thread类，实现多线程
//
//    //网络字节输出流(输出至不同用户)
//    private static ArrayList<ObjectOutputStream> osToClilents;
//    //网络字节输入流
//    private ObjectInputStream is;
//
//    //服务器的ServerSocket对象
//    private static ServerSocket server;
//    //接收连接用户的Socket对象socket
//    private Socket socket;
//
//    //线程编号
//    private static int anInt = 0;
//    //线程名称
//    private String name;
//
//    //构造器
//    public Server(Socket socket1, String name) {
//        this.socket = socket1;
//        this.name = name;
//        try {
//            //构建is流
//            is = new ObjectInputStream(socket1.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //run方法
//    public void run() {
//        try {
//            //接收用户的信息
//            String msgFromClient;
//
//            //构建os流
//            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
//            osToClilents.add(os);
//            System.out.println("IO构造完成\n");
//
//            do {
//                //读取用户信息
//                msgFromClient = (String) is.readObject();
//                System.out.println(this.name + ":" + msgFromClient);
//
//                //回传消息
//                os.writeObject(msgFromClient);
//                os.flush();
//
//            } while (!msgFromClient.equals("登出")); //只要未登出则循环监听
//
//            //回传登出消息
//            os.writeObject(msgFromClient);
//            os.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //main函数
//    public static void main(String[] args) {
//
//        try {
//            //创建服务器对象
//            server = new ServerSocket(6666);
//            osToClilents = new ArrayList<ObjectOutputStream>();
//
//            //反复循环实现不断接收用户的效果
//            while (true) {
//                //反复等待
//                Socket socket1 = server.accept();
//                System.out.println("\n等待连接...\n");
//                //编号增加
//                anInt++;
//                System.out.println("Thread " + anInt + ":已连接" + socket1.getInetAddress().getHostName());
//                //新增线程
//                Thread t = new Server(socket1, "Thread " + anInt);
//                //使线程准备着，一旦有响应，直接进入run()方法
//                t.start();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

package CS;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

    private static final int MAX_USERS = 100;  //最大用户数目
    private static final ArrayList<ObjectOutputStream> osToClients = new ArrayList<>();
    private static ServerSocket server;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_USERS);

    private ObjectInputStream is;
    private Socket socket;
    private String name;

    public Server(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String msgFromClient;

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            osToClients.add(os);
            System.out.println("IO构造完成\n");

            do {
                msgFromClient = (String) is.readObject();
                System.out.println(this.name + ":" + msgFromClient);

                os.writeObject(msgFromClient);
                os.flush();

            } while (!msgFromClient.equals("登出"));

            os.writeObject(msgFromClient);
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            server = new ServerSocket(6666);

            while (true) {
                Socket socket1 = server.accept();
                System.out.println("\n等待连接...\n");

                if (osToClients.size() < MAX_USERS) {
                    int userNumber = osToClients.size() + 1;
                    System.out.println("Thread " + userNumber + ":已连接" + socket1.getInetAddress().getHostName());
                    Thread t = new Server(socket1, "Thread " + userNumber);
                    threadPool.execute(t);
                } else {
                    System.out.println("连接已达到最大用户数目，拒绝新连接。");
                    socket1.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
