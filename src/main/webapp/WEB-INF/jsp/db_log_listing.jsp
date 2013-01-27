<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/media/images/favicon.ico" />
		
		<title>Slina Web Log</title>
		<style type="text/css" title="currentStyle">
			@import "css/demo_page.css";
			@import "css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="jstree/jquery.jstree.js"></script>
		<script type="text/javascript"  src="jstree/jquery.cookie.js"></script>
		<script type="text/javascript"  src="jstree/jquery.hotkeys.js"></script>
		
		<script type="text/javascript" charset="utf-8">

		/* Formating function for row details */
		function fnFormatDetails ( oTable, nTr ) {
			
			var aData = oTable.fnGetData( nTr );
			
			var contentdata = "";
			
			for (var i=0; i < aData.logDetails.length; i++)  {
				contentdata +=  aData.logDetails[i];
				contentdata += "\n";
			}
			contentdata += "";
			var output = $('<div>').text(contentdata).html() ;				
			return output;
		}		
		
		
		
			
			$(document).ready(function() {

				/*
				 * Insert a 'details' column to the table
				 */
				var nCloneTh = document.createElement( 'th' );
				var nCloneTd = document.createElement( 'td' );
				nCloneTd.innerHTML = '<img src="images/details_open.png">';
				nCloneTd.className = "center";
				
				$('#example thead tr').each( function () {
					this.insertBefore( nCloneTh, this.childNodes[0] );
				} );
				
				$('#example tbody tr').each( function () {
					this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
				} );
				
				/*
				 * Initialse DataTables, with no sorting on the 'details' column
				 */				
				
				
				
				
				
				var oTable = $('#example').dataTable( {
				    "sAjaxSource": "getLogEvents.html",
				    "aaSorting": [[ 1, "desc" ]], 
				    "aoColumns": [
				       { "mDataProp": "detailsLink", "sType": "string" ,"sClass": "center", "bSortable": false, "aDataSort": [ 1 ] },
	                   { "mDataProp": "dateTime", "sType": "string", "bSortable": true, "iDataSort": 6  },
	                   { "mDataProp": "server", "sType": "string"},
	                   { "mDataProp": "logLevel", "sType": "string"},
	                   { "mDataProp": "logClass" ,"sType": "string" },
	                   { "mDataProp": "logSummary" , "sType": "string"   },
	                   { "mDataProp": "timestamp" , "bSearchable": false, "bVisible": false, "sType": "numeric"   }

				              ],
				    "bAutoWidth": false,
				   
	                "fnServerData": function (sSource, aoData, fnCallback) {

	                    <%-- ajax call. expects json data back --%>
							//console.log("calling ajax");
	                      $.ajax({
	                          "dataType": "json",
	                          "type": "GET",
	                          "url": "dbgetLogListings.html?uniqueId="+0,
	                          "data": aoData,
	                          "success": function(json) {
	                              fnCallback(json);
	                              },
	                          "error":function(e) {
	                            <%-- redirect to 500 error page --%>
	                            console.log(e);
	                             window.location.replace("jqexception.html");
	                          }

	                      });

	                  } 

					});	
					

				
				
				/* Add event listener for opening and closing details
				 * Note that the indicator for showing which row is open is not controlled by DataTables,
				 * rather it is done here
				 */
				$('#example tbody td img').live('click', function () {
					var nTr = $(this).parents('tr')[0];
					if ( oTable.fnIsOpen(nTr) )
					{
						/* This row is already open - close it */
						this.src = "images/details_open.png";
						oTable.fnClose( nTr );
					}
					else
					{
						/* Open this row */
						this.src = "images/details_close.png";
						oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
					}
				} );				
				

				
				var node_list = [];
				
				$("#demo4").jstree({ 
					"themes" : {
						"theme" : "default",
						"dots" : true,
						"icons" : true
					},	
					 "json_data" : {
				            "ajax": {
				                "url": "dbmenuTree.html"
				            }
	                    },
	                    "dnd" : {
	            			"drop_finish" : function (data) { 

	            				var id = parseInt($('a', $(data.o)).attr("id"));
	        					if(id == null) {
	        						return;
	        					}
	        					//console.log("drop_finish");
	            				//console.log(id);
	            				
	            				if($.isNumeric(id)) {
	            					node_list.push(id);
	            				}

	            				//console.log(node_list);
	            				refreshMultiTable(node_list);
	        					return false; 
	            			}
	            		}       
	             ,
					"plugins" : ["themes","json_data","ui", "dnd"] })
				// 1) if using the UI plugin bind to select_node
				.bind("select_node.jstree", function (event, data) { 
					// `data.rslt.obj` is the jquery extended node that was clicked
					
					var id = jQuery.data(data.rslt.obj[0],"id");
					
		
					if(id == null || !($.isNumeric(id))) {
						return;
					}

					//console.log("drop_finish");
					selectedNodeId = id;
					node_list = [];
					node_list.push(id);

					refreshTable(id);
					//alert(id);
				})
				// 2) if not using the UI plugin - the Anchor tags work as expected
				//    so if the anchor has a HREF attirbute - the page will be changed
				//    you can actually prevent the default, etc (normal jquery usage)
				.delegate("a", "click", function (event, data) { event.preventDefault(); });


				
				$("#demo5").jstree({ 
					"themes" : {
						"theme" : "default",
						"dots" : true,
						"icons" : true
					},   
	             "plugins" : ["themes","html_data", "ui" ] })
				// 1) if using the UI plugin bind to select_node
				.bind("select_node.jstree", function (event, data) { 

					var id = data.rslt.obj[0].id;
					refreshWarnings(id);
					//alert(id);
				})
				// 2) if not using the UI plugin - the Anchor tags work as expected
				//    so if the anchor has a HREF attirbute - the page will be changed
				//    you can actually prevent the default, etc (normal jquery usage)
				.delegate("a", "click", function (event, data) { event.preventDefault(); });
				
				
				function refreshWarnings(envid) {
					
					//console.log(envid);
					
			         $.ajax({
		                    "dataType": "json",
		                    "type": "GET",
		                    "url": "getProdWarnings.html?env="+envid,
		                    "success": function(json) {

		                    	//console.log(json);
		                    	oSettings = oTable.fnSettings();
		                    	oTable.fnClearTable(this);
		                    	//oTable.fnClearTable();
							    for (var i=0; i<json.aaData.length; i++)  {
							    	oTable.oApi._fnAddData(oSettings, json.aaData[i]);							    
								}
								 
								oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
								fnShowHide(2, true);
								oTable.fnDraw();		                    	

		                        },
		                        "error":function(e) {
		                            oTable.fnClearTable();
		                    }
		                });
				}				
				
				
				
				
				

				
				
				function refreshTable(logid) {
					
					//console.log(logid);
					
			         $.ajax({
		                    "dataType": "json",
		                    "type": "GET",
		                    "url": "dbgetLogListings.html?uniqueId="+logid,
		                    "success": function(json) {

		                    	//console.log(json);
		                    	oSettings = oTable.fnSettings();
		                    	oTable.fnClearTable(this);
		                    	//oTable.fnClearTable();
							    for (var i=0; i<json.aaData.length; i++)  {
							    	oTable.oApi._fnAddData(oSettings, json.aaData[i]);
							    
								}
								 
								oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
								fnShowHide(2, false);
								oTable.fnDraw();		                    	

		                        },
		                        "error":function(e) {
		                            oTable.fnClearTable();
		                    }
		                });
				}				
				

				function refreshMultiTable(logidArray) {
										
				//	console.log("refresh table");

					
			         $.ajax({
		                    "dataType": "json",
		                    "type": "GET",
		                    "url": "getLogMultiListings.html?scope="+logidArray,
		         //           "data": JSON.stringify({ scope: logidArray }),
		                    "success": function(json) {		                    	
		                    	//console.log(json);
		                    	oSettings = oTable.fnSettings();
		                    	oTable.fnClearTable(this);
		                    	//oTable.fnClearTable();
							    for (var i=0; i<json.aaData.length; i++)  {
							    	oTable.oApi._fnAddData(oSettings, json.aaData[i]);
							    
								}
								 
								oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
								fnShowHide(2, false);
								oTable.fnDraw();		                    	

		                        },
		                        "error":function(e) {
		                            oTable.fnClearTable();
		                    }
		                });
				}				
								
				function fnShowHide( iCol, iVis )
				{
					/* Get the DataTables object again - this is not a recreation, just a get of the object */
					var oTable = $('#example').dataTable();
					
					var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
					oTable.fnSetColumnVis( iCol, iVis );
				}				
				
				
				
				
				
				
			} );
		</script>
	</head>
	<body id="dt_example">


<div id="parent">	

	<h1>Slina Web Log</h1>	

		<div id="menu" style="overflow:auto;padding-bottom: 180px;">


			<h5>Log file locations</h5>	

<div class="spacer"><b>Log Files</b></div>
	     <div id="demo4" class="demo">
			<ul class="jstree-open">
					<li id="shtml_1" >
						<a href="#">Child node 1</a>
					</li>
					<li id="shtml_2">
						<a href="#">Child node 1</a>
					</li>
					<li id="shtml_3">
						<a href="#">Child node 2</a>
					</li>
					<li id="shtml_4">
						<a href="#">Root node 2</a>
					</li>					
			 </ul>
			</div>

<div style="position:relative;height: 180px;clear:both;padding-top:10px;">
	<div ><b>Error Queue</b></div>

	     <div id="demo5" class="demo">
			<ul class="jstree-open">
					<li id="shtml_1" >
						<a href="#">Production Events</a>
					</li>				
			 </ul>
			</div>	

</div>



		</div>	
	
		<div id="container">
						
			<h5>Log File Details</h5>
			
			<div id="demo" class="jstree-drop" >
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
			<th>Date-time</th>
			<th>Server</th>
			<th>Log Level</th>
			<th>Log Class</th>
			<th>Log Summary</th>
		</tr>
	</thead>
	<tbody>

	</tbody>
</table>
			</div>
			<div class="spacer"></div>

 <div class="jstree-drop" style="clear:both; height:40px; line-height:60px; "></div>


</div>
</div>


	</body>
</html>