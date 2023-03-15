package ssafy.library.util;

import org.springframework.stereotype.Component;
import ssafy.library.domain.Address;
import ssafy.library.domain.Member;
import ssafy.library.exception.NullExistException;

@Component
public class NullExistExceptionChecker {

    public void check(Member member) {
        if(member.getName()==null || member.getBirth()==null ||
                member.getPhone()==null || member.getEmail()==null)
            throw new NullExistException("필드에 null이 존재합니다!");

        check(member.getAddress());
    }

    public void check(Address address) {
        if(address==null ||
                address.getZipcode()==null ||
                address.getMain_address()==null ||
                address.getSub_address()==null)
            throw new NullExistException("필드에 null이 존재합니다!");
    }
}
