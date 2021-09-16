package com.hz.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysSystem)实体类
 *
 * @author makejava
 * @since 2021-09-10 15:18:31
 */
@TableName("sys_system")
public class SysSystem implements Serializable {
    private static final long serialVersionUID = -50390021134778596L;
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 子系统名称
     */
    @TableField("name")
    private String name;
    /**
     * path
     */
    @TableField("path")
    private String path;
    /**
     * 子系统图标URL
     */
    @TableField("icon_url")
    private String iconUrl;
    /**
     * 子系统类别
     */
    @TableField("category")
    private String category;
    /**
     * 子系统状态（0正常 1停用）
     */
    @TableField("status")
    private String status;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 删除标记（0正常 1删除）
     */
    @TableField("del_flag")
    private String delFlag;
    /**
     * 所有权
     */
    @TableField("owner")
    private String owner;
    /**
     * 主题名称
     */
    @TableField("topic")
    private String topic;
    /**
     * 私钥
     */
    @TableField("system_key")
    private String systemKey;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

}

