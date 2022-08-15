package jpashop.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @GetMapping("/login")
    public String loginPage (){
        return "/basic/login";
    }
    
    @GetMapping("/join")
    public String joinPage(){
        return "/basic/join";
    }
}
