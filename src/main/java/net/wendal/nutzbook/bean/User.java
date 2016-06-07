package net.wendal.nutzbook.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/6/6 17:57
 */
@Table("t_user")
public class User extends BasePojo {

    @Id
    protected int id;
    @Name
    @Column
    protected String name;
    @Column("passwd")
    @ColDefine(width = 128)
    protected String password;
    @Column
    protected String salt;
    @Column
    private boolean locked;
    @ManyMany(from = "u_id", relation = "t_user_role", target = Role.class, to = "role_id")
    protected List<Role> roles;
    @ManyMany(from = "u_id", relation = "t_user_permission", target = Permission.class, to = "permission_id")
    protected List<Permission> permissions;
    @One(target = UserProfile.class, field = "id", key = "userId")
    protected UserProfile profile;

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}