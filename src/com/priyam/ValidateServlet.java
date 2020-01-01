package com.priyam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;    
import javax.mail.internet.*;    

/**
 * Servlet implementation class ValidateServlet
 */
@WebServlet("/ValidateServlet")
public class ValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String usermail="priyamroy137@gmail.com";
	private static String pass="pm:sphs-5'@";
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Resource(name="jdbc/hello")
	private DataSource dataSource;
    public ValidateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("validate").equals("logout")){
			HttpSession session=request.getSession();
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("validate").equals("verify")){
			HttpSession session=request.getSession();
			if(request.getParameter("otp").equals(session.getAttribute("otp"))) {
				session.setAttribute("verification", "y");
				if(!request.getParameter("page").equals("login")) {
					User user=(User)session.getAttribute("user");
					ArrayList values=new ArrayList();
					values.add(user.getEmail());
					values.add(user.getName());
					System.out.println(user.getEmail());
					values.add(session.getAttribute("pass"));
					values.add(true);
					ArrayList values1=new ArrayList();
					values1.add(user.getEmail());
					values1.add(null);
					values1.add(null);	
					session.removeAttribute("pass");
					if(DBUtil.insertRow(dataSource,"user", values, 0)&&DBUtil.insertRow(dataSource,"user_details", values1, 0)) {
						response.sendRedirect("home.jsp");
					}
					else {
						DBUtil.deleteUser(dataSource, request.getParameter("email"));
						request.getRequestDispatcher("register.jsp").forward(request, response);
					}
				}
				else response.sendRedirect("home.jsp");
			}
			else{
				request.setAttribute("warning", "Invalid details. Please try again.");
				if(request.getParameter("page").equals("login")){
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				else{
					DBUtil.deleteUser(dataSource, request.getParameter("email"));
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}
			}
		}
		else if(request.getParameter("validate").equals("login")){
			User user=DBUtil.getUserLogin(dataSource,request.getParameter("email"),request.getParameter("password"));
			if(user!=null){
				String otp=generateOTP();
				System.out.println(otp);
				HttpSession session=request.getSession();
				session.setAttribute("verification", "n");
				session.setAttribute("otp", otp);
				session.setAttribute("user", user);
				request.setAttribute("page", "login");
				request.getRequestDispatcher("verify.jsp").forward(request, response);
				send(usermail,pass,request.getParameter("email"),"User Verification for PBook","Hi, your OTP is "+otp+".");
			}
			else{
				request.setAttribute("warning", "Invalid details. Please try again.");
				request.getRequestDispatcher("login.jsp").forward(request, response);	
			}
		}
		else if(request.getParameter("validate").equals("register")){			
				HttpSession session=request.getSession();
				User user=new User();
				user.setEmail(request.getParameter("email"));
				user.setName(request.getParameter("name"));
				user.setActive(true);
				session.setAttribute("user", user);
				session.setAttribute("pass", request.getParameter("password"));
				String otp=generateOTP();
				System.out.println(otp);
				session.setAttribute("verification", "n");
				session.setAttribute("otp", otp);
				request.setAttribute("page", "registration");
				request.getRequestDispatcher("verify.jsp").forward(request, response);
				send(usermail,pass,request.getParameter("email"),"User Verification for PBook","Hi, your OTP is "+otp+".");
		}
	}
	
	public static void send(String from,String password,String to,String sub,String msg){  
        //Get properties object    
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "587");    
        //get Session   
//        Session session = Session.getDefaultInstance(props,null);  
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);  
         message.setFrom(new InternetAddress(from));  
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         message.setSubject(sub);    
         message.setText(msg);    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}    
           
  }  
	
    static String generateOTP() 
    { 
        // Using numeric values 
        String numbers = "0123456789"; 
  
        // Using random method 
        Random rndm_method = new Random(); 
  
        char[] otp = new char[6]; 
  
        for (int i = 0; i < 6; i++) 
        { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            otp[i] = 
             numbers.charAt(rndm_method.nextInt(numbers.length())); 
        } 
        return String.valueOf(otp); 
    } 

}
