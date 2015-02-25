package com.socialmap.server.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by yy on 2/25/15.
 */
@Entity(name = "teams")
public class Team {
    private long id;
    private String name;
    private String description;
    private Set<User> members;
    private Set<User> leaders;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "teams")
    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    @OneToMany
    @JoinTable(name = "team_leaders")
    public Set<User> getLeaders() {
        return leaders;
    }

    public void setLeaders(Set<User> leaders) {
        this.leaders = leaders;
    }
}
