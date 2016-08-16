package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.WhenCreated;
import scala.xml.PrettyPrinter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Receipt Model
 */
@Entity
public class Receipt extends Model {
    /** auto generated id (Read only)  ***/
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /***** Receipt's info ******/
    private ArrayList<Transaction> trans;
    private double totalPrice;
    private int paymentId;
    private int userid;
    @CreatedTimestamp
    @WhenCreated
    private Timestamp date;
    /** Database connector **/
    public static Model.Finder<Integer,Receipt> receiptdb = new Model.Finder<>(Receipt.class);

    /**************************************************************
     *********************  CONSTRUCTOR  **************************
     **************************************************************/
    /**
     * Default Constructor
     */
    public Receipt(){
        trans = new ArrayList<>();
        userid = -1;
        totalPrice = 0.00;
        paymentId = -1;
        date = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    /**
     * Custom Constructor
     * @param cart
     * @param paymentId
     */
    public Receipt(Cart cart, int paymentId) {
        trans = new ArrayList<Transaction>();
        ArrayList<ItemInfo> items = new ArrayList<>(cart.getItems().values());
        for(ItemInfo i : items) {
            Transaction t = new Transaction(-1,i.getCatalogId(),i.getId(),i.getNumToBuy(),i.getPrice());
            t.save();
            trans.add(t);
            int inv = i.getInventory();
            int numToBuy = i.getNumToBuy();
            i.setInventory(inv - numToBuy);
            i.setNumToBuy(0);
        }
        userid = cart.getId();
        totalPrice = cart.getTotalPrice();
        this.paymentId = paymentId;
        date = new Timestamp(Calendar.getInstance().getTime().getTime());
        cart.emptyCart();
    }

    /**
     * Get total price
     * @return totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

	/**
	 * set total price
	 * @param totalPrice
	 */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

	/**
	 * Get payment id
	 * @return paymentId
	 */
    public int getPaymentId() {
        return paymentId;
    }

	/**
	 * Set payment id
	 * @param paymentId
	 */
    public void setPayment(int paymentId) {
        this.paymentId = paymentId;
    }

	/**
	 * get timestamp date
	 * @return date
	 */
    public Timestamp getDate() {
        return date;
    }

	/**
	 * set user id
	 * @param userid
	 */
    public void setUserId(int userid) {
        this.userid = userid;
    }

	/**
	 * get user id
	 * @return userid
	 */
    public int getUserId() {
		return userid;
	}

	/**
	 * get id
	 * @return id - receipt id
	 */
    public int getId() {
        return id;
    }

	/**
	 * get transactions
	 * @return trans - transactions list
	 */
    public ArrayList<Transaction> getTrans(){
        return trans;
    }

	/**
	 * set transactions
	 * @param trans - transactions list
	 */
    public void setTrans(ArrayList<Transaction> trans){
        this.trans = trans;
        double cost = 0.0;
        for(Transaction t : trans){
            cost += t.getTotalPrice();
        }
        totalPrice = cost;
    }

	/**
	 * save to database
	 */
    public void saveToDb(){
        this.save();
        for(Transaction t: trans){
            t.setReceiptId(id);
            t.save();
        }
    }
}
