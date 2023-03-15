package ssafy.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Address;
import ssafy.library.domain.Member;
import ssafy.library.exception.*;
import ssafy.library.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        Long memberId = memberService.join(member);

        // then
        Member savedMember = memberRepository.findById(memberId);
        assertThat(member).isEqualTo(savedMember);

    }

    @Test
    public void 널데이터에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", null);
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(NullExistException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    public void 데이터범위에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "19981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(DataOutOfRangeException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    public void 중복에러() throws Exception {

        // given
        Address address1 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member1 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address1);

        Address address2 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member2 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address2);

        // when & then
        assertThrows(DuplicateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
    }

    @Test
    public void 미래에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "301128", "01071852569", "byh9811@naver.com", address);

        // when & then
        assertThrows(DateException.class, () -> {
            memberService.join(member);
        });

    }

    @Test
    public void 이메일포맷에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(EmailFormatException.class, () -> {
            memberService.join(member);
        });

    }

}