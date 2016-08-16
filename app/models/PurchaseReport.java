package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manage all uer's purchases
 */
public class PurchaseReport {
    private HashMap<Integer,Receipt> receipts;
    private double totalCost;

	/**
	 * default constructor
	 */
    public PurchaseReport(){
        receipts = new HashMap<>();
        totalCost = 0.0;
    }

	/**
	 * add a receipt
	 * @param receipt
	 */
    public void addReceipt(Receipt receipt){
        receipts.put(receipt.getId(),receipt);
        totalCost += receipt.getTotalPrice();
    }

	/**
	 * add list of receipts
	 * @param receipts - list
	 */
    public void addAllReceipts(HashMap<Integer,Receipt> receipts){
        this.receipts = receipts;
        for(Receipt r: receipts.values()){
            totalCost += r.getTotalPrice();
        }
    }

	/**
	 * add list of receipts
	 * @param receipts
	 */
    public void addAllRecipts(ArrayList<Receipt> receipts){
        for(Receipt r: receipts){
            this.receipts.put(r.getId(),r);
            totalCost +=r.getTotalPrice();
        }
    }

	/**
	 * get all receipts
	 * @return receipts
	 */
    public ArrayList<Receipt> getAllReceipts(){
        return new ArrayList<>(this.receipts.values());
    }

	/**
	 * get receipt
	 * @param id - receipt id
	 * @return receipt
	 */
    public Receipt getReceipt(int id){
        return receipts.get(id);
    }

	/**
	 * get total cose
	 * @return total cost
	 */
    public double getTotalCost(){
        return totalCost;
    }
}
