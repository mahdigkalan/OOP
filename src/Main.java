import Classes.*;
import Functions.Functions;

import java.lang.reflect.Field;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean inputValidation = true;
        while (inputValidation) {
            String input = scanner.nextLine();
            if (input.equals("END")) {
                inputValidation = false;
                break;
            } else {
                String[] inputArray = input.split(" ");
                if (inputArray[0].equals("ADD") && (inputArray[1].equals("USER") || inputArray[1].equals("ADMIN")) && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    if (!inputArray[1].equals("ADMIN") && !inputArray[1].equals("USER")) {
                        System.out.println("Choosen Role is Invalid!");
                    } else {
                        Functions.checkPassword(inputArray[3], inputArray[1], inputArray[2]);
                    }
                } else if (inputArray[0].equals("LOGIN") && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    Functions.LogIn(inputArray[3], inputArray[1], inputArray[2]);
                } else if (input.equals("LOGOUT") && Role.loggedInRoleExistance) {
                    if (Role.loggedInRoleExistance) {
                        Role.loggedInRole = null;
                        Role.loggedInRoleExistance = false;
                        System.out.println("Logged out sucessfully!");
                    } else {
                        System.out.println("There is no LoggedIn account!");
                    }
                } else if (inputArray[0].equals("FORGET") && inputArray[1].equals("PASSWORD") && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    String Role = inputArray[2];
                    String userName = inputArray[3];
                    Functions.ForgetPassword(Role, userName);
                } else if (inputArray[0].equals("SELECT") && !inputArray[1].equals("MENU") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin) {
                    Admin admin = (Admin) Role.loggedInRole;
                    Functions.searchRestaurant(admin, inputArray[1]);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("RESTAURANT") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin) {
                    Admin admin = (Admin) Role.loggedInRole;
                    System.out.println("please enter yout restaurant name : ");
                    String restaurantName = scanner.nextLine();
                    Restaurant restaurant = new Restaurant(restaurantName);
                    restaurant.restaurantOwner = admin;
                    restaurant.restaurantID = Functions.setID("restaurant");
                    admin.adminRestaurants.add(restaurant);
                    Restaurant.allRestaurantsArrayList.add(restaurant);
                    System.out.println("restaurant created successfully!");
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("RESTAURANT") && inputArray[2].equals("LIST") && inputArray.length == 3 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin) {
                    Admin admin = (Admin) Role.loggedInRole;
                    Functions.showRestaurantList(admin);
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("FOODTYPE") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null) {
                    Functions.showFoodType();
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("FOODTYPE") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    System.out.println("Add number of foodtypes you want to add : ");
                    int numberOfFoodTypes = scanner.nextInt();
                    for (int i = 0; i < numberOfFoodTypes; i++) {
                        String foodtype = scanner.nextLine();
                        restaurant.restaurantFoodType.add(foodtype);
                    }
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("FOODTYPE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    if (restaurant.restaurantOrders.size() > 0) {
                        System.out.println("You can't edit food type when restaurant has active order!");
                    } else {
                        System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE? (enter yes or no)");
                        String answer = scanner.nextLine();
                        if (answer.toLowerCase().equals("yes")) {
                            restaurant.restaurantMenu.clear();
                            restaurant.restaurantFoodType.clear();
                            for (int i = 0; i < inputArray.length - 2; i++) {
                                restaurant.restaurantFoodType.add(inputArray[i + 2]);
                            }
                            System.out.println("food type edited succesfully , now you have to recreate your restaurant menu!");
                        } else if (answer.toLowerCase().equals("no")) {

                        } else {
                            System.out.println("invalid command");
                        }
                    }
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("MENU") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2) {
                    Functions.showMenuForAdmin();
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 5) {
                    String foodID = inputArray[2];
                    String changingParameters = inputArray[3];
                    String newValue = inputArray[4];
                    Functions.editFood(foodID, changingParameters, newValue);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null) {
                    String foodName = new String("");
                    for (int i = 2; i < inputArray.length - 1; i++) {
                        foodName += inputArray[i] + " ";
                    }
                    foodName = foodName.trim();
                    int foodCost = Integer.parseInt(inputArray[inputArray.length - 1]);
                    Functions.addFood(foodName, foodCost);
                } else if (inputArray[0].equals("DELETE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3) {
                    String foodID = inputArray[2];
                    Functions.deleteFood(foodID);
                } else if (inputArray[0].equals("DEACTIVE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3) {
                    String foodID = inputArray[2];
                    Functions.deactiveFood(foodID);
                } else if (inputArray[0].equals("ACTIVE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3) {
                    String foodID = inputArray[2];
                    Functions.activeFood(foodID);
                } else if (inputArray[0].equals("DISCOUNT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 5) {
                    String foodID = inputArray[2];
                    int discountPercent = Integer.parseInt(inputArray[3]);
                    int timestampHour = Integer.parseInt(inputArray[4]);
                    Functions.discountFood(foodID, discountPercent, timestampHour);
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("LOCATION") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    if (restaurant.restaurantLocation.trim().equals("")) {
                        System.out.println("Location doesn't accepted!");
                    } else {
                        System.out.println("Restaurant Location is : " + restaurant.restaurantLocation);
                    }
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("LOCATION") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    System.out.println("Please enter the restaurant location : ");
                    String location = scanner.nextLine();
                    restaurant.restaurantLocation = location;
                    System.out.println("Location edited successfully!");
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3) {
                    String foodID = inputArray[2];
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    boolean foodIDExistance = Functions.foodIDExistanceChecker(restaurant, foodID);
                    if (foodIDExistance) {
                        Food food = Functions.foodIDfounder(restaurant, foodID);
                        Food.selectedFoodForAdmin = food;
                    } else {
                        System.out.println("this foodID doesn't exist in this restaurant!");
                    }
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATINGS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null && inputArray.length == 2) {
                    Food food = Food.selectedFoodForAdmin;
                    System.out.println(food.foodRatedNumber + " people rate this food and rating is : " + food.getRating() + " of 5 .");
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null && inputArray.length == 2) {
                    Food food = Food.selectedFoodForAdmin;
                    if (food.foodCommentsArrayList.size() == 0) {
                        System.out.println("There is no comment yet!");
                    } else {
                        for (int i = 0; i < food.foodCommentsArrayList.size(); i++) {
                            Comment comment = food.foodCommentsArrayList.get(i);
                            System.out.println("User " + comment.commentedUser.getUserName() + " commented : " + comment.comment + " , comment ID : " + comment.commentID);
                        }
                    }
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("RESPONSE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null) {
                    String commentID = inputArray[3];
                    String response = new String("");
                    if (inputArray.length == 4) {
                        System.out.println("Please enter the message you want : ");
                        response = scanner.nextLine();
                        Functions.commentResponse(commentID, response);
                    } else {
                        for (int i = 4; i < inputArray.length; i++) {
                            response += inputArray[i];
                        }
                        Functions.commentResponse(commentID, response);
                    }
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("CART") && inputArray[2].equals("STATUS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User){
                    Functions.showCartStatus((User) Role.loggedInRole);
                } else if (inputArray[0].equals("CONFIRM") && inputArray[1].equals("ORDER") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User){
                    String orderID = inputArray[2];
                    Functions.confirmOrder((User) Role.loggedInRole, orderID);
                } else if (inputArray[0].equals("CHARGE") && inputArray[1].equals("ACCOUNT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User){
                    Functions.chargeAccount((User) Role.loggedInRole);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("ACCOUNT") && inputArray[2].equals("CHARGE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User){
                    Functions.showAccountCharge((User) Role.loggedInRole);
                } else if (inputArray[0].equals("SEARCH") && inputArray[1].equals("RESTAURANT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null) {
                    String restaurantName = inputArray[2];
                    Functions.ShowRelatedRestaurants(restaurantName);
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("RESTAURANT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null) {
                    String restaurantID = inputArray[1];
                    Functions.selectRestaurant(restaurantID);
                } else if (inputArray[0].equals("ACCESS") && inputArray[1].equals("ORDER") && inputArray[2].equals("HISTORY") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Food.selectedFoodForUser == null){
                    Functions.showOrdersHistory((User) Role.loggedInRole);
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("ORDER") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null){
                    Functions.selectOrder(inputArray[2]);
                } else if (inputArray[0].equals("SEARCH") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String foodName = inputArray[1];
                    Functions.ShowRelatedFoods(foodName);
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String foodID = inputArray[1];
                    Functions.selectFood(foodID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.showRestaurantComments(Restaurant.loggedInRestaurantForUser);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.getRestaurantComment(Restaurant.loggedInRestaurantForUser);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("COMMENT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String commentID = inputArray[2];
                    Functions.editRestaurantComment(commentID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    System.out.println("This restaurant gets the rate of \"" + Restaurant.loggedInRestaurantForUser.getRating() + "\" from the Users!");
                } else if (inputArray[0].equals("SUBMIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.getRestaurantRating();
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String ratingID = inputArray[2];
                    Functions.editRestaurantRating(ratingID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.showFoodComments(Food.selectedFoodForUser);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.getFoodComment(Food.selectedFoodForUser);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("COMMENT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    String commentID = inputArray[2];
                    Functions.editFoodComment(commentID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    System.out.println("This food gets the rate of \"" + Food.selectedFoodForUser.getRating() + "\" from the Users!");
                } else if (inputArray[0].equals("SUBMIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.getFoodRating();
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    String ratingID = inputArray[2];
                    Functions.editFoodRating(ratingID);
                } else {
                    System.out.println("invalid command");
                }
            }
        }
    }
}