package datatypes;

import java.util.Hashtable;

public class StrDoubleArrayKeyValueVO {
	
	private String strKey;
	private String strOriginal;
	private Hashtable<String, String> values = new Hashtable<String, String>(); 
	private Hashtable<String, String> values2 = new Hashtable<String, String>(); 
	
	public StrDoubleArrayKeyValueVO() {
		this.strOriginal = "";
		this.values = new Hashtable<String,String>();
		this.setValues2(new Hashtable<String,String>());
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

	public Hashtable<String, String> getValues2() {
		return values2;
	}

	public void setValues2(Hashtable<String, String> values2) {
		this.values2 = values2;
	}
	
	

}
