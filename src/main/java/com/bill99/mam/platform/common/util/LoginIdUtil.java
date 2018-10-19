package com.bill99.mam.platform.common.util;

import com.bill99.mam.platform.common.constant.DataPattern;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class LoginIdUtil {

    public static int getLoginIdType(String idContent) {
        int idType = 0;
        if (validateEmail(idContent)) {
            idType = 1;
        }
        if (validateMobile(idContent)) {
            idType = 2;
        }

        return idType;
    }

    public static boolean validateEmail(String idContent) {
        if (StringUtils.isEmpty(idContent)) {
            return false;
        }
        String pattern = DataPattern.EMAIL_PATTERN;
        return Pattern.matches(pattern, idContent);
    }

    public static boolean validateMobile(String idContent) {
        if (StringUtils.isEmpty(idContent)) {
            return false;
        }
        String pattern = "((1\\d{10})|((0\\d{2,3}){1}([1-9]\\d{6,7}){1}))";
        return Pattern.matches(pattern, idContent);
    }


}
