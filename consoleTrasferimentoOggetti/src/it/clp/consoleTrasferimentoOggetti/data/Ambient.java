package it.clp.consoleTrasferimentoOggetti.data;

import it.clp.consoleTrasferimentoOggetti.DataManager;

public class Ambient{
	private String sName = null;
	private String sID = null;
	private String sIP = null;
	private String sPort = null;
	
	public Ambient()
	{ /*Dummy constructor*/}
	
	
	/**
	 *  Costruttore che popola 
	 * @param sID
	 */
	public Ambient(String sID)
	{
		Ambient amb = DataManager.getAmbientDetail(sID);
		this.setID(amb.getID());
		this.setName(amb.getName());
		this.setPort(amb.getPort());
		this.setIP(amb.getIP());
	}
	
	/**
	 * Imposta il nome dell'ambiente 
	 * @param sName
	 */
	public void setName(String sName)
	{
		if (sName !=null)
			this.sName = sName;
	}
	
	/**
	 * Restituisce il nome dell'ambiente 
	 * @return
	 */
	public String getName()
	{
		if (this.sName != null)
			return this.sName;
		else
			return "";
	}
	
	/**
	 * Imposta la porta ssh per l'ambiente 
	 * @param sPort
	 */
	public void setPort(String sPort)
	{
		if (sPort != null)
			this.sPort = sPort;
	}
	
	/**
	 * restituisce il numero di porta sul quale è attivo il servizio ssh per l'ambiente 
	 * @return
	 */
	public String getPort()
	{
		if (this.sPort != null)
			return this.sPort;
		else
			return "";
	}
	
	/**
	 * Imposta l'identificativo dell'ambiente
	 * @param sID
	 */
	public void setID(String sID)
	{
		if (sID != null)
			this.sID = sID;
	}
	
	/**
	 * Restituisce l'identificativo dell'abiente
	 * @return
	 */
	public String getID()
	{
		if (this.sID != null)
			return this.sID;
		else
			return "";
	}
	
	/**
	 * Imposta l'ip sul quale è attivo il servizio di ssh che corrisponde al Dmgr
	 * @param sIP
	 */
	public void setIP(String sIP)
	{
		if (sIP != null)
			this.sIP = sIP;
	}
	
	/**
	 * Restituisce l'ip sul quale è attivo il servizio di ssh che corrisponde al Dmgr
	 * @param sIP
	 */	
	public String getIP()
	{
		if (this.sIP != null)
			return this.sIP;
		else
			return "";
	}	
}
