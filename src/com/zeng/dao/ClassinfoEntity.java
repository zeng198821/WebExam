package com.zeng.dao;

import javax.persistence.*;

/**
 * Created by zeng on 2016-07-23.
 */
@Entity
@Table(name = "classinfo", schema = "exam", catalog = "")
public class ClassinfoEntity {
    private int classId;
    private String className;
    private Integer gradeId;

    @Id
    @Column(name = "classId", nullable = false)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "className", nullable = true, length = 20)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "GradeId", nullable = true)
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassinfoEntity that = (ClassinfoEntity) o;

        if (classId != that.classId) return false;
        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (gradeId != null ? !gradeId.equals(that.gradeId) : that.gradeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (gradeId != null ? gradeId.hashCode() : 0);
        return result;
    }
}
