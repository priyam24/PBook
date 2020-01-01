<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.priyam.User,com.priyam.ChatBean,com.priyam.UserDetails,java.io.File,java.util.ArrayList,com.priyam.DBUtil,javax.annotation.Resource,javax.sql.DataSource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
<title>Chat</title>
<style>
nav{
opacity: 0.85;
}
nav:hover{
opacity: 1.0;
}
  .collup {
    margin-top:20px;
  }
    body { 
background-image: url("images/bg.jpg");
background-size: cover;
} 
div.card {
  opacity: 0.85;
}
div.card:hover{
opacity: 1.0;
}
  .scrolling {
  height:300px;
  overflow-y: auto;
}
    .coll2 {
    margin-top:20px;
    margin-bottom:20px;
  }
/* width */
::-webkit-scrollbar {
  width: 10px;
}
::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px grey; 
    border-radius: 10px;
}
::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px grey; 
}
</style>
</head>
<body>
<%!
@Resource(name="jdbc/hello")
private DataSource dataSource;
%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%>
<%
User user=(User)session.getAttribute("user");
String ver=(String)session.getAttribute("verification");
	if(user==null||ver==null|| (ver!=null&&!ver.equals("y"))){
		response.sendRedirect("login.jsp");
	}
	else { %>
<nav class="navbar navbar-dark navbar-expand-md fixed-top bg-primary">
  <a style="border-radius: 25px;" class="navbar-brand bg-primary text-white" href="home.jsp">PBook</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
            <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-home btn btn-primary" href=<%= "home.jsp?" %>> Home</a>
      </li>
      <li class="nav-item">
		<a style="border-radius: 25px;" class="nav-link fas fa-user-circle btn btn-primary" href=<%= "profile.jsp?email="+user.getEmail() %>> Profile</a>
      </li>
      <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-search btn btn-primary" href="finder.jsp"> Finder</a>
      </li>
      <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-user-friends btn btn-primary" href="suggestions.jsp"> Suggestions</a>
      </li>    
      <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-power-off btn btn-primary" data-toggle="modal" data-target="#myModal" href="#">Logout</a>
      </li>
    </ul>
  </div>  
</nav>
 <div class="modal fade" id="myModal" style="opacity: 1.0;" >
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Logout</h4>
          <button style="border-radius: 25px;" type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          Do you want to logout?
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <a style="border-radius: 25px;" class="text-white btn btn-danger" href=<%= "ValidateServlet?validate=logout" %>  >Logout</a>
        </div>
        
      </div>
    </div>
  </div>
<% 
User profile=DBUtil.getUserDetails(dataSource, request.getParameter("email"));
%>
<div class="container-fluid" style="margin-top:80px;">
<h5><a href=<%= "profile.jsp?email="+profile.getEmail()%> >Go back to <%= profile.getName() %>'s profile</a></h5>
<div class="card" style="opacity:0.7;border-radius: 30px;">
<div class="card-header" style="opacity:0.9;">
<h5>Chat</h5>
</div>
<div class="card-body scrolling">
<%
ArrayList<ChatBean> chats=DBUtil.getMessages(dataSource, user.getEmail(), profile.getEmail());
if(chats!=null){
for(int i=0;i<chats.size();i++){
	ChatBean chat=chats.get(i);
	%>

	<div class="row">
	<%
	if(chat.getFrom().equals(user.getEmail())){
		%>
		<div class="col-sm"></div>
		<div class="col-sm"></div>
		<div class="col-sm">
			<div class="card bg-dark text-white" style="margin-bottom:10px;border-radius: 50px;">
	<div class="card-body">
		<div class="card-text"><%= chat.getMessage() %></div>
		</div>
			</div>
	</div>
	<%}
	else{%>
		<div class="col-sm">
					<div class="card bg-dark text-white" style="margin-bottom:10px;border-radius: 50px;">
	<div class="card-body">
		<div class="card-text"><%= chat.getMessage() %></div>
		</div>
		</div>
			</div>
			<div class="col-sm"></div>
		<div class="col-sm"></div>
	<%}
	%>
	</div>
<%
}
	}
%>
</div>
<div class="card-footer">
<div class="row">
<div class="col-sm-9"></div>
<div class="col-sm-3">
<form class="form-inline" action="ServicesServlet" method="post">
<div class="row">
<div class="col-sm">
<div class="form-group">
  <textarea style="border-radius: 25px;" name="message" class="form-control" rows="2" id="comment" placeholder="Type here..." required></textarea>
</div>
</div>
<input type="hidden" name="dest" value=<%= profile.getEmail() %> >
<div class="col-sm">
<div class="form-group">
  <button type="submit" name="service" value="Chat" class="btn"><i class='fas fa-location-arrow text-primary' style='font-size:36px;'></i></button>
</div>
</div>
</div>
</form>
</div>
</div>
</div>
</div>
</div>

<%
	}
%>
</body>
</html>