package Classes;

import java.util.ArrayList;

public class Order {
    public static int counterIDOrder ;
    public ArrayList<Food> orderFoods = new ArrayList<Food>();
    public String orderID;
    public double orderCost;
    public int deliveryTimeRemains;
    public User orderedUser;
}
