package DTO;

import java.sql.Date;
import java.time.LocalDate;

public class NoteDTO {
    private Integer noteID;
    private Integer categoryID;
    private Integer userID;
    private String title;
    private String description;
    private Integer stateID;
    private Boolean isMyDay;
    private Boolean isImportance;
    private LocalDate createDate;
    private LocalDate dueDate;

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    public void setMyDay(Boolean myDay) {
        isMyDay = myDay;
    }

    public void setImportance(Boolean importance) {
        isImportance = importance;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getNoteID() {
        return noteID;
    }

    public Integer getCategoryID() {
        if(categoryID != null)
            return categoryID;
        else return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStateID() {
        return stateID;
    }

    public Boolean getMyDay() {
        return isMyDay;
    }

    public Boolean getImportance() {
        return isImportance;
    }

    public Date getCreateDate() {
        return java.sql.Date.valueOf(createDate);
    }

    public Date getDueDate() {
        if(dueDate != null) return java.sql.Date.valueOf(dueDate);
        else return null;
    }

    public NoteDTO(Integer noteID, Integer userID, Integer categoryID, String title, String description, Integer stateID, Boolean isMyDay, Boolean isImportance, LocalDate createDate, LocalDate dueDate) {
        this.noteID = noteID;
        this.userID = userID;
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
        this.stateID = stateID;
        this.isMyDay = isMyDay;
        this.isImportance = isImportance;
        this.createDate = createDate;
        this.dueDate = dueDate;
    }

    public NoteDTO(Integer userID, Integer categoryID, String title, String description, Integer stateID, Boolean isMyDay, Boolean isImportance, LocalDate createDate, LocalDate dueDate) {
        this.userID = userID;
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
        this.stateID = stateID;
        this.isMyDay = isMyDay;
        this.isImportance = isImportance;
        this.createDate = createDate;
        this.dueDate = dueDate;
    }

    public NoteDTO(Integer categoryID, Integer userID, String title, Boolean isMyDay, Boolean isImportance, LocalDate dueDate) {
        this.categoryID = categoryID;
        this.userID = userID;
        this.title = title;
        this.stateID = 12002;
        this.description = "";
        this.isMyDay = isMyDay;
        this.isImportance = isImportance;
        this.createDate = LocalDate.now();
        this.dueDate = dueDate;
    }
}
