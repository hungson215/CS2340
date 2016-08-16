package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * ItemInfo Model keeps all item's info
 */
@Entity
public class Donation extends Model {
	/** auto generated id (Read only) **/
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** Donations's info **/
	private String name;
    private String des;
	private String seller;
    private String charity;

    /** Database connector **/
    public static Model.Finder<Integer,Donation> donationdb = new Model.Finder<>(Donation.class);

    /*********************************************************
     ********************** CONSTRUCTOR  *********************
     *********************************************************/
    /**
     * Default constructor
     */
    public Donation(){
        name="";
        des="Unknown";
        seller = "Unknown";
        charity = "Unknown";
    }
    /**
     * Custom constructor
     * @param name - item name
     * @param img_url - item picture
     * @param price - item price
     * @param des - item description
     * @param inventory - quantity
     */
    public Donation(String name, String des) {
        this.name = name;
        this.des = des;
        this.seller = "Unknown";
        charity = "Unknown";
    }

    /***************************************************************************************
     **************************** GETTER and SETTER  ***************************************
     ***************************************************************************************/
    /**
     * Get the item's id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Get the item's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the item's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the item's description
     * @return
     */
    public String getDes() {
        return des;
    }

    /**
     * Set the item's description
     * @param des
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * Get the seller's name
     * @return
     */
    public String getSeller() {
        return seller;
    }

    /**
     * Set the seller's name
     * @param seller
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }
    /**
     * Set the seller's address
     * @param charity
     */
    public void setSellerAddress(String charity){
        this.charity = charity;
    }
    /**
     * Get the seller's address
     * @return String
     */
    public String getSellerAddress(){
        return charity;
    }
}
