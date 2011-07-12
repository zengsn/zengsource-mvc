<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration Form</title>
	<link rel="shortcut icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<link rel="icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<!-- ExtJS 4 -->
	<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="./resources/css/icon.css" />
	<script type="text/javascript" src="${_EXT_URL_}/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${_EXT_URL_}/examples/shared/example.css" />
	<script type="text/javascript" src="./signon.js"></script>
    <style type="text/css">
        /* Styling of global error indicator */
        .form-error-state {
            font-size: 11px;
            padding-left: 20px;
            height: 16px;
            line-height: 18px;
            background: no-repeat 0 0;
            cursor: default;
        }
        .form-error-state-invalid {
            color: #C30;
            background-image: url(${_EXT_URL_}/resources/themes/images/default/form/exclamation.gif);
        }
        .form-error-state-valid {
            color: #090;
            background-image: url(${_EXT_URL_}/resources/themes/images/default/dd/drop-yes.gif);
        }

        /* Error details tooltip */
        .errors-tip .error {
            font-style: italic;
        }
    </style>
</head>
<body>

	<div class="x-hidden">
	    <h1>Registration Form | <a href="./signin.jxp">Sign in</a></h1>
	
	    <p>This example shows a common site registration form. The form appears simple but it shows a few special things: </p>
	    <ul class="feature-list">
	        <li>The display of field errors has been customized. Fields have <code>msgTarget:'none'</code> so
	            the errors are not displayed with the individual fields; instead event listeners are attached
	            to the FormPanel to group up all error messages into a custom global error indicator, with a
	            persistent tooltip showing the error details.</li>
	        <li>The "Terms of Use" link has an event handler attached so it opens the page in a modal Ext window.</li>
	        <li>The password fields have custom validation attached to verify the user enters the same value in both.</li>
	        <li>The submit button has <code>formBind:true</code> so it is only enabled when the form becomes valid.</li>
	    </ul>
	
	    <p>The js is not minified so it is readable. See <a href="registration.js">registration.js</a>.</p>
	</div>

</body>
</html>