package jpashop.jpashop.component;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public class SessionCheckInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Long memberId = -1L;
        if (session != null) {
            memberId = session.getAttribute("MEMBERID") != null ?
                (Long) session.getAttribute("MEMBERID") : -1L;
        }
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            String requestURI = request.getRequestURI();
            response.sendRedirect("/member/login?path=" + requestURI);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
