package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Address;
import ssafy.library.domain.Member;
import ssafy.library.repository.MemberRepository;
import ssafy.library.util.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final NullExistExceptionChecker nullExistExceptionChecker;
    private final DataOutOfRangeExceptionChecker dataOutOfRangeExceptionChecker;
    private final DuplicateExceptionChecker duplicateExceptionChecker;
    private final DateExceptionChecker dateExceptionChecker;
    private final EmailFormatExceptionChecker emailFormatExceptionChecker;

    public Long join(Member member) {
        validate(member);

        return memberRepository.save(member);
    }

    public List<Member> findMembers() {
        return memberRepository.findMembers();
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    public void updatePhone(Member member, String phone) {
        nullExistExceptionChecker.check(member);
        dataOutOfRangeExceptionChecker.check(member);
        duplicateExceptionChecker.checkPhone(phone);
        member.updatePhone(phone);
    }

    public void updateEmail(Member member, String email) {
        nullExistExceptionChecker.check(member);
        dataOutOfRangeExceptionChecker.check(member);
        duplicateExceptionChecker.checkEmail(email);
        emailFormatExceptionChecker.check(email);
        member.updateEmail(email);
    }

    public void updateAddress(Member member, Address address) {
        nullExistExceptionChecker.check(member);
        dataOutOfRangeExceptionChecker.check(member);
        duplicateExceptionChecker.check(member);
        member.updateAddress(address);
    }

    private void validate(Member member) {
        nullExistExceptionChecker.check(member);
        dataOutOfRangeExceptionChecker.check(member);
        duplicateExceptionChecker.check(member);
        dateExceptionChecker.check(member.getBirth());
        emailFormatExceptionChecker.check(member.getEmail());
    }

}
