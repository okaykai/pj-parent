package cn.edu.xcu.wyb.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @description: 自定义的PasswordEncoder
 * @author: Keith
 */

@Component
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return "{MD5}" + DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.startsWith("{MD5}")) {
            encodedPassword = encodedPassword.substring(5);
        }
        String encodedRawPassword = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        return encodedPassword.equals(encodedRawPassword);
    }
}