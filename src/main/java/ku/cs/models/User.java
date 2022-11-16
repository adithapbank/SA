package ku.cs.models;



public class User {
    private String type;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String idName; //รหัสพนักงาน
    private String imagePath;



    public User(String type, String name, String surname, String username, String password, String idName, String imagePath){

        this.type = type;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.idName = idName;
    }

    // ใช้ตอน register
    public User(String name, String surname, String username, String password) {
        this("user", name, surname, username, password, "null", "null");
    }

    public void  setPassword(String password) {this.password = password;}

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getUsername() {
        return username;
    }
    public String getIdName() {
        return idName;
    }
    public String getImagePath() { return imagePath; }

    public void setIdName(String idName) {
        this.idName = idName;
    }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String toCsv() {
        if (!this.idName.equals("null")) {
            return type + "," + name + "," + surname + "," + username + "," + password + "," + idName + "," + imagePath;
        }
        else {
            return type + "," + name + "," + surname + "," + username + "," + password + "," + "null" + "," + imagePath;
        }
    }

    public boolean checkUser(String username) {
        String username1 = this.username;
        return username1.equals(username);
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    @Override
    public String toString() {
        return username;
    }
}
