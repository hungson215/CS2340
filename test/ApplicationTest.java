import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.*;
import org.junit.*;
import org.omg.CORBA.TIMEOUT;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;


import static org.junit.Assert.*;


/**
 *
 * Tests Group 13's Final Project using JUnit Testing
 * Uses AssertEquals & AssertTrue to see if all the pages
 * are displaying everything correctly.
 *
 * @author hkim406
 * @version 1.01
 */
public class ApplicationTest {

    /**
     * Initializing every object for JUnit testing.
     * timeout set to 1500ms.
     */
    private static final int TIMEOUT = 1500;

    private ArrayList<Catalog> sales = new ArrayList<>();
    private ArrayList<ItemInfo> itemInfos = new ArrayList<>();
    private ArrayList<Payment> payment = new ArrayList<>();
    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Receipt> receipts = new ArrayList<>();

    private Cart cart = new Cart();

    private double totalcost;

    private FinancialReport finReport = new FinancialReport();

    private HashMap<Integer, Payment> payments = new HashMap<>();

    private String errorMsg;
    private String message;
    private String password;
    private String username;

    private User user = new User();



    @Before
    public void setup() {
        User firstUser = new User();
    }


    /**
     * Checks to see if the current user's profile is
     * showing up in the profile page.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void profilePageTest() {
        Content profile = views.html.profile.render(user);
        assertEquals("text/html", profile.contentType());

        assertTrue(profile.body().contains("Username:"));
        assertTrue(profile.body().contains("Account type:"));

    }


    /**
     * Checks to see if the checkout page displays things correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void checkoutPageTest() {
        Content chckout = views.html.checkout.render(cart, user, payment);
        assertEquals("text/html", chckout.contentType());

        assertTrue(chckout.body().contains("Checkout"));

    }

    /**
     * Checks to see if the financialReport page displays things correctly.
     *
     * Also checks the layout of the page.
     */
    @Test
    public void fncReportPageTest() {
        Content fnpage = views.html.financial_report.render(finReport);
        assertEquals("text/html", fnpage.contentType());

        assertTrue(fnpage.body().contains("Income"));
        assertTrue(fnpage.body().contains("Total Balance:"));
        assertTrue(fnpage.body().contains("Done"));
    }

    /**
     * Checks to see if the UserList page displays things correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void seeAllUserPageTest() {
        Content userPage = views.html.all_users.render(allUsers, user);
        assertEquals("text/html", userPage.contentType());

        assertTrue(userPage.body().contains("All Users"));
        assertTrue(userPage.body().contains("User List"));

    }

    /**
     * Checks to see if the Login page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void loginPageTest() {
        Content login = views.html.login.render(errorMsg, username, password);
        assertEquals("text/html", login.contentType());

        assertTrue(login.body().contains("Please Login"));
        assertTrue(login.body().contains("Sign in"));
        assertTrue(login.body().contains("Cancel"));

    }

    /**
     * Checks to see if the Register page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void registerPageTest() {
        Content register = views.html.register.render(message, username);
        assertEquals("text/html", register.contentType());

        assertTrue(register.body().contains("Please Register"));
        assertTrue(register.body().contains("Register"));
        assertTrue(register.body().contains("Cancel"));

    }

    /**
     * Checks to see if the purchaseHistory page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void prchaseHstryPageTest() {
        Content purch = views.html.purchase_history.render(
                totalcost, receipts, payments, user);
        assertEquals("text/html", purch.contentType());

        assertTrue(purch.body().contains("Purchase History"));
        assertTrue(purch.body().contains("Email Receipt"));

        assertTrue(purch.body().contains("Done"));
    }

    /**
     * Checks to see if the creating Catalog page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void createCatalogPageTest() {
        Content cat = views.html.create_catalog.render();
        assertEquals("text/html", cat.contentType());

        assertTrue(cat.body().contains("Create a catalog"));
        assertTrue(cat.body().contains("Name"));
        assertTrue(cat.body().contains("Description"));

        assertTrue(cat.body().contains("Create"));
    }

    /**
     * Checks to see if the Sales Report page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void saleReportPageTest() {
        Content saleRep = views.html.sale_report.render(sales);
        assertEquals("text/html", saleRep.contentType());

        assertTrue(saleRep.body().contains("Sale Report"));
        assertTrue(saleRep.body().contains("Description"));

        assertTrue(saleRep.body().contains("Report"));

    }

    /**
     * Checks to see if the Assigning Roles page works correctly.
     *
     * Also checks the layout of the page.
     */
    @Test(timeout = TIMEOUT)
    public void assignRolePageTest() {
        Content assign = views.html.role_assign.render(sales, allUsers);
        assertEquals("text/html", assign.contentType());

        assertTrue(assign.body().contains("Role Assignment"));
        assertTrue(assign.body().contains("Seller"));
        assertTrue(assign.body().contains("Book Keeper"));
        assertTrue(assign.body().contains("Cashier"));
        assertTrue(assign.body().contains("Clerk"));

        assertTrue(assign.body().contains("Save"));

    }

    /**
     * Checks to see if the Buying page works correctly.
     *
     * Also checks the layout of the page.
     * Notice 'Nothing to buy :(' will display when logged in
     * as the SuperUser - 'user'
     */
    @Test(timeout = TIMEOUT)
    public void buyPageTest() {
        Content buy = views.html.buy.render(itemInfos);
        assertEquals("text/html", buy.contentType());

        assertTrue(buy.body().contains("Nothing to buy :("));
    }

}
