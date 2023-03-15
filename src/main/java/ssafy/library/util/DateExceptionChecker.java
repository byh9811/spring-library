package ssafy.library.util;

import org.springframework.stereotype.Component;
import ssafy.library.domain.Member;
import ssafy.library.exception.DateException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Component
public class DateExceptionChecker {

    public void check(String date) {
        // 6자리의 생년월일로는 미래인지 판단하기 어려우므로 대충 24년생~49년생을 미래라고 정의
        int year = Integer.parseInt(date.substring(0, 2));

        if(year>23 && year<50)
            throw new DateException("생년월일이 유효하지 않습니다!");
    }
}
