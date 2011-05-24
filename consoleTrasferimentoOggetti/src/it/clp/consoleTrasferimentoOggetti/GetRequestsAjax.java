package it.clp.consoleTrasferimentoOggetti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import com.google.gson.*;

/**
 * Servlet implementation class GetRequests
 */
public class GetRequestsAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRequestsAjax() {
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
		ResultSet result = null;
		PrintWriter out = response.getWriter();
		SimpleDateFormat  formatterDataOra = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss" );
		int iTotalRows = -1;
		int iNumFilteredRecords = -1;

		
		//response.setContentType("text/html");

		iTotalRows =  DataManager.getNumberRequest();
		iNumFilteredRecords = DataManager.getNumberRowFiltered(request.getParameter("sSearch"));
		
		try
		{
			
			result = DataManager.getRequestsRow(request.getParameter("sSearch"), request.getParameter("iSortCol_0"), request.getParameter("sSortDir_0"),Integer.parseInt(request.getParameter("iDisplayStart")), Integer.parseInt(request.getParameter("iDisplayLength")));
			
			if ( result != null)
			{
				out.println("{\n\"sEcho\": \"" + request.getParameter("sEcho") + "\",");
				out.println("\"aaData\": [");
				
				int iNumRecords = 0;
				
				//Check if there is rows and go to the end for get the total row number
				if (result.last())
				{
					iNumRecords = result.getRow();
				}
				
				result.beforeFirst();
				while (result.next())
				{
					
					if ( result.getRow() < iNumRecords )
					{
						
						out.print("[\n\"" + UtilityFunction.escapeStringJSON("<input type=\"checkbox\"/> ") + "\",");
					
						out.print("\"" + UtilityFunction.escapeStringJSON(formatterDataOra.format( result.getTimestamp("reqDateTime") )) + "\", ");
						out.print("\"" + result.getString("ambientName") + "\", ");
						
						if (result.getString("primaSuccessiva").compareTo("P") == 0 )
							out.print("\"" + UtilityFunction.escapeStringJSON("<h1 class=\"checked\"/>Si</h1>") + "\",");
						else
							out.print("\"" + UtilityFunction.escapeStringJSON("<h1 class=\"un-checked\"/>No</h1>") + "\",");
					
						out.print("\"" + UtilityFunction.escapeStringJSON("<a class=\"view_application\" href=\"InfoApplication?applID=" + result.getString("applID") +"\">" + result.getString("progetto") + "<br/>" ));
						if (result.getString("sottoprogetto") != null)
							out.print( UtilityFunction.escapeStringJSON(result.getString("sottoprogetto") ) );
						out.print(UtilityFunction.escapeStringJSON("</a>" )+ "\", ");
						//out.println("\""+ StringEscapeUtils.escapeJavaScript("<a class=\"modal_ajax\" href=\"DownloadEmailContent?mailID=" + result.getString("mailID") + "\"><h1  class=\"open_mail\">Vedi E-mail</h1></a>") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON("<a href=\"DownloadEmailContent?mailID=" + result.getString("mailID") + "\"><h1  class=\"open_mail\">Vedi E-mail</h1></a>") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON("<a href=\"DownloadTransferObject?fileID=" + result.getString("attachID") + "\"><h1  class=\"view_trasf_form\">Modulo trasferimento</h1></a>") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON("<a href=\"viewDetailRequest.jsp?reqID=" +  result.getString("reqID") + "\" ><h1  class=\"deploy_request\">Effettua deploy</h1></a>") + "\"\n],");
					}
					else if (result.getRow() == iNumRecords) 
					{
					
						out.print("[\n\"" + UtilityFunction.escapeStringJSON("<input type=\"checkbox\"/> ") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON(formatterDataOra.format( result.getTimestamp("reqDateTime") )) + "\",");
						out.println("\"" + result.getString("ambientName") + "\",");
						if (result.getString("primaSuccessiva").compareTo("P") == 0 )
							out.print("\"" + UtilityFunction.escapeStringJSON("<h1 class=\"checked\"/>Si</h1>") + "\",");
						else
							out.print("\"" + UtilityFunction.escapeStringJSON("<h1 class=\"un-checked\"/>No</h1>") + "\",");			
						out.print("\"" + UtilityFunction.escapeStringJSON("<a class=\"view_application\" href=\"InfoApplication?applID=" + result.getString("applID") +"\">" + result.getString("progetto") + "<br/>" ));
						if (result.getString("sottoprogetto") != null)
							out.print(  UtilityFunction.escapeStringJSON(result.getString("sottoprogetto")) ) ;
						
						out.print(UtilityFunction.escapeStringJSON("</a>" )+ "\", ");
						out.println("\""+ UtilityFunction.escapeStringJSON("<a class=\"modal_ajax\" href=\"DownloadEmailContent?mailID=" + result.getString("mailID") + "\"><h1  class=\"open_mail\">Vedi E-mail</h1></a>") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON("<a href=\"DownloadTransferObject?fileID=" + result.getString("attachID") + "\"><h1  class=\"view_trasf_form\">Modulo trasferimento</h1></a>") + "\",");
						out.println("\"" + UtilityFunction.escapeStringJSON("<a href=\"viewDetailRequest.jsp?reqID=" +  result.getString("reqID") + "\" ><h1  class=\"deploy_request\">Effettua deploy</h1></a>") + "\"\n]");
	
					}
				}
				
				out.println("],");
				
				if (iNumFilteredRecords >= 0)
					out.println("\"iTotalDisplayRecords\": "+ iNumFilteredRecords + ",");
				else
					out.println("\"iTotalDisplayRecords\": "+ iTotalRows + ",");
				
				out.println("\"iTotalRecords\": " + iTotalRows + "");
				out.println("}");
			}
		}
		catch ( SQLException e)
		{
			e.printStackTrace();
			
		}
		
	}
		
}
