package com.cdd.gsl.vo;

public class CountCoustomer {
    private Long userId;

    private Integer countAddCoustomer = 0;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCountAddCoustomer() {
        return countAddCoustomer;
    }

    public void setCountAddCoustomer(Integer countAddCoustomer) {
        this.countAddCoustomer = countAddCoustomer;
    }
}
