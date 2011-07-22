package it.clp.consoleTrasferimentoOggetti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxShunt
 */
public class AjaxShunt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxShunt() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sCommand = null;
		ServletOutputStream out = response.getOutputStream();
		
		sCommand = request.getParameter("cmd");
		 
		 /**
		  * Sezione per smistare i comandi 
		  */
		 if (sCommand.compareTo("deploydone") == 0)
		 {
			 int iRequestID = Integer.valueOf( request.getParameter("reqID")); 
			 DataManager.setDeployRequest(iRequestID, true);
			 out.println("{done:true}");
		 }
		
	}

}
