package jpashop.jpashop.acceptance.utils.send;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import org.springframework.http.MediaType;

public class MemberApiUtils {
    public static ExtractableResponse<Response> 회원가입하기(String 아이디, String 비밀번호){
        MemberJoinDTO 회원가입전문 = new MemberJoinDTO(아이디, 비밀번호);
        return RestAssured
            .given().log().all()
            .body(회원가입전문)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/members")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 회원조회하기(Integer 회원번호){
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/"+회원번호)
            .then().log().all()
            .extract();
    }

}
