package it.clp.consoleTrasferimentoOggetti.data;

import it.clp.consoleTrasferimentoOggetti.DataManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TransferRequest {
	private int idRequest= 0;
	private Timestamp timeRequest = null;
	private String sProgetto = null;
	private String sSottoprogetto = null;
	private String sPrimaSuccessiva = null;
	private SimpleDateFormat  formatterData = null; 
	private SimpleDateFormat  formatterTime = null;
	private String sTagCVS = null;
	private String sModuloCVS = null;
	private Ambient oAmbient = null;
	private String mailID = null;
	private String attachID = null;
	
	public TransferRequest()
	{ /*Dummy constructor*/	}
	
	public TransferRequest(String requestID)
	{
		this.setCurrentRequest(requestID);
	}	
	
	/**
	 * Utilizzato per impostare la richiesta corrente
	 * @param sReqID
	 */
	public void setCurrentRequest(String sReqID)
	{
		this.formatterData = new SimpleDateFormat("dd/MM/yyyy" );
		this.formatterTime = new SimpleDateFormat("HH:mm:ss" );
		TransferRequest req;
		req = DataManager.getRequestDetail(sReqID);
		this.idRequest = Integer.parseInt(sReqID);
		this.sProgetto = req.getProjectName();
		this.sSottoprogetto = req.getSubProjectName();
		this.timeRequest = req.getTimestamp();
		this.sModuloCVS = req.getModuleCVS();
		this.sTagCVS = req.getTagCVS();
		this.sPrimaSuccessiva = req.getPrimaSuccessiva();
		this.oAmbient = req.getAmbient();
		this.mailID = req.getMailID();
		this.attachID = req.getAttachID();
	}
	
	public int getRequestID()
	{
		return this.idRequest; 
	}
	
	/**
	 * Permette di impostare il nome del progetto
	 * @param sNomeProgetto
	 */
	public void setProjectName(String sNomeProgetto)
	{
		if (sNomeProgetto != null)
			this.sProgetto = sNomeProgetto;
			
	}
	public String getProjectName()
	{
		if (this.sProgetto != null)
			return this.sProgetto;
		else
			return "";
	}
	
	public void setSubProjectName(String sNomeSottoProgetto)
	{
	 	if (sNomeSottoProgetto != null)
	 		this.sSottoprogetto = sNomeSottoProgetto; 
	}
	
	public String getSubProjectName()
	{
		if (this.sSottoprogetto != null)
			return this.sSottoprogetto;
		else
			return "";
	}
	
	public void setPrimaSuccessiva(String sPrimaSuccessiva)
	{
		this.sPrimaSuccessiva = sPrimaSuccessiva;
	}
	
	public String getPrimaSuccessiva()
	{
		return this.sPrimaSuccessiva;
	}
	
	public void setDateTime(Timestamp oTime)
	{
		this.timeRequest = oTime;
	}
	
	public Timestamp getTimestamp()
	{
		if (this.timeRequest != null)
			return this.timeRequest;
		else 
			return null;
	}
	
	public String getDateString()
	{
		if (this.timeRequest != null)
			return this.formatterData.format(this.timeRequest);
		else
			return "";
	}
	
	public String getTimeString()
	{
		if (this.timeRequest != null)
			return this.formatterTime.format(this.timeRequest);
		else
			return "";
	}	
	
	public String getTagCVS()
	{
		if (this.sTagCVS != null) 
			return this.sTagCVS;
		else
			return "";
	}
	
	public void setTagCVS(String sTag)
	{
		if (sTag != null)
				this.sTagCVS = sTag;
	}
	
	public String getModuleCVS()
	{
		if (this.sModuloCVS != null) 
			return this.sModuloCVS;
		else
			return "";
	}
	
	public void setModuleVCS(String sModule)
	{
		if (sModule != null)
			this.sModuloCVS = sModule;
	}
	
	public void setAmbient(Ambient oAmbient)
	{
		if (oAmbient != null)
			this.oAmbient = oAmbient;
	}
	
	public Ambient getAmbient()
	{
		if (this.oAmbient != null)
			return this.oAmbient;
		else
			return null;	
	}
	
	public void setMailID(String mailID)
	{
		if (mailID != null)
			this.mailID  = mailID;
	}
	
	public String getMailID()
	{
		if (this.mailID != null)
			return this.mailID;
		else	
			return "";
	}	
	
	public void setAttachID(String attachID)
	{
		if (attachID != null)
		{
			this.attachID = attachID;
		}
	}
	
	public String getAttachID()
	{
		if (this.attachID != null)
			return this.attachID;
		else
			return "";
	}
	
}
