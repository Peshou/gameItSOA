package com.gameit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "appuser") //user in POSTGRESQL is a reserved word
public class User extends AbstractBaseEntity {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 500)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(updatable = false, nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "userSeller", cascade = CascadeType.ALL)
    private Set<Game> sellingGames;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserGameOrder> userGameOrders = new HashSet<UserGameOrder>(0);

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public Set<Game> getSellingGames() {
        return sellingGames;
    }

    public void setSellingGames(Set<Game> sellingGames) {
        this.sellingGames = sellingGames;
    }

    public Set<UserGameOrder> getUserGameOrders() {
        return userGameOrders;
    }

    public void setUserGameOrders(Set<UserGameOrder> userGameOrders) {
        this.userGameOrders = userGameOrders;
    }
}
