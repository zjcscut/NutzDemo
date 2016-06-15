package net.wendal.nutzbook.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/6/15 10:54
 */
@Table("pro_user")
public class ProUser {

    @Id
    private Integer id;

    @Column("name")
    private String name;

    @Column("compony")
    private String company;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @Column("eamil")
    private String eamil;

    @Column("birth")
    private Date birth;

    @Column("create_time")
    private Date createTime;

    @Column("is_enable")
    private Integer isEnable;

    @Column("enable_desc")
    private String enableDesc;

    @Column("is_delete")
    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getEnableDesc() {
        return enableDesc;
    }

    public void setEnableDesc(String enableDesc) {
        this.enableDesc = enableDesc;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
