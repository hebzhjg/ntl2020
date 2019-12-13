package net.xdclass.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class User {

    /**
     * 返回使用别名，保护后台
     */
    @JsonProperty("cname")
    private String name;

    private int age;

    /**
     * 电话为空值时，不返回
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    /**
     * 密码不返回
     */
    @JsonIgnore
    private String pwd;

    /**
     * 对日期字段，进行格式化
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",locale = "zh",timezone="GMT+8")
    private Date ctime;


    /**
     *
     * @param name
     * @param age
     * @param phone
     * @param pwd
     * @param ctime
     */
    public User(String name, int age, String phone, String pwd, Date ctime) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.pwd = pwd;
        this.ctime = ctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
