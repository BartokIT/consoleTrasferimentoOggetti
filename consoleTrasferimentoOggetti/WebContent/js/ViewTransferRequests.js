$(document).ready(function() { 
	oTable = $('#main_trasf_table').dataTable( {
		"sDom": '<"H"lCfr>t<"F"ip>',
		"bServerSide": true,
		"sAjaxSource": "GetTransferRequest.do",
		"bJQueryUI": true,
		"bProcessing": true,
		"aoColumnDefs": [{"bSortable": false, "aTargets": [0,5,6,7]},
						{"bSearchable":false, "aTargets": [0,5,6,7]},
						{"sClass": "center", "aTargets": [0,5,6,7]},
						{"bVisible": false, "aTargets": [5]},
						{"sType": "date-euro", "aTargets": [1]}
						],		
		"oColVis": {
			"buttonText": "&nbsp;",
			"bRestore": true,
			"aiExclude": [ 0],
			"sAlign": "left"
		},
		"sPaginationType": "full_numbers",
		"oLanguage": {
			"sProcessing": "Loading...<img  src='style/images/blue_loader.gif'/>"
		}
	});	


	$("a.modal_ajax").live('click', function()
			{
				var url = this.href;
				var dialog = null;
				//var dialog = $("#dialog");
				//if ($("#dialog").lenght == 0) {
				dialog =  $('<div id="dialog" style="display:hidden"></div>').appendTo('body');
					
				//}
			     dialog.load(
			            url,
			            {},
			            function(responseText, textStatus, XMLHttpRequest) { 
			            	dialog.dialog({
			            		width:900,
			            		height: 600,
			        			modal: true
			            	});    	
			           }
			        );

				return false;
			}
	);
	$("a.view_application").live('click', function()
			{
				var url = this.href;
				window.open(url,"myappl","status=0,toolbar=0,menubar=0,resizable=1,width=750,height=650");
				return false;
			}
	);	
});