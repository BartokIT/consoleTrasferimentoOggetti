package it.clp.consoleTrasferimentoOggetti;

public  class UtilityFunction {
	public static String escapeStringJSON(String s)
	{
		return s.replace("\"", "\\\"");
	}
}
