<jsp:useBean id="requestObject"
	class="it.clp.consoleTrasferimentoOggetti.data.TransferRequest"></jsp:useBean>
<jsp:setProperty name="requestObject" property="currentRequest"
	param="reqID" /><?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>ViewTransferRequests</title>
	<link rel="stylesheet" href="style/start/jquery-ui-1.8.11.custom.css"
		type="text/css" />
	<link rel="stylesheet" href="style/ColVisAlt.css" type="text/css" />
	<link rel="stylesheet" href="style/demo_table_jui.css" type="text/css" />
	<link rel="stylesheet" href="style/main.css" type="text/css" />
	<link rel="stylesheet" href="style/maintable.css" type="text/css" />
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.sorting.plugin.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
	<script type="text/javascript" src="js/viewDetailRequest.js"></script>
	<script type="text/javascript" src="js/ColVis.min.js"></script>
</head>
<body>

<div id="head">
<strong>Richiesta di trasferimento</strong>
del: <jsp:getProperty name="requestObject" property="dateString" /> <br/>
ORE: <jsp:getProperty name="requestObject" property="timeString" /> <br/> 
</div>



<div id="tabs">
	<ul>
		<li><a href="#tabs-general">Generali</a></li>
		<li><a href="#tabs-2">Datasource</a></li>
	</ul>
	<div id="tabs-general">
		Progetto: <jsp:getProperty name="requestObject" property="projectName" /> <br/>
		Sottoprogetto: <jsp:getProperty name="requestObject" property="subProjectName" /> <br/> <hr/>
		CVS <br/>
		Tag: <jsp:getProperty name="requestObject" property="tagCVS" /> <br/>
		Modulo: <jsp:getProperty name="requestObject" property="moduleCVS" />
	</div>
	<div id="tabs-2">
		<p></p>
	</div>
</div>
</body>
</html>
