package Classes;

import java.util.ArrayList;

public class Admin extends Role {
    public static ArrayList<Admin> adminArrayList = new ArrayList<Admin>() ;
    public ArrayList<Restaurant> adminRestaurants = new ArrayList<Restaurant>() ;
    public ArrayList<Restaurant> alphabeticSortOfAdminRestaurants = new ArrayList<Restaurant>() ;
    private String userName ;
    private String password ;

    public Admin(String userName,String password){
        this.userName = userName ;
        this.password = password ;
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
}
