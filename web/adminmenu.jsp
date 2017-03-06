<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.*" errorPage="/error.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="../CSS/user_menu_style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script type="text/javascript" src="../JS/bootstrap.min.js"></script>
    <script type="text/javascript" src="../JS/scripts.js"></script>
    <title>Admin-Dashboard</title>
</head>

<body>
    <div id="leftBar" class="col-md-3">
        <%
        User currentUser = (User)session.getAttribute("user");
        if (currentUser == null){
            out.println("<jsp:forword page=/login.jsp/>");
        }
        String userName = currentUser.getUserName().substring(0,1).toUpperCase();
        userName = userName + currentUser.getUserName().substring(1);
        out.println("<div class=\"hellouser\">"+"Hello "+userName+"</div>");
        %>
        <ul>
            <li class="list-nav">
                <a class="list-btn" href="/controller/adminsession">
                    <span class="glyphicon glyphicon-dashboard"></span>&nbsp; Session
                </a>
            </li>
            <li class="list-nav">
                <a class="list-btn" href="/controller/userlist">
                    <span class="glyphicon glyphicon-user"></span>&nbsp; User-List
                </a>
            </li>
            <li class="list-nav">
                <a class="list-btn" href="/controller/login">
                    <span class="glyphicon glyphicon-log-out"></span>&nbsp; Log-Out
                </a>
            </li>
        </ul>
</div>
</body>
</html>