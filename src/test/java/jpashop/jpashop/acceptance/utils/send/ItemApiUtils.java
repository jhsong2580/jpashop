package jpashop.jpashop.acceptance.utils.send;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import org.springframework.http.MediaType;

public class ItemApiUtils {

    public static ExtractableResponse<Response> 영화등록하기(
        String 영화이름,
        int 가격,
        int 수량,
        String 감독,
        String 배우

    ) {
        ItemAddDTO 요청전문 = new ItemAddDTO(영화이름, 가격, 수량, "MOVIE", null, null, null, null,
            감독, 배우);

        return RestAssured
            .given().log().all()
            .body(요청전문)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/items")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 책등록하기(
        String 책이름,
        int 가격,
        int 수량,
        String 작가,
        String isbn

    ) {
        ItemAddDTO 요청전문 = new ItemAddDTO(책이름, 가격, 수량, "BOOK", 작가, isbn, null, null,
            null, null);

        return RestAssured
            .given().log().all()
            .body(요청전문)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/items")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 앨범등록하기(
        String 앨범이름,
        int 가격,
        int 수량,
        String 아티스트,
        String 기타정보

    ) {
        ItemAddDTO 요청전문 = new ItemAddDTO(앨범이름, 가격, 수량, "ALBUM", null, null, 아티스트, 기타정보,
            null, null);

        return RestAssured
            .given().log().all()
            .body(요청전문)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/items")
            .then().log().all()
            .extract();
    }
}
