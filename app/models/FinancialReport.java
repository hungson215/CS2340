package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manage user's sale and purchase history
 */
public class FinancialReport {
    private PurchaseReport purchaseReport;
    private ArrayList<SaleReport> saleReport;
    private double balance;
    private double totalRev;

	/**
	 * default constructor
	 */
    public FinancialReport(){
        balance = 0.0;
        totalRev =0.0;
        purchaseReport = new PurchaseReport();
        saleReport = new ArrayList<>();
    }

	/**
	 * get balance
	 * @return balance
	 */
    public double getBalance(){
        return  balance;
    }

	/**
	 * get sale reports
	 * @return saleReport - all sale reports
	 */
    public ArrayList<SaleReport> getSaleReport(){
        return saleReport;
    }

	/**
	 * get purchase report
	 * @return purchaseReport
	 */
    public PurchaseReport getPurchaseReport(){
        return purchaseReport;
    }

	/**
	 * add sale report
	 * @param saleReport
	 */
    public void addSaleReport(SaleReport saleReport){
        this.saleReport.add(saleReport);
        balance += saleReport.getTotalRev();
        totalRev += saleReport.getTotalRev();
    }

    /**
     * update the revenue
     * @param t
     */
    public void update(Transaction t){
        totalRev +=t.getTotalPrice();
    }

	/**
	 * add list of reports
	 * @param reports - list of sale reports
	 */
    public void addAllSaleReport(ArrayList<SaleReport> reports){
        for(SaleReport s: reports) {
            addSaleReport(s);
        }
    }

	/**
	 * set purchase report
	 * @param purchaseReport
	 */
    public void setPurchaseReport(PurchaseReport purchaseReport){
        this.purchaseReport = purchaseReport;
        balance -= purchaseReport.getTotalCost();
    }

	/**
	 * get total revenue
	 * @return totalRev
	 */
    public double getTotalRev(){
        return totalRev;
    }

	/**
	 * add receipt
	 * @param receipt
	 */
    public void addReceipt(Receipt receipt){
        purchaseReport.addReceipt(receipt);
        balance -= purchaseReport.getTotalCost();
    }

	/**
	 * get all receipts
	 * @param p - permissions
	 */
    public ArrayList<Receipt> getAllReceipts(){
        return purchaseReport.getAllReceipts();
    }

	/**
	 * get receipt
	 * @param id - report id
	 */
    public Receipt getReceipt(int id){
        return purchaseReport.getReceipt(id);
    }

	/**
	 * add list of receipts
	 * @param receipts
	 */
    public void addAllReceipts(HashMap<Integer,Receipt> receipts){
        purchaseReport.addAllReceipts(receipts);
        balance -= purchaseReport.getTotalCost();
    }
}
