package com.github.yaoguoh.common.elasticsearch.enums;

/**
 * 父子关系中子类型的映射名称.
 */
public enum ChildType {

    /**
     * 子类型：被收藏的新闻.
     */
    NEWS("news"),

    /**
     * 子类型：被关注的医生
     */
    DOCTOR("doctor")
    ;

    private String value;

    ChildType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
