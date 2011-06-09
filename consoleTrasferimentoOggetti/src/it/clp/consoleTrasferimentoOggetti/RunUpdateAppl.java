package it.clp.consoleTrasferimentoOggetti;

import it.clp.consoleTrasferimentoOggetti.data.Ambient;
import it.clp.consoleTrasferimentoOggetti.data.TransferRequest;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * Servlet implementation class RunUpdateAppl
 */
public class RunUpdateAppl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunUpdateAppl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ServletOutputStream out = response.getOutputStream();
		TransferRequest oRequest = new TransferRequest();
		Ambient oAmbient=null;
		String sPublickKeyPath = null;
		String sKeyFilePass = null;
		String sUserName = null;
		String sExportAmbient = null;
		Context ctx=null;
		String sCommand=null;
		
		//Recupero i dati per la richiesta corrente
		String sRequestID = request.getParameter("requestID");
		String sRefCommand = request.getParameter("command");
		
		//Reperisco il path della chiave privata
		try
		{
			ctx = new InitialContext();
			java.net.URL url = (java.net.URL) ctx.lookup("url/transferRequestKeyPath");
			sPublickKeyPath = url.getPath();
		}
		catch ( NamingException e)
		{	e.printStackTrace();	}	
				
		//Prelevo le informazioni dal modulo di trasferimento
		oRequest.setCurrentRequest(sRequestID);
		oAmbient = oRequest.getAmbient();
		
		//Imposto dei parametri relativi all'ambiente
		if (oAmbient.getID().compareTo("COL") == 0)
		{		
			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
		} else if (oAmbient.getID().compareTo("ESE") == 0)
		{
			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
			
		} else if (oAmbient.getID().compareTo("MAN") == 0)
		{
			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
			
		} else if (oAmbient.getID().compareTo("INT") == 0)
		{
			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
			
		} else if (oAmbient.getID().compareTo("PRE") == 0)
		{
//			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
			
		} else if (oAmbient.getID().compareTo("FOR") == 0)
		{
			//Utilizziamo la chiave privata
//			sPublickKeyPath = "D:\\Documents and Settings\\ppacld83p16e472q\\Documenti\\Sviluppo\\consoleTrasferimentoOggetti\\consoleTrasferimentoOggetti\\putty.pem";
//			sKeyFilePass = "QAZxswEDCvfr";
//			sUserName = "mmoretti";
			
		} else
		{
			out.println("Impossibile trovare l'ambiente nella richiesta " + oAmbient.getID());
			return;
		}

		sKeyFilePass = "QAZxswEDCvfr";
		sUserName = "mmoretti";

		
		//Controllo sul formato del campo tag
		if (oRequest.getTagCVS().contains(" "))
		{
			out.println("Impossibile trovare l'ambiente nella richiesta " + oAmbient.getID());
			return;			
		}
		
		File keyfile = new File(sPublickKeyPath); // leggere le faq relative al prodotto
		sExportAmbient = "export AMBIENTE=" + oAmbient.getName().toUpperCase().replace("-", "_");
		
		response.setContentType("text/html");
		out.println("<html>" +
				"<head></head>" + 
				"<body><div id=\"runUpdateApplResponse\" style=\"width:100%;background-color:black;color:#DDD;font-family: 'Courier New', Courier, monospace;font-size:12px;\">");
		
		//Mi connetto via ssh alla shell remota
		if (sRefCommand  == null)
		{
			UtilityFunction.resetNumberCommands();
			sCommand = ".  /home/mmoretti/RILASCI/setEnv.ksh;" + sExportAmbient + ";cd /home/mmoretti/RILASCI;/usr/IBM/WebSphere/AppServer/java/jre/bin/java RunAs ./AggiornamentoApplicazione.ksh -p " + oRequest.getModuleCVS() + " -t " + oRequest.getTagCVS();			
		}
		else
		{
			//Recupero la sessione
			HttpSession oSession = request.getSession();
			String sSessionCommand=(String) oSession.getAttribute(sRefCommand );
			
			if (sSessionCommand == null)
			{
				out.println("Impossibile recuperare il comando dalla sessione " + sRefCommand);
			}
			
			sCommand = ".  /home/mmoretti/RILASCI/setEnv.ksh;" + sExportAmbient + ";cd /home/mmoretti/RILASCI;/usr/IBM/WebSphere/AppServer/java/jre/bin/java RunAs" + sSessionCommand;
			out.println(sCommand);
			out.println("</div></body></html>");
			return;
		}
		
		try
		{
			/* Create a connection instance */
			Connection conn = new Connection(oAmbient.getIP());
			/* Now connect */
			conn.connect();

			/* Authenticate */
			boolean isAuthenticated = conn.authenticateWithPublicKey(sUserName, keyfile, sKeyFilePass);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */
			Session sess = conn.openSession();
			
			out.println("Sto richiamando lo script remoto...<br/>");
			
			System.out.println(sCommand);
			sess.execCommand(sCommand);
			
			//sess.execCommand("export AMBIENTE=Collaudo;cd /home/mmoretti/RILASCI;/usr/IBM/WebSphere/AppServer/java/bin/java RunAs MutEntCoo_CVS ME_COL_20110525A");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			InputStream stderr = new StreamGobbler(sess.getStderr());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			BufferedReader brErr = new BufferedReader(new InputStreamReader(stderr));
			
			int iRow = 0;
			while (true)
			{
				String line = br.readLine();
				String linee = brErr.readLine();
				
				if ((linee == null) && (line == null))
					break;
				if (linee != null)
					System.out.println(linee + "<br/>");
				
				if (line != null)
					{
						//out.println(line + "<br/>");
						String sFormattedLine = UtilityFunction.convertBash2Html(line,iRow);
						
						out.println( UtilityFunction.linkDeployString(sFormattedLine, line, request.getSession(),sRequestID) + "<br/>");
						
						iRow++;
					}
				out.flush();
			}

			/* Close this session */			
			sess.close();

			/* Close the connection */
			conn.close();

		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}	
		out.println("</div></body></html>");
	}

}
