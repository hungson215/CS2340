package models;

import com.avaje.ebean.Model;
import scala.concurrent.java8.FuturesConvertersImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Cart Model
 */

@Entity
public class Cart extends Model {
    /** using user'id as the key*/
    @Id
    private int id;

    /** Cart's info **/
    private HashMap<Integer,ItemInfo> items;
    private double totalPrice;
    /** Database connector **/
    public static Model.Finder<Integer,Cart> cartdb = new Model.Finder<>(Cart.class);

    /*********************************************************************
     ********************** CONSTRUCTOR  *********************************
     *********************************************************************/
    /**
     * Default Constructor
     *
     */
    public Cart(){
        items = new HashMap<>();
        totalPrice = 0.0;
        id = -1;
    }

    /*********************************************************************
     * *************** GETTER and SETTER methods  ************************
     * *******************************************************************/
    /**
     * Set cart's items
     */
    public void setItems(HashMap<Integer,ItemInfo> items){
        this.items = items;
        ArrayList<ItemInfo> allItems = new ArrayList<>(items.values());
        totalPrice = 0.00;
        for(ItemInfo i : allItems){
            totalPrice += i.getPrice() * i.getNumToBuy();
        }
    }
    /**
     * Set id
     */
    public void setId(int userid){
        id = userid;
    }
    /**
     * Get Id
     * @return int
     */
    public int getId() { return id;}

    /**
     * Get the cart's Total price
     * @return
     */
    public double getTotalPrice(){
        return totalPrice;
    }

    /**
     * Get items in the cart
     * @return HashMap
     */
    public HashMap<Integer,ItemInfo> getItems() {
        return items;
    }
    public ArrayList<ItemInfo> getItemList(){return new ArrayList<>(items.values());}

    /************************************************************
     ***************** OTHER METHODS  ***************************
     ************************************************************/
    /**
     * Get buying item's quantity
     * @return int
     */
    public int getItemQuantity(int itemid){
        return items.get(itemid).getNumToBuy();
    }
    /**
     * Empty the cart
     *
     */
    public void emptyCart(){
        Set<Integer> keys = items.keySet();
        for(Integer key: keys) {
            ItemInfo i = items.get(key);
            i.setCartid(-1);
            i.save();
        }
        items.clear();
        totalPrice = 0.00;
        this.save();
    }
    /**
     * Search for an item in the cart
     * @param itemid
     */
    public ItemInfo search(int itemid){
        return items.get(itemid);
    }
    /**
     * Remove an item from cart
     * @param itemid
     */
    public ItemInfo removeFromCart(int itemid){
        ItemInfo i = items.get(itemid);
        if(i!= null) {
            i.setCartid(-1);
            int n = i.getNumToBuy();
            totalPrice -= n * i.getPrice();
            i.save();
            items.remove(itemid);
        }
            return i;
    }
    /**
     * Add an item to the cart
     * @param item
     */
    public void addToCart(ItemInfo item) {
        int n = item.getNumToBuy();
        if (n + 1 <= item.getInventory()) {
            if (item.getCartid() != id) {
                item.setCartid(id);
                items.put(item.getId(), item);
            }

            item.setNumToBuy(n + 1);
            totalPrice += item.getPrice();
            item.save();
        }
    }
    /**
     * Update the cart
     * @param itemid
     * @param q
     */
    public void updateCart(int itemid,int q){
        ItemInfo i = items.get(itemid);
        if(i!=null){
            int n = i.getNumToBuy();
            totalPrice -= n * i.getPrice();
            if(q <= i.getInventory()) {
                i.setNumToBuy(q);
                totalPrice += q * i.getPrice();
                i.save();
            }
        }
    }
    /**
     * Check if the cart is empty
     * @return boolean
     */
    public boolean isEmpty(){return items.isEmpty();}
}
