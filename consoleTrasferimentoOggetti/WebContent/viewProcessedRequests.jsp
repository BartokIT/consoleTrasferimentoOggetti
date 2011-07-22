<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<link rel="stylesheet" href="style/start/jquery-ui-1.8.11.custom.css"
	type="text/css" />
<link rel="stylesheet" href="style/ColVisAlt.css" type="text/css" />
	<link rel="stylesheet" href="style/demo_table_jui.css" type="text/css" />
<link rel="stylesheet" href="style/menubar.css" type="text/css" />
<link rel="stylesheet" href="style/main.css" type="text/css" />
	<link rel="stylesheet" href="style/maintable.css" type="text/css" />
	<!--[if lt IE 7]>
		<link rel="stylesheet" href="style/ie6fix.css" type="text/css" />
	<![endif]-->	
	<title>ViewTransferRequests</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.sorting.plugin.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
	<script type="text/javascript" src="js/ColVis.min.js"></script>
	<script type="text/javascript" src="js/jquery.bgpos.js"></script>
	<script type="text/javascript" src="js/ViewProcessedRequests.js"></script>
</head>
<body>
<jsp:include page="header.jspf" flush="false"></jsp:include>
<form>
	<div id="container_main_trasf_table">
		<table id="main_trasf_table" style="width: 950px">
			<thead>
				<tr>
					<th><input type="checkbox" /></th>
					<th>Ricevuto</th>
					<th>Ambiente</th>
					<th>Prima?</th>
					<th>Applicazione</th>
					<th>Dettagli</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</form>	
</body>
</html>
