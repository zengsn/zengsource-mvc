Ext.require([
    'Ext.form.*',
    'Ext.Img',
    'Ext.tip.QuickTipManager'
]);

Ext.onReady(function() {
    Ext.tip.QuickTipManager.init();
	
	Ext.define('account.fielderror', {
        extend: 'Ext.data.Model',
        fields: ['id', 'msg']
    });

    var formPanel = Ext.widget('form', {
        //renderTo: Ext.getBody(),
        frame: true,
        width: 340,
        bodyPadding: 10,
        bodyBorder: true,
        title: 'Sign on a new account',

        defaults: {
            anchor: '100%'
        },
        fieldDefaults: {
            labelAlign: 'left',
            msgTarget: 'none',
            invalidCls: '' //unset the invalidCls so individual fields do not get styled as invalid
        },
		 
		errorReader: Ext.create('Ext.data.reader.Xml', {
            model: 'account.fielderror',
            record : 'field',
            successProperty: '@success'
        }),

        /*
         * Listen for validity change on the entire form and update the combined error icon
         */
        listeners: {
            fieldvaliditychange: function() {
                this.updateErrorState();
            },
            fielderrorchange: function() {
                this.updateErrorState();
            }
        },

        updateErrorState: function() {
            var me = this,
                errorCmp, fields, errors;

            if (me.hasBeenDirty || me.getForm().isDirty()) { //prevents showing global error when form first loads
                errorCmp = me.down('#formErrorState');
                fields = me.getForm().getFields();
                errors = [];
                fields.each(function(field) {
                    Ext.Array.forEach(field.getErrors(), function(error) {
                        errors.push({name: field.getFieldLabel(), error: error});
                    });
                });
                errorCmp.setErrors(errors);
                me.hasBeenDirty = true;
            }
        },

        items: [{
            xtype: 'textfield',
            name: 'username',
            fieldLabel: 'User Name',
            allowBlank: false,
            minLength: 4
        }, {
            xtype: 'textfield',
            name: 'email',
            fieldLabel: 'Email Address',
            vtype: 'email',
            allowBlank: false
        }, {
            xtype: 'textfield',
            name: 'password1',
            fieldLabel: 'Password',
            inputType: 'password',
            style: 'margin-top:15px',
            allowBlank: false,
            minLength: 6
        }, {
            xtype: 'textfield',
            name: 'password2',
            fieldLabel: 'Repeat Password',
            inputType: 'password',
            allowBlank: false,
            /**
             * Custom validator implementation - checks that the value matches what was entered into
             * the password1 field.
             */
            validator: function(value) {
                var password1 = this.previousSibling('[name=password1]');
                return (value === password1.getValue()) ? true : 'Passwords do not match.'
            }
        },

        /*
         * Terms of Use acceptance checkbox. Two things are special about this:
         * 1) The boxLabel contains a HTML link to the Terms of Use page; a special click listener opens this
         *    page in a modal Ext window for convenient viewing, and the Decline and Accept buttons in the window
         *    update the checkbox's state automatically.
         * 2) This checkbox is required, i.e. the form will not be able to be submitted unless the user has
         *    checked the box. Ext does not have this type of validation built in for checkboxes, so we add a
         *    custom getErrors method implementation.
         */
        {
            xtype: 'checkboxfield',
            name: 'acceptTerms',
            fieldLabel: 'Terms of Use',
            hideLabel: true,
            style: 'margin-top:15px',
            boxLabel: 'I have read and accept the <a href="http://www.sencha.com/legal/terms-of-use/" class="terms">Terms of Use</a>.',

            // Listener to open the Terms of Use page link in a modal window
            listeners: {
                click: {
                    element: 'boxLabelEl',
                    fn: function(e) {
                        var target = e.getTarget('.terms'),
                            win;
                        if (target) {
                            win = Ext.widget('window', {
                                title: 'Terms of Use',
                                modal: true,
                                html: '<iframe src="' + target.href + '" width="950" height="500" style="border:0"></iframe>',
                                buttons: [{
                                    text: 'Decline',
                                    handler: function() {
                                        this.up('window').close();
                                        formPanel.down('[name=acceptTerms]').setValue(false);
                                    }
                                }, {
                                    text: 'Accept',
                                    handler: function() {
                                        this.up('window').close();
                                        formPanel.down('[name=acceptTerms]').setValue(true);
                                    }
                                }]
                            });
                            win.show();
                            e.preventDefault();
                        }
                    }
                }
            },

            // Custom validation logic - requires the checkbox to be checked
            getErrors: function() {
                return this.getValue() ? [] : ['You must accept the Terms of Use']
            }
        }],

        dockedItems: [{
            xtype: 'container',
            dock: 'bottom',
            layout: {
                type: 'hbox',
                align: 'middle'
            },
            padding: '10 10 5',

            items: [{
                xtype: 'component',
                id: 'formErrorState',
                baseCls: 'form-error-state',
                flex: 1,
                validText: 'Form is valid',
                invalidText: 'Form has errors',
                tipTpl: Ext.create('Ext.XTemplate', '<ul><tpl for="."><li><span class="field-name">{name}</span>: <span class="error">{error}</span></li></tpl></ul>'),

                getTip: function() {
                    var tip = this.tip;
                    if (!tip) {
                        tip = this.tip = Ext.widget('tooltip', {
                            target: this.el,
                            title: 'Error Details:',
                            autoHide: false,
                            anchor: 'top',
                            mouseOffset: [-11, -2],
                            closable: true,
                            constrainPosition: false,
                            cls: 'errors-tip'
                        });
                        tip.show();
                    }
                    return tip;
                },

                setErrors: function(errors) {
                    var me = this,
                        baseCls = me.baseCls,
                        tip = me.getTip();

                    errors = Ext.Array.from(errors);

                    // Update CSS class and tooltip content
                    if (errors.length) {
                        me.addCls(baseCls + '-invalid');
                        me.removeCls(baseCls + '-valid');
                        me.update(me.invalidText);
                        tip.setDisabled(false);
                        tip.update(me.tipTpl.apply(errors));
                    } else {
                        me.addCls(baseCls + '-valid');
                        me.removeCls(baseCls + '-invalid');
                        me.update(me.validText);
                        tip.setDisabled(true);
                        tip.hide();
                    }
                }
            }, {
                xtype: 'button',
                formBind: true,
                disabled: true,
                text: 'Submit to sign on',
                //width: 140,
				iconCls: 'signon-icon',
                handler: function() {
                    var form = this.up('form').getForm();

                    /* Normally we would submit the form to the server here and handle the response...*/                    
                    form.submit({
                        clientValidation: true,
                        url: 'signon.jxp',
                        success: function(form, action) {
							//Ext.Msg.alert('Succeeded!');
							window.location.replace('./signin.jxp')
                        },
                        failure: function(form, action) {
                            Ext.Msg.alert('Failed!');
                        }
                    });
                }
            }]
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
					text: 'Sign In',
					flex: 1,	
					//sacle: 'large',
					iconAlign: 'right',
					margins: '0',
					iconCls: 'signin-icon',
					tooltip: 'Sign on a new blog account.',
					handler: function(btn) {
						window.location.replace('./signin.jxp');
					}
				}]	
			}]
		}]
	});

});
