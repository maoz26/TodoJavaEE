<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  import="model.* , java.util.*" errorPage="/error.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html/; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="./CSS/add_task_style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script type="text/javascript" src="../JS/bootstrap.min.js"></script>
    <title>Session</title>
    <jsp:include page="adminmenu.jsp"/>
</head>

<body>
    <div class="col-md-9 col-md-offset-3">
        <h2>Session</h2>
        <table class="table">
            <%@ taglib uri="/WEB-INF/tld/todolist.tld" prefix="sessions" %>
            <sessions:sessionstag sessions="${sessions}"></sessions:sessionstag>
        </table>
    </div>
</body>
</html>