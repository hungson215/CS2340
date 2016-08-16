package models;

import java.util.ArrayList;

/**
 * Manage all transactions of a catalog
 */
public class SaleReport {
    private ArrayList<Transaction> trans;
    private double totalRev;
    private String sale;
    private FinancialReport report;


    /**
     * Set financial report
     */
    public void setReport(FinancialReport report){
        this.report = report;
    }
    /**
     * Get financial report
     */
    public FinancialReport getReport(){
        return report;
    }
    /**
	 * default constructor
	 */
    public SaleReport(){
        trans = new ArrayList<Transaction>();
        totalRev = 0.00;
        sale ="";
    }

	/**
	 * set sale name
	 * @param sale - sale name
	 */
    public void setSaleName(String sale){
        this.sale = sale;
    }

	/**
	 * get sale name
	 * @return sale - sale name
	 */
    public String getSaleName(){
        return sale;
    }

	/**
	 * add a transaction to the report
	 * @param tran - transaction
	 */
    public void addTrans(Transaction tran){
        trans.add(tran);
        if(report != null)
            report.update(tran);
        totalRev +=tran.getTotalPrice();
    }

	/**
	 * set a transaction to the report
	 * @param tran - transaction
	 */
    public void setTrans(ArrayList<Transaction> trans){
        this.trans = trans;
        for(Transaction t: trans){
            totalRev += t.getTotalPrice();
        }
    }

	/**
	 * get transaction
	 * @return trans - transaction
	 */
    public ArrayList<Transaction> getTrans(){
        return trans;
    }

	/**
	 * get total revenue
	 * @return totalRev - total revenue
	 */
    public double getTotalRev(){
        return totalRev;
    }
}
