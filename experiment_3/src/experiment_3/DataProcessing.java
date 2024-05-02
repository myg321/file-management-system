package experiment_3;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;

public  class DataProcessing {

	private static boolean connectToDB=false;
	
	static Hashtable<String, User> users;
	static Hashtable<String, Doc> docs;		//值为文件类Doc的哈希表

	static {
		users = new Hashtable<String, User>();
		users.put("jack", new Operator("jack","123","operator"));
		users.put("rose", new Browser("rose","123","browser"));
		users.put("kate", new Administrator("kate","123","administrator"));
		Init();

		//获取当前系统时间
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		docs = new Hashtable<String,Doc>();

		//下行为老师提供的代码预置一条文件，当重新运行时哈希表重置，选择文件列表查看，只可查看到该文件
		docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));
	}

	//初始化代码块
	public static  void Init(){
		// connect to database
		// update database connection status
//		if (Math.random()>0.2)
//			connectToDB = true;
//		else
//			connectToDB = false;
	}

	//查找文件
	public static Doc searchDoc(String ID) throws SQLException {
		if (docs.containsKey(ID)) {
			Doc temp =docs.get(ID);
			return temp;
		}
		return null;
	}

	//获取所有文件
	public static Enumeration<Doc> getAllDocs() throws SQLException{		
		Enumeration<Doc> e  = docs.elements();
		return e;
	} 

	//添加新文件
	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
		Doc doc;
		if (docs.containsKey(ID))
			return false;
		else{
			doc = new Doc(ID,creator,timestamp,description,filename);
			docs.put(ID, doc);
			return true;
		}
	} 

	public static User searchUser(String name) throws SQLException{
//		if ( !connectToDB ) 
//			throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );
		
		if (users.containsKey(name)) {
			return users.get(name);			
		}
		return null;
	}
	
	public static User searchUser(String name, String password) throws SQLException {
//		if ( !connectToDB ) 
//	        throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );
		
		if (users.containsKey(name)) {
			User temp =users.get(name);
			if ((temp.getPassword()).equals(password))
				return temp;
		}
		return null;
	}
	
	public static Enumeration<User> getAllUser() throws SQLException{
//		if ( !connectToDB ) 
//	        throw new SQLException( "Not Connected to Database" );
//		
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );
		
		Enumeration<User> e  = users.elements();
		return e;
	}

	
	public static boolean updateUser(String name, String password, String role) throws SQLException{
		User user;
//		if ( !connectToDB ) 
//	        throw new SQLException( "Not Connected to Database" );
//		
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Update" );
		
		if (users.containsKey(name)) {
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name, password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name, password, role);
			else
				user = new Browser(name, password, role);
			users.put(name, user);
			return true;
		}else
			return false;
	}
	
	public static boolean insertUser(String name, String password, String role) throws SQLException{
		User user;
//		if ( !connectToDB ) 
//	        throw new SQLException( "Not Connected to Database" );
//		
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Insert" );
		
		if (users.containsKey(name))
			return false;
		else{
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name,password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name,password, role);
			else if(role.equalsIgnoreCase("browser"))
				user = new Browser(name,password, role);
			else return false;
			users.put(name, user);
			return true;
		}
	}
	
	public static boolean deleteUser(String name) throws SQLException{
//		if ( !connectToDB ) 
//	        throw new SQLException( "Not Connected to Database" );
//		
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Delete" );
		
		if (users.containsKey(name)){
			users.remove(name);
			return true;
		}else
			return false;
	}	
            
	public static void disconnectFromDB() throws SQLException {
		if ( connectToDB ){
			// close Statement and Connection            
			try{
//				if (Math.random()>0.5)
//					throw new SQLException( "Error in disconnecting DB" );      
//			}catch ( SQLException sqlException ){                                            
//			    sqlException.printStackTrace();           
			}finally{                                            
				connectToDB = false;              
			}
		} 
   }
	
}
