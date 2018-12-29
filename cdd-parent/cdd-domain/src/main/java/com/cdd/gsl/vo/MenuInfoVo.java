package com.cdd.gsl.vo;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class MenuInfoVo {
    private Long menuId;

    private String menuName;

    private String menuCode;

    private Long parentId;

    private List<MenuInfoVo> menuInfoList;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<MenuInfoVo> getMenuInfoList() {
        return menuInfoList;
    }

    public void setMenuInfoList(List<MenuInfoVo> menuInfoList) {
        this.menuInfoList = menuInfoList;
    }
}
