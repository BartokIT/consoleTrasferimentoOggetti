$(document).ready(function() { 
	oTable = $('#main_request_report').dataTable( {
		"sDom": '<"H">t<"F">',
		"bJQueryUI": true,
		"sScrollY": "200px",
		"bPaginate": "false",
		"bScrollCollapse": true,
		"aoColumnDefs": [{"sType": "date-euro", "aTargets": [0]}
							]			
	} );	
		
});