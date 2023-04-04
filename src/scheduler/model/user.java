package scheduler.model;

public class user {
    private String username;
    private String password;
    private int userID;

    public user(int userID, String username, String password){
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
