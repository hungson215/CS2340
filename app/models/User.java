package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.RawSql;
import tyrex.util.ArraySet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static models.User.Role.*;

/**
 *  User Model keeps all user account info
 */
@Entity
public class User extends Model{
    /** user account type or role for each user **/
    public static enum Role {UNKNOWN,SUPER_USER,SELL_ADMIN,GUEST,SELLER,BOOK_KEEPER,CASHIER,CLERK,LOGIN_ADMIN,DONATOR}

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private int id;//auto generated id

    /**** user info ****/
    private String                              username;
    private String                              password;
    private String                              email;
    private String                              address;
    private String                              phone;
    /*** user roles ****/
    private Role                                superUser;
    private Role                                sellAdmin;
    private Role                                seller;
    private Role                                bookKeeper;
    private Role                                cashier;
    private Role                                clerk;
    private Role                                guest;
    private Role                                donator;
	private Role								loginAdmin;
    private int db;
    /** status of the user account **/
    private boolean isLocked;

    /** models that are associated with the user **/
    private HashMap<Integer,Catalog> saleEvent;
    private Cart cart;
    private HashMap<Integer,Payment> payments;
    private HashMap<Role, ArrayList<Catalog>> saleRoles;
    private FinancialReport report;

    /** Database connector **/
    public static Model.Finder<Integer,User> userdb = new Model.Finder<>(User.class);

    /**
     * Default constructor
     * Creates User object
     */
    public User(){
        username="";
        password="";
        email="N/A";
        address="N/A";
        isLocked=false;
        saleRoles =new HashMap<>();
        cart = new Cart();
        saleEvent = new HashMap<>();
        payments = new HashMap<>();
        guest = Role.GUEST;
        superUser = Role.UNKNOWN;
        sellAdmin = Role.SELL_ADMIN;
        bookKeeper = Role.UNKNOWN;
        cashier = Role.UNKNOWN;
        clerk = Role.UNKNOWN;
        seller = Role.UNKNOWN;
        donator = Role.UNKNOWN;
		loginAdmin = Role.UNKNOWN;
        report = new FinancialReport();
    }
    /********************************************************************
     *                                                                  *
     *                  GETTER and SETTER methods                       *
     *                                                                  *
     ********************************************************************/
    /**
     * Set user's sale role
     */
    public void setSaleRoles(HashMap<Role,ArrayList<Catalog>> saleRoles) {
        this.saleRoles = saleRoles;
        for (Role r : saleRoles.keySet())
            addRole(r);
    }

	public void setLock(boolean lock) {
			this.isLocked = lock;
		}
    /**
     * Set saleEvent
     * @param saleEvent
     */
    public void setSaleEvent(HashMap<Integer,Catalog> saleEvent){
        this.saleEvent = saleEvent;
        for(Catalog c: saleEvent.values()){
            report.addSaleReport(c.getSaleReport());
        }
    }
    /**
     * Set receipt
     */
    public void addReceipts(HashMap<Integer,Receipt> receipts){
        report.addAllReceipts(receipts);
    }
    /**
     * Init the user cart
     */
    public void setCart(Cart cart){
        this.cart = cart;
    }
    /**
     * Get cart
     * @return Cart
     */
    public Cart getCart(){
        return cart;
    }
    /********  ID  *********/
    /**
     * Get ID
     * @return int
     */
    public int getId(){return id;}

    /***********  USERNAME  *********/
    /**
     * Set Username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get username
     * @return
     */
    public String getUsername() {
		return username;
	}

    /***********   PHONE    ***********/
    /**
     * Set user's phone number
     * @param phone
     */
    public void setPhone(String phone) {
		this.phone = phone;
	}

    /**
     *
     * @return user's phone number
     */
    public String getPhone(){return phone;}

    /************   PASSWORD  ***********/
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return password;}

    /************   EMAIL   *************/
    public void setEmail(String email){this.email = email;}
    public String getEmail(){return email;}

    /************* ADDRESS  **********/
    public void setAddress(String address){this.address = address;}
    public String getAddress(){return address;}
    /************************************************************************
     *                                                                      *
     *                            OTHER METHODS                             *
     *                                                                      *
     ************************************************************************/

    /************************   RECEIPT   *************************/

    /**
     * Get all receipts
     * @returns Array<Receipt>
     */
    public ArrayList<Receipt> getReceipts() {
        return report.getAllReceipts();
    }

    /**
     * Make purchase
     * @params Payment
     * @return Receipt
     */
    public Receipt purchase(Payment payment) {
        Receipt receipt = new Receipt(cart,payment.getId());
        receipt.saveToDb(); //Save the new receipt to the database
        report.addReceipt(receipt);
        return receipt;
    }
    /**
     * Add receipt
     * @param receipt
     */
    public void addReceipt(Receipt receipt){
        if(receipt.getUserId() == id){
            report.addReceipt(receipt);
        }
    }
    /**
     * Get receipt
     * @return Receipt
     */
    public Receipt getReceipt(int receiptId)
    {
        return report.getReceipt(receiptId);
    }
    /***********************  CART  **********************/
    /**
     * Add an item to the user's cart
     * @param item
     */
    public void addToCart(ItemInfo item){
        cart.addToCart(item);
        cart.save();
    }
    /**
     * Remove an item from cart
     * @param itemid
     */
    public void removeFromCart(int itemid){
        cart.removeFromCart(itemid);
        cart.save();
    }
    /**
     * Update cart
     * @param itemid
     * @param q
     */
    public void updateCart(int itemid, int q) {
        cart.updateCart(itemid,q);
        cart.save();
    }
    /***********************  SALE EVENT   **************************/

    /**
     * Create a sale. Sale items are stored in a sale catalog
     * @params Catalog
     * @return void
     */
    public void createSale(Catalog catalog) {
        if(catalog.getUserId() == -1) {
            catalog.setUserId(id);
            catalog.getSaleReport().setReport(report);
            catalog.saveToDb();  //Save the new catalog to the database
            report.addSaleReport(catalog.getSaleReport());
        }
        saleEvent.put(catalog.getId(),catalog);
    }
    /**
     * Get a sale catalog by id
     * @params int
     * @return Catalog or null
     */
    public Catalog getSale(int cataid) {
        if(saleEvent.containsKey(cataid))
            return saleEvent.get(cataid);
        return null;
    }
    /**
    * Add new item to the catalog
    * @params int
    * @params Inteminfo
    * @return void
    */
    public void addToCatalog(int cataid, ItemInfo item) {
        Catalog c = saleEvent.get(cataid);
        if(c!= null) {
            item.setSeller(username);
            item.setSellerAddress(address);
            c.addItem(item);
        }
    }
    /**
     * Get all the sale events of the current user
     * @return ArrayList<Catalog>
     */
    public ArrayList<Catalog> getSales() {
        return new ArrayList<>(saleEvent.values());
    }

    /**
     * Get the item list in the selected catalog
     * @params int
     * @return ArrayList<ItemInfo>
     */
    public ArrayList<ItemInfo> getItems(int cataid) {
         Catalog c = saleEvent.get(cataid);
         if(c != null)
             return c.getItems();
         else
             return null;
    }

    /**
     * Search forh' i=f an items in the selected catalog
     * @params int
     * @params String query
     * @return ArrayList<ItemInfo>
     */
    public ArrayList<ItemInfo> search(int cataid,String query){
        Catalog c = saleEvent.get(cataid);
        if(c != null)
            return c.search(query);
        else
            return null;
    }
    /**
     * Close a sell
     * @param cataid
     */
    public void closeSale(int cataid){
        Catalog c = saleEvent.get(cataid);
        if(c != null)
            c.close().save();
    }
    /*********************************  ROLE   *********************************/
    public void removeRole(Role role){
        //noinspection Duplicates
        switch(role){
            case SUPER_USER:
                superUser = UNKNOWN;
                break;
            case SELL_ADMIN:
                sellAdmin = UNKNOWN;
                break;
            case SELLER:
                seller = UNKNOWN;
                break;
            case BOOK_KEEPER:
                bookKeeper = UNKNOWN;
                break;
            case CASHIER:
                cashier = UNKNOWN;
                break;
            case CLERK:
                clerk = UNKNOWN;
                break;
			case LOGIN_ADMIN:
	            clerk = UNKNOWN;
	            break;
        }
        if(saleRoles.containsKey(role)){
            ArrayList<Catalog> sales = saleRoles.get(role);
            for(Catalog c: sales){
                c.permission(role,-1);
                c.save();
            }
            saleRoles.remove(role);
        }
        save();
    }
    public void addRole(Role role){
        //noinspection Duplicates
        switch(role){
            case SUPER_USER:
                superUser = SUPER_USER;
                break;
            case SELL_ADMIN:
                sellAdmin = SELL_ADMIN;
                break;
            case SELLER:
                seller = SELLER;
                break;
            case BOOK_KEEPER:
                bookKeeper = BOOK_KEEPER;
                break;
            case CASHIER:
                cashier = CASHIER;
                break;
            case CLERK:
                clerk = CLERK;
                break;
			case LOGIN_ADMIN:
				loginAdmin = LOGIN_ADMIN;
				break;
        }
        if(!saleRoles.containsKey(role)){
            saleRoles.put(role, new ArrayList<Catalog>());
        }
        save();
    }

    public void addRole(Catalog c, Role role){
        addRole(role);
        ArrayList<Catalog> sales = saleRoles.get(role);
        if(c!=null){
            sales.add(c);
            c.permission(role, id);
            c.save(); //update the database
        }
    }
    /**
     * Get all the assigned roles of the user
     * @return ArrayList<Role>
     */
    public ArrayList<Role> getRoles(){
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(GUEST);
        if(superUser == SUPER_USER)
            roles.add(SUPER_USER);
        if(sellAdmin == SELL_ADMIN)
            roles.add(SELL_ADMIN);
        if(bookKeeper == BOOK_KEEPER)
            roles.add(BOOK_KEEPER);
        if(cashier == CASHIER)
            roles.add(CASHIER);
        if(clerk == CLERK)
            roles.add(CLERK);
		if(loginAdmin == LOGIN_ADMIN)
	        roles.add(LOGIN_ADMIN);
        return roles;
    }

    public String rolesToString(){
        StringBuilder str = new StringBuilder();
        if(isSuperUser())
            str.append("SUPER_USER/");
        if(isSellAdmin())
            str.append("SELL_ADMIN/");
        if(isGuest())
            str.append("GUEST/");
        for(Role r: saleRoles.keySet())
            str.append(r.toString() + "/" );
        return str.toString();
    }
    /**
     * Get all catalogs(Sales) that the user is assigned to a certain role
     * @return ArrayList
     */
    public ArrayList<Catalog> getSales(Role role) {
        return saleRoles.get(role);
    }
    /**
     * Check if the user is the SUPER_USER
     * @return boolean
     */
    public boolean isSuperUser(){return superUser == SUPER_USER;}

    /**
     * Check if the user is the Sell_Admin
     * @return boolean
     */
    public boolean isSellAdmin(){return sellAdmin == SELL_ADMIN;}

    /**
     * Check if the user is the Seller
     * @return boolean
     */
    public boolean isSeller(){return seller == SELLER;}

    /**
     * Check if the user is the Book keeper
     * @return boolean
     */
    public boolean isBookKeeper(){return bookKeeper == BOOK_KEEPER;}

    /**
     * Check if the user is the Cashier
     * @return boolean
     */
    public boolean isCashier(){return cashier == CASHIER;}

    /**
     * Check if the user is the clerk
     * @return boolean
     */
    public boolean isClerk(){return clerk == CLERK;}

    /**
     * Check if the user is the Guest
     * @return boolean
     */
    public boolean isGuest(){return guest == GUEST;}

	/**
     * Check if the user is the Login Admin
     * @return boolean
     */
    public boolean isLoginAdmin(){return loginAdmin == LOGIN_ADMIN;}

    /**********************  PAYMENT  *****************************/
    /**
     * Add payment
     * @param payment
     */
    public void addPayment(Payment p){
        p.setUserId(id);
        p.save();
        payments.put(p.getId(),p);
    }
    /**
     * Add new payment
     * @param card
     * @param cardName
     * @param cardNum
     * @param cvv
     * @param month
     * @param year
     * @param route
     * @param acccountNum
     */
    public void addPayment(Payment.Card card, String cardName, String cardNum, String cvv, String month, String year, String route, String acccountNum){
        Payment payment = new Payment();
        payment.setUserId(id);
        payment.setType(card);
        payment.setCardName(cardName);
        payment.setCardNum(cardNum);
        payment.setCvv(cvv);
        payment.setExpMonth(month);
        payment.setExpYear(year);
        payment.setRoutingNum(route);
        payment.setAccountNum(acccountNum);
        payment.save();
        payments.put(payment.getId(),payment);
    }

    /**
     * Remove a payment
     * @param id
     */
    public void removePayment(int id){
        if(payments.containsKey(id)){
            payments.get(id).delete();
            payments.remove(id);
        }
    }
    /**
     * Set payment
     * @param payments
     */
    public void setPayments(HashMap<Integer,Payment> payments){
        this.payments = payments;
    }

    public HashMap<Integer,Payment> getPayments(){return payments;}
    /**************** LOCK/UNLOCK ACCOUNT *****************/
     /**
     * Check if the user's account is locked
     * @return boolean
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * After 3 failed login attempts users' status turns to locked
     * sets status of the user
     *@return User
 -+

     */
    public User lock() {
        isLocked = true;
        return this;
    }
    public User unlock(){
        isLocked = false;
        return this;
    }
    /*********************** DATABASE ***************************/

    /**
     * Save the user to the database
     */
    public void saveToDB() {
        this.save();
        cart.setId(this.id);
        cart.save();
    }

	/**
	 * @return report - user's financial report
	 */
    public FinancialReport getReport(){
        return report;
    }
}
