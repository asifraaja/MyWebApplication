package student;

import java.util.HashMap;
import java.util.Map;

public class Student {
	public enum addrType{
		HOME, OFFICE
	}
	private int rollnum;
	private String fname;
	private String lname;
	private String gender;
	private String dept;
	private Map<addrType,Address> addresses;
	private String mobileNum;
	
	public int getRollnum() {
		return rollnum;
	}
	
	public void setRollnum(int rollnum) {
		this.rollnum = rollnum;
	}

	public void setRollnum() {
		rollnum = new java.util.Random().nextInt(9999)+1;
		System.out.println(rollnum);
	}
	public Student() {
		addresses = new HashMap<addrType,Address>();
	}
	
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public void setAddress(String addressType, Address address) {
		addrType type = addrType.valueOf(addressType);
		addresses.put(type, address);
	}
	public Address getAddress(String addressType) {
		addrType type = addrType.valueOf(addressType);
		Address address = new Address();
		address = addresses.get(type);
		return address;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname.toLowerCase();
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname.toLowerCase();
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getAddrType(boolean flag) {
		if(flag)
			return addrType.HOME.toString();
		else 
			return addrType.OFFICE.toString();
	}
}
