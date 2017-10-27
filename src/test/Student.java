package test;

public class Student {
	private String fName;
	private String lName;
	private boolean goldCustomer;
	
	public Student(String fName, String lName, boolean goldCustomer) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.goldCustomer = goldCustomer;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public boolean isGoldCustomer() {
		return goldCustomer;
	}

	public void setGoldCustomer(boolean goldCustomer) {
		this.goldCustomer = goldCustomer;
	}
	
	
}
