package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Access permission of a catalog
 */
@Entity
public class Permission extends Model {
    @Id
    private int id; //sale Id
    private int seller;
    private int bookKeeper;
    private int clerk;
    private int cashier;
    public static Model.Finder<Integer,Permission> pdb = new Model.Finder<>(Permission.class);

	/**
	 * default constructor
	 */
    public Permission(){
        id=-1;
        seller = -1;
        bookKeeper = -1;
        clerk = -1;
        cashier = -1;
    }

	/**
	 * @return id - permission id
	 */
    public int getId(){
        return id;
    }

	/**
	 * @param id - permission id
	 */
    public void setId(int id){
        this.id = id;
    }

	/**
	 * @return seller - catalog seller
	 */
    public int getSeller() {
        return seller;
    }

	/**
	 * @param seller - catalog seller
	 */
    public void setSeller(int seller) {
        this.seller = seller;
    }

	/**
	 * @return bookKeeper - catalog bookKeeper
	 */
    public int getBookKeeper() {
        return bookKeeper;
    }

	/**
	 * @param bookKeeper - catalog bookKeeper
	 */
    public void setBookKeeper(int bookKeeper) {
        this.bookKeeper = bookKeeper;
    }

	/**
	 * @return clerk - catalog clerk
	 */
    public int getClerk() {
        return clerk;
    }

	/**
	 * @param clerk - catalog clerk
	 */
    public void setClerk(int clerk) {
        this.clerk = clerk;
    }

	/**
	 * @return cashier - catalog cashier
	 */
    public int getCashier() {
        return cashier;
    }

	/**
	 * @param cashier - catalog cashier
	 */
    public void setCashier(int cashier) {
        this.cashier = cashier;
    }
}
