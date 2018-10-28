package utils;

public class BusinessInfoDetails {
	
	private String fname,lname,storeName,street,city,district,state,pincode,ponumber;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPonumber() {
		return ponumber;
	}

	public void setPonumber(String ponumber) {
		this.ponumber = ponumber;
	}

	public void setBusinessInfoDetails(String fname,String lname,String storeName,String street,String city,String district,String state,String pincode,String ponumber) {
		this.fname = fname;
		this.ponumber = ponumber;
		this.pincode = pincode;
		this.state = state;
		this.district = district;
		this.city = city;
		this.street = street;
		this.storeName = storeName;
		this.lname = lname;
	}
	
	public void printBusinessInfoDetails() {
		System.out.println("---------------------Business Information Details--------------------");
		System.out.println("First Name: "+fname);
		System.out.println("Last Name: "+lname);
		System.out.println("Store name: "+storeName);
		System.out.println("Street: "+street);
		System.out.println("City: "+city);
		System.out.println("District: "+district);
		System.out.println("Pin code/Postal code: "+pincode);
		System.out.println("PO Number: "+ponumber);
		System.out.println("State: "+state);
		System.out.println("-----------------------------------------");		
	}
}
