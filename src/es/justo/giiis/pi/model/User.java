package es.justo.giiis.pi.model;

import java.util.List;

public class User {
	private int idu;
	private String username;
	private String password;
	private String email;
	private int idi;

	public boolean validate(List<String> validationMessages) {
		if (username == null || username.trim().isEmpty()) {
			validationMessages.add("Fill in the Username field.");
		} else if (username.length() > 16) {
			validationMessages.add("Username length cannot be higher than 16 characters.");
		} else if (username.length() < 3) {
			validationMessages.add("Username length must be higher than 3 characters.");
		} else if (username.contains(" ")) {
			validationMessages.add("Username cannot have blank spaces.");
		} else if (!username.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Invalid Username (Pattern allowed:[a-zA-Z][a-zA-Z0-9_-]*).");
		}

		if (password == null || password.trim().isEmpty()) {
			validationMessages.add("Fill in the Password field.");
		} else if (password.length() > 40) {
			validationMessages.add("Password length cannot be higher than 40 characters.");
		} else if (password.length() < 6) {
			validationMessages.add("Password length must be higher than 6 characters.");
		} else if (password.contains(" ")) {
			validationMessages.add("Password cannot have blank spaces.");
		} else if (!password.matches("(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*")) {
			validationMessages.add("Invalid password. It must contain at least 1 upper case and 1 number.");
		}

		if (email == null || email.trim().isEmpty()) {
			validationMessages.add("Fill in the email field.");
		} else if (email.length() > 70) {
			validationMessages.add("Email length cannot be higher than 70 characters.");
		} else if (email.contains(" ")) {
			validationMessages.add("Email cannot have blank spaces.");
		} else if (!email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")) {
			validationMessages.add("Invalid Email.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdi() {
		return idi;
	}

	public void setIdi(int idi) {
		this.idi = idi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + idi;
		result = prime * result + idu;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idi != other.idi)
			return false;
		if (idu != other.idu)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [idu=" + idu + ", username=" + username + ", password=" + password + ", email=" + email + ", idi="
				+ idi + "]";
	}

}
