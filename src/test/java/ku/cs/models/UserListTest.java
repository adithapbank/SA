package ku.cs.models;

import ku.cs.services.DataSource;
import ku.cs.services.UserListFileDataSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserListTest {
    @Test
    void testSearchByUsername(){
        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();

        User user = userList.SearchByUsername("kia123");

        assertEquals("kia123",user.getUsername());
    }
}