package Classes;

import java.util.ArrayList;

public class Food {
    public static Food selectedFoodForAdmin ;
    public static Food selectedFoodForUser ;
    public static ArrayList<Food> allFoodsArrayList = new ArrayList<Food>() ;
    public ArrayList<Comment> foodCommentsArrayList = new ArrayList<Comment>() ;
    public ArrayList<Rating> foodRatingsArrayList = new ArrayList<Rating>() ;
    public ArrayList<Order> foodOrderHistory = new ArrayList<Order>() ;
    public static int counterIDFood ;
    public String foodName ;
    public String foodID ;
    public int foodCost ;
    public boolean possibilityOfOrdering ;
    public boolean discountActivation ;
    public boolean activeOrder ;
    public int discountValue ;
    public int discountTimeStampHour ;
    public int foodOrdered ;
    public int foodRatedNumber ;


    public Food(String foodName,int foodCost){
        this.foodName = foodName ;
        this.foodCost = foodCost ;
    }

    public double getRating(){
        double sum = 0;
        for (int i = 0; i < this.foodRatingsArrayList.size(); i++)
            sum += this.foodRatingsArrayList.get(i).rating;
        return (sum / this.foodRatingsArrayList.size());
    }
}
