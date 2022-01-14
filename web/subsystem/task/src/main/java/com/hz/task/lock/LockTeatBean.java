package com.hz.task.lock;

import lombok.Data;

/**
 * @explain
 * @Classname TeatBean
 * @Date 2022/1/13 16:47
 * @Created by hanzhao
 */
@Data
public class LockTeatBean {
    private static LockTeatBean lockTeatBean;
    // 版本号
    private int version;
    // 名称
    private String name;
    // 笑容
    private boolean smile;

    private LockTeatBean() {

    }

    public static LockTeatBean getBean(){
        lockTeatBean = new LockTeatBean();
        lockTeatBean.setVersion(1);
        lockTeatBean.setName("吴亦凡");
        lockTeatBean.setSmile(true);
        return lockTeatBean;
    }

    public Boolean setBean(Boolean smile,int version){
        /**
         * 验证当前版本号是否正确，如果不正确则更新失败
         */
        if(this.version == version){
            this.smile = smile;
            this.version++;
            return true;
        }
        return false;
    }
}
