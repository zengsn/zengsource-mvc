Ext.require([
    'Ext.form.*',
    'Ext.data.*'
]);

Ext.onReady(function(){

    Ext.define('example.account', {
        extend: 'Ext.data.Model',
        fields: [
            'username', 'password'
        ]
    });

    Ext.define('example.fielderror', {
        extend: 'Ext.data.Model',
        fields: ['id', 'msg']
    });

    var formPanel = Ext.create('Ext.form.Panel', {
        //renderTo: 'form-ct',
        frame: true,
        title:'Sign In to demo blog',
        width: 340,
        bodyPadding: 5,
        waitMsgTarget: true,

        fieldDefaults: {
            labelAlign: 'right',
            labelWidth: 85,
            msgTarget: 'side'
        },

        // configure how to read the XML data
        reader : Ext.create('Ext.data.reader.Xml', {
            model: 'example.account',
            record : 'account',
            successProperty: '@success'
        }),

        // configure how to read the XML errors
        errorReader: Ext.create('Ext.data.reader.Xml', {
            model: 'example.fielderror',
            record : 'field',
            successProperty: '@success'
        }),

        items: [{
            xtype: 'fieldset',
            title: 'demo/demo',
            defaultType: 'textfield',
            defaults: {
                width: 280
            },
            items: [{
                    fieldLabel: 'Username',
                    emptyText: 'type the username',
                    name: 'username'
                }, {
                    fieldLabel: 'Password',
                    name: 'password',
					inputType: 'password',
					allowBlank: false,
					validator: function(value) {
						//var username = this.previousSibling('[name=username]');
						//return (value === 'demo' && username.getValue() === 'demo') ? true : 'Please use demo/demo.'
						return true;
					}
                }
            ]
        }],

        buttons: [{
            text: 'Sign In',
            //disabled: true,
            formBind: true,
			iconCls: 'signin-icon',
            handler: function(){
                this.up('form').getForm().submit({
                    url: 'signin.jxp',
                    submitEmptyText: false,
                    waitMsg: 'Signing in...',
					success: function(form, action) {
						//Ext.Msg.alert('Done!', form.getValues(true));
						//window.location.replace('./user.jxp?id=demo');
						var backUrl = './home.jxp';
						var getParams = document.URL.split("?");
						if (getParams.length > 1) {
							// transforming the GET parameters into a dictionnary
							var params = Ext.urlDecode(getParams[getParams.length - 1]);
							backUrl = params.back;
						}
						window.location.replace(backUrl);
					},
					failure: function(form, action) {
						Ext.Msg.alert('Failed', form.getValues(true));
					}
                });
            }
        }]
    });
	
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
				width: 340,
				margins:'0 0 5 0'
			},
			items:[formPanel, {
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
					text: 'Return',
					flex: 1,	
					//sacle: 'large',
					iconAlign: 'left',
					iconCls: 'back-icon',
					tooltip: '',
					handler: function(btn) {
						window.location.replace('./');
					}
				},{
					xtype:'button',
					text: 'Sign On',
					flex: 1,	
					//sacle: 'large',
					iconAlign: 'right',
					margins: '0',
					iconCls: 'signon-icon',
					tooltip: 'Sign on a new blog account.',
					handler: function(btn) {
						window.location.replace('./signon.jxp');
					}
				}]	
			}]
		}]
	});

});
