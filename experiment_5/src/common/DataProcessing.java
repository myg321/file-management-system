package common;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

public  class DataProcessing {

	private static boolean connectToDB = false;

	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;

	//连接数据库
	public static void connectToDB(String driverName, String url, String user, String password)
			throws Exception {
		//加载驱动
		Class.forName(driverName);
		//数据库连接
		conn = DriverManager.getConnection(url, user, password);
		connectToDB = true;
	}

	//断开数据库
	public static void disconnectFromDB() throws SQLException {
		if (connectToDB) {
			//释放资源
			rs.close();
			stmt.close();
			conn.close();
			connectToDB = false;
		}
	}

	//查找文件
	public static Doc searchDoc(String ID) throws SQLException {

		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");
		Doc doc = null;

		//创建sql语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句：获取FileID列中值为ID的数据，注意前后用单引号连接，双引号为String语句两端固定格式
		String sql = "select * from doc_info where FileID='" + ID + "'";

		//处理对象
		rs = stmt.executeQuery(sql);

		//获取数据库信息
		if (rs.next()) {
			String FileID = rs.getString("FileID");
			String Creator = rs.getString("Creator");
			Timestamp timestamp = Timestamp.valueOf(rs.getString("Timestamp"));
			String Description = rs.getString("Description");
			String FileName = rs.getString("FileName");
			doc = new Doc(FileID, Creator, timestamp, Description, FileName);
		}
		return doc;
	}

	//获取所有文件
	public static Enumeration<Doc> getAllDocs() throws SQLException{
		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//建立哈希表
		Hashtable<String, Doc> docs = new Hashtable<String, Doc>();

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句
		String sql = "select * from doc_info "; //获取doc_info所有值

		//处理对象
		rs = stmt.executeQuery(sql);

		//获取数据库信息
		while (rs.next()) {

			//遍历存放
			String FileID = rs.getString("FileID");
			String Creator = rs.getString("Creator");
			Timestamp timestamp = Timestamp.valueOf(rs.getString("Timestamp")); //string转Timestamp
			String Description = rs.getString("Description");
			String FileName = rs.getString("FileName");

			docs.put(FileID, new Doc(FileID, Creator, timestamp, Description, FileName));
		}

		//返回枚举容器
		Enumeration<Doc> e = docs.elements();
		return e;
	} 

	//添加文件
	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename)
			throws SQLException{

		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句
		String sql = "INSERT INTO doc_info VALUES('" + ID + "','" + creator + "','" + timestamp + "','"
				+ description + "','" + filename + "')";
		//更新列表
		stmt.executeUpdate(sql);

		return true;
	} 

	//查找用户
	public static User searchUser(String name) throws SQLException{
		User user = null;

		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句：获取UserName数据列中值为name的数据，变量前后用单引号连接，双引号为sql语句的String格式
		String sql = "select * from user_info where UserName='" + name + "'";

		//处理对象
		rs = stmt.executeQuery(sql);

		//获取数据库信息
		if (rs.next()) {
			String UserName = rs.getString("UserName");
			String Password = rs.getString("Password");
			String Role = rs.getString("Role");

			user = new User(UserName, Password, Role);
		}

		return user;
	}

	//密码验证后查找用户
	public static User searchUser(String name, String password) throws SQLException {

		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");
		User user = null;

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句：获取UserName数据列中值为name的数据，变量前后用单引号连接，双引号为sql语句的String格式
		String sql = "select * from user_info where UserName='" + name + "'";

		//处理对象
		rs = stmt.executeQuery(sql);

		//获取数据库信息(验证密码)
		if (rs.next()) {
			String UserName = rs.getString("UserName");
			String Password = rs.getString("Password");
			String Role = rs.getString("Role");

			user = new User(UserName, Password, Role);

			if (Password.equals(password)) {
				return user;
			} else {
				return null;
			}
		}
		return null;
	}

	//获取所有用户
	public static Enumeration<User> getAllUser() throws SQLException{
		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//建立哈希表
		Hashtable<String, User> users = new Hashtable<String, User>();

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句
		String sql = "select * from user_info "; //获取user_info所有值

		//处理对象
		rs = stmt.executeQuery(sql);

		//获取数据库信息
		while (rs.next()) {
			//遍历存放
			String UserName = rs.getString("UserName");
			String Password = rs.getString("Password");
			String Role = rs.getString("Role");

			users.put(UserName, new User(UserName, Password, Role));
		}

		//返回枚举容器
		Enumeration<User> e = users.elements();
		return e;
	}

	//更新用户
	public static boolean updateUser(String name, String password, String role) throws SQLException{
		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句：更改user_info相关数据
		String sql = "UPDATE user_info SET Password='" + password + "',Role='" + role
				+ "'WHERE UserName='" + name + "'";

		//更新列表
		stmt.executeUpdate(sql);

		return true;
	}

	//添加用户
	public static boolean insertUser(String name, String password, String role) throws SQLException{

		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		//执行sql语句
		String sql = "INSERT INTO user_info VALUES('" + name + "','" + password + "','" + role + "')";

		//更新列表
		stmt.executeUpdate(sql);

		return true;
	}

	//删除用户
	public static boolean deleteUser(String name) throws SQLException{
		if (!connectToDB)
			throw new SQLException("未连接到数据库 :(");

		//创建语句对象
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		//执行sql语句
		String sql = "DELETE FROM user_info WHERE UserName='" + name + "'";
		
		//更新列表
		stmt.executeUpdate(sql);

		return true;
	}	

	
}
