package jpashop.jpashop;

import jpashop.jpashop.component.SessionCheckInterceptor;
import jpashop.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final MemberRepository memberRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckInterceptor(memberRepository))
            .excludePathPatterns("/member/**");
    }
}
