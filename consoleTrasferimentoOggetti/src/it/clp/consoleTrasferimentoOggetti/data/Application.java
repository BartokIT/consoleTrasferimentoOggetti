package it.clp.consoleTrasferimentoOggetti.data;

import it.clp.consoleTrasferimentoOggetti.DataManager;

public class Application {
	private String sProject = null;
	private String sSubProject = null;
	private boolean bJVM5 = false;
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
}
