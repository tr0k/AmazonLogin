<html>
<body>
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

<%--<form action="awslogin" method="GET">--%>
    <a href="javascript:;" id="LoginWithAmazon" onclick="parentNode.submit();">
        <img border="0" alt="Login with Amazon"
             src="https://images-na.ssl-images-amazon.com/images/G/01/lwa/btnLWA_gold_156x32.png"
             width="156" height="32"/>
    </a>
<%--</form>--%>


<script type="text/javascript">

    document.getElementById('LoginWithAmazon').onclick = function() {
        options = { scope : 'profile' };
        amazon.Login.authorize(options,
            'http://localhost:8080/handlelogin');
        return false;
    };

</script>
</body>
</html>
