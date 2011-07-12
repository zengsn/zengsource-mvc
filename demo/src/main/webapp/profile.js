Ext.require([
    'Ext.form.*',
    'Ext.layout.container.Column',
    'Ext.tab.Panel'
]);


/*!
 * Ext JS Library 3.3.1
 * Copyright(c) 2006-2010 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
Ext.onReady(function(){

    Ext.QuickTips.init();

    var bd = Ext.getBody();

    /*
     * ================  Simple form  =======================
     */
    //bd.createChild({tag: 'h2', html: 'Form 1 - Very Simple'});

    Ext.define('user.profile', {
        extend: 'Ext.data.Model',
        fields: [            
            'id', 'username', 'email', 'avatar'
        ]
    });

    Ext.define('user.fielderror', {
        extend: 'Ext.data.Model',
        fields: ['id', 'msg']
    });

    var formPanel = Ext.create('Ext.form.Panel', {
        url:'profile.jxp',
        frame:true,
        title: 'Edit Profile',
        bodyStyle:'padding:5px 5px 0',
        width: 340,
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75
        },
        defaultType: 'textfield',
        defaults: {
            anchor: '100%'
        },

        // configure how to read the XML data
        reader : Ext.create('Ext.data.reader.Xml', {
            model: 'user.profile',
            record : 'user',
            successProperty: '@success'
        }),

        // configure how to read the XML errors
        errorReader: Ext.create('Ext.data.reader.Xml', {
            model: 'user.fielderror',
            record : 'field',
            successProperty: '@success'
        }),

        items: [{
            xtype: 'displayfield',
            fieldLabel: 'Username',
            name: 'username',
            value: 'loading ...'
        }, {
            fieldLabel: 'Email',
            name: 'email',
            vtype:'email'
        }, {
			xtype: 'filefield',
            fieldLabel: 'Avatar',
            name: 'avatar'
        }, {
            xtype: 'displayfield',
            name: 'avatarpreview',
            fieldLabel: 'Preview',
			height: 55,
            value: '<img id="img-avatar" src="'+Ext.get('user-avatar').dom.src+'" style="width:50px;height:50px;"/>'
        }],

        buttons: [{
            text: 'Reload',
			handler : function() {
				formPanel.getForm().load({
					url: 'user.jxp',
					params: {
						action: 'load'
					},
					waitMsg: 'Loading...',
					success: function(form, action) {
						Ext.get('img-avatar').dom.src=action.result.data.avatar;
					},
					failure: function(form, action) {
						Ext.Msg.alert('Failed', form.getValues(true));
					}
				});
			}
        },{
            text: 'Save',
            formBind: true,
            handler: function(){
                this.up('form').getForm().submit({
                    url: 'profile.jxp',
                    submitEmptyText: false,
                    waitMsg: 'Saving Data...',
					success: function(form, action) {
						Ext.Msg.alert('Succeed!');
					},
					failure: function(form, action) {
						Ext.Msg.alert('Failed', form.getValues(true));
					}
                });
            }
        },{
            text: 'Cancel',
            handler: function(){
                window.location.replace('./home.jxp');
            }
        }]
    });

    //formPanel.render(document.body);	
	
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
					text: 'Return to home',
					flex: 1,	
					//sacle: 'large',
					iconAlign: 'left',
					iconCls: 'home-icon',
					tooltip: '',
					handler: function(btn) {
						window.location.replace('./home.jxp');
					}
				},{
					xtype:'button',
					text: 'Sign Out',
					flex: 1,	
					//sacle: 'large',
					iconAlign: 'right',
					margins: '0',
					iconCls: 'signout-icon',
					tooltip: 'Sign on a new blog account.',
					handler: function(btn) {
						window.location.replace('./signout.jxp');
					}
				}]	
			}]
		}]
	});
	
	formPanel.getForm().load({
		url: 'user.jxp?action=load',
		waitMsg: 'Loading...'
	});
	
});