<jsp:useBean id="requestObject"
	class="it.clp.consoleTrasferimentoOggetti.data.TransferRequest">
<jsp:setProperty name="requestObject" property="currentRequest"
	param="reqID" />
</jsp:useBean><% boolean bErrorDeploy=false; %><?xml version="1.0" encoding="UTF-8" ?>
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
<link rel="stylesheet" href="style/menubar.css" type="text/css" />
<link rel="stylesheet" href="style/jquery.jscrollpane.css" type="text/css" />
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=UTF-8" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="js/jquery.dataTables.sorting.plugin.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="js/ColVis.min.js"></script>
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/jquery.bgpos.js"></script>
<script type="text/javascript" src="js/viewDetailRequest.js"></script>
</head>
<body>
<jsp:include page="header.jspf" flush="false"></jsp:include>
<div id="container_mail_detail_requests">
	<div id="head"
		class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix info_appl_head">
		<div style="width:90%; float: left;">	
		<h2>Richiesta di trasferimento</h2>
		<em>del <jsp:getProperty name="requestObject" property="dateString" />
		</em><br />
		<em>ore <jsp:getProperty name="requestObject" property="timeString" />
		</em><br />
		<em>ambiente <%=requestObject.getAmbient().getName()  %> </em>
		</div>
		<div style="width:10%; float: left;">			
			<a class="mail_link" href="DownloadEmailContent?mailID=<jsp:getProperty name="requestObject" property="mailID" />"><img src="style/images/mail_icon_16x16.png"/> E-mail</a><br/>
			<a class="module_link" href="DownloadTransferObject?fileID=<jsp:getProperty name="requestObject" property="attachID" />"><img src="style/images/excel_icon_16x16.png"/> Modulo </a>
		</div>
		<a id="deploy_done" href="AjaxShunt.do?cmd=deploydone&amp;reqID=<jsp:getProperty name="requestObject" property="requestID" />">Conferma esecuzione deploy</a>
	</div>
<div id="tabs">
<ul>
	<li><a href="#tabs-general">Generali</a></li>
	<li><a href="#tabs-2">Datasource</a></li>
</ul>
<div id="tabs-general">
<h3>Applicazione</h3>
Progetto: <%= requestObject.getApplication().getProject() %><br />
Sottoprogetto: <%= requestObject.getApplication().getSubProject() %> <br />
Compatibile JVM 1.5: <%= requestObject.getApplication().getJVM5CompatibleString() %><br/>
<hr />
<h3>Info</h3>
<jsp:getProperty property="note" name="requestObject"/>
<hr />
<h3>CVS</h3>
Tag: <% if (requestObject.getTagCVS().contains(" "))
		{  bErrorDeploy = true;
		%> <span style="background-color: red"><jsp:getProperty name="requestObject" property="tagCVS" /> [Errore!]</span> <%}
		else
		{%> <jsp:getProperty name="requestObject" property="tagCVS" /><%} %><br/>
Modulo: <jsp:getProperty name="requestObject" property="moduleCVS" />
<hr />
<div>Ambiente: <%= requestObject.getAmbient().getName() %> <br />
Tipologia trasferimento: <% if (requestObject.getPrimaSuccessiva().compareTo("P") == 0)
				{bErrorDeploy = true;%> Prima installazione <%}
				else if (requestObject.getPrimaSuccessiva().compareTo("S") == 0)
				{%> Aggiornamento <br /><%}
				else
				{ bErrorDeploy = true;%> <strong style="color: red">Errore</strong> <%}
				if (bErrorDeploy == false)
				{ %>
					<div id="form_container" class="form_update">
						<form action="#" method="post">
							<fieldset>
								<input id="requestID" type="hidden"	name="requestID" value="<jsp:getProperty name="requestObject" property="requestID" />" />
								<input id="tagCVS" type="hidden" name="tagCVS" value="<jsp:getProperty name="requestObject" property="tagCVS" />" />
								<input id="moduleCVS" type="hidden" name="moduleCVS" value="<jsp:getProperty name="requestObject" property="moduleCVS" />" />
								<input id="run_update" class="submit" type="submit" name="action_update_appl" value="Inizia procedura aggiornamento" />
							</fieldset>
						</form>
					</div>
				<%} %>
</div>
</div>
<div id="tabs-2">
<p></p>
</div>
</div>
</div>
</body>
</html>
