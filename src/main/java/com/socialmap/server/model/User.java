package com.socialmap.server.model;

import com.socialmap.server.ServerDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yy on 2/22/15.
 */
@Entity(name = "users")
public class User implements UserDetails, Serializable {

    private long id;
    @NotNull
    private String username;
    @NotNull
    //@Size(min = 6, max = 20)
    private String password;
    private boolean enabled;
    private Set<Authority> authorities;
    private String realname;
    private String idcard;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthday;
    private String phone;
    private String email;
    private Status status;
    private Image avatar;
    private Image bgimage;
    private Gender gender;

    public User() {
        enabled = true;
        authorities = new HashSet<>();
        status = Status.OFFLINE;
        avatar = ServerDefaults.userAvatar();
        gender = Gender.UNSELECTED;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(unique = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToOne
    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    @OneToOne
    public Image getBgimage() {
        return bgimage;
    }

    public void setBgimage(Image bgimage) {
        this.bgimage = bgimage;
    }

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public enum Status {
        ONLINE,
        OFFLINE
    }

    public enum Gender {
        MALE,
        FEMALE,
        UNSELECTED
    }
}
