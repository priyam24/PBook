package com.priyam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class DBUtil {
	private static Connection con=null;
	
//	public static Connection getConnection() {
//		try {
//			if(con==null){
//				Class.forName("com.mysql.jdbc.Driver");
//				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hello", "root", "root");
//			}
//			return con;
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public static boolean insertRow(DataSource dataSource, String tablename, ArrayList values, int autorow) {
		PreparedStatement ps=null;
		try {
			int noOfCols=values.size();
			String sql="insert into "+tablename+" values (";
			for(int i=0;i<noOfCols;i++) {
				sql+="?";
				if(i<noOfCols-1) {
					sql+=",";
				}
			}
			sql+=")";
			con=dataSource.getConnection();
			ps=con.prepareStatement(sql);
			for(int i=1;i<=noOfCols;i++) {
				if(i==autorow) {
					ps.setInt(i, 0);
				}
				else {
					if(values.get(i-1) instanceof Integer) {
						ps.setInt(i, (int)values.get(i-1));
					}
					else if(values.get(i-1) instanceof Long) {
						ps.setLong(i, (Long)values.get(i-1));
					}
					else if(values.get(i-1) instanceof String) {
						ps.setString(i, (String)values.get(i-1));
					}
					else if(values.get(i-1) instanceof Float) {
						ps.setFloat(i, (Float)values.get(i-1));
					}
					else if(values.get(i-1) instanceof Boolean) {
						ps.setBoolean(i, (Boolean)values.get(i-1));
					}
					else if(values.get(i-1) instanceof java.util.Date) {
						ps.setDate(i, new java.sql.Date(((java.util.Date)values.get(i-1)).getTime()));
					}
					else{
						ps.setNull(i, java.sql.Types.VARCHAR);
					}
				}
			}
			
			if(ps.executeUpdate()>0) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static User getUserLogin(DataSource dataSource,String uname, String pass) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select * from user where email=? and password=?";
			con=dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, pass);
			rs=ps.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setEmail(uname);
				user.setName(rs.getString("name"));
				user.setActive(rs.getBoolean("active"));
				return user;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean updateUserDetails2(DataSource dataSource,String email,String about, String path) {
		PreparedStatement ps=null;
		try {	
			String sql=null;
			con=dataSource.getConnection();
			if(about==null&&path==null){
				return true;
			}
			if(about==null){
				sql="update user_details set path=? where email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, path);
				ps.setString(2, email);
			}
			else if(path==null){
				sql="update user_details set about=? where email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, about);
				ps.setString(2, email);
			}
			else{
				sql="update user_details set about=?, path=? where email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, about);
				ps.setString(2, path);
				ps.setString(3, email);
			}
			if(ps.executeUpdate()!=-1){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static UserDetails getUserDetails2(DataSource dataSource,String email) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select about,path from user_details where email=?";
			con=dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email);
			rs=ps.executeQuery();
			if(rs.next()){
				UserDetails user=new UserDetails();
				user.setEmail(email);
				user.setProf_pic_path(rs.getString("path"));
				user.setAbout(rs.getString("about"));
				return user;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isFollowing(DataSource dataSource,String email1, String email2) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select * from follow where email1=? and email2=?";
			con=dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email1);
			ps.setString(2, email2);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static ArrayList<String> getFollowers(DataSource dataSource,String email2) {
		ArrayList<String> list=new ArrayList<String>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select email1 from follow where email2=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email2);
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("email1"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<User> getSuggestions(DataSource dataSource,String email) {
		ArrayList<User> list=new ArrayList<User>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select email,name,active from user"+
		" where email in("+
					"select email2 from follow"+
		" where email2 in("+
					"select email2 from follow"+
		" where email1 in("+
					"select email1 from follow"+
		" where email2 in("+
					"select email2 from follow"+
		" where email1 =?)))"+
					" and email2 not in ("+
		"select email2 from follow"+
					" where email1=?)"+
		" and email2 not in(?))";
			/*
			 * this will return people's profiles followed by people
			 *  who also follows people whom you follow as well
			 * */
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, email);
			ps.setString(3, email);
			rs=ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setActive(rs.getBoolean("active"));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<String> getFollowing(DataSource dataSource,String email1) {
		ArrayList<String> list=new ArrayList<String>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select email2 from follow where email1=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email1);
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("email2"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static ArrayList<User> getSearchResults(DataSource dataSource,String name) {
		name=name.trim();
		if(name.length()==0){
			return null;
		}
		ArrayList<User> list=new ArrayList<User>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select email,name,active from user where name like '%"+name+"%'";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			//ps.setString(1, name);
			rs=ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setActive(rs.getBoolean("active"));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static User getUserDetails(DataSource dataSource,String email2) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select name,email,active from user where email=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email2);
			rs=ps.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setActive(rs.getBoolean("active"));
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean unFollow(DataSource dataSource,String email1, String email2) {
		PreparedStatement ps=null;
		try {			
			String sql="delete from follow where email1=? and email2=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email1);
			ps.setString(2, email2);
			if(ps.executeUpdate()>0){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean deleteUser(DataSource dataSource,String email) {
		PreparedStatement ps=null;
		try {			
			String sql="delete from user_details where email=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email);
			if(ps.executeUpdate()>0){
				sql="delete from user where email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, email);
				if(ps.executeUpdate()>0){
					return true;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static ArrayList<Post> getUserPosts(DataSource dataSource,String email) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		try {			
			String sql="select * from post where email=? order by postdate desc,time desc";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email);
			rs=ps.executeQuery();
			ArrayList<Post> posts=new ArrayList<Post>();
			while(rs.next()){
				Post post=new Post();
				post.setId(rs.getInt("id"));
				post.setEmail(rs.getString("email"));
				post.setLikes(rs.getInt("likes"));
				post.setMessage(rs.getString("message"));
				post.setDate(new java.util.Date(rs.getDate("postdate").getTime()));
				post.setTime(rs.getLong("time"));
				post.setActive(rs.getBoolean("active"));
				
				sql="select email from liked where id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, post.getId());
				rs1=ps.executeQuery();
				ArrayList<String> likers=new ArrayList<String>();
				while(rs1.next()){
					likers.add(rs1.getString("email"));
				}
				post.setLikers(likers);
				posts.add(post);
			}
			//Collections.sort(posts);
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
				if(rs1!=null){
					rs1.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Post> getAllPosts(DataSource dataSource,String email) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		try {			
			String sql="select * from post where email=? or email in (select email2 from follow where email1=?) order by postdate desc,time desc;";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, email);
			rs=ps.executeQuery();
			ArrayList<Post> posts=new ArrayList<Post>();
			while(rs.next()){
				Post post=new Post();
				post.setId(rs.getInt("id"));
				post.setEmail(rs.getString("email"));
				post.setLikes(rs.getInt("likes"));
				post.setMessage(rs.getString("message"));
				post.setDate(new java.util.Date(rs.getDate("postdate").getTime()));
				post.setTime(rs.getLong("time"));
				post.setActive(rs.getBoolean("active"));
				
				sql="select email from liked where id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, post.getId());
				rs1=ps.executeQuery();
				ArrayList<String> likers=new ArrayList<String>();
				while(rs1.next()){
					likers.add(rs1.getString("email"));
				}
				post.setLikers(likers);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
				rs1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<ChatBean> getMessages(DataSource dataSource,String email1, String email2) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select * from chat where (email1=? and email2=?) or (email1=? and email2=?) order by timing asc;";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, email1);
			ps.setString(2, email2);
			ps.setString(3, email2);
			ps.setString(4, email1);
			rs=ps.executeQuery();
			ArrayList<ChatBean> chats=new ArrayList<ChatBean>();
			while(rs.next()){
				ChatBean chat=new ChatBean();
				chat.setId(rs.getInt("id"));
				chat.setFrom(rs.getString("email1"));
				chat.setTo(rs.getString("email2"));
				chat.setMessage(rs.getString("message"));
				chat.setTime(rs.getLong("timing"));
				chats.add(chat);
			}
			return chats;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean isLiked(DataSource dataSource, int id,String email) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {			
			String sql="select * from liked where id=? and email=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, email);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean unLike(DataSource dataSource,int id, String email) {
		PreparedStatement ps=null;
		try {			
			String sql="delete from liked where id=? and email=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, email);
			if(ps.executeUpdate()>0){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean deletePost(DataSource dataSource,int id) {
		PreparedStatement ps=null;
		try {			
			String sql="delete from post where id=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			if(ps.executeUpdate()>0){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean updatePost(DataSource dataSource,int id,int likes, String message, boolean active) {
		PreparedStatement ps=null;
		try {			
			String sql="update post set likes=?, message=?, active=? where id=?";
			con=(Connection) dataSource.getConnection();
			ps=con.prepareStatement(sql);
			ps.setInt(1, likes);
			ps.setString(2, message);
			ps.setBoolean(3, active);
			ps.setInt(4, id);
			if(ps.executeUpdate()>0){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
}
