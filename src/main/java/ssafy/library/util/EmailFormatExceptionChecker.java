package ssafy.library.util;

import org.springframework.stereotype.Component;
import ssafy.library.exception.EmailFormatException;

import java.util.regex.Pattern;

@Component
public class EmailFormatExceptionChecker {

    public void check(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        if(!pattern.matcher(email).matches())
            throw new EmailFormatException("이메일 형식이 올바르지 않습니다!");
    }
}
