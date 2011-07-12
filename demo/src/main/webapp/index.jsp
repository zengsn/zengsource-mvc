<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("_EXT_URL_", "http://zsn.cc/3rdp/ext/4.0.0");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MVC Demo by ZSN.cc</title>
	<link rel="shortcut icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<link rel="icon" href="http://zsn.cc/resources/images/favicon.ico" />
	<!-- ExtJS 4 -->
	<link rel="stylesheet" type="text/css" href="${_EXT_URL_}/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="./resources/css/icon.css" />
	<script type="text/javascript" src="${_EXT_URL_}/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${_EXT_URL_}/examples/shared/example.css" />
	<script type="text/javascript">	
        Ext.onReady(function() {	
			Ext.QuickTips.init();
			var viewport = Ext.create('Ext.Viewport', {
				layout:'border',
				margins: '5 5 5 5',
				items: [{
					region: 'north',
					margins: '0 0 5 0',
					height: 0,
					html: ''
				},{
					region:'center',
					margins: '0 5 5 5',
					//frame: true,
					layout: {
						type: 'vbox',
						padding:'5',
						pack:'center',
						align:'center'
					},
					defaults:{
						width: 500,
						margins:'0 0 5 0'
					},
					items:[{
						margins:'5',						
						height: 80,
						xtype:'button',
						//sacle: 'large',
						iconAlign: 'top',
						iconCls: 'mvc-icon',
						text: 'A Java MVC library based on Spring Framework.'
					},{
						border: false,
						//frame: true,
						layout: {
							type:'hbox',
							padding:'0',
							//pack:'center',
							align:'middle'
						},
						defaults:{margins:'0 5 0 0'},
						items:[{
							xtype:'button',
							text: 'Demo with HTML',
                            flex: 1,	
    						sacle: 'large',
    						iconAlign: 'top',
    						iconCls: 'w3c-icon',
							disabled: true,
							tooltip: ''
						},{
							xtype:'button',
							text: 'Demo with ExtJS4',
                            flex: 1,	
    						sacle: 'large',
    						iconAlign: 'top',
    						iconCls: 'extjs4-icon',
							tooltip: 'View demo built with ExtJS 4 library.',
							handler: function(btn) {
								window.location.replace('./signin.jxp');
							}
						},{
							xtype:'button',
							text: 'Demo with jQuery',
                            flex: 1,	
    						sacle: 'large',
    						iconAlign: 'top',
    						iconCls: 'jquery-icon',
							disabled: true,
							tooltip: ''
						},{
							xtype:'button',
							text: 'Demo with YUI 3',
							margins:'0',
                            flex: 1,	
    						sacle: 'large',
    						iconAlign: 'top',
    						iconCls: 'yui3-icon',
							disabled: true,
							tooltip: ''
						}]						
					}]
				}]
			});
		});
	</script>
</head>
<body>
<div class="x-hidden">
	<h1>Hello World!</h1>
	<p>Click to <a href="./signin.jxp">Sign in</a> to view more demos. </p>
	<p>Or <a href="./signon.jxp">Sign on</a> a new account to try more demos.</p>
</div>
</body>
</html>