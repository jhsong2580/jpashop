package jpashop.jpashop.service;

import jpashop.jpashop.domain.Member;
import jpashop.jpashop.dto.member.request.MemberJoinDTO;
import jpashop.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinMember(MemberJoinDTO memberJoinDTO){
        if(memberRepository.existsByName(memberJoinDTO.getIdentification())){
            throw new IllegalStateException("해당 아이디의 회원이 이미 존재합니다");
        }
        memberRepository.save(Member.from(memberJoinDTO));
    }
}
