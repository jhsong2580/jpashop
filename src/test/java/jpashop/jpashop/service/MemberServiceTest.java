package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    public void init() {
        memberService = new MemberService(memberRepository);
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
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("해당 아이디의 회원이 이미 존재합니다");
    }
}