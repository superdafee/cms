package com.zjs.cms.entity;

import javax.persistence.*;
import java.security.Timestamp;

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
    private int phase;
    private int nature;
    private String contact;
    private String address;
    private int type;
    private String fondingyear;
    private Timestamp createtime;
    private String isdeleted;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SchoolSequence")
    @SequenceGenerator(name = "SchoolSequence", sequenceName = "seq_school", allocationSize = 1)
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

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFondingyear() {
        return fondingyear;
    }

    public void setFondingyear(String fondingyear) {
        this.fondingyear = fondingyear;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }
}
