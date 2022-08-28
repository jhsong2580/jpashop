package jpashop.jpashop.controller;

import javax.servlet.http.HttpServletRequest;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.dto.member.form.MemberLoginDTO;
import jpashop.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(@ModelAttribute MemberLoginDTO memberLoginDTO) {
        return "/basic/login";
    }

    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute MemberLoginDTO loginDTO,
        BindingResult bindingResult,
        HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            return "/basic/login";
        }

        memberService.login(loginDTO, httpServletRequest);
        return "redirect:/items";
    }

    @GetMapping("/join")
    public String joinPage(@ModelAttribute MemberJoinDTO memberJoinDTO) {
        return "/basic/join";
    }

    @PostMapping("/join")
    public String joinProcess(@Validated @ModelAttribute MemberJoinDTO memberJoinDTO,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/basic/join";
        }

        memberService.joinMember(memberJoinDTO);

        return "redirect:/member/login";
    }
}
