package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

/**
 * This model contains the instance variables needed to login
 * to the application's home page.
 */
public class Login extends Model{
    @Constraints.Required
    private String username;
    @Constraints.Required
    private String password;

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
}
