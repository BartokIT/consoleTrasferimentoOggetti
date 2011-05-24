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
		this.sProgetto = req.getProjectName();
		this.sSottoprogetto = req.getSubProjectName();
		this.timeRequest = req.getTimestamp();
		this.sModuloCVS = req.getModuleCVS();
		this.sTagCVS = req.getTagCVS();
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
	
	public void setPrimaSuccessiva(char cPrimaSuccessiva)
	{
		this.sPrimaSuccessiva = String.valueOf(cPrimaSuccessiva);
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
		return this.timeRequest;
	}
	
	public String getDateString()
	{
		return this.formatterData.format(this.timeRequest);
	}
	
	public String getTimeString()
	{
		return this.formatterTime.format(this.timeRequest);
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
}
