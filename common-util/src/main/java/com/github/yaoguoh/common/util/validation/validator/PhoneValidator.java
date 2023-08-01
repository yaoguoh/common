package com.github.yaoguoh.common.util.validation.validator;

import cn.hutool.core.util.PhoneUtil;
import com.github.yaoguoh.common.util.validation.constraints.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * The type Phone validator.
 *
 * @author yaoguohh
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return PhoneUtil.isPhone(s);
    }
}
