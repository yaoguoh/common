package com.github.yaoguoh.common.util.validation.validator;

import cn.hutool.core.util.PhoneUtil;
import com.github.yaoguoh.common.util.validation.constraints.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The type Phone validator.
 *
 * @author yaoguohh
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return PhoneUtil.isPhone(s);
    }
}
