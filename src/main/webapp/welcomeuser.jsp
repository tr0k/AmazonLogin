<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome </title>
</head>
<body>
<jsp:useBean id = "user" scope="session" class = "beans.UserInfo"/>
User name: <%= user.getName()%><br>
User id: <%= user.getUserId()%><br>
Email address: <%= user.getEmail()%>
</body>
</html>
