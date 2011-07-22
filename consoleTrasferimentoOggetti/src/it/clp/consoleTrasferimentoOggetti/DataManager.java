package it.clp.consoleTrasferimentoOggetti;

import it.clp.consoleTrasferimentoOggetti.data.Application;
import it.clp.consoleTrasferimentoOggetti.data.Ambient;
import it.clp.consoleTrasferimentoOggetti.data.TransferRequest;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class DataManager {
	private static DataSource oMySqlDataSource=null;

	/**
	 *  Si occupa di prelevare il data source
	 */
	public static DataSource getDataSource()
	{
		Context ctx;
		//Reperisco il data source se non è stato mai fatto


		if (DataManager.oMySqlDataSource  == null)

			try
			{
				ctx = new InitialContext();
			    //DataManager.oMySqlDataSource = (DataSource)ctx.lookup("jdbc/trasfObj");
				DataManager.oMySqlDataSource = (DataSource)ctx.lookup("jdbc/oraTrasfObj");
				//DataManager.oMySqlDataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/MySqlRedHat");
			    // TODO: Vedere di usare l'helper
				 
			}
			catch ( NamingException e)
			{
				System.out.println(e.toString());
			}	
			
		return DataManager.oMySqlDataSource;
	}
	
	/**
	 * Funzione che si occupa di prelevare il numero totale di richieste di trasferimento
	 * @return
	 */
	public static int getNumberRequest()
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		int iTotalRows = -1;
		ds = DataManager.getDataSource();
		try {
			con = ds.getConnection();
			
			String sSQLString = "SELECT COUNT(reqID) FROM transferRequests";
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			if (result.next())
			{
				iTotalRows = result.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
		
		return iTotalRows;
	}

	/**
	 * Restituisce il numero di record coinvolti nella ricerca di una parola nel campo progetto e sottoProggetto della tabella applications
	 * @return
	 */
	public static int getNumberRowFiltered(String sSearchString)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		int iNumFilteredRecords = -1;

		//Prelevo il data source
		ds = DataManager.getDataSource();
		
		// Valuto la richiesta di ricerca solamente se non è vuota e maggiore di 2 caratteri
		if ( sSearchString != null && sSearchString.trim().compareTo("") != 0 && sSearchString.length() > 2)
		{
			
			String sSearch_SQL = " AND ( CONTAINS (progetto, '";
			String aSearch_Word[] = sSearchString.split(" ");
			
			for (int iWord=0; iWord < aSearch_Word.length; iWord++)
			{
				sSearch_SQL = sSearch_SQL + "" + aSearch_Word[iWord] + "% ";
			}
			sSearch_SQL = sSearch_SQL +  "' , 1) > 0 )";
			
			String sSQLString = "SELECT COUNT(applID) FROM applications, attachments, containsAttachments, mailItems, sendedWith, ambients, concernReq, transferRequests" +
			" WHERE (ambients.ambientID = concernReq.ext_ambientID) AND" +
			" (concernReq.ext_reqID = transferRequests.reqID) AND" +
			" (sendedWith.ext_reqID = transferRequests.reqID) AND " +
			" (sendedWith.ext_mailID = mailItems.mailID) AND " +
			" (containsAttachments.ext_mailID = mailItems.mailID) AND " +
			"(containsAttachments.ext_attachID = attachments.attachID) AND " +
			" (applications.applID = concernReq.ext_applID) "+
			sSearch_SQL;
			
			try {
				con = ds.getConnection();
				
				//String sSQLString = "SELECT COUNT(reqID) FROM transferRequests";
				
				statement = con.prepareStatement(sSQLString);
				result = statement.executeQuery();			
				
				if (result.next())
				{
					 iNumFilteredRecords  = result.getInt(1);
				}
				
			} catch (SQLException e) {
				// TODO Blocco catch generato automaticamente
				e.printStackTrace();
			}			
		}
		
		return iNumFilteredRecords;
	}	
	
	public static ResultSet getRequestsRow(String sSearchString, String sColonnaOrdinamento, String sTipoOrdinamento, int iStartRow, int iNumberRows)
	{
		return getRequestsRow( sSearchString,  sColonnaOrdinamento,  sTipoOrdinamento,  iStartRow, iNumberRows, "N");
	}
	
	public static ResultSet getRequestsRow(String sSearchString, String sColonnaOrdinamento, String sTipoOrdinamento, int iStartRow, int iNumberRows, String sDeployed)
	{
		
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		
		String sSearch_SQL = "";
		String sOrder_SQL = " ORDER BY reqDateTime ";

		int sort_col = Integer.parseInt(sColonnaOrdinamento);
		switch (sort_col)
		{
			case 2: //Colonna degli ambienti
				sOrder_SQL = " ORDER BY ambientNAME "; //TODO: verificare se possibile sostituire con ambientID
				break;
			case 3: //Colonna della prima o successiva installazione
				sOrder_SQL = " ORDER BY primaSuccessiva "; 
				break;
			case 4:
				if (sTipoOrdinamento.compareTo("asc") == 0)
					sOrder_SQL = " ORDER BY progetto ASC, sottoProgetto ";
				else
					sOrder_SQL = " ORDER BY progetto DESC, sottoProgetto "; 
				break;
			
		}
		
		if (sTipoOrdinamento.compareTo("asc") == 0)
			sOrder_SQL = sOrder_SQL + " ASC ";
		else
			sOrder_SQL = sOrder_SQL + " DESC ";		
		
		// Valuto la richiesta di ricerca solamente se non è vuota e maggiore di 2 caratteri
		if ( sSearchString != null && sSearchString.trim().compareTo("") != 0 && sSearchString.length() > 2)
		{

			sSearch_SQL = " AND ( CONTAINS (progetto, '";
			String aSearch_Word[] = sSearchString.split(" ");
			
			for (int iWord=0; iWord < aSearch_Word.length; iWord++)
			{
				sSearch_SQL = sSearch_SQL + "" + aSearch_Word[iWord] + "% ";
			}
			sSearch_SQL = sSearch_SQL +  "' , 1) > 0 )";
		}
		
		String s = "SELECT * FROM (SELECT t.*, ROWNUM AS rn FROM " +
		"(SELECT  appl.applID, mail.mailID, req.primaSuccessiva, req.reqDateTime, att.attachID, appl.progetto, appl.sottoProgetto,  req.reqID, req.tagCVS, req.moduloCVS, amb.ambientName, att.attachName" +
		" FROM applications appl, attachments att, containsAttachments cont, mailItems mail, sendedWith snd, ambients amb, concernReq conc, transferRequests req " +
		" WHERE (amb.ambientID = conc.ext_ambientID) AND" +
		" (conc.ext_reqID = req.reqID) AND" +
		" (snd.ext_reqID = req.reqID) AND " +
		" (snd.ext_mailID = mail.mailID) AND " +
		" (cont.ext_mailID = mail.mailID) AND " +
		" (cont.ext_attachID = att.attachID) AND " +
		" (req.deployed = '" + sDeployed + "') AND " +
		" (appl.applID = conc.ext_applID) " +
		sSearch_SQL + 
		sOrder_SQL + " ) t )" +
		" WHERE (rn>= " + (iStartRow + 1 ) + ") AND (ROWNUM <= " +  iNumberRows + " )" ; 
			
				
		//System.out.println(s);
		
		try {
		ds = DataManager.getDataSource();
		con = ds.getConnection();
		statement = con.prepareStatement(s, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		result = statement.executeQuery();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
		return result;
	}
	
	/**
	 * Restituisce un inputReader utilizzato per restituire il file excel che contiene il modulo di trasferimento
	 * @param sFileID
	 * @return
	 */
	public static Blob getTransferRequestFile(String sFileID, StringBuffer sFileName)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		Blob file = null;
		
		ds = DataManager.getDataSource();
		
		try {
			con = ds.getConnection();
			String sSQLString = "SELECT attachName, attachment FROM attachments" +
			" WHERE (attachID = " + sFileID +  ") ";			
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			
			if (result.next())
			{
				file = result.getBlob("attachment");
				sFileName.append(result.getString("attachName"));
			
			}
			
		} catch (SQLException e) {
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
		
		return file;
	}
	
	public static Blob getEmailFile(String sMailID, StringBuffer sFileName)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		Blob file = null;
		
		
		
		ds = DataManager.getDataSource();
		
		try {
			con = ds.getConnection();
			String sSQLString  = "SELECT mailID, item, attachID, attachName FROM mailItems, containsAttachments, attachments " +
			" WHERE (mailID = '" + sMailID +  "') AND (containsAttachments.ext_mailID = mailItems.mailID) AND (containsAttachments.ext_attachID = attachments.attachID)  ";			
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			
			if (result.next())
			{
				file = result.getBlob("item");
				sFileName.append(result.getString("mailID").substring(0, result.getString("mailID").length() / 4 ));
				sFileName.append(".msg");
				System.out.println(sFileName);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		
		return file;		
	}

	public static ResultSet getApplicationInformation(String sApplicationID)
	{
		
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		
		String sSQL_String = "SELECT ambientName, reqDateTime, primaSuccessiva, progetto, sottoProgetto FROM applications,transferRequests, concernReq,ambients " +
		" WHERE (applications.applID = '" + sApplicationID +  "' AND (concernReq.ext_applID = applications.applID) " +
				" AND (concernReq.ext_reqID = transferRequests.reqID) AND (concernReq.ext_ambientID = ambients.ambientID))" +
				" ORDER BY transferRequests.reqDateTime DESC";

		try {
			
			ds = DataManager.getDataSource();
			con = ds.getConnection();
			statement = con.prepareStatement(sSQL_String);			
			result = statement.executeQuery();
		
		} catch (SQLException e)
		{
			e.printStackTrace();
		}	
		
		return result;
	}
	
	/**
	 * Funzione che si occupa di prelevare il numero totale di richieste di trasferimento
	 * @return
	 */
	public static TransferRequest getRequestDetail(String sReqID)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		TransferRequest req = null;
		ds = DataManager.getDataSource();
		
		try {
			con = ds.getConnection();
			
			String sSQLString = 
			" SELECT  appl.applID, mail.mailID, req.deployed, req.note, req.primaSuccessiva, req.reqDateTime, att.attachID, appl.progetto, appl.sottoProgetto,  req.reqID, req.tagCVS, req.moduloCVS, amb.ambientName, att.attachName, amb.ambientID " +
			" FROM applications appl, attachments att, containsAttachments cont, mailItems mail, sendedWith snd, ambients amb, concernReq conc, transferRequests req " +
			" WHERE (amb.ambientID = conc.ext_ambientID) AND" +
			" (conc.ext_reqID = req.reqID) AND" +
			" (snd.ext_reqID = req.reqID) AND " +
			" (snd.ext_mailID = mail.mailID) AND " +
			" (cont.ext_mailID = mail.mailID) AND " +
			"(cont.ext_attachID = att.attachID) AND " +
			" (appl.applID = conc.ext_applID) AND " +
			" (req.reqID = " + sReqID + ")";
			
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			if (result.next())
			{
				req = new TransferRequest();
				req.setProjectName(result.getString("progetto"));
				req.setSubProjectName(result.getString("sottoProgetto"));
				req.setDateTime(result.getTimestamp("reqDateTime"));
				req.setModuleVCS(result.getString("moduloCVS"));
				req.setTagCVS(result.getString("tagCVS"));
				req.setPrimaSuccessiva(result.getString("primaSuccessiva"));
				req.setMailID(result.getString("mailID"));
				req.setAttachID(result.getString("attachID"));
				req.setDeployed(result.getString("deployed"));
				req.setNote(result.getString("note"));
				Ambient oAmb =DataManager.getAmbientDetail(result.getString("ambientID"));
				req.setAmbient(oAmb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return req;
	}	

	public static void sendMail()
	{
	    System.out.println("Entering MailServlet");
	    
	 // extract parameters from HttpServletRequest object
	     String email = "clpapa@inpdap.gov.it";
	     String messageBody = "Prova prova";
	     String subject = "Email di prova";
	     String destinationAddress = "GestioneWAS@inpdap.gov.it"; 
	     try 
	     {
	         // look up MailSession
	         Context context = new InitialContext();
	         Session mailSession =  (Session)context.lookup("mail/trasfObj");
	         Message msg = new MimeMessage(mailSession);
	         msg.setFrom(new InternetAddress(email));
	         msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationAddress));
	         // Set the subject and body text
	         msg.setSubject(subject);
	         msg.setText(messageBody);
	         // send message
	         Transport.send(msg);
	         System.out.println("Message Sent");

	     }
	 catch (NamingException e) 
	     {
	         e.printStackTrace();

	     }
	 catch (MessagingException e)
	     {
	         e.printStackTrace();
	     }
	     System.out.println("Exiting Mail procedure");
	}
	
	/**
	 * Restituisce una istanza di Ambient popolata 
	 * @param sID
	 * @return
	 */
	public static Application getApplicationDetail(int iID)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		Application appl = null;
		ds = DataManager.getDataSource();
		
		try {
			con = ds.getConnection();
			
			String sSQLString = 
			" SELECT  appl.applID, appl.WAS61COMPATIBLE, appl.progetto, appl.sottoprogetto, appl.CODAREAAPPL, appl.CODAPPL, appl.CODAREAFUNZ" +
			" FROM application appl " +
			" WHERE (appl.applID = " + iID + ")";
			
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			if (result.next())
			{
				appl = new Application();
				appl.setProject(result.getString("progetto"));
				appl.setSubProject(result.getString("sottoprogetto"));
				appl.setJVM5Compatible(result.getString("WAS61COMPATIBLE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appl;		
	}
	
	/**
	 * Restituisce una istanza di Ambient popolata 
	 * @param sID
	 * @return
	 */
	public static Ambient getAmbientDetail(String sID)
	{
		Connection con = null;
		DataSource ds = null;
		PreparedStatement statement= null;
		ResultSet result = null;
		Ambient amb = null;
		ds = DataManager.getDataSource();
		
		try {
			con = ds.getConnection();
			
			String sSQLString = 
			" SELECT  amb.ambientID, amb.ambientName, amb.ip, amb.port" +
			" FROM ambients amb " +
			" WHERE (amb.ambientID = '" + sID + "')";
			
			statement = con.prepareStatement(sSQLString);
			result = statement.executeQuery();
			
			if (result.next())
			{
				amb = new Ambient();
				amb.setID(result.getString("ambientID"));
				amb.setName(result.getString("ambientName"));				
				amb.setIP(result.getString("ip"));
				amb.setPort(result.getString("port"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return amb;		
	}
}
