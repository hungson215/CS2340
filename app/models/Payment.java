package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by David Nguyen on 7/10/2016.
 */
@Entity
public class Payment extends Model {

    public static enum Card {NONE,Visa,Master,Check,Cash}

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;
    private Card type;
    private String cvv;
    private String cardNum;
    private String expMonth;
    private String expYear;
    private String cardName;
    private String accountNum;
    private String routingNum;

    /** Database connector **/
    public static Model.Finder<Integer,Payment> paymentdb = new Model.Finder<>(Payment.class);

	/**
	 * default constructor
	 */
    public Payment(){
        type = Card.NONE;
        cvv = "";
        cardName="";
        cardNum="";
        expMonth="";
        expMonth="";
        accountNum="";
        routingNum="";
        userid=-1;
    }

	/**
	 * @return id - payment id
	 */
    public int getId() {
		return id;
	}

	/**
	 * @param userid - user's id
	 */
    public void setUserId(int userid) {
        this.userid = userid;
    }

	/**
	 * @return userid - user's id
	 */
    public int getUserId() {
		return userid;
	}

	/**
	 * @param type - card type
	 */
    public void setType(Card type){
        this.type = type;
    }

	/**
	 * @return type - card type
	 */
    public Card getType() {
		return type;
	}

	/**
	 * @param cvv - card's cvv
	 */
	public void setCvv(String ccv) {
		this.cvv = ccv;
	}

	/**
	 * @return cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cardNum - card's number
	 */
	public void setCardNum(String CardNum){
        if(CardNum.trim().length() == 16) {
            this.cardNum = CardNum;
        }
    }

	/**
	 * @return cardNum - card's number
	 */
    public String getCardNum() {
		return cardNum;
	}

	/**
	 * @param expMonth - expiration month
	 */
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	/**
	 * @param expYear - expiration year
	 */
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	/**
	 * @return expMonth - expiration month
	 */
	public String getExpMonth() {
		return expMonth;
	}

	/**
	 * @return expYear - expiration year
	 */
	public String getExpYear() {
		return expYear;
	}

	/**
	 * @return expiration date month/year
	 */
	public String getExpDate() {
		return (expMonth+"/"+expYear);
	}

	/**
	 * @param cardName - card's name
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * @return cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * @param accountNum - account number
	 */
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	/**
	 * @return accountNum - account number
	 */
	public String getAccountNum() {
		return accountNum;
	}

	/**
	 * @param routingNum - routing number
	 */
	public void setRoutingNum(String routingNum) {
		this.routingNum=routingNum;
	}

	/**
	 * @return routingNum - routing number
	 */
	public String getRoutingNum() {
		return routingNum;
	}

	/**
	 * add a credit card
	 * @param type - card type
	 * @param cardName - card's name
	 * @param cardNum - card's number
	 * @param cvv - card's cvv
	 * @param expMonth - expiration month
	 * @param expYear - expiration year
	 */
	public void addCredit(Card type, String cardName,String cardNum,String cvv,String expMonth,String expYear){
        this.type = type;
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

	/**
	 * add a check
	 * @param routingNum - routing number
	 * @param accountNum - account number
	 */
	public void addCheck(String routingNum, String accountNum){
        this.type = Card.Check;
        this.routingNum = routingNum;
        this.accountNum = accountNum;
    }
}
