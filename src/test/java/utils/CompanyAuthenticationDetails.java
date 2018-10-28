package utils;

public class CompanyAuthenticationDetails {
	
	private String email,password,phone,passcode;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	
	public void setCompanyAuthenticationDetails(String email,String password,String phone) {
		this.email=email;
		this.password=password;
		this.phone=phone;
	}
	
	public void printCompanyAuthenticationDetails()
	{
		System.out.println("Company Authentication Details\n-------------------------------");
		System.out.println("email: "+email+"\npassword: "+password+"\nphone: "+phone+"\n-------------------------------");
	}

}
