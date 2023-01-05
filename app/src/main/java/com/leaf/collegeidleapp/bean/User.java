package com.leaf.collegeidleapp.bean;

import java.sql.Timestamp;

/**
 * 学生实体类
 * @author : autumn_leaf
 */
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String major;
    private String year;
    private Timestamp registerTime;
    private Integer goodsBought;
    private Integer goodsSold;
    private Integer requestPublishedFinished;
    private Integer requestTakenFinished;
    private Integer rateGoods;
    private Integer rateRequest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getGoodsBought() {
        return goodsBought;
    }

    public void setGoodsBought(Integer goodsBought) {
        this.goodsBought = goodsBought;
    }

    public Integer getGoodsSold() {
        return goodsSold;
    }

    public void setGoodsSold(Integer goodsSold) {
        this.goodsSold = goodsSold;
    }

    public Integer getRequestPublishedFinished() {
        return requestPublishedFinished;
    }

    public void setRequestPublishedFinished(Integer requestPublishedFinished) {
        this.requestPublishedFinished = requestPublishedFinished;
    }

    public Integer getRequestTakenFinished() {
        return requestTakenFinished;
    }

    public void setRequestTakenFinished(Integer requestTakenFinished) {
        this.requestTakenFinished = requestTakenFinished;
    }

    public Integer getRateGoods() {
        return rateGoods;
    }

    public void setRateGoods(Integer rateGoods) {
        this.rateGoods = rateGoods;
    }

    public Integer getRateRequest() {
        return rateRequest;
    }

    public void setRateRequest(Integer rateRequest) {
        this.rateRequest = rateRequest;
    }
}
