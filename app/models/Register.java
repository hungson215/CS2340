package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

/**
 * This model contains the instance variables needed to register
 * to the application
 */
public class Register extends Model {
    @Constraints.Required
    private String username;
    @Constraints.Email
    public String email;
    @Constraints.Required
    private String password;
    @Constraints.Required
    public String confirm_password;

	/**
	 * get username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * set username
	 * @param username
	 */
	public void setUsername(String name) {
		this.username = name;
	}

	/**
	 * get password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set password
	 * @param password
	 */
	public void setPassword(String pass) {
		this.password = pass;
	}

	/**
	 * get confirm password
	 * @return confirm_password
	 */
	public String getConfirmPassword() {
		return confirm_password;
	}

	/**
	 * set confirm password
	 * @param password
	 */
	public void setConfirmPassword(String pass) {
		this.confirm_password = pass;
	}

	/**
	 * get email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
