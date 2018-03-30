package example.dinotest.entity;

public class Dino {

    private String name;
    private String authorName;
    private String colorTID;
    private String about;
    private int day;
    private int month;
    private int year;
    private String fileId;

    public Dino() {
    }

    public Dino(String name, String authorName, String colorTID, String about, int day, int month, int year, String fileId) {
        this.name = name;
        this.authorName = authorName;
        this.colorTID = colorTID;
        this.about = about;
        this.day = day;
        this.month = month;
        this.year = year;
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getColorTID() {
        return colorTID;
    }

    public void setColorTID(String colorTID) {
        this.colorTID = colorTID;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
