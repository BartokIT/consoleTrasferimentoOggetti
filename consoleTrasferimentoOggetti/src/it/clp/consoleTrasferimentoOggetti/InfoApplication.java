package it.clp.consoleTrasferimentoOggetti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringEscapeUtils;

/**
 * Servlet implementation class InfoApplication
 */
public class InfoApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoApplication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		ResultSet result = null;
		String applID = null;
		String projectName = null;
		String sub_projectName = null;
		Timestamp rowDateTime = null;
		SimpleDateFormat  formatterDataOra = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss" );
		SimpleDateFormat  formatterOnlyData = new SimpleDateFormat("01/MM/yyyy" );
		String rowDate = null;
		
		HashMap<String, Integer> datiGraph = new HashMap<String,Integer>();
		
		int count = 0;
		
		//Prelevo l'id del file
		applID = request.getParameter("applID");

		try
		{
			
			result = DataManager.getApplicationInformation(applID);
			
			count = 0;
			out.println("<html><head>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/main.css\" />" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/maintable.css\" />" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/start/jquery-ui-1.8.11.custom.css\" />" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style/demo_table_jui.css\" />" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.min.js\"></script>" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery-ui-1.8.12.custom.min.js\"></script>" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.dataTables.min.js\"></script>" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/jquery.dataTables.sorting.plugin.js\"></script>" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/highcharts.js\"></script>" +
					"<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/InfoApplication.js\"></script>" +
					"</head>" +
					"<body style=\"pading:0;margin:0\">");
			
			if (result.next())
			{

				projectName = result.getString("progetto");
				sub_projectName = result.getString("sottoprogetto");
				
				out.println("<div style=\"padding:0;margin:0\">");
				out.println("<div class=\"ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix info_appl_head\">");
				out.println("Progetto: <span class=\"info_appl_head_proj\">"+ projectName + "</span><br/> Sotto-Progetto: <span class=\"info_appl_head_proj\">" + sub_projectName + "</span></div>");
				out.println("<div id=\"chart-container-1\" style=\"margin:0; height:300px;\">Tables</div>");
				//Ciclo per ottenere tutte quante le informazioni di trasferimento dell'oggetto
				out.println("<div  id=\"container_main_request_report\" style=\"width: 750px\">");
				out.println("<table id=\"main_request_report\"><thead><tr><th>Data/Ora</th><th>Ambiente</th><th style=\"font-size:.8em\">Prima installazione</th></tr></thead><tbody>");
				do
				{
					rowDateTime = result.getTimestamp("reqDateTime");
					rowDate = formatterOnlyData.format(result.getTimestamp("reqDateTime"));
					out.println("<tr>");
						out.println("<td>" + StringEscapeUtils.escapeHtml(formatterDataOra.format(rowDateTime)) + "</td>");
						out.println("<td>" + StringEscapeUtils.escapeHtml(result.getString("ambientName")) + "</td>");
						if (result.getString("primaSuccessiva").compareTo("P") == 0 )
							out.print("<td><h1 class=\"checked\"/>Si</h1></td>");
						else
							out.print("<td><h1 class=\"un-checked\"/>No</h1></td>");
					out.println("</tr>");
					
					Integer dateCount = datiGraph.get(rowDate);
					
					if (dateCount != null)
					{
						datiGraph.put(rowDate, new Integer(dateCount + 1));	
					}
					else
					{
						datiGraph.put(rowDate, new Integer(1));						
					}
					
					count++;
				} while (result.next());
				
				out.println("</tbody></table></div>");
			
				out.println("</div>");
				out.println("<script>" +
						"var chart = new Highcharts.Chart({\n" +
						"chart: {\n" +
						"renderTo: 'chart-container-1'\n" +
						"},\n" + 
						"title: { text: ''},\n" +
						"legend: {enabled: false},\n" +
						"tooltip: {\n" +
					   "formatter: function() {\n" +
					      "return '<strong>Deploy del mese di '+" +
					         "Highcharts.dateFormat('%b %Y', this.x) + '</strong><br/>' + this.y;" + 
					   "}\n}," +
						"xAxis: {\n" +
							"type: 'datetime',\n" +
							"tickInterval:'2592000000',\n" +
					        "dateTimeLabelFormats: {\n" +
						        "day: '%b %Y',\n"+
						        "week: '%b %Y',\n"+
						        "month: '%b %Y',\n"+
						        "year: '%Y'\n" +
						        "}\n" + 
						"},\n" +
						"yAxis: {\n" +
								"title: { text: 'Numero di richieste'}" +
					       // "}\n" + 
					"},\n" +						
						"series: [{\n" +
						"data: [\n");
				
				Iterator<String> it = datiGraph.keySet().iterator();
				while (it.hasNext())
				{
					String data = it.next();
					Date tmpTime =  formatterOnlyData.parse(data);
					Calendar  c = Calendar.getInstance();
					c.setTime(tmpTime);
					out.print("[Date.UTC("  + c.get(Calendar.YEAR) +"," + (c.get(Calendar.MONTH) ) + "," + c.get(Calendar.DAY_OF_MONTH)  + ")," + datiGraph.get(data) + " ]");
					if (it.hasNext())
						out.println(",");
					else
						out.println("");
				}

				out.println("]\n" +
       
						"}]\n" + 
						"});\n" +
						"</script>");
				
			}
			out.println("</body></html>");
		}
		catch ( SQLException e)
		{
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Blocco catch generato automaticamente
			System.out.println("Impossibile fare il parsing della stringa di Data");
			e.printStackTrace();
		}
		finally 
		{

			
		}
		
		if (count == 0)
		{
		//	out.println("ID not found");
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
