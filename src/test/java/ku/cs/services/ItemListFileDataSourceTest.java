package ku.cs.services;

import ku.cs.models.ItemList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemListFileDataSourceTest {
    @Test
    void  testWriteData() {
        DataSource<ItemList> dataSource = new ItemListFileDataSource();
        ItemList item = dataSource.readData();

        dataSource = new ItemListFileDataSource("csv-data", "itemTest1.csv");
        dataSource.writeData(item);

        ItemList readDiary = dataSource.readData();

        assertEquals(item.toString(), readDiary.toString());
    }
}