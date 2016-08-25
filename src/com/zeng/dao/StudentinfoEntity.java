package com.zeng.dao;

import javax.persistence.*;

/**
 * Created by zeng on 2016-07-23.
 */
@Entity
@Table(name = "studentinfo", schema = "exam", catalog = "")
public class StudentinfoEntity {
    private int studentId;
    private String studentName;
    private Integer capId;
    private Integer classId;
    private String phoneNo;
    private String email;

    @Id
    @Column(name = "studentId", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "studentName", nullable = true, length = 50)
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Basic
    @Column(name = "capId", nullable = true)
    public Integer getCapId() {
        return capId;
    }

    public void setCapId(Integer capId) {
        this.capId = capId;
    }

    @Basic
    @Column(name = "classId", nullable = true)
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "phoneNo", nullable = true, length = 12)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentinfoEntity that = (StudentinfoEntity) o;

        if (studentId != that.studentId) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (capId != null ? !capId.equals(that.capId) : that.capId != null) return false;
        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;
        if (phoneNo != null ? !phoneNo.equals(that.phoneNo) : that.phoneNo != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (capId != null ? capId.hashCode() : 0);
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        result = 31 * result + (phoneNo != null ? phoneNo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
