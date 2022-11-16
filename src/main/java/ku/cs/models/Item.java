package ku.cs.models;

public class Item {
    private String idName; //รหัสพนักงาน
    private String name; //ชื่อพนักงาน
    private String department; // แผนก
    private int salary; //เงินเดือน
    private String errorLevel; //ระดับความผิด
    private String description; //รายละเอียดบทลงโทษ
    private String imagePath; //ภาพพนักงาน




    public Item(String idName) {
        this.idName = idName;
        this.name = "null";
        this.department = "null";
        this.salary = 0;
        this.errorLevel = "null";
        this.description = "null";
        this.imagePath = "null";

    }

    public Item(String idName, String name, String department , int salary, String errorLevel, String description, String path){
        this.idName = idName;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.errorLevel = errorLevel;
        this.description = description;
        this.imagePath = path;

    }

    public Item(String idName, String name, String errorLevel){
        this.idName = idName;
        this.name = name;
        this.errorLevel = errorLevel;


    }

//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//    public String getItemName() {
//        return itemName;
//    }


    public void setIdName(String idName) {this.idName = idName;}
    public void setName(String name) {
        this.name = name;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }
    public void setDescription(String description) {this.description = description;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}




    public String getIdName() {
        return idName;
    }
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public int getSalary() {
        return salary;
    }
    public String getErrorLevel() {
        return errorLevel;
    }
    public String getDescription() {return description;}
    public String getImagePath() {return imagePath;}


    public boolean checkIdName(String idName) {
        String idName1 = this.idName;
        return idName1.equals(idName);
    }
//    public boolean checkName(String Name) {
//        String Name1 = this.name;
//        return Name1.equals(Name);
//    }




    public String toString() {
        return idName + "," + name + "," + department + "," + salary + "," + errorLevel + "," + description + "," + imagePath;

    }
}
