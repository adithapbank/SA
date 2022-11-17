package ku.cs.models;

public class Item {
    private String idName; //รหัสพนักงาน
    private String name; //ชื่อพนักงาน
    private String department; // แผนก
    private Double salary; //เงินเดือน
    private Integer errorLevel; //ระดับความผิด
    private String description; //รายละเอียดบทลงโทษ
    private String imagePath; //ภาพพนักงาน
    private String caseName;
    private Integer caseId;

    public Item(Integer caseId, String department, String caseName, String idName) {
        this.caseId = caseId;
        this.department = department;
        this.caseName = caseName;
        this.idName = idName;
    }

    public Item(String idName) {
        this.idName = idName;
        this.name = "null";
        this.department = "null";
        this.salary = 0.0;
        this.errorLevel = 0;
        this.description = "null";
        this.imagePath = "null";

    }

    public Item(String idName, String name, String department , Double salary, Integer errorLevel, String description){
        this.idName = idName;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.errorLevel = errorLevel;
        this.description = description;

    }

    public Item(String idName, String name, String department, Double salary, Integer errorLevel, Integer caseId, String description, String caseName){
        this.idName = idName;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.errorLevel = errorLevel;
        this.description = description;
        this.caseName = caseName;
        this.caseId = caseId;

    }

    public void setIdName(String idName) {this.idName = idName;}
    public void setName(String name) {
        this.name = name;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public void setErrorLevel(Integer errorLevel) {
        this.errorLevel = errorLevel;
    }
    public void setDescription(String description) {this.description = description;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getIdName() {
        return idName;
    }
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public Double getSalary() {
        return salary;
    }
    public Integer getErrorLevel() {
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
