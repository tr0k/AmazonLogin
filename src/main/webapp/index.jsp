<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Single page template</title>
    <link rel="stylesheet" href="//code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.css" />
    <script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
    <script src="//code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.js"></script>
</head>

<body>

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

<div data-role="page">

    <div data-role="header">
        <h1>Welcome to Test Page!</h1>
    </div><!-- /header -->

    <div data-role="content" style="text-align: center;">

        <!-- Login with Amazon -->
        <a href="javascript:;" id="LoginWithAmazon" onclick="parentNode.submit();">
            <img border="0" alt="Login with Amazon"
                 src="https://images-na.ssl-images-amazon.com/images/G/01/lwa/btnLWA_drkgry_312x64_pressed.png"
                 width="156" height="32"/>
        </a>
    </div><!-- /content -->

    <div data-role="footer">
        <h4>Hope you enjoy stay with us :)</h4>
    </div><!-- /footer -->

</div><!-- /page -->

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
