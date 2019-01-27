package com.cdd.gsl.vo;

public class CompanyVo {

    private String companyName;

    private String background;

    private String description;

    private Long userId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CompanyVo{" +
                "companyName=" + companyName +
                ", background='" + background + '\'' +
                ", description=" + description +
                '}';
    }
}
