package net.wendal.nutzbook.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author zhangjinci
 * @version 2016/6/15 10:55
 */
@Table("pro_address")
public class ProAddress {

    @Id
    private Integer id;

    @Column("user_id")
    private Integer userId;

    @Column("name")
    private String name;

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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
