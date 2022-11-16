package ku.cs.services;

import ku.cs.models.UserList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFileDataSourceTest {
    @Test
    void  testWriteData(){
        DataSource<UserList> dataSource = new UserListFileDataSource();
        UserList user = dataSource.readData();

        dataSource = new UserListFileDataSource("csv-data","userTest1.csv");
        dataSource.writeData(user);

        UserList readDiary = dataSource.readData();

        assertEquals(user.toCsv(),readDiary.toCsv());
    }

}