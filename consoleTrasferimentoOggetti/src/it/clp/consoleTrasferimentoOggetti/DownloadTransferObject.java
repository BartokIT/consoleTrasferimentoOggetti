package it.clp.consoleTrasferimentoOggetti;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadTransferObject
 */
public class DownloadTransferObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadTransferObject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		ServletOutputStream byteWriter = null;
		BufferedInputStream inputFile= null;
		StringBuffer sFileName = new StringBuffer();
		Blob oFile = null;
		
		//Prelevo l'id del file
		String sFileID = request.getParameter("fileID");
		oFile = DataManager.getTransferRequestFile(sFileID, sFileName);
		
			if (oFile != null)
			{

			    try {			
				response.reset();
			    response.setBufferSize(DEFAULT_BUFFER_SIZE);
			    response.setContentType("application/octet-stream");

				response.setHeader("Content-Length", String.valueOf(oFile.length()));
			    response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName.toString() + "\"");

			    byteWriter = response.getOutputStream();
			    inputFile = new BufferedInputStream( oFile.getBinaryStream() , DEFAULT_BUFFER_SIZE);
			    
			    int length = 0;
			    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			    while ((length = inputFile.read(buffer)) > 0) 
			    {
			    	byteWriter.write(buffer, 0, length);
			    }			   
			    byteWriter.flush();
				inputFile.close();
				byteWriter.close();
				} catch (SQLException e) {
					// TODO Blocco catch generato automaticamente
					e.printStackTrace();
				}
				
			}
			else
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("File not found");
		}		

	}

}
