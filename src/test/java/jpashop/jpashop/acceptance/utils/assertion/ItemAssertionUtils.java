package jpashop.jpashop.acceptance.utils.assertion;

import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;

public class ItemAssertionUtils {

    public static void 책등록됨(
        ExtractableResponse<Response> 등록결과,
        String 책이름,
        int 가격,
        int 수량,
        String 작가,
        String isbn) {

        assertAll(
            () -> Assertions.assertThat(등록결과.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("itemId").toString()).isNotNull(),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("price").toString())
                .isEqualTo(String.valueOf(가격)),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("isbn").toString())
                .isEqualTo(isbn)
        );
    }

    public static void 영화등록됨(
        ExtractableResponse<Response> 등록결과,
        String 영화이름,
        int 가격,
        int 수량,
        String 감독,
        String 배우
    ) {
        assertAll(
            () -> Assertions.assertThat(등록결과.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("itemId").toString()).isNotNull(),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("price").toString())
                .isEqualTo(String.valueOf(가격)),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("director").toString())
                .isEqualTo(감독)
        );
    }

    public static void 앨범등록됨(
        ExtractableResponse<Response> 등록결과,
        String 앨범이름,
        int 가격,
        int 수량,
        String 아티스트,
        String 기타정보
    ) {
        assertAll(
            () -> Assertions.assertThat(등록결과.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("itemId").toString()).isNotNull(),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("price").toString())
                .isEqualTo(String.valueOf(가격)),
            () -> Assertions.assertThat(등록결과.body().jsonPath().get("artist").toString())
                .isEqualTo(아티스트)
        );
    }

}
