package com.socialmap.server.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yy on 2/25/15.
 */
@Entity(name = "images")
public class Image implements Serializable{
    private long id;
    private String name;
    private byte[] data;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    @Column(length = 1024*1024*5)
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Image){
            Image other = (Image)obj;
            if (data.hashCode() == other.data.hashCode()){
                return true;
            }
        }
        return false;
    }

    public Image() {

    }

    public Image(String name, byte[] data){
        this.name = name;
        this.data = data;
    }
}
