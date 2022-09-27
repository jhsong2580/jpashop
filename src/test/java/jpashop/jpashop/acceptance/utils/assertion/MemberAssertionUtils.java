package jpashop.jpashop.acceptance.utils.assertion;

import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;

public class MemberAssertionUtils {

    public static void 회원등록됨(ExtractableResponse<Response> 응답) {
        assertAll(
            () -> Assertions.assertThat(응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> Assertions.assertThat(응답.body().jsonPath().get("id").toString()).isNotNull()
        );
    }

}
