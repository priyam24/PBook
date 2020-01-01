<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.priyam.User"%>
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
    <style>
.coll{
margin: 20px;
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
  </style>
<title>Registration</title>
</head>
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%> 
<%
User user=(User)session.getAttribute("user");
String ver=(String)session.getAttribute("verification");
if(user!=null&&ver!=null&&ver.equals("y")){
	request.getRequestDispatcher("home.jsp").forward(request, response);
}
String warning=(String)request.getAttribute("warning");
%>
<div class="container-fluid coll">
<div class="row">
<div class="col-sm-4"></div>
<div class="col-sm-4">
<div class="card">
<div class="card-body">
<h5 class="text-danger" ><%= (warning!=null)?warning:"" %></h5>
  <h2>Please enter your details</h2>
  <form action="ValidateServlet" method="post">
    <div class="form-group">
      <label for="uname">Email:</label>
      <input type="text" class="form-control" id="uname" placeholder="Enter email" name="email" required>
      <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
    </div>
        <div class="form-group">
      <label for="uname">Name:</label>
      <input type="text" class="form-control" id="uname" placeholder="Enter name" name="name" required>
      <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
      <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
    </div>
    <button type="submit" name="validate" value="register" class="btn btn-primary">Submit</button>
  </form>
  <h5>Registered already?<a href="login.jsp">Login here.</a></h5>
</div>
</div>
</div>
<div class="col-sm-4"></div>
</div>
</div>
</body>
</html>