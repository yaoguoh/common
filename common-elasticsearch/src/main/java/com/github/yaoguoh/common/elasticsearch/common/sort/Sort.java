package com.github.yaoguoh.common.elasticsearch.common.sort;


import org.elasticsearch.search.sort.SortOrder;

import java.io.Serializable;
import java.util.*;

/**
 * The type Sort.
 *
 * @author dqq
 */
public class Sort implements Serializable {

    private static final long serialVersionUID = 7870291462629796420L;

    /**
     * The constant DEFAULT_DIRECTION.
     */
    public static final SortOrder DEFAULT_DIRECTION;

    static {
        DEFAULT_DIRECTION = SortOrder.ASC;
    }

    private final List<Order> orders;

    /**
     * Instantiates a new Sort.
     *
     * @param orders the orders
     */
    public Sort(Order... orders) {
        this(Arrays.asList(orders));
    }

    /**
     * Instantiates a new Sort.
     *
     * @param orders the orders
     */
    public Sort(List<Order> orders) {
        this.orders = Collections.unmodifiableList(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }

    /**
     * The type Order.
     */
    public static class Order implements Serializable {

        private static final long serialVersionUID = -5851377171775401201L;

        private final SortOrder sortOrder;
        private final String property;

        public Order(SortOrder sortOrder, String property) {
            this.sortOrder = sortOrder == null ? Sort.DEFAULT_DIRECTION : sortOrder;
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public SortOrder getSortOrder() {
            return sortOrder;
        }
    }
}
