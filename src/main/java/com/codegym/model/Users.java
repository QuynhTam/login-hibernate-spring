package com.codegym.model;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users{
    private Integer id;
    private String username;
    private String password;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    @Column(name = "username", length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

//    @Column(name = "password", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
