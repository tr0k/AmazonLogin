<%@ page import="beans.UserInfo" %>
<%@ page import="aws.ListAccessKeys" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Obtain Amazon SDK -->
    <div id="amazon-root"></div>
    <script type="text/javascript">
        var clientId = 'amzn1.application-oa2-client.78eefa18c9a741649d84af098aa7f915';
        window.onAmazonLoginReady = function() {
            amazon.Login.setClientId(clientId);
        };
        (function(d) {
            var a = d.createElement('script'); a.type = 'text/javascript';
            a.async = true; a.id = 'amazon-login-sdk';
            a.src = 'https://api-cdn.amazon.com/sdk/login1.js';
            d.getElementById('amazon-root').appendChild(a);
        })(document);
    </script>
</head>
<body>

<div>
    <% UserInfo user = new UserInfo();%>
    User name: <%= user.getName()%><br>
    User id: <%= user.getUserId()%><br>
    Email address: <%= user.getEmail()%>
</div>
<div>
    <a class="ui-button ui-widget ui-corner-all" id="LogoutBtn">Logout</a>
</div>
<div>
    <form action="aws" method="POST" target="\aws">
        <input class="ui-button ui-widget ui-corner-all" type="submit" value="Use AWS">
    </form>
</div>

<script type="text/javascript">
    document.getElementById('LogoutBtn').onclick = function() {
        amazon.Login.logout();
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/handlelogout");
        xhr.send(null);
        alert("You have been logged out successfully.");
        window.location.href = "index.jsp";
    };
</script>
</body>
</html>
