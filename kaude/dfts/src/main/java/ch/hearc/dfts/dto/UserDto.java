package ch.hearc.dfts.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ch.hearc.dfts.models.User;

public class UserDto {
	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;

	@NotEmpty
	private String email;
	
	public UserDto() {
		this.name = " ";
		this.email = " ";
		this.password = " ";
		this.matchingPassword = " ";
	}
	
	public UserDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = " ";
		this.matchingPassword = " ";
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
