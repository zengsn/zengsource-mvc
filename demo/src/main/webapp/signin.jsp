<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Sign in</title>
	<link rel="shortcut icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<link rel="icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<!-- ExtJS 4 -->
	<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="./resources/css/icon.css" />
	<script type="text/javascript" src="${_EXT_URL_}/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${_EXT_URL_}/examples/shared/example.css" />
	<script type="text/javascript" src="./signin.js"></script>
    
</head>
<body>
	<div class="x-hidden">
		<h1>Sign in | <a href="./signon.jxp">Sign on</a></h1>

		<p>The page markup of this example is rewritten with official ExtJS 4 form example:  
			<a href="http://dev.sencha.com/deploy/ext-4.0-beta3/examples/form/xml-form.html">XML Form</a>.</p>
		<ul class="feature-list">
			<li>Username: demo</li>
			<li>Password: demo</li>
		</ul>

		<p><a href="http://www.zengsource.org/demo">Download</a> the demo.</p>
		
		<p><a id="back-url" href="${_BACK_}">${_BACK_}</a></p>
		
		<div id="form-ct"></div>
	</div>
    
</body>
</html>