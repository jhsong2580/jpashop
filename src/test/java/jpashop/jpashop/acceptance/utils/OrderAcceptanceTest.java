package jpashop.jpashop.acceptance.utils;

import static jpashop.jpashop.acceptance.MemberAcceptanceTest.사용자비밀번호;
import static jpashop.jpashop.acceptance.MemberAcceptanceTest.사용자아이디;
import static jpashop.jpashop.acceptance.utils.send.ItemApiUtils.책등록하기;
import static jpashop.jpashop.acceptance.utils.send.MemberApiUtils.회원가입하기;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import jpashop.jpashop.acceptance.AcceptanceTest;
import jpashop.jpashop.acceptance.MemberAcceptanceTest;
import jpashop.jpashop.acceptance.utils.assertion.ItemAssertionUtils;
import jpashop.jpashop.acceptance.utils.send.ItemApiUtils;
import jpashop.jpashop.acceptance.utils.send.MemberApiUtils;
import jpashop.jpashop.acceptance.utils.send.OrderApiUtils;
import jpashop.jpashop.dto.member.form.MemberJoinDTO;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class OrderAcceptanceTest extends AcceptanceTest {

    private String 회원번호="";
    private String 아이템번호="";

    @BeforeEach
    public void init(){
        super.init();
        회원번호 =  회원가입하기(사용자아이디, 사용자비밀번호).body().jsonPath().get("id").toString();
        아이템번호 = 책등록하기("책", 5000, 50, "작가", "08282").body().jsonPath().get("itemId").toString();
    }


    @Test
    public void 주문하기 (){

        OrderAddDTO orderAddDTO = new OrderAddDTO(Long.parseLong(아이템번호), 5);
        OrderAddDTOList orderAddDTOList = new OrderAddDTOList(Arrays.asList(orderAddDTO));

        for(int i = 0 ; i< 50; i++){
            new Thread(() -> OrderApiUtils.주문하기(Long.parseLong(아이템번호), 5))
                .run();
        }
    }
}
