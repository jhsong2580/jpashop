package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.dto.member.MemberDTO;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.dto.member.form.MemberLoginDTO;
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

    public void login(MemberLoginDTO memberLoginDTO, HttpServletRequest httpServletRequest){
        Member member = memberRepository.findByName(memberLoginDTO.getIdentification())
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다"));

        if(!member.validPassword(memberLoginDTO.getPassword())){
            throw new IllegalStateException("password가 일치하지 않습니다");
        }

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("MEMBERID",member.getId());
    }
}