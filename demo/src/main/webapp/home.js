Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', extUrl+'/examples/ux/');
Ext.require([
    'Ext.form.*',
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.util.*',
    'Ext.toolbar.Paging',
    'Ext.ux.PreviewPlugin',
    'Ext.ModelManager',
    'Ext.tip.QuickTipManager'
]);

Ext.onReady(function(){
    Ext.tip.QuickTipManager.init();
	
	Ext.define('message.fielderror', {
        extend: 'Ext.data.Model',
        fields: ['id', 'msg']
    });

    Ext.define('Message', {
        extend: 'Ext.data.Model',
        fields: [
            'bid', 'avatar', 'content', 'forwards', 'comments', 'stat',
            {name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'},
            'poster', 'excerpt', 'threadid'
        ],
        idProperty: 'bid'
    });

    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        pageSize: 50,
        model: 'Message',
        remoteSort: true,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'ajax',
            url: './message.jxp?action=listJson',
			pararms: {action:'listJson'},
			basePararms: {action:'listJson'},
            reader: {
                root: 'messages',
                totalProperty: 'totalCount'
            },
            // sends single sort as multi parameter
            simpleSortMode: true
        },
        sorters: [{
            property: 'lastpost',
            direction: 'DESC'
        }],
		listeners: {
			'load' : function(store, records, successful, operation) {
				Ext.Array.forEach(records, function(item, index, allItems) {
					item.data.stat = '<i>Comments(' + item.data.comments + '), Forwards(' + item.data.forwards + ') </i>';
				});
			}
		}
    });
	
	var formPanel = Ext.create('Ext.form.Panel', {
        //renderTo: 'form-blog',
        frame: true,
        //title: Ext.get('user').dom.innerHTML,
        //width: 600,
		region: 'center',
		//height: 175,
        bodyPadding: 5,
		cls: 'text-align:center;',
		//border: false,
		margins: '5 5 5 5',
		buttonAlign: 'center',
        waitMsgTarget: true,

        fieldDefaults: {
            labelAlign: 'right',
            labelWidth: 85,
			hideLabel: true,
            msgTarget: 'side'
        },
		// configure how to read the XML errors
        errorReader: Ext.create('Ext.data.reader.Xml', {
            model: 'message.fielderror',
            record : 'field',
            successProperty: '@success'
        }),
		layout:'column',
        items: [{
			xtype: 'container',
			columnWidth: 1,
			layout: 'anchor',
			items: [{
				xtype: 'textarea',
				name      : 'message',
				allowBlank: false,
				anchor: '100%',
				height: 80,
				listeners: {
					'change' : function(field, newVal, oldVal) {
						if (newVal) {
							var left = 140-newVal.length;
							Ext.get('char-counting').setStyle('color', 'red');
							Ext.get('char-counting').update(left - 0);
						}
					}
				}
			}, {
				xtype: 'toolbar',
				items: [ Ext.create('Ext.button.Split', {
					text: 'Face',
					//handler: onButtonClick,
					tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
					iconCls: 'face-icon',
					menu: {
						items: [{
							text: 'Pick a Color',
							//handler: onItemClick,
							menu: {
								showSeparator: false,
								items: [
									Ext.create('Ext.ColorPalette', {
										listeners: {
											select: function(cp, color){
												Ext.example.msg('Color Selected', 'You chose {0}.', color);
											}
										}
									}), '-',
									{
										text: 'More Colors...',
										//handler: onItemClick
									}
								]
							}
						}]
					}
				}), Ext.create('Ext.button.Split', {
					text: 'Picture',
					//handler: onButtonClick,
					tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
					iconCls: 'picture-icon'
				}), Ext.create('Ext.button.Split', {
					text: 'Video',
					//handler: onButtonClick,
					tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
					iconCls: 'video-icon'
				}), Ext.create('Ext.button.Split', {
					text: 'Music',
					//handler: onButtonClick,
					tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
					iconCls: 'music-icon'
				}), Ext.create('Ext.button.Split', {
					text: 'Topic',
					//handler: onButtonClick,
					tooltip: {text:'This is a an example QuickTip for a toolbar item', title:'Tip Title'},
					iconCls: 'topic-icon'
				}), '->', 'Post <span id="char-counting" style="color: #385F95;">140</span> more chararcters ... ']
			}]
		},{
			xtype: 'container',
			width: 5
		},{
			id: 'btn-post',
			xtype  :'button',
			text   : 'Post',
			//scale  : 'large',
			//height : 24,
			width:110,
			height: 110,
			style: {
				'marginLeft': '10px',
				'fontSize': '16px'
			},
			handler: function(){
				this.up('form').getForm().submit({
					url: 'message.jxp',
					params: {action: 'post'},
					submitEmptyText: false,
					waitMsg: 'Saving Data...',
					success: function(form, action) {
						//Ext.Msg.alert('Done!', form.getValues(true));
						//window.location.replace('./user.jxp?id=demo');
						//window.location.replace('./home.jxp');
						store.loadPage(1);
						form.reset();
					},
					failure: function(form, action) {
						Ext.Msg.alert('Failed', form.getValues(true));
					}
				});
			}
		}]
    });
	
	function renderAvatar(value) {
		return '<img id="avatar" src="' + value + '" />';
	}

    // pluggable renders
    function renderTopic(value, p, record) {
        return Ext.String.format(
            '<i> Message from <b><a href="profile.jxp?id={2}&action=view" target="_blank">{2}</a></b> at {3}, comments(<a href="#">{4}</a>), forwards(<a href="#">{5}</a>), <a href="#">favor it</a>, <a href="#">hide it</a>, <a href="#">delete it</a> </i><br/> <p id="msg-content">{0}</p>',
            value.replace(/\n/i, '<br/>'),
            record.getId(),
            record.data.poster,
			Ext.Date.dateFormat(record.data.lastpost, 'M j, Y, g:i a'),
			record.data.comments,
			record.data.forwards
        );
    }

    function renderLast(value, p, r) {
        return Ext.String.format('{0}<br/>by {1}', Ext.Date.dateFormat(value, 'M j, Y, g:i a'), r.data['poster']);
    }


    var pluginExpanded = true;
    var grid = Ext.create('Ext.grid.Panel', {
        region: 'center',
        //title: 'Recent messages from your friends:',
		frame: true,
        store: store,
		margins: '0 5 5 5',
        trackMouseOver: false,
        disableSelection: true,
        loadMask: true,
		/*
        viewConfig: {
            id: 'gv',
            trackOver: false,
            plugins: [{
                ptype: 'preview',
                bodyField: 'stat',
                expanded: true,
                pluginId: 'preview'
            }]
        },*/
        // grid columns
        columns:[{
            text: "Avatar",
            width: 64,
            dataIndex: 'avatar',
            renderer: renderAvatar,
            sortable: false
        },{
            id: 'content',
            text: "Messages",
            dataIndex: 'content',
            flex: 1,
            renderer: renderTopic,
            sortable: false
        },{
            text: "Poster",
            dataIndex: 'poster',
            width: 100,
            hidden: true,
            sortable: true
        },{
            text: "Forwards",
            dataIndex: 'forwards',
            width: 70,
            align: 'right',
            sortable: true
        },{
            text: "Comments",
            dataIndex: 'comments',
            width: 70,
            align: 'right',
            sortable: true
        },{
            id: 'last',
            text: "Last Post",
            dataIndex: 'lastpost',
            width: 150,
            hidden: true,
            renderer: renderLast,
            sortable: true
        }],
        // paging bar on the bottom
        bbar: Ext.create('Ext.PagingToolbar', {
            store: store,
            displayInfo: true,
            displayMsg: 'Displaying topics {0} - {1} of {2}',
            emptyMsg: "No topics to display",
            items:[
                '-', {
                text: 'Show Post Time',
                pressed: pluginExpanded,
                enableToggle: true,
                toggleHandler: function(btn, pressed) {
                    //var preview = Ext.getCmp('gv').getPlugin('preview');
                    //preview.toggleExpanded(pressed);
                }
            }]
        })
    });

    // trigger the data store load
    store.loadPage(1);
	
	
	var viewport = Ext.create('Ext.Viewport', {
		id: 'border-example',
		layout: 'border',
		border: false,
		items: [{			
			layout: 'border',
			border: true,
			height: 150,
			region: 'north',
			margins: '5 5 5 5',
			frame: true,
			//title: Ext.get('user').dom.innerHTML,
			items: [{				
				region: 'west',
				layout: 'border',
				width: 240,
				bodyPadding: 5,
				cls: 'text-align:center;',
				margins: '5 0 5 5',
				defaults: {
					border: false,
					bodyPadding: 5
				},
				frame: true,
				items: [{
					id: 'user-info-panel',
					region: 'north',
					height: 64,
					contentEl: 'user-info'
				}, {
					id: 'user-overview-panel',
					region: 'center',
					contentEl: 'user-overview'
				}]
			}, formPanel]
		}, grid] 
	});
});