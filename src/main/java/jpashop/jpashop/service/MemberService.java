package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.dto.member.MemberDTO;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinMember(MemberJoinDTO memberJoinDTO) {
        if (memberRepository.existsByName(memberJoinDTO.getIdentification())) {
            throw new IllegalStateException("해당 아이디의 회원이 이미 존재합니다");
        }
        memberRepository.save(Member.from(memberJoinDTO));
    }

    public List<MemberDTO> findAllMembers() {
        return memberRepository.findAll()
            .stream()
            .map(MemberDTO::from)
            .collect(Collectors.toList());
    }
}
