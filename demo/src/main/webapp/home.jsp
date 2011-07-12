<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${user.username}'s Blog</title>
	<link rel="shortcut icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<link rel="icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<!-- ExtJS 4 -->
	<script type="text/javascript">
		var extUrl = '${_EXT_URL_}';
	</script>
	<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/resources/css/ext-all.css" />
	<script type="text/javascript" src="${_EXT_URL_}/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${_EXT_URL_}/examples/shared/example.css" />
	<script type="text/javascript" src="./home.js"></script>
    <style>
        .x-grid-cell-content b {
            /*display: block;*/
        }
        .x-grid-cell-content .x-grid-cell-inner {
            white-space: normal;
        }
        .x-grid-cell-content p {
        	height: 24px;
        	font-size: 13px;
        }
        .x-grid-cell-content a {
            color: #385F95;
            text-decoration: none;
        }
        .x-grid-cell-content a:hover {
            text-decoration:underline;
        }
		.x-grid-cell-content .x-grid-cell-innerf {
			padding: 5px;
		}
		.x-grid-rowbody {
	        padding: 0 5px 5px 5px; 
		}
		#user-info-panel img, img#avatar  {
			padding: 2px;
			margin-right: 8px;
			border: 1px #BCCFE5 solid;
		}
		#user-overview-panel li {
			float: left;
			font-size: 14px;
			font-weight: bold;
			padding-right: 8px;
		}
		#user-info-panel a, #user-overview-panel a {
			font-size: 13px;
			text-decoration: none;
		}
		#user-info-panel a:hover, #user-overview-panel a:hover {
			font-size: 13px;
			text-decoration: underline;
		}
		.face-icon,.picture-icon,.video-icon,.music-icon,.topic-icon,.none-icon {
			background-image: url(http://zsn.cc/resources/icons/silk/png/image.png) !important;
		}
		#btn-post .x-btn-inner {
			font-size: 18px;
		}
		#msg-content {
			font-size: 14px;
		}
    </style>
    
</head>
<body>

    <span id="user" class="x-hidden"> Welcome to Demo Blog of <a href="./profile.jxp?id=${user.id}">${user.username}</a> :: Home</span>
	
	<div id="user-info" class="x-hidden">  
		<img src="${user.avatar}" style="float:left"/>
		<p class="x-grid-cell-content">
			<a href="./home.jxp?id=${user.id}" style="font-size: 18px;">${user.username}</a>&nbsp;
			(<a href="./user.jxp" style="font-size: 12px;">Edit</a>)&nbsp;
			(<a href="./signout.jxp" style="font-size: 12px;">Sign out</a>)</p>
		<p class="x-grid-cell-content"><a href="http://zsn.cc">http://zsn.cc</a></p>
	</div>
	
	<div id="user-overview" class="x-hidden">  
		<ul class="x-grid-cell-content">
			<li>${user.messages} <br/><a href="#">Messages</a></li>
			<li>${user.following} <br/><a href="#">Following</a></li>
			<li>${user.followers} <br/><a href="#">Followers</a></li>
		</ul>
    </div>
</body>
</html>