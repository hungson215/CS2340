package models;

import com.avaje.ebean.Model;

import java.util.ArrayList;

/**
 * This model contains current user's profile info
 */

public class Profile extends Model {

    private String username;
    private String email;
    private String phone;
    private String address;

    /***********************************************
     ************** CONSTRUCTOR ********************
     ***********************************************/
    /**
     * Default Constructor
     */
    public Profile(){
        username = "";
        email = "";
        phone = "";
        address = "";
    }
    /************************************************
     ************** SETTER and GETTER ***************
     ************************************************/
    /**
     * Get username
     * @return String
     */
    public String getUsername(){
        return username;
    }
    /**
     * Setter username
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }
    /**
     * Get email
     * @return email
     */
    public String getEmail(){
        return email;
    }
    /**
     * Set email
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }
    /**
     * Get phone
     * @return phone
     */
    public String getPhone(){
        return phone;
    }
    /**
     * Set phone
     * @param phone
     */
    public void setPhone(String phone){
        this.phone = phone;
    }
    /**
     * Get address
     * @return String
     */
    public String getAddress(){
        return address;
    }

    /**
     * Set addrress
     * @param address
     */
    public void setAddress(String address){
        this.address = address;
    }
}


