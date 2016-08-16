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
public class ItemInfo extends Model {
	/** auto generated id (Read only) **/
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** Item's info **/
	private int catalogid;
	private String name;
    private String img_url;
    private double price;
    private String des;
	private int inventory;
	private String seller;
    private String sellerAddress;
	private int cartid;
    private int numToBuy;

    /** Database connector **/
    public static Model.Finder<Integer,ItemInfo> itemdb = new Model.Finder<>(ItemInfo.class);

    /*********************************************************
     ********************** CONSTRUCTOR  *********************
     *********************************************************/
    /**
     * Default constructor
     */
    public ItemInfo(){
        name="";
        img_url ="";
        price=0.00;
        des="Unknown";
        inventory = 1;
        seller = "Unknown";
        cartid = -1;
        sellerAddress = "Unknown";
        numToBuy = 0;
    }
    /**
     * Custom constructor
     * @param name - item name
     * @param img_url - item picture
     * @param price - item price
     * @param des - item description
     * @param inventory - quantity
     */
    public ItemInfo(String name, String img_url,double price,String des, int inventory) {
        this.name = name;
        this.img_url = img_url;
        this.des = des;
        this.price = price;
        this.seller = "Unknown";
        this.inventory = inventory;
        cartid = -1;
        sellerAddress = "Unknown";
        numToBuy = 0;
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
     * Get the catalog's id
     * @return int
     */
    public int getCatalogId() {
        return catalogid;
    }

    /**
     * Set the catalog's id
     * @param catalogid
     */
    public void setCatalogId(int catalogid) {
        this.catalogid = catalogid;
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
     * Get the item's image url
     * @return String
     */
    public String getImg_url() {
        return img_url;
    }

    /**
     * set the item's image url
     * @param img_url
     */
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    /**
     * Get the item's price
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the item price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
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
     * Get the item's inventory
     * @return int
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * Set the item's inventory
     * @param inventory
     */
    public void setInventory(int inventory) {
        this.inventory = inventory;
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
     * @param sellerAddress
     */
    public void setSellerAddress(String sellerAddress){
        this.sellerAddress = sellerAddress;
    }
    /**
     * Get the seller's address
     * @return String
     */
    public String getSellerAddress(){
        return sellerAddress;
    }
    /**
     * Get the item's cart id
     * @return
     */
    public int getCartid() {
        return cartid;
    }

    /**
     * Set the item's cart id
     * @param cartid
     */
    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

	/**
	 * get number bought
	 * @return numToBuy
	 */
    public int getNumToBuy(){
        return numToBuy;
    }

	/**
	 * set number bought
	 * @param numToBuy
	 */
    public void setNumToBuy(int numToBuy){
        if(numToBuy <= inventory)
            this.numToBuy = numToBuy;
    }
}
