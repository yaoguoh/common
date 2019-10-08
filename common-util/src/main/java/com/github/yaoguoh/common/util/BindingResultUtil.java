package com.github.yaoguoh.common.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

/**
 * BindingResult 工具类
 *
 * @author WYG
 */
public class BindingResultUtil {

    public static void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(
                    // 错误信息整合
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining())
            );
        }
    }
}
