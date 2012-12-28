<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/media/images/favicon.ico" />
		
		<title>Slina Web Log</title>
		<style type="text/css" title="currentStyle">
			@import "../css/demo_page.css";
			@import "../css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="../js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="../js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="../jstree/jquery.jstree.js"></script>
		<script type="text/javascript"  src="../jstree/jquery.cookie.js"></script>
		<script type="text/javascript"  src="../jstree/jquery.hotkeys.js"></script>
		
		
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
				nCloneTd.innerHTML = '<img src="../images/details_open.png">';
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
				    "aoColumns": [
				       { "mDataProp": "detailsLink", "sType": "string" ,"sClass": "center", "bSortable": false, "aDataSort": [ 1 ] },
	                   { "mDataProp": "dateTime", "sType": "string", "bSortable": true  },
	                   { "mDataProp": "logLevel", "sType": "string"},
	                   { "mDataProp": "logClass" ,"sType": "string" },
	                   { "mDataProp": "logSummary" , "sType": "string"   }

				              ],
				    "bAutoWidth": false,
				   
	                "fnServerData": function (sSource, aoData, fnCallback) {

	                    <%-- ajax call. expects json data back --%>
							//console.log("calling ajax");
	                      $.ajax({
	                          "dataType": "json",
	                          "type": "GET",
	                          "url": "getLogEvents.html?uniqueId="+0,
	                          "data": aoData,
	                          "success": function(json) {
	                              fnCallback(json);
	                              },
	                          "error":function(e) {
	                            <%-- redirect to 500 error page --%>
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
						this.src = "../../images/details_open.png";
						oTable.fnClose( nTr );
					}
					else
					{
						/* Open this row */
						this.src = "../../images/details_close.png";
						oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
					}
				} );				
				

				
				
				$("#demo4").jstree({ 
					"themes" : {
						"theme" : "default",
						"dots" : true,
						"icons" : true
					},	
					 "json_data" : {
				            "ajax": {
				                "url": "jstree.html"
				            }
	                    },
					"plugins" : ["themes","json_data","ui"] })
				// 1) if using the UI plugin bind to select_node
				.bind("select_node.jstree", function (event, data) { 
					// `data.rslt.obj` is the jquery extended node that was clicked
					
					var id = jQuery.data(data.rslt.obj[0],"id");
					//console.log(id);
	
					if(id == null) {
						return;
					}

					refreshTable(id);
					//alert(id);
				})
				// 2) if not using the UI plugin - the Anchor tags work as expected
				//    so if the anchor has a HREF attirbute - the page will be changed
				//    you can actually prevent the default, etc (normal jquery usage)
				.delegate("a", "click", function (event, data) { event.preventDefault(); });				
				
				
				function refreshTable(logid) {
					
					//console.log("refresh table");
					
			         $.ajax({
		                    "dataType": "json",
		                    "type": "GET",
		                    "url": "getLogEvents.html?uniqueId="+logid,
		                    "success": function(json) {

		                    	//console.log(json);
		                    	oSettings = oTable.fnSettings();
		                    	oTable.fnClearTable(this);
		                    	//oTable.fnClearTable();
							    for (var i=0; i<json.aaData.length; i++)  {
							    	oTable.oApi._fnAddData(oSettings, json.aaData[i]);
							    
								}
								 
								oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
								oTable.fnDraw();		                    	

		                        },
		                        "error":function(e) {
		                            oTable.fnClearTable();
		                    }
		                });

				}				
				

				
			} );
		</script>
	</head>
	<body id="dt_example">


<div id="parent">	

	<h1>Slina Web Log</h1>	

		<div id="menu">


			<h5>Log file locations</h5>	

<div class="spacer"></div>
	     <div id="demo4" class="demo" style="height:100px;">
				<ul>
					<li id="shtml_1" class="jstree-open">
						<a href="#">Root node 1</a>
						<ul>
							<li id="shtml_2">
									<a href="#">Child node 1</a>
							</li>
							<li id="shtml_3">
								<a href="#">Child node 2</a>
							</li>
						</ul>
					</li>
					<li id="shtml_4">
						<a href="#">Root node 2</a>
					</li>
				</ul>
			</div>



		</div>	
	
		<div id="container">
						
			<h5>Log File Details</h5>
			
			<div id="demo">
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
			<th>Date-time</th>
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


</div>
</div>



	</body>
</html>