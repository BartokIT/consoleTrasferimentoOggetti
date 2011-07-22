package it.clp.consoleTrasferimentoOggetti.data;

import it.clp.consoleTrasferimentoOggetti.DataManager;

public class Application {
	private String sProject = null;
	private String sSubProject = null;
	private boolean bJVM5 = false;
	private String sFunctionalAreaCode = null;
	private String sApplicationAreaCode = null;
	private String sApplicationCode = null;
	
	public Application()
	{ /*Dummy constructor*/}
	
	
	/**
	 *  Costruttore che popola 
	 * @param sID
	 */
	public Application(int iID)
	{
		Application appl = DataManager.getApplicationDetail(iID);
		this.setProject(appl.getProject());
		this.setSubProject(appl.getSubProject());
		this.setJVM5Compatible(appl.getJVM5Compatible());
		this.setApplicationAreaCode(appl.getApplicationAreaCode());
		this.setApplicationCode(appl.getApplicationCode());
		this.setFunctionalAreaCode(appl.getFunctionalAreaCode());
	}
	
	/**
	 * Imposta il nome del progetto  
	 * @param sName
	 */
	public void setProject(String sProject)
	{
		if (sProject !=null)
			this.sProject = sProject;
	}
	
	/**
	 * Restituisce il nome del progeto 
	 * @return
	 */
	public String getProject()
	{
		if (this.sProject != null)
			return this.sProject;
		else
			return "";
	}
	
	/**
	 * Imposta il nome del sotto-progetto  
	 * @param sName
	 */
	public void setSubProject(String sSubProject)
	{
		if (sProject !=null)
			this.sSubProject = sSubProject;
	}
	
	/**
	 * Restituisce il nome del sotto-progeto 
	 * @return
	 */
	public String getSubProject()
	{
		if (this.sSubProject != null)
			return this.sSubProject;
		else
			return "";
	}
	
	/**
	 * Imposta l'identificativo dell'ambiente
	 * @param sID
	 */
	public void setJVM5Compatible(String sJVM5)
	{
		if (sJVM5 != null)
			if (sJVM5.compareTo("Y") == 0)
				this.bJVM5 = true;
	}

	/**
	 * Imposta l'identificativo dell'ambiente
	 * @param sID
	 */
	public void setJVM5Compatible(boolean bJVM5)
	{
		this.bJVM5 = bJVM5;
	}	
	
	/**
	 * Restituisce l'identificativo dell'abiente
	 * @return
	 */
	public boolean getJVM5Compatible()
	{
		return this.bJVM5;
	}
	
	/**
	 * Imposta l'identificativo dell'applicazione
	 * @param sAC
	 */
	public void setApplicationCode(String sAC)
	{
		if ( sAC != null)
				this.sApplicationCode = sAC;
	}
	
	/**
	 * Restituisce l'identificativo dell'applicazione
	 * @return
	 */
	public String getApplicationCode()
	{
		if (this.sApplicationCode != null)
			return this.sApplicationCode;
		else
			return "";
	}
	
	/**
	 * Imposta il codice identificativo dell'area funzionale
	 * @param sFAC
	 */
	public void setFunctionalAreaCode(String sFAC)
	{
		if (sFAC != null)
		{
			this.sFunctionalAreaCode = sFAC;
		}
	}
	
	/**
	 * Imposta il codice identificativo dell'area applicativa
	 * @param sAAC
	 */
	public void setApplicationAreaCode(String sAAC)
	{
		if (sAAC != null)
		{
			this.sApplicationAreaCode = sAAC;
		}
	}
	/**
	 * Restituisce il codice identificativo dell'area funzionale
	 * @return
	 */
	public String getFunctionalAreaCode()
	{
		if (this.sFunctionalAreaCode != null)
				return this.sFunctionalAreaCode;
		else
				return "";
	}
	
	/**
	 * Restuituisce il codice identificativo dell'area applicativa
	 * @return
	 */
	public String getApplicationAreaCode()
	{
		if ( this.sApplicationAreaCode != null)
			return this.sApplicationAreaCode;
		else
			return "";
	}
		
}
