package com.cdd.gsl.vo;

public class CountCoustomer {
    private Long userId;

    private Integer countCoustomer = 0;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCountCoustomer() {
        return countCoustomer;
    }

    public void setCountCoustomer(Integer countCoustomer) {
        this.countCoustomer = countCoustomer;
    }
}
