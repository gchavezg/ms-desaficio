package cl.ms.model;

public class JwtUser {
    private String userName;
    private String id;
    private String email;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.email = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return email;
    }
}
