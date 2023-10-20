package com.hz.api.entity;

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
@TableName("test")
public class TestEntity implements Serializable {
    private static final long serialVersionUID = -50390021134778596L;
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 子系统名称
     */
    @TableField("text")
    private String text;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String text() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

