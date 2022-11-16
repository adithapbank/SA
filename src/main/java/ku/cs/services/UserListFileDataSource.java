package ku.cs.services;

import ku.cs.models.User;
import ku.cs.models.UserList;

import java.io.*;

public class UserListFileDataSource implements DataSource<UserList> {

    private String directoryName;
    private String filename;

    public UserListFileDataSource() { this("csv-data", "users.csv"); }

    public UserListFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileIfNotExist();
    }


    private void initialFileIfNotExist() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public UserList readData() {
        UserList list = new UserList();

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");


                if(data[0].equals("user")){
                    list.addUser(new User(
                            "user",
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                           data[6]

                    ));
                }else if(data[0].equals("admin")){
                    list.addUser(new User(
                            "admin",
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                            (data[6])
                    ));
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

                return list;
    }


            @Override
            public void writeData (UserList userList){    //polymorphism
                String path = directoryName + File.separator + filename;
                File file = new File(path);

                FileWriter writer = null;
                BufferedWriter buffer = null;

                try {
                    writer = new FileWriter(file);
                    buffer = new BufferedWriter(writer);

                    buffer.write(userList.toCsv());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        buffer.close();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
}
