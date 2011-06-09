package it.clp.consoleTrasferimentoOggetti;

import java.util.regex.*;

import javax.servlet.http.HttpSession;

public  class UtilityFunction {
	private static String sCurrentStyle ="";
	private static int iNumCommand=0;

	public static void resetNumberCommands()
	{
		UtilityFunction.iNumCommand = 0;
	}
	
	public static String escapeStringJSON(String s)
	{
		return s.replace("\"", "\\\"");
	}
	
	public static String shellCursorUpBind(String s, int iCurrentRow)
	{
		Pattern pMoveUpCursor = Pattern.compile("\\e\\[([0-9]*)A(.*)\\e\\[u");
		Matcher mMoveUpCursor = pMoveUpCursor.matcher(s);
		if (mMoveUpCursor.find())
		{
			int iDestRow = iCurrentRow - Integer.parseInt(mMoveUpCursor.group(1));
			String sText = mMoveUpCursor.group(2);
			String sHtmlText = UtilityFunction.convertBash2Html(sText);
			String sJS = "var textOrig = $(\"#row" + iDestRow + "\").text();\n";
			sJS += "var textReplace =$(\"" + sHtmlText + "\").text();\n";
			sJS += "var textLast = textOrig.substring(textReplace.length,textOrig.length );\n";
			sJS += "$(\"#row" + iDestRow + "\").replaceWith(\"" + sHtmlText +"\" +  textLast);\n";
			return sJS;
		}
		else
			return "";
	}
	
	public static String convertBash2Html(String s)
	{
		return UtilityFunction.convertBash2Html(s, -1 );
	}
	
	public static String convertBash2Html(String s, int iCurrentRow )
	{
		//Controllo sulla stringa vuota
		if (s.compareTo("") == 0 )
		{
			if (iCurrentRow != -1)
				return "<span id=\"row" + iCurrentRow + "\"></span>";
		}
		
		String sOutput="";
		Pattern pResize = Pattern.compile("\\e\\[(?:[0-9]+;)+t");
		Pattern pSaveCursor = Pattern.compile("\\e\\[s");
		//Pattern pRestoreCursor = Pattern.compile("\\e\\[u");
		Pattern pMoveUpCursor = Pattern.compile("\\e\\[([0-9]*)A(.*)\\e\\[u");
		Pattern pSGR = Pattern.compile("\\e\\[([0-9]*)m");	
		
		
		
		int prev_end = 0;
		//Elimino la sequenza di escape che contiene il ridimensionamento
		s = pResize.matcher(s).replaceAll("");
		s = pSaveCursor.matcher(s).replaceAll("");
		//s = pRestoreCursor.matcher(s).replaceAll("");
		
		Matcher mMoveUpCursor = pMoveUpCursor.matcher(s);
		while (mMoveUpCursor.find())
		{
			sOutput += "<script type=\"text/javascript\">" + UtilityFunction.shellCursorUpBind(mMoveUpCursor.group(), iCurrentRow) + "</script>";
		}
		
		s = pMoveUpCursor.matcher(s).replaceAll("");
		
		Matcher mSGR = pSGR.matcher(s);
		
		//Inizio la ricerca delle sequenze grafiche
		while (mSGR.find())
		{	
			//Controllo se non c'è testo tra il precedente formato e il corrente
			if (prev_end == mSGR.start())
			{
				UtilityFunction.sCurrentStyle = UtilityFunction.convertSGR2Css(mSGR.group(1));
				prev_end = mSGR.end();
			}
			else
			{
				//Creo un nuovo span per la stringa precedente al formato corrente
				
				//System.out.println("Inizio:" + prev_end + " - Fine: "+ m.start() + " - Sottostr: " + m.group() + s );
				try {
					sOutput += "<span style='" + UtilityFunction.sCurrentStyle + "'>" + s.substring(prev_end,mSGR.start()) + "</span>";
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				
				UtilityFunction.sCurrentStyle = UtilityFunction.convertSGR2Css(mSGR.group(1));
				prev_end = mSGR.end();
			}
		}
		
		
		if ( (prev_end  < s.length()) )
		{
			if (UtilityFunction.sCurrentStyle.compareTo("") != 0)
				sOutput += "<span style='" + UtilityFunction.sCurrentStyle + "'>" + s.substring(prev_end,s.length()) + "</span>";
			else
				sOutput += s;
		}
		
		if (iCurrentRow != -1)
			return "<span id=\"row" + iCurrentRow + "\">" + sOutput + "</span>";
		else
			return sOutput;
	}
	
	public static String convertSGR2Css(String sColorCode)
	{
			int color = 0;
			String sColor ="";
			if (sColorCode.compareTo("") != 0)
				color = Integer.parseInt(sColorCode);
			
			switch (color)
			{
				default:
				case 0:
					sColor ="color: white";				
					break;				
				case 1:
					sColor ="font-weight: bold";
					break;
				case 30: 
					sColor = "color: black";
					break;
				case 31: 
					sColor = "color: red";
					break;
				case 32: 
					sColor = "color: green";
					break;
				case 33: 
					sColor = "color: yellow";
					break;
				case 34: 
					sColor = "color: blue";
					break;
				case 35: 
					sColor = "color: #FF00FF";
					break;
				case 36: 
					sColor = "color: #7FFFD4";
					break;
				case 37: 
					sColor = "color: white";
					break;		 
			}
			
			return sColor;
	}
	
	public static String linkDeployString(String sFormatted, String sUnFormatted, HttpSession oSession, String sReqID)
	{
		Pattern deployPattern = Pattern.compile("echo .*;(.*)");
		Matcher m = deployPattern.matcher(sUnFormatted);
		if (m.find())
		{
			oSession.setAttribute(String.valueOf(UtilityFunction.iNumCommand), m.group(1));
			//System.out.println("Prova " + (String) oSession.getAttribute(String.valueOf(UtilityFunction.iNumCommand)));
			String row = "<a class=\"deploy_link\" href=\"RunUpdate.do?requestID="+ sReqID + "&command=" + UtilityFunction.iNumCommand + "\">" + sFormatted + "</a>";
			UtilityFunction.iNumCommand++;
			return row;
		}
		else
			return sFormatted;
	}
}

