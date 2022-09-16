package jpashop.jpashop.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jpashop.jpashop.acceptance.utils.assertion.MemberAssertionUtils;
import jpashop.jpashop.acceptance.utils.send.MemberApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static String 사용자아이디 = "acceptanceTestId";
    public static String 사용자비밀번호 = "acceptanceTestPw";
    @BeforeEach
    public void init(){
        super.init();
    }

    @Test
    public void 사용자_등록하기 (){
        //when
        ExtractableResponse<Response> 회원가입하기 = MemberApiUtils.회원가입하기(사용자아이디, 사용자비밀번호);
        Integer 회원번호 = 회원가입하기.body().jsonPath().get("id");
        //then
        ExtractableResponse<Response> 회원조회하기 = MemberApiUtils.회원조회하기(회원번호);
        MemberAssertionUtils.회원등록됨(회원조회하기);
    }

}
