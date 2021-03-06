<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="/error.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html/; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="./CSS/add_task_style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
           integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script type="text/javascript" src="../JS/bootstrap.min.js"></script>
    <title>Add new task</title>
    <jsp:include page="usermenu.jsp"/>
</head>

<body>
    <div class="col-md-9 col-md-offset-3">
        <h2>Add new task</h2></br>
        <form id="addTask" method="post" action="/ToDoListController/addtask">
            <table>
                <tr>
                    <td>Title:</td>
                    <td><input  type="text" placeholder="title" name="title"  title="Insert Title"/></td>
                </tr>
                <tr>
                    <td>Description: </td>
                    <td><input  type="text" placeholder="description" name="description"  title="Insert Description"/></td>
                </tr>
            </table>
            <input class="submit" value="Submit" type="submit"/>
        </form>
    </div>
</body>
</html>