package it.clp.consoleTrasferimentoOggetti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

//import com.ibm.websphere.rsadapter.DataStoreHelper;
//import com.ibm.websphere.rsadapter.GenericDataStoreHelper;

import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Servlet implementation class GetRequests
 */
public class GetRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  
	{
		PrintWriter out = response.getWriter();
		Context ctx;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		SimpleDateFormat  formatterDataOra = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss" );
		//FieldPosition pos = new FieldPosition(0);

		try
		{
			ctx = new InitialContext();
		    ds = (DataSource)ctx.lookup("jdbc/trasfObj");
		    //Vedere di usare l'helper
			 
		}
		catch ( NamingException e)
		{
			out.println(e.toString());
		}


		
		response.setContentType("text/html");
		
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">" +
				"<head>" +
				"<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>" +
				"<title>Statistiche trasferimento oggetti</title>" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/start/jquery-ui-1.8.11.custom.css\" />" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/main.css\" />" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/maintable.css\" />" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/demo_table_jui.css\" />" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/ColVisAlt.css\" />" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.min.js\"></script>" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery-ui-1.8.12.custom.min.js\"></script>" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.dataTables.min.js\"></script>" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.dataTables.sorting.plugin.js\"></script>" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/ColVis.min.js\"></script>" +
				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/GetRequests.js\"></script>");
				
				out.println("</head>" +
							"<body>" +
							"<form>" +
							"<div id=\"container_main_trasf_table\">" +
							"<table id=\"main_trasf_table\" style=\"width:950px\">" +
					"<thead>" +
					"<tr><th><input class=\"firstCheck\" type=\"checkbox\"/></th><th>Ricevuto</th><th>Ambiente</th><th>Tipo</th><th>Applicazione</th><th>Email</th><th>Modello</th><th>Deploy</th></tr>" + 
					"</thead>" +
			"<tbody>");
		
		try
		{
			con = ds.getConnection();
			
			//String s = "SELECT reqID, tagCVS, moduloCVS FROM transferRequests";
			String s = "select applID, mailID,primaSuccessiva, reqDateTime, attachID, progetto, sottoProgetto,  reqID, tagCVS, moduloCVS, ambientName, attachName FROM applications, attachments, containsAttachments, mailItems, sendedWith, ambients, concernReq, transferRequests" +
					" WHERE (ambients.ambientID = concernReq.ext_ambientID) AND" +
					" (concernReq.ext_reqID = transferRequests.reqID) AND" +
					" (sendedWith.ext_reqID = transferRequests.reqID) AND " +
					" (sendedWith.ext_mailID = mailItems.mailID) AND " +
					" (containsAttachments.ext_mailID = mailItems.mailID) AND " +
					"(containsAttachments.ext_attachID = attachments.attachID) AND " +
					" (applications.applID = concernReq.ext_applID)" +
					" ORDER BY reqDateTime DESC;";
			statement = con.prepareStatement(s);
			result = statement.executeQuery();

			
			while (result.next())
			{
				out.println("<tr><td><input type=\"checkbox\"/></td>");
				out.println("<td>" + formatterDataOra.format( result.getTimestamp("reqDateTime") ) + "</td>");
				out.println("<td>" + result.getString("ambientName") + "</td>");
				out.println("<td>" + result.getString("primaSuccessiva") + "</td>");				
				out.print("<td><a class=\"view_application\" href=\"InfoApplication?applID=" + result.getString("applID") +"\">" + result.getString("progetto") );
				out.println("<br/><em>" + result.getString("sottoprogetto") + "</em></a></td>");
				out.println("<td><a class=\"modal_ajax\" href=\"DownloadEmailContent?mailID=" + result.getString("mailID") + "\"><h1  class=\"open_mail\">Vedi E-mail</h1></a></td>");
				out.println("<td><a href=\"DownloadTransferObject?fileID=" + result.getString("attachID") + "\"><h1  class=\"view_trasf_form\">Modulo trasferimento</h1></a></td>");
				out.println("<td><a onclick=\"$('<div>Dati CVS:<br/> <strong>Modulo</strong>: " + StringEscapeUtils.unescapeJava(result.getString("moduloCVS")) + " <br/> <strong>TAG</strong>: " + StringEscapeUtils.unescapeJava(result.getString("moduloCVS")) + " </div>').dialog()\" href=\"#\" ><h1  class=\"deploy_request\">Effettua deploy</h1></a></td></tr>");
			}
		}
		catch ( SQLException e)
		{
			out.println(e.toString());
		}
		
		out.println("</tbody></table></div></form>");
		out.println("</body></html>")	;
	}
		
}
