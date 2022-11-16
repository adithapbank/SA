package ku.cs.models;

import ku.cs.services.DataSource;
import ku.cs.services.ItemListFileDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItemList {
    private ArrayList<Item> itemList;

    public ItemList(){
        itemList = new ArrayList<>();
    }

    public void addItem(Item item){
        itemList.add(item);
    }

    public ArrayList<Item> getAllItems(){
        return itemList;
    }

    public String toString() {
        String result = "";
        for (Item item : itemList) {
            result += item.toString() + "\n";
        }
        return result;
    }


    public ArrayList<Item> myItemByIdName(String idName){
        DataSource<ItemList> dataSource;
        dataSource = new ItemListFileDataSource();
        ItemList itemListCsv = dataSource.readData();
        ArrayList<Item> itemListfromCsv = itemListCsv.getAllItems();
        ArrayList<Item> newItemList = new ArrayList<Item>();
        for(Item item: itemListfromCsv){
            if (item.checkIdName(idName)){
                newItemList.add(item);
            }
        }
        return newItemList;
    }


    public Item SearchByItemNameAndStoreName(String idName){
        for(Item item: itemList){
            if (item.checkIdName(idName)){
                return item;
//                ,String name
//                 && item.checkName(name)
            }
        }
        return null;
    }

}
