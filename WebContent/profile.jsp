 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*,com.priyam.User,com.priyam.UserDetails,java.util.Date,java.text.SimpleDateFormat,com.priyam.Post,java.util.ArrayList,com.priyam.DBUtil,javax.annotation.Resource,javax.sql.DataSource"%>
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
  .loader {
  left: 50%;
  margin-left: -4em;
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
  height:500px;
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
<title>Profile</title>
</head>
<%!
@Resource(name="jdbc/hello")
private DataSource dataSource;
%>
<%
User user=(User)session.getAttribute("user");
String ver=(String)session.getAttribute("verification");
	if(user==null||ver==null|| (ver!=null&&!ver.equals("y"))){
		response.sendRedirect("login.jsp");
	}
	else{
%>
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%>
<%
User profile=DBUtil.getUserDetails(dataSource, request.getParameter("email"));
UserDetails details=DBUtil.getUserDetails2(dataSource, request.getParameter("email"));
if(details!=null){
	//System.out.println(details.getProf_pic_path());
	//System.out.println(details.getUpdate_count());
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
	if(details.getAbout()==null){
		details.setAbout("");
	}
}
%>
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
      <li class="nav-item active">
		<a style="border-radius: 25px;" class="nav-link fas fa-user-circle btn btn-primary" href=<%= "profile.jsp?email="+user.getEmail() %>> Profile</a>
      </li>
      <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-search btn btn-primary" href="finder.jsp"> Finder</a>
      </li>
      <li class="nav-item">
        <a style="border-radius: 25px;" class="nav-link fas fa-user-friends btn btn-primary" href="suggestions.jsp"> Suggestions</a>
      </li>    
      <li class="nav-item">
        <a style="border-radius: 25px;" style="border-radius: 25px;" class="nav-link fas fa-power-off btn btn-primary" data-toggle="modal" data-target="#myModal" href="#">Logout</a>
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
<div class="container-fluid" style="margin-top:80px">
<div class="card" style="opacity:1.0;width:250px;border-radius: 50px;" >
<img class="card-img-top" src=<%= (details!=null)?details.getProf_pic_path():"images/image.png" %> alt="Card image" style="width:100%;border-radius: 50px;" >
<div class="card-body">
<div class="card-title">
<h5>Name: <%= profile.getName() %></h5>
</div>
<div class="card-title">
<h6><i>Email: <%= profile.getEmail() %></i></h6>
</div>
<div class="card-title">
<h6><i>Short bio: <%= (details!=null)?details.getAbout():"" %></i></h6>
</div>
<div class="row" >
<div class="col-sm">
<a class="card-link" href=<%= "followers.jsp?email="+profile.getEmail()%> >Followers</a>
</div>
<div class="col-sm">
<a class="card-link" href=<%= "following.jsp?email="+profile.getEmail()%> >Following</a> 
</div>
<% if(user!=null && user.getEmail().equals(profile.getEmail())){ 
%>
<!-- Button to Open the Modal -->
<div class="col-sm">
<button style="border-radius: 50px;" type="button" class="btn btn-primary" data-toggle="modal" data-target="#myEditModal">
  Edit profile
</button>
</div>

<!-- The Modal -->
<div class="modal fade" id="myEditModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Edit here</h4>

        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
            <form action="ServicesServlet" method="post" enctype="multipart/form-data">  
            <input type="hidden" name="email" value=<%= user.getEmail() %> />
			Select profile photo: <input type="file" accept=".jpg, .png" name="fname" /><br><br>
			Your Bio: <br><br>
			<textarea class="form-control" name="about" rows="5" id="comment"> <%= (details!=null)? details.getAbout():"" %></textarea><br><br>
			<button style="border-radius: 25px;" class="btn btn-primary" type="submit" name="service" value="upload" >Submit</button>  
		</form>  
      </div>


    </div>
  </div>
</div>
<%} 
%>


<%
if(user!=null && !user.getEmail().equals(profile.getEmail())){
	String useremail=user.getEmail();
	if(DBUtil.isFollowing(dataSource, user.getEmail(), profile.getEmail())){
	%>
	<div class="col-sm">
	<a class="card-link" href=<%="ServicesServlet?service=unfollow&email1="+useremail+"&email2="+profile.getEmail() %> >Unfollow</a>
	</div>
<%
	}else{
%>
<div class="col-sm">
<a class="card-link" href=<%="ServicesServlet?service=follow&email1="+useremail+"&email2="+profile.getEmail() %> >Follow</a>
</div>
<%
	}%>
	<div class="col-sm">
	<a class="card-link" href=<%= "chat.jsp?email="+profile.getEmail() %> >Message</a>
	</div>
	</div>
	</div>
</div>
<%  }
else{
%>
</div>
	</div>
</div>
<div class="row">
<div class="col-sm-6">
<div class="container-fluid card collup" style="opacity:0.9;border-radius: 30px;">
<div class="card-body">
<form action="ServicesServlet">
<div class="form-group">
  <h5><label for="message">Post something:</label></h5>
  <textarea name="message" class="form-control" rows="5" id="comment" required></textarea>
</div>
<input type="hidden" name="page" value="profile" />
  <button style="border-radius: 50px;" type="submit" name="service" value="Post" class="btn btn-primary">Submit</button>
</form>
</div>
</div>
</div>
<div class="col-sm-6"></div>
</div>
<%
}
%>
<h4 class="text-white collup" >Posts :</h4>
<div class="container-fluid collup scrolling">
<%
if(user!=null){
ArrayList<Post> posts=DBUtil.getUserPosts(dataSource, profile.getEmail());
for(int i=0;i<posts.size();i++){
	Post curpost=posts.get(i);
	User curuser=DBUtil.getUserDetails(dataSource, curpost.getEmail());
	boolean isLiked=DBUtil.isLiked(dataSource, curpost.getId(), user.getEmail());
	String format=new SimpleDateFormat("dd-MM-yyyy").format(curpost.getDate());
	String liking=(isLiked)?"Unlike":"Like";
	ArrayList<String> likers=curpost.getLikers();
	ArrayList<User> users=new ArrayList<User>();
	ArrayList<UserDetails> usersdetails=new ArrayList<UserDetails>();
	for(String u: likers){
		users.add(DBUtil.getUserDetails(dataSource, u));
		usersdetails.add(DBUtil.getUserDetails2(dataSource, u));
	}
	%>
	<div class="card collup" style="border-radius: 30px;">
	<div class="card-header">
	<div class="row">
	<div class="col-sm-1" align="left">
	<img src= <%= (details!=null)?details.getProf_pic_path():"images/image.png" %> class="rounded-circle" alt="Profile pic" width="25" height="25" >
	</div>
	<div class="col-sm-2" align="left">
	<h6><a href= <%= "profile.jsp?email="+curuser.getEmail() %> > <%= curuser.getName() %></a></h6>
	</div>
	<div class="col-sm-9" align="right" >
	<h6>Date: <%= format %></h6>
	</div>
	</div>
	</div>
	<div class="card-body">
	<i><%= curpost.getMessage() %></i>
	</div>
	<div class="card-footer">
	<div class="row">
	<div class="col-sm" >
	<form action="ServicesServlet">
	<input type="hidden" name="message" value=<%= curpost.getMessage() %> />
	<input type="hidden" name="id" value=<%= curpost.getId() %> />
	<input type="hidden" name="page" value="profile" />
	<input type="hidden" name="email" value=<%= curpost.getEmail() %> />
	<input type="hidden" name="likes" value=<%= curpost.getLikes() %> />
	<input type="hidden" name="active" value=<%= curpost.isActive() %> />
	<h6><button style="border-radius: 50px;" type="submit" name="service" value="<%= liking %>" class="btn btn-primary"><%= liking %>  <i class="fas fa-thumbs-up"></i></button>
	<span style="border-radius: 50px;" class="badge badge-dark btn " data-toggle="modal" data-target=<%= "#postLikes"+i %> > <%=curpost.getLikes()%> likes </span></h6>
	</form>
</div>
	<%
	if(user.getEmail().equals(curpost.getEmail())){
		%>
		<div class="col-sm" align="right" >
		<form action="ServicesServlet">
		<input type="hidden" name="message" value=<%= curpost.getMessage() %> />
		<input type="hidden" name="id" value=<%= curpost.getId() %> />
		<input type="hidden" name="email" value=<%= curpost.getEmail() %> />
		<input type="hidden" name="page" value="profile" />
		<button style="border-radius: 50px;" type="submit" name="service" value="Remove" class="btn btn-primary">Remove</button>
		</form>	
		</div>
	<%
	}%>
	
	</div>
	</div>
	</div>
	<div class="modal fade" id=<%= "postLikes"+i %> style="opacity: 1.0;" >
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title"><%= users.size()+" user(s) liked this post" %></h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body scrolling">
          <%
          for(int index=0;index<users.size();index++){%>
        	  <a style="border-radius: 50px;" class="list-group-item list-group-item-action" href= <%= "profile.jsp?email="+users.get(index).getEmail() %> >
        	  <img src= <%= (usersdetails.get(index).getProf_pic_path()!=null)?usersdetails.get(index).getProf_pic_path():"images/image.png" %> class="rounded-circle" alt="Profile pic" width="25" height="25" >
        	   <%=users.get(index).getName() %>
        	   </a>
          <%}
          %>
          
        </div>
        
      </div>
    </div>
  </div>
 <%} }
 }%>
 </div>
</body>
</html>