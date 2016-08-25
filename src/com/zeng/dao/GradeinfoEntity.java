package com.zeng.dao;

import javax.persistence.*;

/**
 * Created by zeng on 2016-07-23.
 */
@Entity
@Table(name = "gradeinfo", schema = "exam", catalog = "")
public class GradeinfoEntity {
    private int gradeId;
    private String gradeName;

    @Id
    @Column(name = "gradeId", nullable = false)
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @Basic
    @Column(name = "GradeName", nullable = true, length = 20)
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeinfoEntity that = (GradeinfoEntity) o;

        if (gradeId != that.gradeId) return false;
        if (gradeName != null ? !gradeName.equals(that.gradeName) : that.gradeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gradeId;
        result = 31 * result + (gradeName != null ? gradeName.hashCode() : 0);
        return result;
    }
}
