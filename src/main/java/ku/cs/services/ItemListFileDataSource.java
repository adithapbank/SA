//package ku.cs.services;
//
//import ku.cs.models.Item;
//import ku.cs.models.ItemList;
//
//import java.io.*;
//
//public class ItemListFileDataSource implements DataSource<ItemList>{
//    private String directoryName;
//    private String filename;
//
//    public ItemListFileDataSource() { this("csv-data", "Items.csv"); }
//
//    public ItemListFileDataSource(String directoryName, String filename) {
//        this.directoryName = directoryName;
//        this.filename = filename;
//        initialFileIfNotExist();
//    }
//
//
//    private void initialFileIfNotExist() {
//        File file = new File(directoryName);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//
//        String path = directoryName + File.separator + filename;
//        file = new File(path);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public ItemList readData() {
//        ItemList list = new ItemList();
//
//        String path = directoryName + File.separator + filename;
//        File file = new File(path);
//
//        FileReader reader = null;
//        BufferedReader buffer = null;
//
//        try {
//            reader = new FileReader(file);
//            buffer = new BufferedReader(reader);
//
//            String line = "";
//            while ((line = buffer.readLine()) != null) {
//                String[] data = line.split(",");
//                list.addItem(new Item(
//                        data[0],
//                        data[1],
//                        data[2],
//                        Double.parseDouble(data[3]),
//                        data[4],
//                        data[5],
//                        data[6]
//                        ));
//
//            }
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                buffer.close();
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
//    @Override
//    public void writeData(ItemList itemList) { //polymorphism
//        String path = directoryName + File.separator + filename;
//        File file = new File(path);
//
//        FileWriter writer = null;
//        BufferedWriter buffer = null;
//
//        try {
//            writer = new FileWriter(file);
//            buffer = new BufferedWriter(writer);
//
//            buffer.write(itemList.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                buffer.close();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//}
