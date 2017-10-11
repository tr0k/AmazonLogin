<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="beans.SessionManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>Welcome page</title>
</head>
<body>
<c:choose>
    <c:when test="${SessionManager.getInstance().isSessionValid() ne true}">
        <span style="color: red; ">Session is invalid! </span>
    </c:when>
    <c:otherwise>
        <span style="color: green; ">Session is valid! </span>
        <%@ include file="WEB-INF/userinfo.jsp" %>
    </c:otherwise>
</c:choose>
</body>
</html>
