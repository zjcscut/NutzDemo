package net.wendal.nutzbook.bean;

import org.nutz.dao.entity.annotation.Table;

/**
 * @author zhangjinci
 * @version 2016/6/15 11:25
 */
@Table("pro_relation")
public class ProRelation {

    private Integer id;

    private String name;

    private String relation;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
