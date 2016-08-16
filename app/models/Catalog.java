package models;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Catalog  model keeps catalog info
 */
@Entity
public class Catalog extends Model {
    /** auto generated id (Read only) ***/
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /***  catalog info ***/
    private int userid;
    private String name;
    private String des;
    private HashMap<Integer,ItemInfo> itemlist;
    private boolean isClosed;
    private Permission permission;
    private SaleReport report;

    /**  database connector **/
    public static Model.Finder<Integer,Catalog> catadb = new Model.Finder<>(Catalog.class);

    /********************************************************
     ******************** CONSTRUCTOR ***********************
     ********************************************************/

    /**
     * Default Constructor
     */
    public Catalog(){
        userid = -1;
        name = "";
        des = "";
        itemlist = new HashMap<>();
        isClosed = false;
        permission = new Permission();
        permission.setId(id);
        report = new SaleReport();
    }

    /*******************************************************************
     *******************  GETTER and SETTER ****************************
     *******************************************************************/
    /**
     * Set item list
     */
    public void setItems(HashMap<Integer,ItemInfo> items){
        this.itemlist = items;
    }
    /**
     *Get the catalog's id
     * @return int
     */
    public int getId(){
        return id;
    }
    /**
     * Get seller id
     * @return int
     */
    public int getUserId() {
        return userid;
    }

    /**
     * Set seller id
     * @param userid
     */
    public void setUserId(int userid) {
        this.userid = userid;
    }

    /**
     * Get catalog's name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set catalog's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        report.setSaleName(name);
    }

    /**
     * Get catalog description
     * @return
     */
    public String getDes() {
        return des;
    }

    /**
     * Set catalog's description
     * @param des
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * Check if the catalog is closed
     * @return
     */
    public boolean isClosed(){
        return isClosed;
    }

    /**
     * Close the catalog
     * @return Catalog
     */
    public Catalog close() {
        isClosed = true;
        this.save();
        return this;
    }

    /*****************************************************************************
     ******************************* OTHER METHODS *******************************
     *****************************************************************************/

    /************************  ADD/REMOVE/SEARCH AN ITEM  *************************/
    /**
     * Add a new item to the catalog
     * @param item
     */
    public void addItem(ItemInfo item)
    {
        if(item.getCatalogId() != id) {
            item.setCatalogId(id);
            item.save(); // update the item
        }
        itemlist.put(item.getId(),item);
    }
    /**
     * Remove an item from the catalog
     * @return ItemInfo
     */
    public ItemInfo removeItem(int itemid){
        ItemInfo i = itemlist.remove(itemid);
        i.setCatalogId(-1);
        i.save();
        return i;
    }
	/**
     *Get all item in the catalog
     * @return ArrayList<ItemInfo>
     */
    public ArrayList<ItemInfo> getItems(){
        return new ArrayList<>(itemlist.values());
    }

	/**
     * Search for items in the catalog
     * @return list of items that match the search
     */
    public ArrayList<ItemInfo> search(String query) {
        ArrayList<ItemInfo> list = new ArrayList<>();
        for(ItemInfo item: itemlist.values()) {
            if(item.getName().toLowerCase().contains(query.toLowerCase()) || item.getDes().toLowerCase().contains(query.toLowerCase())) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Search for an item in the catalog
     * @param itemid
     * @return ItemInfo
     */
    public ItemInfo search(int itemid){
        return itemlist.get(itemid);
    }
    /*******************************  SET/REMOVE PERMISSION ***********************************/
    /**
     *Set Access Permission to a user
     * @param role
     * @param userid
     */
    public void permission(User.Role role,int userid) {
        switch(role) {
            case BOOK_KEEPER:
                permission.setBookKeeper(userid);
                break;
            case SELLER:
                permission.setSeller(userid);
                break;
            case CASHIER:
                permission.setCashier(userid);
                break;
            case CLERK:
                permission.setClerk(userid);
                break;
        }
        permission.save();
    }
    /**
     * Get Access Permission
     * @return HashMap<User.Role,Integer>
     */
    public Permission getPermissions(){
        return permission;
    }

	/**
	 * set permissions
	 * @param p - permissions
	 */
    public void setPermisson(Permission p) {
        this.permission = p;
    }
    /*******************************  TRANSACTION REPORT  *****************************************/
	/**
	 * set transaction
	 * @param trans - list of transactions
	 */
    public void setTrans(ArrayList<Transaction> trans){
        report.setTrans(trans);
    }

	/**
	 * add transaction
	 * @param tran - transaction being added
	 */
    public void addTrans(Transaction tran){
        tran.setCatalogId(id);
        report.addTrans(tran);
    }

	/**
	 * gets sale report
	 * @return report - sale report
	 */
    public SaleReport getSaleReport(){
        return report;
    }

	/**
	 * saves database
	 */
    public void saveToDb(){
        this.save();
        report.setSaleName(name);
    }
}
