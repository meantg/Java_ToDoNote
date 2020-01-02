package DTO;

public class CategoryDTO {
    private Integer categoryID;
    private Integer userID;
    private String categoryName;
    private String icon;
    private Integer numOfNotes;

    public CategoryDTO(Integer categoryID, Integer userID, String categoryName, String icon, Integer numOfNotes) {
        this.categoryID = categoryID;
        this.userID = userID;
        this.categoryName = categoryName;
        this.icon = icon;
        this.numOfNotes = numOfNotes;
    }

    public CategoryDTO(Integer userID, String categoryName, String icon, Integer numOfNotes) {
        this.userID = userID;
        this.categoryName = categoryName;
        this.icon = icon;
        this.numOfNotes = numOfNotes;
    }

    public CategoryDTO(Integer userID, String categoryName) {
        this.userID = userID;
        this.categoryName = categoryName;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getNumOfNotes() {
        return numOfNotes;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setNumOfNotes(Integer numOfNotes) {
        this.numOfNotes = numOfNotes;
    }

}
