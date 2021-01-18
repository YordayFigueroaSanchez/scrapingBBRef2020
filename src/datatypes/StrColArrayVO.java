package datatypes;

import java.util.ArrayList;
import java.util.Hashtable;

public class StrColArrayVO {
	
	private String strKey;
	private String strOriginal;
	private ArrayList<Hashtable<String, String>>  colValues = new ArrayList<Hashtable<String, String>>();
	
	public StrColArrayVO() {
		
	}

	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}

	public String getStrOriginal() {
		return strOriginal;
	}

	public void setStrOriginal(String strOriginal) {
		this.strOriginal = strOriginal;
	}

	public ArrayList<Hashtable<String, String>> getColValues() {
		return colValues;
	}

	public void setColValues(ArrayList<Hashtable<String, String>> colValues) {
		this.colValues = colValues;
	}

			

}
