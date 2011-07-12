<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${user.username}'s Profile</title>
	<link rel="shortcut icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<link rel="icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<!-- ExtJS 4 -->
	<script type="text/javascript">
		var extUrl = '${_EXT_URL_}';
	</script>
	<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="./resources/css/icon.css" />
	<script type="text/javascript" src="${_EXT_URL_}/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${_EXT_URL_}/examples/shared/example.css" />
	<script type="text/javascript" src="./profile.js"></script>
</head>
<body>
	<div class="x-hidden">

		<h1>Dynamic Forms built with JavaScript</h1>
		
		<p>
		    These forms do not do anything and have very little validation. They solely demonstrate
		    how you can use Ext Forms to build and layout forms on the fly.
		</p>
		
		<p>The js is not minified so it is readable. See <a href="dynamic.js">dynamic.js</a>.</p>
		
		<img id="user-avatar" src="${user.avatar}" class="x-hidden"/>
		
	</div>

</body>
</html>