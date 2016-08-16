package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Transaction record
 */
@Entity
public class Transaction extends Model{
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int receiptId;
    private int catalogId;
    private int itemId;
    private double totalPrice;
    private int quantity;

    /** Database Connector **/
    public static Model.Finder<Integer,Transaction> transdb = new Model.Finder<>(Transaction.class);

    /**************************************************
     ************* CONSTRUCTOR  ***********************
     **************************************************/
    /**
     * Default Constructor
     */
    public Transaction(){
        itemId = -1;
        catalogId = -1;
        totalPrice = 0.00;
        quantity = 0;
        receiptId = -1;
    }
    /**
     * Custom Constructor
     */
    public Transaction(int receiptId,int catalogId, int itemId, int quantity, double itemPrice){
        this.receiptId = receiptId;
        this.itemId = itemId;
        this.catalogId = catalogId;
        this.quantity = quantity;
        this.totalPrice = quantity * itemPrice;
    }
    /***************************************************
     ************* GETTER and SETTER  ******************
     ***************************************************/
    /**
     * Get receipt number
     * @return int
     */
    public int getReceiptId(){
        return receiptId;
    }
    /**
     * Set receipt number
     * @param receiptId
     */
    public void setReceiptId(int receiptId){
        this.receiptId = receiptId;
    }
    /**
     * Get the transaction's catalog id
     * @return int
     */
    public int getCatalogId(){
        return catalogId;
    }
    /**
     *Set the transaction's catalog id
     * @param catalogId
     */
    public void setCatalogId(int catalogId){
        this.catalogId = catalogId;
    }

    /**
     * Get the item id
     * @return int
     */
    public int getItemId(){
        return itemId;
    }
    /**
     * Set the item id
     * @param itemId
     */
    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    /**
     * Get totalPrice
     * @return double
     */
    public double getTotalPrice(){
        return totalPrice;
    }
    /**
     * Set totalPrice
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
    /**
     * Get the buy quantity
     * @return int
     */
    public int getQuantity(){
        return quantity;
    }
    /**
     * Set quantity
     * @param quantity
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

	/**
	 * get id
	 * @return id - transaction id
	 */
    public int getId(){
        return id;
    }
}
