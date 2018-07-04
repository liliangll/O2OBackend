package cn.com.efuture.o2o.backend.mybatis.entity;

public class Dept {
    private String deptId;

    private String name;

    private String nameEn;

    private String alias;

    private String parentId;

    private Integer level;

    private String hasChild;

    private String b2cDeptId;

    private Integer status;

    private Integer isSysB2c;

    private String b2cParentId;

    private Short isOverseas;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getB2cDeptId() {
        return b2cDeptId;
    }

    public void setB2cDeptId(String b2cDeptId) {
        this.b2cDeptId = b2cDeptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsSysB2c() {
        return isSysB2c;
    }

    public void setIsSysB2c(Integer isSysB2c) {
        this.isSysB2c = isSysB2c;
    }

    public String getB2cParentId() {
        return b2cParentId;
    }

    public void setB2cParentId(String b2cParentId) {
        this.b2cParentId = b2cParentId;
    }

    public Short getIsOverseas() {
        return isOverseas;
    }

    public void setIsOverseas(Short isOverseas) {
        this.isOverseas = isOverseas;
    }
}