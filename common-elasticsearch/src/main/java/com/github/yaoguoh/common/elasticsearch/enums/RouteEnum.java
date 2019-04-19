package com.github.yaoguoh.common.elasticsearch.enums;

/**
 * The enum Route enum.
 *
 * @author dqq
 */
public enum RouteEnum {

    /**
     * Route 1 route enum.
     */
    ROUTE1("1"),
    /**
     * Route 2 route enum.
     */
    ROUTE2("2"),
    /**
     * Route 3 route enum.
     */
    ROUTE3("3"),
    /**
     * Route 4 route enum.
     */
    ROUTE4("4"),
    /**
     * Route 5 route enum.
     */
    ROUTE5("5");

    private String value;

    RouteEnum(String value){
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
