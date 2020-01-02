package DTO;

public class UserDTO {
    private Integer userID;
    private String fullname;
    private String username;
    private String password;
    private String gender;
    private String phoneNumber;
    private String email;

    public UserDTO(Integer userID, String fullname, String username, String password, String gender, String phoneNumber, String email) {
        this.userID = userID;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserDTO(String fullname, String username, String password, String gender, String phoneNumber, String email) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
