package vn.ngh.example;

/**
 * Created by Ngh on 7/13/2017.
 */

public class User {
    private String name;
    private String email;
    private int idAvatar;

    public User(String name) {
        this(name, "", 0);
    }

    public User(String name, int idAvatar) {
        this(name, "", idAvatar);
    }

    public User(String name, String email, int idAvatar) {
        this.name = name;
        this.email = email;
        this.idAvatar = idAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdAvatar() {
        return idAvatar;
    }

    public void setIdAvatar(int idAvatar) {
        this.idAvatar = idAvatar;
    }

}
