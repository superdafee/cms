package com.zjs.cms.entity;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Date;

/**
 * 学校
 * Created by dafee on 2014/9/2.
 */
@Entity
@Table(name = "t_school")
public class School {
    private Long id;
    private String region;
    private String name;
    private Integer phase;
    private Integer nature;
    private String contact;
    private String address;
    private Integer type;
    private String fondingyear;
    private Date createtime;
    private String isdeleted;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFondingyear() {
        return fondingyear;
    }

    public void setFondingyear(String fondingyear) {
        this.fondingyear = fondingyear;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }
}
