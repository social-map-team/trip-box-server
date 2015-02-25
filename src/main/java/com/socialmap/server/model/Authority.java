package com.socialmap.server.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by yy on 2/23/15.
 */
@Entity(name = "authorities")
public class Authority implements GrantedAuthority {

    private String name;

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public String getAuthority() {
        return name;
    }
}
