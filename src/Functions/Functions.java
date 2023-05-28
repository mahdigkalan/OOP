package Functions;
import Classes.*;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Functions {
    public static Scanner scanner = new Scanner(System.in) ;
    public static void checkPassword(String password,String Role,String userName){
        boolean numberic = false ;
        boolean capitalLetter = false ;
        boolean smallLetter = false ;
        int passwordLength = password.length() ;
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 57 && asciiCode > 48){
                numberic = true ;
            }
        }
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 90 && asciiCode > 65){
                capitalLetter = true ;
            }
        }
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 122 && asciiCode > 97){
                smallLetter = true ;
            }
        }
        if (!numberic){
            System.out.println("Passowrd must have at least one number!");
        }
        if (!capitalLetter){
            System.out.println("Password must have at least one capital letter!");
        }
        if (!smallLetter){
            System.out.println("Password must have at least one small letter!");
        }
        if (passwordLength < 8){
            System.out.println("Password must have at least 8 charecter!");
        }
        if (numberic && capitalLetter && smallLetter && passwordLength >= 8){
            if (Role.equals("ADMIN")){
                if (isUserNameUnique(userName,Role)){
                    Admin admin = new Admin(userName,password) ;
                    Admin.adminArrayList.add(admin) ;
                    System.out.println("Account created successfully");
                    makeSecurityQuestion(admin);
                }else {
                    System.out.println("An Admin exist with this username!");
                }
            }else if (Role.equals("USER")){
                if (isUserNameUnique(userName,Role)){
                    User user = new User(userName,password) ;
                    User.userArrayList.add(user) ;
                    System.out.println("Account created successfully");
                    makeSecurityQuestion(user);
                }else {
                    System.out.println("A User exist with this username!");
                }
            }
        }
    }
    public static void LogIn(String password,String Role,String userName){
        if (Role.equals("ADMIN")){
            if (isUserNameUnique(userName,Role)){
                System.out.println("This username does not exist!");
            }else {
                Admin admin = Admin.adminArrayList.get(findRoleIndex(userName,Role)) ;
                if (admin.getPassword().equals(password)){
                    Classes.Role.loggedInRole = admin ;
                    Classes.Role.loggedInRoleExistance = true ;
                    System.out.println("you are logged in as ADMIN!");
                    if (admin.adminRestaurants.size() == 1){
                        Restaurant.loggedInRestaurantForAdmin = admin.adminRestaurants.get(0) ;
                    }else {
                        showRestaurantList(admin);
                    }
                }else {
                    System.out.println("password is incorrect!");
                }
            }
        }else if (Role.equals("USER")){
            if (isUserNameUnique(userName,Role)){
                System.out.println("This username does not exist!");
            }else {
                User user = User.userArrayList.get(findRoleIndex(userName,Role)) ;
                if (user.getPassword().equals(password)){
                    Classes.Role.loggedInRole = user ;
                    Classes.Role.loggedInRoleExistance = true ;
                    System.out.println("you are logged in as USER!");
                    Functions.showAllAvailableRestaurants();
                }else {
                    System.out.println("password is incorrect!");
                }
            }
        }
    }
    public static boolean isUserNameUnique(String UserName,String Role){
        boolean isUserNameUnique = true ;
        if (Role.equals("ADMIN")){
            for (int i = 0 ; i < Admin.adminArrayList.size() ; i++){
                if (Admin.adminArrayList.get(i).getUserName().equals(UserName)){
                    isUserNameUnique = false ;
                }
            }
        }else if (Role.equals("USER")){
            for (int i = 0 ; i < User.userArrayList.size() ; i++){
                if (User.userArrayList.get(i).getUserName().equals(UserName)){
                    isUserNameUnique = false ;
                }
            }
        }
        return isUserNameUnique ;
    }
    public static int findRoleIndex(String userName,String Role){
        int index = 0 ;
        if (Role.equals("ADMIN")){
            for (int i = 0 ; i < Admin.adminArrayList.size() ; i++){
                if (Admin.adminArrayList.get(i).getUserName().equals(userName)){
                    index = i ;
                }
            }
        }else if (Role.equals("USER")){
            for (int i = 0 ; i < User.userArrayList.size() ; i++){
                if (User.userArrayList.get(i).getUserName().equals(userName)){
                    index = i ;
                }
            }
        }
        return index ;
    }
    public static void ForgetPassword(String Role,String userName){
        if (Role.equals("ADMIN")){
            Admin admin = Admin.adminArrayList.get(findRoleIndex(userName,Role)) ;
            System.out.println("please answer following question to reset your password : ");
            System.out.println(admin.securityQuestion);
            String answer = scanner.nextLine() ;
            if (answer.equals(admin.getSecurityQuestionAnswer())){
                System.out.println("enter your new password : ");
                String password = scanner.nextLine() ;
                admin.setPassword(password);
            }else{
                System.out.println("your answer is incorrect!");
            }
        }else {
            User user = User.userArrayList.get(findRoleIndex(userName,Role)) ;
            System.out.println("please answer following question to reset your password : ");
            System.out.println(user.securityQuestion);
            String answer = scanner.nextLine() ;
            if (answer.equals(user.getSecurityQuestionAnswer())){
                System.out.println("enter your new password : ");
                String password = scanner.nextLine() ;
                user.setPassword(password);
            }else{
                System.out.println("your answer is incorrect!");
            }
        }
    }
    public static void makeSecurityQuestion(Role role){
        System.out.println("now please answer this security question for when you forget your password : ");
        double randomNumber = (Math.random()) * 5 ;
        int randomInt = (int) randomNumber ;
        String Question = new String("");
        String Answer ;
        if (randomInt==0){
            Question = new String("What is your favourite color ? : ") ;
        }else if (randomInt==1){
            Question = new String("What is your favourite food ? : ") ;
        }else if (randomInt==2){
            Question = new String("What is your favourite soccer team ? : ") ;
        }else if (randomInt==3){
            Question = new String("What is your favourite actor/actress ? : ") ;
        }else if (randomInt==4){
            Question = new String("What is your favourite movie ? : ") ;
        }
        System.out.println(Question);
        role.securityQuestion = new String(Question) ;
        String answer = scanner.nextLine() ;
        role.setSecurityQuestionAnswer(answer);
    }
    public static void searchRestaurant(Admin admin,String ID){
        boolean restaurantExistance = false ;
        for (int i = 0 ; i < admin.adminRestaurants.size() ; i++){
            if (admin.adminRestaurants.get(i).restaurantID.equals(ID)){
                restaurantExistance = true ;
                Restaurant.loggedInRestaurantForAdmin = admin.adminRestaurants.get(i) ;
            }
        }
        if (restaurantExistance == false){
            System.out.println("There is no Restaurant with this ID!");
        }else {
            System.out.println("We are at panel of "+Restaurant.loggedInRestaurantForAdmin.restaurantName+" with ID of "+Restaurant.loggedInRestaurantForAdmin.restaurantID);
        }
    }
    public static void showFoodType(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        for (int i = 0 ; i < restaurant.restaurantFoodType.size() ; i++){
            System.out.println( (i+1) +". "+restaurant.restaurantFoodType.get(i));
        }
    }
    public static String setID(String thingString){
        String firstchapter = new String("") ;
        String ID = new String("") ;
        int counter = 0 ;
        int randomNumber = (int) ( ((Math.random()) * 9999) + 1 ) ;
        if (thingString.equals("restaurant")){
            firstchapter = new String("R") ;
            Restaurant.counterIDRestaurant++ ;
            counter = Restaurant.counterIDRestaurant ;
        }else if (thingString.equals("food")){
            firstchapter = new String("F") ;
            Food.counterIDFood++ ;
            counter = Food.counterIDFood ;
        }else if (thingString.equals("comment")){
            firstchapter = new String("C") ;
            Comment.counterIDComment++ ;
            counter = Comment.counterIDComment ;
        }else if (thingString.equals("order")){
            firstchapter = new String("O") ;
            Order.counterIDOrder++ ;
            counter = Order.counterIDOrder ;
        }else if (thingString.equals("rating")){
            firstchapter = new String("RA") ;
            Rating.counterIDRating++ ;
            counter = Rating.counterIDRating ;
        }
        ID = firstchapter + randomNumber + counter ;
        return ID ;
    }
    public static void showMenuForAdmin(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        if (restaurant.restaurantMenu.size() == 0){
            System.out.println("No food added to the menu yet!");
        }else {
            for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
                Food food = restaurant.restaurantMenu.get(i) ;
                System.out.println("food name : "+food.foodName+" * food id : "+food.foodID+" * food cost : "+food.foodCost+" * active discount : "+food.discountActivation+" * food rate : "+food.getRating());
            }
        }
    }
    public static void editFood(String foodID,String changingParameters,String newValue){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        Food food = new Food("",0) ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                food = restaurant.restaurantMenu.get(i) ;
            }
        }
        if (changingParameters.equals("NAME")){
            food.foodName = newValue ;
        }else if (changingParameters.equals("PRICE")){
            food.foodCost = Integer.parseInt(newValue) ;
        }else if (changingParameters.equals("DISCOUNT")){
            if (food.discountActivation){
                food.discountActivation = false ;
            }else {
                int discount = scanner.nextInt() ;
                food.discountActivation = true ;
                food.discountValue = discount ;
            }
        }
    }
    public static void addFood(String foodName,int foodCost){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        Food food = new Food(foodName,foodCost) ;
        food.foodID = setID("food") ;
        restaurant.restaurantMenu.add(food) ;
        Food.allFoodsArrayList.add(food) ;
        System.out.println("food added to menu successfully!");
    }
    public static void showRestaurantList(Admin admin){
        for (int i = 0 ; i < admin.adminRestaurants.size() ; i++){
            Restaurant restaurant = admin.adminRestaurants.get(i) ;
            System.out.println("Restaurant Name : "+restaurant.restaurantName+" , Restaurant ID : "+restaurant.restaurantID) ;
        }
    }
    public static void deleteFood(String foodID){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = false , foodExistanceRestaurant = false;
        int foodIndexFood = 0 , foodIndexRestaurant = 0 ;
        for (int i = 0 ; i < Food.allFoodsArrayList.size() ; i++){
            if (Food.allFoodsArrayList.get(i).foodID.equals(foodID)){
                foodIDExistance = true ;
                foodIndexFood = i ;
            }
        }
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (restaurant.restaurantMenu.get(i).foodID.equals(foodID)){
                foodExistanceRestaurant = true ;
                foodIndexRestaurant = i ;
            }
        }
        if (foodIDExistance){
            if (foodExistanceRestaurant){
                Food.allFoodsArrayList.remove(foodIndexFood) ;
                restaurant.restaurantMenu.remove(foodIndexRestaurant) ;
                System.out.println("Food deleted successfully!");
            }else {
                System.out.println("This food ID does't exist in this Restaurant!");
            }
        }else {
            System.out.println("This food ID does't exist!");
        }
    }
    public static void deactiveFood(String foodID){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.possibilityOfOrdering){
                if (food.activeOrder){
                    System.out.println("You can't deactive a food when it has active order!");
                }else {
                    System.out.println("Are you sure you want to deactive this food ?");
                    String answer = scanner.nextLine() ;
                    if (answer.toLowerCase().equals("yes")){
                        food.possibilityOfOrdering = false ;
                        System.out.println("food deactived successfully!");
                    }
                }
            }else {
                System.out.println("food has been deactive!");
            }

        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static void activeFood(String foodID){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.possibilityOfOrdering){
                System.out.println("food has been active!");
            }else {
                System.out.println("Are you sure you want to active this food ?");
                String answer = scanner.nextLine() ;
                if (answer.toLowerCase().equals("yes")){
                    food.possibilityOfOrdering = true ;
                    System.out.println("food actived successfully!");
                }
            }
        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static void discountFood(String foodID,int discountPercent,int timestampHour){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.discountActivation){
                System.out.println("You can't add another discount to the food when it has an active discount!");
            }else {
                if (discountPercent <= 50){
                    food.discountActivation = true ;
                    food.discountValue = discountPercent ;
                    food.discountTimeStampHour = timestampHour ;
                }else {
                    System.out.println("You can't a discount with more than half of food cost!");
                }
            }
        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static boolean foodIDExistanceChecker(Restaurant restaurant,String foodID){
        boolean foodIDExistance = false ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                foodIDExistance = true ;
            }
        }
        return foodIDExistance ;
    }
    public static Food foodIDfounder(Restaurant restaurant,String foodID){
        Food food = restaurant.restaurantMenu.get(0) ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                food = restaurant.restaurantMenu.get(i) ;
            }
        }
        return food ;
    }
    public static boolean commentIDExistanceChecker(String commentID){
        Food food = Food.selectedFoodForAdmin ;
        boolean commentExistance = false ;
        for (int i = 0 ; i < food.foodCommentsArrayList.size() ; i++){
            if (commentID.equals(food.foodCommentsArrayList.get(i).commentID)){
                commentExistance = true ;
            }
        }
        return commentExistance ;
    }
    public static Comment commentFounder(String commentID){
        Food food = Food.selectedFoodForAdmin ;
        Comment comment = food.foodCommentsArrayList.get(0) ;
        for (int i = 0 ; i < food.foodCommentsArrayList.size() ; i++){
            if (commentID.equals(food.foodCommentsArrayList.get(i).commentID)){
                comment = food.foodCommentsArrayList.get(i) ;
            }
        }
        return comment ;
    }
    public static void commentResponse(String commentID,String response){
        boolean commentIDExistance = commentIDExistanceChecker(commentID) ;
        if (commentIDExistance){
            Comment comment = commentFounder(commentID) ;
            if (comment.commentResponse.equals("")){
                comment.commentResponse = response ;
                System.out.println("your response accepted!");
            }else {
                System.out.println("you have responsed to this comment before!");
            }
        }else {
            System.out.println("This commentID doesn't exist in this food!");
        }
    }
    public static void showAllAvailableRestaurants(){
        if (Restaurant.allRestaurantsArrayList.size() == 0)
            System.out.println("Sorry, There is no available restaurant at this time !!!");
        else {
            System.out.println("Here is the list of available restaurants for you:");
            for (int i = 0; i < Restaurant.allRestaurantsArrayList.size(); i++)
                System.out.println("Restaurant \"" + Restaurant.allRestaurantsArrayList.get(i).restaurantName + "\" -> with the ID \"" + Restaurant.allRestaurantsArrayList.get(i).restaurantID + "\"");
        }
    }
    public static void ShowRelatedRestaurants (String name){
        boolean found = false;
        for (int i = 0; i < Restaurant.allRestaurantsArrayList.size(); i++)
            if (Restaurant.allRestaurantsArrayList.get(i).restaurantName.indexOf(name) != -1)
                found = true;
        if (found) {
            System.out.println("Related restaurants with the name \"" + name + "\":");
            for (int i = 0; i < Restaurant.allRestaurantsArrayList.size(); i++)
                if (Restaurant.allRestaurantsArrayList.get(i).restaurantName.indexOf(name) != -1)
                    System.out.println("Restaurant \"" + Restaurant.allRestaurantsArrayList.get(i).restaurantName + "\" -> with the ID \"" + Restaurant.allRestaurantsArrayList.get(i).restaurantID + "\" found!");
        } else
            System.out.println("There is no related restaurant with this name !!!");
    }
    public static void selectRestaurant (String restaurantID){
        int k = -1;
        for (int i = 0; i < Restaurant.allRestaurantsArrayList.size(); i++)
            if (Restaurant.allRestaurantsArrayList.get(i).restaurantID.equals(restaurantID))
                k = i;
        if (k != -1) {
            Restaurant.loggedInRestaurantForUser = Restaurant.allRestaurantsArrayList.get(k);
            System.out.println("Restaurant with the ID \"" + restaurantID + "\" selected!");
            showMenuForUser(Restaurant.loggedInRestaurantForUser);
        }
        else
            System.out.println("There is no related restaurant with this ID !!!");

    }
    public static void showMenuForUser(Restaurant restaurant){
        if (restaurant.restaurantMenu.size() == 0)
            System.out.println("Sorry, There is no available food for you at this time !!!");
        else {
            System.out.println("Here is the list of foods at this restaurant:");
            for (int i = 0; i < restaurant.restaurantMenu.size(); i++)
                System.out.println("Food \"" + restaurant.restaurantMenu.get(i).foodName + "\" with the ID \"" + restaurant.restaurantMenu.get(i).foodID + "\" and the price \"" + restaurant.restaurantMenu.get(i).foodCost + "\"");
        }
    }
    public static void ShowRelatedFoods (String name){
        boolean found = false;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName.indexOf(name) != -1)
                found = true;
        if (found) {
            System.out.println("Related foods with the name \"" + name + "\":");
            for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
                if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName.indexOf(name) != -1)
                    System.out.println("Food \"" + Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName + "\" -> with the ID \"" + Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodID + "\" found!");
        } else
            System.out.println("There is no related food with this name !!!");
    }
    public static void selectFood (String foodID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodID.equals(foodID))
                k = i;
        if (k != -1) {
            Food.selectedFoodForUser = Restaurant.loggedInRestaurantForUser.restaurantMenu.get(k);
            System.out.println("Food with the ID \"" + foodID + "\" selected!");
        }
        else
            System.out.println("There is no related food with this ID !!!");
    }
    public static void showRestaurantComments (Restaurant restaurant){
        if (restaurant.restaurantCommentsArrayList.size() == 0)
            System.out.println("There is no comments about this restaurant!");
        else {
            System.out.println("Comments:");
            for (int i = 0; i < restaurant.restaurantCommentsArrayList.size(); i++)
                System.out.println("\"" + restaurant.restaurantCommentsArrayList.get(i).commentedUser.getUserName() + "\" says: " + restaurant.restaurantCommentsArrayList.get(i).comment + " about this restaurant.");
        }
    }
    public static void getRestaurantComment(Restaurant restaurant){
        System.out.print("Please enter your comment about this restaurant \"" + restaurant.restaurantName + "\": ");
        String comment = scanner.nextLine();
        restaurant.restaurantCommentsArrayList.add(new Comment());
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).comment = comment;
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).commentID = setID("comment");
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).commentedUser = (User) Role.loggedInRole;
        System.out.println("Thanks for your oponion :)");
    }
    public static void editRestaurantComment(String commentID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(i).commentID == commentID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no comment with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(k).commentedUser))
            System.out.println("Sorry, You can't edit this comment because it's Not yours !!!");
        else {
            System.out.print("Please enter your new comment: ");
            String newComment = scanner.nextLine();
            Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(k).comment = newComment;
            System.out.println("Thanks");
        }
    }
    public static void getRestaurantRating (){
        System.out.print("Please enter your rating to this restaurant: (0 to 5)");
        double rating  = scanner.nextDouble();
        if (rating >= 0 && rating <= 5){
            System.out.println("Thanks for your rating :)");
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.add(new Rating());
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).rating = rating;
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).ratingID = setID("rating");
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).ratedUser = (User) Role.loggedInRole;
        } else
            System.out.println("Sorry, You should rate from 0 to 5 !!!");
    }
    public static void editRestaurantRating(String ratingID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(i).ratingID == ratingID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no rating with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(k).ratedUser))
            System.out.println("Sorry, You can't edit this rating because it's Not yours !!!");
        else {
            System.out.print("Please enter your new rating: ");
            double newRating = scanner.nextDouble();
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(k).rating = newRating;
            System.out.println("Thanks");
        }
    }
    public static void getFoodComment(Food food){
        System.out.print("Please enter your comment about this food \"" + food.foodName + "\": ");
        String comment = scanner.nextLine();
        food.foodCommentsArrayList.add(new Comment());
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).comment = comment;
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).commentID = setID("comment");
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).commentedUser = (User) Role.loggedInRole;
        System.out.println("Thanks for your oponion :)");
    }
    public static void editFoodComment(String commentID){
        int k = -1;
        for (int i = 0; i < Food.selectedFoodForUser.foodCommentsArrayList.size(); i++)
            if (Food.selectedFoodForUser.foodCommentsArrayList.get(i).commentID == commentID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no comment with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Food.selectedFoodForUser.foodCommentsArrayList.get(k).commentedUser))
            System.out.println("Sorry, You can't edit this comment because it's Not yours !!!");
        else {
            System.out.print("Please enter your new comment: ");
            String newComment = scanner.nextLine();
            Food.selectedFoodForUser.foodCommentsArrayList.get(k).comment = newComment;
            System.out.println("Thanks");
        }
    }
    public static void getFoodRating (){
        System.out.print("Please enter your rating to this food: (0 to 5)");
        double rating  = scanner.nextDouble();
        if (rating >= 0 && rating <= 5){
            System.out.println("Thanks for your rating :)");
            Food.selectedFoodForUser.foodRatingsArrayList.add(new Rating());
            Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).rating = rating;
            Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).ratingID = setID("rating");
            Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).ratedUser = (User) Role.loggedInRole;
        } else
            System.out.println("Sorry, You should rate from 0 to 5 !!!");
    }
    public static void editFoodRating(String ratingID){
        int k = -1;
        for (int i = 0; i < Food.selectedFoodForUser.foodRatingsArrayList.size(); i++)
            if (Food.selectedFoodForUser.foodRatingsArrayList.get(i).ratingID == ratingID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no rating with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Food.selectedFoodForUser.foodRatingsArrayList.get(k).ratedUser))
            System.out.println("Sorry, You can't edit this rating because it's Not yours !!!");
        else {
            System.out.print("Please enter your new rating: ");
            double newRating = scanner.nextDouble();
            Food.selectedFoodForUser.foodRatingsArrayList.get(k).rating = newRating;
            System.out.println("Thanks");
        }
    }
    public static void showFoodComments (Food food){
        if (Food.selectedFoodForUser.foodCommentsArrayList.size() == 0)
            System.out.println("There is no comments about this food!");
        else {
            System.out.println("Comments:");
            for (int i = 0; i < Food.selectedFoodForUser.foodCommentsArrayList.size(); i++)
                System.out.println("\"" + Food.selectedFoodForUser.foodCommentsArrayList.get(i).commentedUser.getUserName() + "\" says: \"" + Food.selectedFoodForUser.foodCommentsArrayList.get(i).comment + "\" about this food.");
        }
    }
    public static void showOrdersHistory(User user){
        System.out.println("Orders history for you:");
        for (int i = 0; i < user.userOrders.size(); i++){
            System.out.print((i+1) + "- You ordered ");
            for (int j=0;j<user.userOrders.get(i).orderFoods.size();j++)
                System.out.print("\"" + user.userOrders.get(i).orderFoods.get(j).foodName + "\" ");
            System.out.println();
        }
    }
    public static void selectOrder(String orderID){
        int k = -1;
        for (int i = 0; i < ((User)Role.loggedInRole).userCart.cartorders.size(); i++)
            if (((User)Role.loggedInRole).userCart.cartorders.get(i).orderID.equals(orderID))
                k = i;
        if (k != -1 ) {
            System.out.println("Order with the ID \"" + orderID + "\" selected!");
            int orderSize = ((User) Role.loggedInRole).userCart.cartorders.get(k).orderFoods.size();
            if (orderSize != 0) {
                double totalCost = 0;
                System.out.println("Your order contains this foods:");
                for (int i = 0; i < orderSize; i++) {
                    System.out.print("Food \"" + ((User) Role.loggedInRole).userCart.cartorders.get(k).orderFoods.get(i).foodName + "\" ");
                    System.out.print("with the ID \"" + ((User) Role.loggedInRole).userCart.cartorders.get(k).orderFoods.get(i).foodID + "\" ");
                    System.out.println("with the price \"" + ((User) Role.loggedInRole).userCart.cartorders.get(k).orderFoods.get(i).foodCost + "\"");
                    totalCost += ((User) Role.loggedInRole).userCart.cartorders.get(k).orderFoods.get(i).foodCost;
                }
                System.out.println("Total money that you should pay is \"" + totalCost + "\"");
            } else
                System.out.println("You add no foods in this Order!");
        } else
            System.out.println("There is no Order with this ID !!!");
    }
    public static void showCartStatus(User user){
        for (int i=0;i<user.userCart.cartorders.size();i++){
            System.out.print("From the restaurant \"" + user.userCart.cartorders.get(i).orderedRestaurant.restaurantName + "\" ");
            System.out.print("with the total cost \"" + user.userCart.cartorders.get(i).getOrderCost() + "\" ");
            System.out.println("and the ID \"" + user.userCart.cartorders.get(i).orderID + "\".");
        }
    }
    public static void confirmOrder(User user, String orderID){
        int k = -1;
        for (int i = 0; i < user.userCart.cartorders.size(); i++)
            if (user.userCart.cartorders.get(i).orderID.equals(orderID))
                k = i;
        if (k != -1 && user.userCart.cartorders.get(k).getOrderCost() <= user.getAccountCharge()){
            user.userOrders.add(user.userCart.cartorders.get(k));
            user.setAccountCharge(user.getAccountCharge() - user.userCart.cartorders.get(k).getOrderCost());
            user.userCart.cartorders.get(k).orderedRestaurant.restaurantOrdersHistory.add(user.userCart.cartorders.get(k));
            user.userCart.cartorders.remove(k);
            System.out.println("Order successfully confirmed");
        } else if (k != -1 && user.userCart.cartorders.get(k).getOrderCost() > user.getAccountCharge()){
            System.out.println("Please charge your account first !!!");
        }
        else
            System.out.println("There is no order with this ID !!!");
    }
    public static void chargeAccount(User user) {
        System.out.print("How much would you want to charge? ");
        double newCharge = scanner.nextDouble();
        if (newCharge >= 0) {
            System.out.println("Account charged successfully :)");
            user.setAccountCharge(newCharge);
        }
        else
            System.out.println("Sorry, you should enter a positive value !!!");
    }
    public static void showAccountCharge(User user){
        System.out.println("You have \"" + user.getAccountCharge() + "\"$ in your account :)");
    }
}