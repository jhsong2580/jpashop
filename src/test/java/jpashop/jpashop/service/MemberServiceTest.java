package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.dto.member.MemberDTO;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.dto.member.form.MemberLoginDTO;
import jpashop.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    private MockHttpServletRequest httpServletRequest;
    private MemberService memberService;

    @BeforeEach
    public void init() {
        memberService = new MemberService(memberRepository);
        httpServletRequest = new MockHttpServletRequest();
        MockHttpSession httpSession = new MockHttpSession();
        httpServletRequest.setSession(httpSession);
    }

    @Test
    public void saveMember() {
        //given
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO("id", "pw");
        when(memberRepository.existsByName(any())).thenReturn(false);

        //when & then
        assertDoesNotThrow(() -> memberService.joinMember(memberJoinDTO));
    }

    @Test
    public void throwDupMemberSave() {
        //given
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO("id", "pw");
        when(memberRepository.existsByName(any())).thenReturn(true);

        //when & then
        assertThatThrownBy(() -> memberService.joinMember(memberJoinDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 아이디의 회원이 이미 존재합니다");
    }

    @Test
    public void findAllMembers() {
        //given
        Member member1 = new Member("1", "1PW", null);
        Member member2 = new Member("2", "2PW", null);
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member1, member2));
        //when
        List<MemberDTO> allMembers = memberService.findAllMembers();
        //then
        assertAll(
            () -> assertThat(allMembers).extracting("identification").containsExactly("1", "2"),
            () -> assertThat(allMembers).extracting("city").containsExactly("", "")
        );
    }

    @Test
    public void findAllMembersEmptyList() {
        //given
        when(memberRepository.findAll()).thenReturn(new ArrayList<>());
        //when
        List<MemberDTO> allMembers = memberService.findAllMembers();
        //then
        assertThat(allMembers.size()).isEqualTo(0);
    }

    @Test
    public void loginWithUnknownUser() {
        //given
        when(memberRepository.findByName("id")).thenReturn(Optional.empty());
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("id", "pw");

        //when & then
        assertThatThrownBy(() -> memberService.login(memberLoginDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("존재하지 않는 회원입니다");
    }

    @Test
    public void loginWithWrongPassword() {
        //given
        when(memberRepository.findByName("id")).thenReturn(
            Optional.of(new Member("id", "pw", null)));

        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("id", "wrongPw");
        //when & then
        assertThatThrownBy(() -> memberService.login(memberLoginDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("password가 일치하지 않습니다");
    }

    @Test
    public void loginNormal() {
        //given
        when(memberRepository.findByName("id")).thenReturn(
            Optional.of(new Member("id", "pw", null)));

        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("id", "pw");
        //when & then
        assertDoesNotThrow(() -> memberService.login(memberLoginDTO));

    }
}