package jpashop.jpashop.controller;

import java.util.UUID;
import jpashop.jpashop.dto.member.MemberDTO;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.dto.member.form.MemberLoginDTO;
import jpashop.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> loginProcess(@Validated @RequestBody MemberLoginDTO loginDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors().toString());
        }

        memberService.login(loginDTO);
        // TODO: 2022/08/28 jwp Token
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> joinProcess(@Validated @RequestBody MemberJoinDTO memberJoinDTO,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().toString());
        }

        MemberDTO memberDTO = memberService.joinMember(memberJoinDTO);

        return ResponseEntity.ok(memberDTO);
    }

    @GetMapping(path = "{id}", consumes = "application/json")
    public ResponseEntity<Object> getMemberInfo(@PathVariable(name = "id", required = true) Long memberId) {
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }
}
