package ch.hearc.dfts.dto;


import ch.hearc.dfts.models.User;

public class UserDto {

	private String name;

	private String password;
	private String matchingPassword;

	private String email;
	
	public UserDto() {
		this.name = "";
		this.email = "";
		this.password = "";
		this.matchingPassword = "";
	}
	
	public UserDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = "";
		this.matchingPassword = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
