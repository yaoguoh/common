package com.github.yaoguoh.common.elasticsearch.exception;

import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.rest.RestStatus;
import org.springframework.util.ObjectUtils;


/**
 * The type Mine exception.
 *
 * @author dqq
 */
public class ESResponseException extends RuntimeException {

    /**
     * 异常码
     */
    private int code;

    /**
     * Instantiates a new Mine exception.
     *
     * @param code    the code
     * @param message the message
     */
    public ESResponseException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 在异常中包装Elasticsearch返回的状态码.
     *
     * @param status  the status
     * @param message the message
     */
    public ESResponseException(StatusToXContentObject status, String message){
        new ESResponseException(status.status().getStatus(), message);
    }

    /**
     * Instantiates a new Mine exception.
     *
     * @param status  the status
     * @param message the message
     */
    public ESResponseException(RestStatus status, String message) {
        new ESResponseException(status.getStatus(), message);
    }

    /**
     * Create mine exception mine exception.
     *
     * @param status  the status
     * @param message the message
     * @return the mine exception
     */
    public static ESResponseException createMineException(StatusToXContentObject status, String message){
        if (ObjectUtils.isEmpty(status))
            return new ESResponseException(status, message);
        else
            return new ESResponseException(RestStatus.INTERNAL_SERVER_ERROR, message);
    }
}
