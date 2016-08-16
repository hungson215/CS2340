package controllers;

import com.google.inject.Inject;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.*;
import scala.Console;
import views.html.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private FormFactory formFactory;
    private HashMap<Integer,User> userlist;
    private User currentUser;
	private HashMap<Integer,ItemInfo> allItems;
    private HashMap<Integer,Catalog> allSales;
	private HashMap<Integer,Donation> donationlist;
	private int counter;
    /**
     * Creates instance of HomeController
     * adds user into ArrayList
     */
    @Inject
    public HomeController(FormFactory formFactory){
        this.formFactory = formFactory;
        userlist = new HashMap<>();
        currentUser = null;
        allSales = new HashMap<>();
        allItems = new HashMap<>();
		donationlist = new HashMap<>();

        //Init users
        List<User> users = User.userdb.all();
        //Create default_user account if there's any registered user
        if(users == null || users.isEmpty()) {
            User default_user = new User();
            default_user.setUsername("user");
            default_user.setPassword("pass");
            default_user.setEmail("user@gatech.edu");
            default_user.addRole(User.Role.SUPER_USER);

            Catalog default_catalog = new Catalog();
            default_catalog.setName("Catalog 1");
            default_catalog.setDes("Default Catalog");

            userlist.put(default_user.getId(),default_user);
            default_user.saveToDB();
            default_user.createSale(default_catalog);
            allSales.put(default_catalog.getId(),default_catalog);

            ItemInfo default_item;
            default_item = new ItemInfo();
            default_item.setName("Iphone S5");
            default_item.setImg_url("http://i.ebayimg.com/images/g/UHcAAOSwPc9Wx5Nb/s-l1600.jpg");
            default_item.setPrice(300);
            default_item.setDes("A used iphone");
            default_item.setInventory(10);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Galaxy Edge S7");
            default_item.setImg_url("none");
            default_item.setPrice(300);
            default_item.setDes("Barely used Galaxy Phone");
            default_item.setInventory(2);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Iphone 6S");
            default_item.setImg_url("http://i.ebayimg.com/images/g/fxIAAOSwubRXIPsr/s-l500.jpg");
            default_item.setPrice(499);
            default_item.setDes("Fully functional, clean ESN. Average condition.");
            default_item.setInventory(5);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Ipad Air");
            default_item.setImg_url("http://i.ebayimg.com/00/s/MTQwOFgxNjAw/z/BxUAAOSwI2dXFjx6/$_12.JPG");
            default_item.setPrice(300);
            default_item.setDes("brand-new, unused, unopened, undamaged item");
            default_item.setInventory(10);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Samsung galaxy edge");
            default_item.setImg_url("http://i.ebayimg.com/images/g/tmUAAOSwDuJW0AAn/s-l500.jpg");
            default_item.setPrice(699.99);
            default_item.setDes("New Samsung Galaxy");
            default_item.setInventory(10);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("NEW Samsung Galaxy Gear Watch");
            default_item.setImg_url("http://i.ebayimg.com/images/g/1JkAAOSwe7BWvhKx/s-l500.jpg");
            default_item.setPrice(220);
            default_item.setDes("A brand-new, unused, unopened, undamaged item in its original packaging");
            default_item.setInventory(4);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Samsung Galaxy Note");
            default_item.setImg_url("http://i.ebayimg.com/images/g/VbUAAOSw7ThUk4Mf/s-l500.jpg");
            default_item.setPrice(300);
            default_item.setDes("A brand-new, unused, unopened, undamaged item in its original packaging");
            default_item.setInventory(5);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);

            default_item = new ItemInfo();
            default_item.setName("Apple Watch Sport");
            default_item.setImg_url("http://i.ebayimg.com/images/g/610AAOSwoudW3vYv/s-l500.jpg");
            default_item.setPrice(250);
            default_item.setDes("Refurbished (Like New Condition) w/ 90 Day Warranty");
            default_item.setInventory(10);
            default_item.setSeller(default_user.getUsername());
            default_catalog.addItem(default_item);
            allItems.put(default_item.getId(),default_item);
        } else {

            //Get all items from the database group them by SaleId and CartId
            List<ItemInfo> dbItems =  ItemInfo.itemdb.all();
            HashMap<Integer,ArrayList<ItemInfo>> groupItemBySaleId = new HashMap<>();
            HashMap<Integer,ArrayList<ItemInfo>> groupItemByCartId = new HashMap<>();

            for(ItemInfo i : dbItems){
                allItems.put(i.getId(),i);
                if(groupItemBySaleId.containsKey(i.getCatalogId())){
                    groupItemBySaleId.get(i.getCatalogId()).add(i);
                }else{
                    ArrayList<ItemInfo> groupBySale = new ArrayList<>();
                    groupBySale.add(i);
                    groupItemBySaleId.put(i.getCatalogId(),groupBySale);
                }
                if(groupItemByCartId.containsKey(i.getCartid())){
                    groupItemByCartId.get(i.getCartid()).add(i);
                } else {
                    ArrayList<ItemInfo> groupByCart = new ArrayList<>();
                    groupByCart.add(i);
                    groupItemByCartId.put(i.getCartid(),groupByCart);
                }
            }
            //Init Permissions using catalogId as the key
            List<Permission> dbPermissions = Permission.pdb.all();
            HashMap<Integer,Permission> permissions = new HashMap<>();
            for(Permission p : dbPermissions){
                permissions.put(p.getId(),p);
            }
            //Init Transaction
            List<Transaction> dbTrans = Transaction.transdb.all();
            HashMap<Integer,ArrayList<Transaction>> groupByReceiptId = new HashMap<>();
            HashMap<Integer,ArrayList<Transaction>> groupByCatalogId = new HashMap<>();
            for(Transaction t: dbTrans){
                if(groupByCatalogId.containsKey(t.getCatalogId())){
                    groupByCatalogId.get(t.getCatalogId()).add(t);
                } else {
                    ArrayList<Transaction> tran = new ArrayList<>();
                    tran.add(t);
                    groupByCatalogId.put(t.getCatalogId(),tran);
                }
                if(groupByReceiptId.containsKey(t.getReceiptId())){
                    groupByReceiptId.get(t.getReceiptId()).add(t);
                } else {
                    ArrayList<Transaction> tran = new ArrayList<>();
                    tran.add(t);
                    groupByReceiptId.put(t.getReceiptId(),tran);
                }
            }
            //Init receipts
            List<Receipt> dbReceipts = Receipt.receiptdb.all();
            HashMap<Integer,ArrayList<Receipt>> receipts = new HashMap<>();
            for(Receipt r: dbReceipts){
                if(groupByReceiptId.containsKey(r.getId())){
                    r.setTrans(groupByReceiptId.get(r.getId()));
                }
                if(receipts.containsKey(r.getUserId())){
                    receipts.get(r.getUserId()).add(r);
                } else {
                    ArrayList<Receipt> receipt = new ArrayList<>();
                    receipt.add(r);
                    receipts.put(r.getUserId(),receipt);
                }
            }
            //Init Catalog
            List<Catalog> dbSales =Catalog.catadb.all();
            HashMap<Integer,ArrayList<Catalog>> catalogs = new HashMap<>();
            for(Catalog c: dbSales){
                c.getSaleReport().setSaleName(c.getName());
                allSales.put(c.getId(),c);
                //Load transactions to each catalog
                if(groupByCatalogId.containsKey(c.getId())){
                    c.setTrans(groupByCatalogId.get(c.getId()));
                }
                //Load Items to each catalog
                if(groupItemBySaleId.containsKey(c.getId())){
                    ArrayList<ItemInfo> items = groupItemBySaleId.get(c.getId());
                    HashMap<Integer,ItemInfo> itemsHashMap = new HashMap<>();
                    for(ItemInfo i : items){
                        itemsHashMap.put(i.getId(),i);
                    }
                    c.setItems(itemsHashMap);
                }
                //Load permission to each catalog
                if(permissions.containsKey(c.getId())) {
                    c.setPermisson(permissions.get(c.getId()));
                }
                //Put all the catalog into an hashmap
                if(catalogs.containsKey(c.getUserId())){
                    catalogs.get(c.getUserId()).add(c);
                } else {
                    ArrayList<Catalog> catalog = new ArrayList<>();
                    catalog.add(c);
                    catalogs.put(c.getUserId(),catalog);
                }
            }
            //Init Payment
            List<Payment> dbpayments = Payment.paymentdb.all();
            HashMap<Integer,ArrayList<Payment>> allPayments = new HashMap<>();
            for(Payment p: dbpayments){
                if(allPayments.containsKey(p.getUserId())){
                    allPayments.get(p.getUserId()).add(p);
                } else {
                    ArrayList<Payment> payment =new ArrayList<>();
                    payment.add(p);
                    allPayments.put(p.getUserId(),payment);
                }
            }

            //Init Cart
            List<Cart> dbCarts = Cart.cartdb.all();
            HashMap<Integer,Cart> carts = new HashMap<>();
            for(Cart c: dbCarts){
                //Load all items to the cart
                if(groupItemByCartId.containsKey(c.getId())){
                    ArrayList<ItemInfo> items = groupItemByCartId.get(c.getId());
                    HashMap<Integer,ItemInfo> itemsHashMap = new HashMap<>();
                    for(ItemInfo i: items){
                        itemsHashMap.put(i.getId(),i);
                    }
                    c.setItems(itemsHashMap);
                }
                carts.put(c.getId(),c);
            }
            //Init users
            for(User u : users) {
                //Load catalogs to the user
                if (catalogs.containsKey(u.getId())) {
                    HashMap<Integer, Catalog> sales = new HashMap<>();
                    for (Catalog c : catalogs.get(u.getId())) {
                        sales.put(c.getId(), c);
                        u.setSaleEvent(sales);
                    }
                }
                //Load cart to the user
                if (carts.containsKey(u.getId())) {
                    u.setCart(carts.get(u.getId()));
                }
                //Load payment to the user
                if (allPayments.containsKey(u.getId())) {
                    HashMap<Integer, Payment> payment = new HashMap<>();
                    for (Payment p : allPayments.get(u.getId()))
                        payment.put(p.getId(), p);
                    u.setPayments(payment);
                }
                //Load Receipts to the user
                if (receipts.containsKey(u.getId())) {
                    HashMap<Integer, Receipt> receiptHashMap = new HashMap<>();
                    for (Receipt r : receipts.get(u.getId()))
                        receiptHashMap.put(r.getId(), r);
                    u.addReceipts(receiptHashMap);
                }
                //Load permission to the user
                HashMap<User.Role, ArrayList<Catalog>> userroles = new HashMap<>();
                ArrayList<Catalog> bookkeeper = new ArrayList<>();
                ArrayList<Catalog> seller = new ArrayList<>();
                ArrayList<Catalog> clerk = new ArrayList<>();
                ArrayList<Catalog> cashier = new ArrayList<>();
                ArrayList<Catalog> saleList = new ArrayList<>(allSales.values());
                for (Catalog c : saleList) {
                    if (c.getPermissions().getBookKeeper() == u.getId()) {
                        bookkeeper.add(c);
                    }
                    if (c.getPermissions().getSeller() == u.getId()) {
                        seller.add(c);
                    }
                    if (c.getPermissions().getCashier() == u.getId()) {
                        cashier.add(c);
                    }
                    if (c.getPermissions().getClerk() == u.getId()) {
                        clerk.add(c);
                    }
                }
                if(bookkeeper.size() > 0)
                    userroles.put(User.Role.BOOK_KEEPER, bookkeeper);
                if(cashier.size()>0)
                    userroles.put(User.Role.CASHIER, cashier);
                if(seller.size() > 0)
                    userroles.put(User.Role.SELLER, seller);
                if(clerk.size() > 0)
                    userroles.put(User.Role.CLERK, clerk);
                u.setSaleRoles(userroles);
                userlist.put(u.getId(),u);
            }
        }
    }
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        DynamicForm request = formFactory.form().bindFromRequest();
        if(request.get("login") != null){
            return ok(login.render("","",""));
        } else if(request.get("register") != null) {
            return ok(register.render("",""));
        } else
            return ok(index.render());
    }

    /**
     * @return renders login page
     * displays Wrong username or password
     * if user does not exist or password is incorrect
     */
	 public Result login(){
 	 		Login user = formFactory.form(Login.class).bindFromRequest().get();
 			List<User> users = new ArrayList<>(userlist.values());
 			for(User e: users){
 	 			if (e.getUsername().equals(user.getUsername()) && e.getPassword().equals(user.getPassword())) {
                    if(e.isLocked())
                        return ok(login.render("Your account is LOCKED!", user.getUsername(), user.getPassword()));
 	 				counter = 0;
 	 				currentUser = e;
 	 				return show("main");
 	 			} else if (e.getUsername().equals(user.getUsername()) && e.getPassword().equals(user.getPassword())
 	 					&& e.isLocked()) {
 	 				return ok(login.render("Your account has been locked due to too many failed attempts." +
 	 						" Contact an administrator", user.getUsername(), user.getPassword()));
 	 			}
 	 			if (e.getUsername().equals(user.getUsername()) && !e.getPassword().equals(user.getPassword())) {
 	 				if (counter >= 3) {
 	 					e.setLock(true);
 	 					return ok(login.render("Too many failed attempts!! Your account is locked.",
 	 							user.getUsername(), user.getPassword()));
 	 				} else {
 	 					counter++;
 	 					return ok(login.render("Wrong password!!", user.getUsername(), user.getPassword()));
 	 				}
 	 			}
 	 		}
 	 		return ok(login.render("Wrong username!!", user.getUsername(), user.getPassword()));
 	 	}

    /**
     * Creates user
     * @return renders to register page
     */
    public Result register(){
        Register reg = formFactory.form(Register.class).bindFromRequest().get();
        if(!reg.getPassword().equals(reg.getConfirmPassword())){
            return ok(register.render("Please reconfirm your password",""));
        }
        List<User> users = new ArrayList<>(userlist.values());
        for(User e: users){
            if(e.getUsername().equals(reg.getUsername()))
                return ok(register.render("Username Already Exists",reg.getUsername()));
        }
        User user = new User();
        user.setUsername(reg.getUsername());
        user.setPassword(reg.getPassword());
        user.setEmail(reg.getEmail());
        user.saveToDB();
        userlist.put(user.getId(),user);
        return ok(index.render());
    }

    /**
     * @return to homepage when logged out
     */
    public Result logout(){
        currentUser = null;
        return index();
    }

    /**
     *
     * @return save profile and render profile page with updated info
     */
    public Result save_profile() {
        Profile profile = formFactory.form(Profile.class).bindFromRequest().get();
        List<User> users = new ArrayList<>(userlist.values());
        for (User e : users)
            if (e.getUsername().equals(profile.getUsername())) {
                e.setAddress(profile.getAddress());
                e.setEmail(profile.getEmail());
                e.setPhone(profile.getPhone());
                e.save();
                return show("profile");
            }
        return internalServerError("");
    }

    /**
     * Show request page
     * @param page
     * @return Result
     */
    public Result show(String page)
    {
        switch (page) {
            case "checkout":
                return ok(checkout.render(currentUser.getCart(),currentUser,new ArrayList<>(currentUser.getPayments().values())));
            case "home":
                if(currentUser.isSuperUser())
                    return ok(admin.render(new ArrayList<>(userlist.values())));
                else
                    return ok(home.render());
            case "buy":
                ArrayList<ItemInfo> availItems = new ArrayList<>();
                ArrayList<Catalog> allCatalogs = new ArrayList<>( allSales.values());
                for(Catalog c: allCatalogs){
                    if(!c.isClosed() && c.getUserId() != currentUser.getId()){
                        availItems.addAll(c.getItems());
                    }
                }
                return ok(buy.render(availItems));
            case "financial_report":
                return ok(financial_report.render(currentUser.getReport()));
            case "purchase_history":
                PurchaseReport r = currentUser.getReport().getPurchaseReport();
                return ok(purchase_history.render(r.getTotalCost(),r.getAllReceipts(),currentUser.getPayments(),currentUser));
            case "close_sale":
                return ok(close_sale.render(currentUser.getSales()));
            case "cart":
                return ok(views.html.cart.render(currentUser.getCart()));
            case "create_catalog":
                return ok(create_catalog.render());
            case "main":
                return ok(main.render(currentUser, currentUser.getCart()));
            case "profile":
                return ok(profile.render(currentUser));
            case "sale_report":
                return ok(sale_report.render(currentUser.getSales()));
			case "donate":
				return ok(donate.render());
            default:
                return notFound("Can't load the catalog");
        }
    }
    /**
     * Process users' privilege data request
     */
    public Result savePrivilege(){
        DynamicForm request = formFactory.form().bindFromRequest();
        List<User> users = new ArrayList<>(userlist.values());
        for(User user: users) {
            if(request.get("islocked"+user.getId())!= null)
                user.lock();
            else
                user.unlock();
            if(request.get("super-user" + user.getId()) != null)
                user.addRole(User.Role.SUPER_USER);
            else
                user.removeRole(User.Role.SUPER_USER);
            if(request.get("sell-admin" + user.getId()) != null)
                user.addRole(User.Role.SELL_ADMIN);
            else
                user.removeRole(User.Role.SELL_ADMIN);
            if(request.get("book-keeper" + user.getId()) != null)
                user.addRole(User.Role.BOOK_KEEPER);
            else
                user.removeRole(User.Role.BOOK_KEEPER);
            if(request.get("clerk" + user.getId()) != null)
                user.addRole(User.Role.CLERK);
            else
                user.removeRole(User.Role.CLERK);
            if(request.get("cashier" + user.getId()) != null)
                user.addRole(User.Role.CASHIER);
            else
                user.removeRole(User.Role.CASHIER);
            if(request.get("seller" + user.getId()) != null)
                user.addRole(User.Role.SELLER);
            else
                user.removeRole(User.Role.SELLER);
			if(request.get("login-admin" + user.getId()) != null)
	            user.addRole(User.Role.LOGIN_ADMIN);
	        else
	            user.removeRole(User.Role.LOGIN_ADMIN);
            user.save();
        }
        return ok();
    }


    /************************************************
     * Add new item to the selected catalog handlers*
     ************************************************/

    public Result addItemPage(int catalogId){
        return ok(sale.render(catalogId));
    }
	/**
	 *
	 * adds new item
	 */
	public Result addItem(int cataid){
        ItemInfo newItem = formFactory.form(ItemInfo.class).bindFromRequest().get();
		currentUser.addToCatalog(cataid,newItem);
        allItems.put(newItem.getId(),newItem);
        return ok();
    }
    /******************End handlers*******************/



    /**************************************************
     * Update Item's info handlers                    *
     **************************************************/
    public Result updateItemPage(int itemid){
        return ok(item.render(allItems.get(itemid)));
    }

	/**
	 * update items
	 * @return page
	 */
    public Result updateItem(int itemid){
        ItemInfo editItem = formFactory.form(ItemInfo.class).bindFromRequest().get();
        Console.println(editItem.getDes());
        ItemInfo i = allItems.get(itemid);
        i.setName(editItem.getName());
        i.setPrice(editItem.getPrice());
        i.setImg_url(editItem.getImg_url());
        i.setDes(editItem.getDes());
		i.setInventory(editItem.getInventory());
		double roundOff = Math.round(editItem.getPrice() * 100.0) / 100.0;
		i.setPrice(roundOff);
        i.save();
        return ok();
    }
    /******************End handler**********************/

    /***************************************************
     * Make a purchase                                 *
     ***************************************************/
    public Result purchase(){
        DynamicForm request = formFactory.form().bindFromRequest();
        Payment p = new Payment();
        if(request.get("paymentchoice") != null) {
            p = currentUser.getPayments().get(Integer.parseInt(request.get("paymentchoice")));
        } else {
            if (request.get("payment").equals("Credit")) {
                if (request.get("cardtype").equals("Visa")) {
                    p.setType(Payment.Card.Visa);
                } else {
                    p.setType(Payment.Card.Master);
                }
                p.setCardName(request.get("cardname"));
                p.setCardNum(request.get("cardnum"));
                p.setCvv(request.get("cardcvv"));
                p.setExpMonth(request.get("expmonth"));
                p.setExpYear(request.get("expyear"));
                currentUser.addPayment(p);
            } else if (request.get("payment").equals("Check")) {
                p.setType(Payment.Card.Check);
                p.setRoutingNum(request.get("routing"));
                p.setAccountNum(request.get("account"));
                currentUser.addPayment(p);
            } else {
                p.setType(Payment.Card.Cash);
                currentUser.addPayment(p);
            }
            currentUser.addPayment(p);
        }

        Receipt r = currentUser.purchase(p);
        ArrayList<Transaction> trans = r.getTrans();
        for(Transaction t : trans){
            allSales.get(t.getCatalogId()).addTrans(t);
        }
        return ok(Integer.toString(r.getId()));
    }
    /*
     *Search for items that match the search query
     * @param int cataid
     * @param String query
     */
    public Result search(String page, int cataid, String query){
        switch(page){
            case "sell":
                return ok(views.html.catalog.render(currentUser.search(cataid,query),currentUser.getSale(cataid),false));
            default:
                return ok(views.html.catalog.render(search(new ArrayList<ItemInfo>(allItems.values()),query),currentUser.getSale(cataid),false));
        }
    }

	/*
     *Search for items that match the search query
     * @param items - list of items
     * @param String query
     */
    public ArrayList<ItemInfo> search(ArrayList<ItemInfo> items, String query){
        ArrayList<ItemInfo> res = new ArrayList<>();
        for(ItemInfo i : items){
            if(i.getName().toLowerCase().contains(query.toLowerCase()) || i.getName().toLowerCase().contains(query.toLowerCase()))
                res.add(i);
        }
        return res;
    }

	/*
	 * Search for items that match the search query
	 * @param String query
	 * @return Result buy page
	 */
	public Result searchBuy(String query){
		ArrayList<ItemInfo> itemsForSale = new ArrayList<>();
		for(User u:userlist.values()){
			if(u.getId() != currentUser.getId()) {
				for(Catalog cata: u.getSales()) {
					if(cata!=null) {
						for(ItemInfo i: cata.getItems()) {
							if(i!=null){
								itemsForSale.add(i);
							}
						}
					}
				}
			}
		}

		ArrayList<ItemInfo> searchList = new ArrayList<>();

	    for(ItemInfo item: itemsForSale) {
			String idString = "" + item.getId();
	        if(item.getName().toLowerCase().contains(query.toLowerCase()) || item.getDes().toLowerCase().contains(query.toLowerCase()) || idString.equals(query)) {
	            searchList.add(item);
	        }
	    }
	    return ok(views.html.buy.render(searchList));
	}

    /**
     *Shows the receipt after checkout
     * @return Result
     */
    public Result showReceipt(int receiptId) {
        Receipt receipt = currentUser.getReceipt(receiptId);
        return ok(views.html.receipt.render(receipt,receipt.getTrans(),allItems,currentUser));
    }

    /**
     *Updates quantity of item in cart
     * @param int Id  Item id
     * @return Result
     */
    public Result updateCart(int id) {
        Cart update = formFactory.form(Cart.class).bindFromRequest().get();
        return ok(views.html.cart.render(currentUser.getCart()));
    }

	/**
	 * Add item to the shopping cart
	 * @return to buy page
	 */
	public Result addToCart(int itemid) {
        ItemInfo i = allItems.get(itemid);
        currentUser.addToCart(i);
        return ok();
	}
	/**
     * Shows list of all users
     * @return to page of all users
     */
    public Result showUsers() {
        return ok(views.html.all_users.render(new ArrayList<User>(userlist.values()), currentUser));
    }

	/**
	 * Shows catalog
	 * @return catalog page
	 */
    public Result showCatalog(int id){
        if(currentUser.getSale(id) == null){
            return ok(catalog.render(allSales.get(id).getItems(),allSales.get(id),false));
        }
        return ok(catalog.render(currentUser.getItems(id),currentUser.getSale(id),false));
    }
    /**
     *Create a catalog or a sale
     * @return Result
     */
    public Result createCatalog() {
        Catalog cata = formFactory.form(Catalog.class).bindFromRequest().get();
        currentUser.createSale(cata);
        allSales.put(cata.getId(),cata);
        return show("main");
    }

    /**
     * show an item's details
     * @param id
     * @return
     */
    public Result showItem(int id){
        return ok(views.html.item.render(allItems.get(id)));
    }

    /**
     * Close a sale
     * @param id
     * @return
     */
    public Result closeSale(int id){
        currentUser.getSale(id).close();
        Catalog c = allSales.get(id);
        for(ItemInfo i : c.getItems()){
            allItems.remove(i.getId());
        }
        allSales.remove(id);
        return ok(unsold.render(c.getItems(),currentUser));
    }

    /**
     * Print item's tag
     * @param id
     * @return
     */
    public Result printTag(int id){
        if(currentUser.getSale(id) != null)
            return ok(print_tag.render(currentUser,currentUser.getSale(id),currentUser.getSale(id).getItems()));
        else {
            User u = userlist.get(allSales.get(id).getUserId());
            return ok(print_tag.render(u,u.getSale(id),u.getSale(id).getItems()));
        }
    }

	/**
	 * Shows role assignment
	 * @return role assignment page
	 */
    public Result showRoleAssign(){
        ArrayList<User> users = new ArrayList<>(userlist.values());
        users.remove(currentUser);
        return ok(role_assign.render(currentUser.getSales(),users));
    }

    /**
     * Save each sale's role assignment
     * @return
     */
    public Result RoleAssign(){
        DynamicForm request = formFactory.form().bindFromRequest();
        for(Catalog c : currentUser.getSales()){
            int sellerid = Integer.parseInt(request.get("seller-role-" + c.getId()));
            int bookkeeperid = Integer.parseInt(request.get("bookkeeper-role-" + c.getId()));
            int cashierid = Integer.parseInt(request.get("cashier-role-" + c.getId()));
            int clerkid = Integer.parseInt(request.get("clerk-role-" + c.getId()));
            if( sellerid != -1){
                int userid = c.getPermissions().getBookKeeper();
                if(userid != -1){
                    userlist.get(userid).getSales(User.Role.SELLER).remove(c);
                }
                c.permission(User.Role.SELLER, sellerid);
                userlist.get(sellerid).addRole(c,User.Role.SELLER);
            }
            if(bookkeeperid != -1){
                int userid = c.getPermissions().getSeller();
                if(userid != -1){
                    userlist.get(userid).getSales(User.Role.BOOK_KEEPER).remove(c);
                }
                c.permission(User.Role.BOOK_KEEPER, bookkeeperid);
                userlist.get(bookkeeperid).addRole(c,User.Role.BOOK_KEEPER);
            }
            if(cashierid != -1){
                int userid = c.getPermissions().getCashier();
                if(userid != -1){
                    userlist.get(userid).getSales(User.Role.CASHIER).remove(c);
                }
                c.permission(User.Role.CASHIER, cashierid);
                userlist.get(sellerid).addRole(c,User.Role.CASHIER);
            }
            if(clerkid != -1){
                int userid = c.getPermissions().getClerk();
                if(userid != -1){
                    userlist.get(userid).getSales(User.Role.CLERK).remove(c);
                }
                c.permission(User.Role.CLERK, clerkid);
                userlist.get(clerkid).addRole(c,User.Role.CLERK);
            }
        }
        return ok();
    }

	/**
	 * Shows sale report details
	 * @return report page
	 */
    public Result showSaleReportDetails(int id){
        if(currentUser.getSale(id)!=null) {
            SaleReport report = currentUser.getSale(id).getSaleReport();
            return ok(sale_report_detail.render(report.getTotalRev(), report.getTrans(), currentUser.getSale(id)));
        } else{
            SaleReport report = allSales.get(id).getSaleReport();
            return ok(sale_report_detail.render(report.getTotalRev(), report.getTrans(), allSales.get(id)));
        }
    }

    /**
     * show all seller's sale reports
     * @return
     */
    public Result showSellerSaleReport(){
        return ok(sale_report.render(currentUser.getSales(User.Role.SELLER)));
    }

    /**
     * show financial report for all seller's sales
     * @return
     */
    public Result showSellerFinancialReport(){
        return ok(seller_financial_report.render(userlist,currentUser.getSales(User.Role.SELLER)));
    }

    /**
     * show financial report for a user
     * @param userid
     * @return
     */
    public Result showUserFinancialReport(int userid){
        return ok(financial_report.render(userlist.get(userid).getReport()));
    }

    /**
     * Show items in a sale without option to modify
     * @param id
     * @return
     */
    public Result clerkViewCatalog(int id){
        return ok(catalog.render(allSales.get(id).getItems(),allSales.get(id),true));
    }

    /**
     * show the sale report for clerk role
     * @return
     */
    public Result showClerkSaleReport(){
        return ok(sale_report.render(currentUser.getSales(User.Role.CLERK)));
    }

    /**
     * show all unsold items of a sale
     * @param saleId
     * @return
     */
    public Result showUnsoldItems(int saleId){
        Catalog c = allSales.get(saleId);
        User u = userlist.get(c.getUserId());
        return ok(unsold.render(c.getItems(),u));
    }

	public Result addDonation() {
		Donation newItem = formFactory.form(Donation.class).bindFromRequest().get();
		donationlist.put(donationlist.size() + 1, newItem);
		return ok("/main/letter");
	}

	public Result donate() {
		return ok(donate.render());
	}

	public Result letter() {
		return ok(letter.render());
	}
}
