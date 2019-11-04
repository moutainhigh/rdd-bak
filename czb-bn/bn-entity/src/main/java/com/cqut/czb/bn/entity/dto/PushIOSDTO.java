package com.cqut.czb.bn.entity.dto;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-03 22:09
 */
public class PushIOSDTO {
    private Integer pathType;

    private String menuName;

    private String iosPath;

    private String title;

    public Integer getPathType() {
        return pathType;
    }

    public void setPathType(Integer pathType) {
        this.pathType = pathType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIosPath() {
        return iosPath;
    }

    public void setIosPath(String iosPath) {
        this.iosPath = iosPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}