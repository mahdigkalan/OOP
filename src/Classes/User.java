package Classes;

import java.util.ArrayList;

public class User extends Role {
    public static ArrayList<User> userArrayList = new ArrayList<User>();
    public ArrayList<Order> userOrders = new ArrayList<Order>();
    public Cart userCart = new Cart();
    private String userName;
    private String password;
    private double accountCharge = 0;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAccountCharge() {
        return accountCharge;
    }
    public void setAccountCharge(double accountCharge) {
        this.accountCharge = accountCharge;
    }
}
