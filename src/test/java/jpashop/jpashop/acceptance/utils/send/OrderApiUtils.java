package jpashop.jpashop.acceptance.utils.send;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import org.springframework.http.MediaType;

public class OrderApiUtils {

    public static ExtractableResponse<Response> 주문하기(long 아이템번호, int 개수) {
        OrderAddDTOList 요청전문 = new OrderAddDTOList(
            Arrays.asList(new OrderAddDTO(아이템번호, 개수)));

        return RestAssured
            .given().log().all()
            .body(요청전문)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/orders")
            .then().log().all()
            .extract();
    }

}
