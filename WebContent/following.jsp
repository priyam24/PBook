<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.priyam.User,com.priyam.UserDetails,java.io.File,java.util.ArrayList,com.priyam.DBUtil,javax.annotation.Resource,javax.sql.DataSource"%>
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
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
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
  opacity: 0.75;
}
div.card:hover{
opacity: 1.0;
}
  .scrolling {
  height:500px;
  overflow-y: auto;
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
<title>Following</title>
</head>
<%!
@Resource(name="jdbc/hello")
private DataSource dataSource;
%>
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
User user=(User)session.getAttribute("user");
String ver=(String)session.getAttribute("verification");
	if(user==null||ver==null|| (ver!=null&&!ver.equals("y"))){
		response.sendRedirect("login.jsp");
	}
	else{%>
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
          <h4 class="modal-title">PBook</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
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
<div class="row">
<div class="col-sm-2"></div>
<div class="col-sm-8">


<div class="container-fluid" style="margin-top:80px">
<%
User profile=DBUtil.getUserDetails(dataSource, request.getParameter("email"));
if(!user.getEmail().equals(profile.getEmail())){
%>
<h5><a href=<%= "profile.jsp?email="+profile.getEmail()%> >Go back to <%= profile.getName() %>'s profile</a></h5>
<%
}
ArrayList<String> followingEmails=DBUtil.getFollowing(dataSource, profile.getEmail());
ArrayList<User> followings=new ArrayList<User>();
for(int i=0;i<followingEmails.size();i++){
	followings.add(DBUtil.getUserDetails(dataSource, followingEmails.get(i)));
}
%>
<button type="button" class="btn btn-outline-primary">Following: <span class="badge badge-light"><%= followings.size() %></span></button>

<div class="scrolling">
<div class="card-columns collup">
<%
for(int i=0;i<followings.size();i++){
	UserDetails details=DBUtil.getUserDetails2(dataSource, followings.get(i).getEmail());
	if(details!=null){
		//System.out.println(details.getProf_pic_path());
		if(details.getProf_pic_path()==null){
			details.setProf_pic_path("images/image.png");
		}
		else{
			String path=details.getProf_pic_path();
			path=path.substring(path.lastIndexOf('/')+1);
			path=request.getRealPath("")+"images/"+path;
			File f=new File(path);
			if(!f.exists()){
				details.setProf_pic_path("images/image.png");	
			}
		}
	}
	%>
	<a href=<%="profile.jsp?email="+followings.get(i).getEmail()  %> >
			  <div class="card bg-primary" style="width:250px;border-radius: 50px;">
  <img class="card-img-top" src=<%= (details!=null)?details.getProf_pic_path():"images/image.png" %> alt="Card image" style="width:100%;border-radius: 50px;" >
    <div class="card-body text-white text-center">
		<%=followings.get(i).getName() %> 
		</div>
		</div>
	
	</a>
<%
}
%>
</div>
</div>
</div>
<div class="col-sm-2"></div>
</div>


<%
}
%>
</div>
</body>
</html>