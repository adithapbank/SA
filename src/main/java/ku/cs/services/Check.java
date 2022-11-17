package ku.cs.services;

import ku.cs.models.User;
import ku.cs.models.UserList;

public class Check {
     public static boolean checkStore(String storeName){
         DataSource<UserList> dataSource = new UserListFileDataSource();
         UserList userList = dataSource.readData();
         for(User user : userList.getAllUsers()){
             if(storeName.equals(user.getIdName())){
                 return true;
             }
         }
         return false;
    }

    public static boolean isString(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);

            // s is a valid integer

            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
            // s is not an integer
        }

        return isValidInteger;
    }
}
