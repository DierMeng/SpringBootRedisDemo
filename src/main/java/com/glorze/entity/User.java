package com.glorze.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @ClassName: User
 * @author: glorze.com_高老四
 * @since: 2018/11/27 16:23
 */
@Alias("user")
public class User implements Serializable {

    private static final long serialVersionUID = -912377600672120329L;

    /**
     * 用户编号,主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 备注
     */
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
