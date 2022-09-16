package jpashop.jpashop.acceptance;

import static jpashop.jpashop.acceptance.utils.send.MemberApiUtils.회원가입하기;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jpashop.jpashop.acceptance.utils.assertion.ItemAssertionUtils;
import jpashop.jpashop.acceptance.utils.send.ItemApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void init() {
        super.init();
        회원가입하기(MemberAcceptanceTest.사용자아이디, MemberAcceptanceTest.사용자비밀번호);
    }

    @Test
    public void 영화등록하기() {
        //given
        String 영화이름 = "영화";
        int 가격 = 1000;
        int 수량 = 50;
        String 감독 = "감독";
        String 배우 = "배우";
        //when
        ExtractableResponse<Response> 영화등록하기_response = ItemApiUtils.영화등록하기(영화이름, 가격, 수량, 감독, 배우);

        //then
        ItemAssertionUtils.영화등록됨(영화등록하기_response, 영화이름, 가격, 수량, 감독, 배우);
    }

    @Test
    public void 책등록하기(){
        //given
        String 책이름 = "책";
        int 가격 = 1000;
        int 수량 = 50;
        String 작가 = "작가";
        String isbn = "090909";

        //when
        ExtractableResponse<Response> 책등록하기_response = ItemApiUtils.책등록하기(책이름, 가격, 수량, 작가, isbn);

        //then
        ItemAssertionUtils.책등록됨(책등록하기_response, 책이름, 가격, 수량, 작가, isbn);
    }

    @Test
    public void 앨범등록하기 (){
        //given
        String 앨범이름 = "앨범";
        int 가격 = 1000;
        int 수량 = 50;
        String 아티스트 = "아티스트";
        String 기타정보 = "나는정보다";

        //when
        ExtractableResponse<Response> 앨범등록하기_response = ItemApiUtils.앨범등록하기(앨범이름, 가격, 수량, 아티스트, 기타정보);

        //then
        ItemAssertionUtils.앨범등록됨(앨범등록하기_response, 앨범이름, 가격, 수량, 아티스트, 기타정보);
    }
}
