package ssafy.library.util;

import org.springframework.stereotype.Component;
import ssafy.library.domain.Address;
import ssafy.library.domain.Category;
import ssafy.library.domain.Member;
import ssafy.library.exception.DataOutOfRangeException;

@Component
public class DataOutOfRangeExceptionChecker {

    public void check(Member member) {
        if(member.getName().length()>50 ||
                member.getBirth().length()>6 ||
                member.getPhone().length()>11 ||
                member.getEmail().length()>100)
            throw new DataOutOfRangeException("데이터가 범위를 벗어났습니다!");

        check(member.getAddress());
    }

    private void check(Address address) {
        if(address.getZipcode().length()>5 ||
                address.getMain_address().length()>255 ||
                address.getSub_address().length()>255)
            throw new DataOutOfRangeException("데이터가 범위를 벗어났습니다!");
    }

    public void check(Category category) {
        if(category.getName().length()>255)
            throw new DataOutOfRangeException("데이터가 범위를 벗어났습니다!");
    }

}
