<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="ISO-8859-1"  errorPage="/error.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html/; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="../CSS/style.css">
	<link rel="stylesheet" type="text/css" href="../CSS/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script type="text/javascript" src="../JS/bootstrap.min.js"></script>
	<script type="text/javascript" src="../JS/scripts.js"></script>
	<title>Login</title>
</head>
<body>

<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="login.jsp" class="active" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="#" id="register-form-link">Register</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
							
								<form id="login-form" action="/ToDoListController/login" method="post" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="id" id="id" tabindex="1" class="form-control" placeholder="Id" value="">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="passwordL" tabindex="2" class="form-control" placeholder="Password">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
								</form>
								
								<form id="register-form" action="/SigninController/signup" method="post" role="form" style="display: none;">
									<div class="form-group">
										<input type="text" name="fname" id="username" tabindex="1" class="form-control" placeholder="First Name" value="">
									</div>
									<div class="form-group">
										<input type="text" name="lname" id="lname" tabindex="1" class="form-control" placeholder="Last Name" value="">
									</div>
									<div class="form-group">
										<input type="text" name="id" tabindex="2" class="form-control" placeholder="ID">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="passwordR" tabindex="2" class="form-control" placeholder="Password">
									</div>

									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
								</form>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>