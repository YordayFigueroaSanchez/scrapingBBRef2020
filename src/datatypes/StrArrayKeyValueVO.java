package datatypes;

import java.util.Hashtable;

public class StrArrayKeyValueVO {
	
	private String strKey;
	private String strOriginal;
	private Hashtable<String, String> values = new Hashtable<String, String>(); 
	
	public StrArrayKeyValueVO() {
		this.strOriginal = "";
		this.values = new Hashtable<String,String>();
	}

	public String getStrOriginal() {
		return strOriginal;
	}

	public void setStrOriginal(String strOriginal) {
		this.strOriginal = strOriginal;
	}

	public Hashtable<String, String> getValues() {
		return values;
	}

	public void setValues(Hashtable<String, String> values) {
		this.values = values;
	}

	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}
	
	

}
