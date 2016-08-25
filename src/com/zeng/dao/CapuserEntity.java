package com.zeng.dao;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zeng on 2016-07-23.
 */
@Entity
@Table(name = "capuser", schema = "exam", catalog = "")
public class CapuserEntity {
    private int capId;
    private String capPasswd;
    private Integer capStatus;
    private Serializable lastloginTime;
    private Boolean needReSetPasswd;

    @Id
    @Column(name = "capId", nullable = false)
    public int getCapId() {
        return capId;
    }

    public void setCapId(int capId) {
        this.capId = capId;
    }

    @Basic
    @Column(name = "capPasswd", nullable = true, length = 30)
    public String getCapPasswd() {
        return capPasswd;
    }

    public void setCapPasswd(String capPasswd) {
        this.capPasswd = capPasswd;
    }

    @Basic
    @Column(name = "capStatus", nullable = true)
    public Integer getCapStatus() {
        return capStatus;
    }

    public void setCapStatus(Integer capStatus) {
        this.capStatus = capStatus;
    }

    @Basic
    @Column(name = "lastloginTime", nullable = true)
    public Serializable getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(Serializable lastloginTime) {
        this.lastloginTime = lastloginTime;
    }

    @Basic
    @Column(name = "NeedReSetPasswd", nullable = true)
    public Boolean getNeedReSetPasswd() {
        return needReSetPasswd;
    }

    public void setNeedReSetPasswd(Boolean needReSetPasswd) {
        this.needReSetPasswd = needReSetPasswd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CapuserEntity that = (CapuserEntity) o;

        if (capId != that.capId) return false;
        if (capPasswd != null ? !capPasswd.equals(that.capPasswd) : that.capPasswd != null) return false;
        if (capStatus != null ? !capStatus.equals(that.capStatus) : that.capStatus != null) return false;
        if (lastloginTime != null ? !lastloginTime.equals(that.lastloginTime) : that.lastloginTime != null)
            return false;
        if (needReSetPasswd != null ? !needReSetPasswd.equals(that.needReSetPasswd) : that.needReSetPasswd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = capId;
        result = 31 * result + (capPasswd != null ? capPasswd.hashCode() : 0);
        result = 31 * result + (capStatus != null ? capStatus.hashCode() : 0);
        result = 31 * result + (lastloginTime != null ? lastloginTime.hashCode() : 0);
        result = 31 * result + (needReSetPasswd != null ? needReSetPasswd.hashCode() : 0);
        return result;
    }
}
