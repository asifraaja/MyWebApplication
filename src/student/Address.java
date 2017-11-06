package student;

public class Address {
	private String doorno;
	private String street;
	private String area;
	private String city;
	private int pincode;
	
	public Address() {
		
	}
	public Address(String doorno, String street, String area, String city, int pincode) {
		this.doorno = doorno;
		this.street = street;
		this.area = area;
		this.city = city;
		this.pincode = pincode;
	}
	public String getDoorno() {
		return doorno;
	}
	public void setDoorno(String doorno) {
		this.doorno = doorno;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(doorno); sb.append(",");
		sb.append(street); sb.append(",");
		sb.append(area); sb.append(",");
		sb.append(city); sb.append(",");
		sb.append(pincode); sb.append(",");
		return sb.toString();
	}
}
