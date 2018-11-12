package com.voucher.manage.model;

public class User_Asset {
    private Integer id;

    private String openId;

    private String charter;

    private String idno;

    private String hirePhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getCharter() {
        return charter;
    }

    public void setCharter(String charter) {
        this.charter = charter == null ? null : charter.trim();
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
    }

    public String getHirePhone() {
        return hirePhone;
    }

    public void setHirePhone(String hirePhone) {
        this.hirePhone = hirePhone == null ? null : hirePhone.trim();
    }
}