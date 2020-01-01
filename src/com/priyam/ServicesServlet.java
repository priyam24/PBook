package com.priyam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.oreilly.servlet.MultipartRequest;
import javax.servlet.http.Part;  

/**
 * Servlet implementation class ServicesServlet
 */
@WebServlet("/ServicesServlet")
@MultipartConfig
public class ServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Resource(name="jdbc/hello")
	private DataSource dataSource;
    public ServicesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("service").equals("follow")){
			ArrayList values=new ArrayList();
			values.add(request.getParameter("email1"));
			values.add(request.getParameter("email2"));
			if(DBUtil.insertRow(dataSource,"follow", values, 0)){
				response.sendRedirect("profile.jsp?email="+request.getParameter("email2"));
			}
		}
		else if(request.getParameter("service").equals("unfollow")){
			if(DBUtil.unFollow(dataSource,request.getParameter("email1"),request.getParameter("email2"))){
				response.sendRedirect("profile.jsp?email="+request.getParameter("email2"));			
			}
		}
		else if(request.getParameter("service").equals("Post")){
			ArrayList values=new ArrayList();
			User user=(User) request.getSession().getAttribute("user");
			values.add(1);
			values.add(user.getEmail());
			values.add(0);
			values.add(request.getParameter("message"));
			values.add(new java.util.Date());
			values.add((Long)System.currentTimeMillis()/1000);
			values.add(true);
			if(DBUtil.insertRow(dataSource,"post", values, 1)){
				if(request.getParameter("page").equals("home")){
					response.sendRedirect("home.jsp");
				}
				else if(request.getParameter("page").equals("profile")){
					response.sendRedirect("profile.jsp?email="+user.getEmail());
				}
				
			}
		}
		else if(request.getParameter("service").equals("Like")){
			User user=(User)request.getSession().getAttribute("user");
			ArrayList values=new ArrayList();
			values.add(Integer.parseInt(request.getParameter("id")));
			values.add(user.getEmail());
			if(DBUtil.updatePost(dataSource, Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("likes"))+1, request.getParameter("message"), Boolean.parseBoolean(request.getParameter("active")))&&DBUtil.insertRow(dataSource, "liked", values, 0)){
				
				if(request.getParameter("page").equals("profile")){
					response.sendRedirect("profile.jsp?email="+request.getParameter("email"));
				}
				else if(request.getParameter("page").equals("home")){
					response.sendRedirect("home.jsp");
				}
			}
		}
		else if(request.getParameter("service").equals("Unlike")){
			User user=(User)request.getSession().getAttribute("user");
			if(DBUtil.updatePost(dataSource, Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("likes"))-1, request.getParameter("message"), Boolean.parseBoolean(request.getParameter("active")))&&DBUtil.unLike(dataSource, Integer.parseInt(request.getParameter("id")), user.getEmail())){
				
				if(request.getParameter("page").equals("profile")){
					response.sendRedirect("profile.jsp?email="+request.getParameter("email"));
				}
				else if(request.getParameter("page").equals("home")){
					response.sendRedirect("home.jsp");
				}
			}
		}
		else if(request.getParameter("service").equals("Remove")){
			if(DBUtil.deletePost(dataSource, Integer.parseInt(request.getParameter("id")))){
				
				if(request.getParameter("page").equals("profile")){
					response.sendRedirect("profile.jsp?email="+request.getParameter("email"));
				}
				else if(request.getParameter("page").equals("home")){
					response.sendRedirect("home.jsp");
				}
			}
		}
		else if(request.getParameter("service").equals("search")){
			ArrayList<User> profiles=DBUtil.getSearchResults(dataSource, request.getParameter("searchitem"));
			request.setAttribute("users", profiles);
			request.getRequestDispatcher("finder.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("service").equals("upload")){
			Part filePart = (Part) request.getPart("fname"); // Retrieves <input type="file" name="file">
		    String fileName =null;
		    String path=null;
		    fileName=getSubmittedFileName(filePart);
		    if(fileName!=null&&(fileName.indexOf(".png")!=-1||fileName.indexOf(".jpg")!=-1||fileName.indexOf(".jpeg")!=-1)){
		    	//System.out.println(fileName);
		    	UserDetails details=DBUtil.getUserDetails2(dataSource, request.getParameter("email"));
		    	//fileName=details.getProf_pic_path().substring(details.getProf_pic_path().lastIndexOf('/')+1);
		    	//System.out.println(fileName);
		    	//String fileName="abc";
		    	FileInputStream fin=null;
		    	FileOutputStream fout=null;
		    	try {
		    		InputStream fileContent = ((Part) filePart).getInputStream();
		    		fin=(FileInputStream) fileContent;
		    		path=request.getRealPath("")+"images/"+request.getParameter("email")+".png"; //for storing in file system--absolute path of Web Content
		    		System.out.println(path);
		    		File f=new File(path);
		    		if(f.exists()) {
		    			f.delete();
		    		}
		    		fout=new FileOutputStream(path);
		    		int i=-1;
		    		while((i=fin.read())!=-1){
		    			fout.write(i);
		    		}
	    		}catch(IOException e) {}
		    	finally {
		    		fin.close();
		    		fout.close();
		    	}
		    	path=request.getContextPath()+"/images/"+request.getParameter("email")+".png"; //storing in database--relative path of Web Content
		    }
		    String about=request.getParameter("about");
		    if(about.equals("")){
		    	about=null;
		    }
		    DBUtil.updateUserDetails2(dataSource, request.getParameter("email"), about, path);
		    response.sendRedirect("profile.jsp?email="+request.getParameter("email"));
		}
		else if(request.getParameter("service").equals("Chat")){
			ArrayList values=new ArrayList();
			User user=(User) request.getSession().getAttribute("user");
			values.add(1);
			values.add(user.getEmail());
			values.add(request.getParameter("dest"));
			values.add(request.getParameter("message"));
			values.add((Long)System.currentTimeMillis()/1000);
			if(DBUtil.insertRow(dataSource,"chat", values, 1)){
					response.sendRedirect("chat.jsp?email="+request.getParameter("dest"));
			}
		}
		else{
			doGet(request,response);
		}
	}
	private static String getSubmittedFileName(Part part) {
	    for (String cd : ((Part) part).getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

}
