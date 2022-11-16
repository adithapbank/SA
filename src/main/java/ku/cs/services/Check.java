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
}
