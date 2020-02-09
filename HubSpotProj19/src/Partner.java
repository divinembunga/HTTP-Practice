import java.util.Arrays;

public class Partner {
	private String firstName;
	private String lastName;
	private String email;
	private String country;
	private String[] dates;
	
	public Partner(String firstName,String lastName,String email,String country,String[] dates){
		this.firstName= firstName;
		this.lastName= lastName;
		this.email= email;
		this.country= country;
		this.dates=dates;
	}

	@Override
	public String toString() {
		return "Partner [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", country="
				+ country + ", dates=" + Arrays.toString(dates) + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String[] getDates() {
		return dates;
	}

	public void setDates(String[] dates) {
		this.dates = dates;
	}
	

}
