package com.zjs.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_student")
public class Student {

    private Long id;
    private String realname;
    private Integer grade;
    private String classname;
    private Date createtime;
    private String isdeleted;

    private School school;


    private List<ParentStudentCon> parentList = new ArrayList<ParentStudentCon>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StudentSequence")
    @SequenceGenerator(name = "StudentSequence", sequenceName = "seq_student", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @Where(clause = "isdeleted='N'")
    public List<ParentStudentCon> getParentList() {
        return parentList;
    }

    public void setParentList(List<ParentStudentCon> parentList) {
        this.parentList = parentList;
    }

    public void addParentList(ParentStudentCon conn) {
        this.parentList.add(conn);
    }

    @ManyToOne
    @JoinColumn(name = "school_id")
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}