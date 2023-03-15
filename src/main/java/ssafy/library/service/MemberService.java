package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Member;
import ssafy.library.repository.MemberRepository;
import ssafy.library.util.*;

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

    private void validate(Member member) {
        nullExistExceptionChecker.check(member);
        dataOutOfRangeExceptionChecker.check(member);
        duplicateExceptionChecker.check(member);
        dateExceptionChecker.check(member.getBirth());
        emailFormatExceptionChecker.check(member.getEmail());
    }

}
