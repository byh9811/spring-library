package ssafy.library.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssafy.library.domain.Member;
import ssafy.library.exception.DuplicateException;
import ssafy.library.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DuplicateExceptionChecker {

    private final MemberRepository memberRepository;

    public void check(Member member) {
        checkPhone(member.getPhone());
        checkEmail(member.getEmail());
    }

    private void checkPhone(String phone) {
        List<Member> memberList = memberRepository.findByPhone(phone);
        if(!memberList.isEmpty())
            throw new DuplicateException("핸드폰 번호가 중복됩니다!");
    }

    private void checkEmail(String email) {
        List<Member> memberList = memberRepository.findByEmail(email);
        if(!memberList.isEmpty())
            throw new DuplicateException("이메일이 중복됩니다!");
    }
}
