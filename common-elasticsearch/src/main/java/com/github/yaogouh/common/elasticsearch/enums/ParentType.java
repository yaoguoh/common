package com.github.yaogouh.common.elasticsearch.enums;

/**
 * The enum Parent type 父子关系中父类型的映射名称.
 */
public enum ParentType {

    User("user");

    private String value;

    ParentType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
