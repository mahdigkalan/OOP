package Classes;

import java.util.ArrayList;

public class Order {
    public static int counterIDOrder;
    public static Order LoggedInOrder;
    public ArrayList<Food> orderFoods = new ArrayList<Food>();
    public String orderID;
    public Restaurant orderedRestaurant;
    public double orderCost;
    public int deliveryTimeRemains;
    public User orderedUser;
    public double getOrderCost(){
        double totalCost = 0;
        for(int i=0;i<orderFoods.size();i++)
            totalCost += orderFoods.get(i).foodCost;
        return totalCost;
    }
}
