package jpashop.jpashop.acceptance.utils;

import static jpashop.jpashop.acceptance.MemberAcceptanceTest.사용자비밀번호;
import static jpashop.jpashop.acceptance.MemberAcceptanceTest.사용자아이디;
import static jpashop.jpashop.acceptance.utils.send.ItemApiUtils.책등록하기;
import static jpashop.jpashop.acceptance.utils.send.MemberApiUtils.회원가입하기;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import jpashop.jpashop.acceptance.AcceptanceTest;
import jpashop.jpashop.acceptance.utils.send.OrderApiUtils;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderAcceptanceTest extends AcceptanceTest {

    private String 회원번호 = "";
    private String 아이템번호 = "";

    @BeforeEach
    public void init() {
        super.init();
        회원번호 = 회원가입하기(사용자아이디, 사용자비밀번호).body().jsonPath().get("id").toString();
        아이템번호 = 책등록하기("책", 5000, 50, "작가", "08282").body().jsonPath().get("itemId").toString();
    }


    @Test
    public void 연속주문하기() throws InterruptedException {

        OrderAddDTO orderAddDTO = new OrderAddDTO(Long.parseLong(아이템번호), 5);
        OrderAddDTOList orderAddDTOList = new OrderAddDTOList(Arrays.asList(orderAddDTO));
        List<Integer> results = new LinkedList<>();
        List<Thread> threads = new LinkedList<>();

        for (int i = 0; i < 70; i++) {
            Thread thread = new Thread(() -> {
                ExtractableResponse<Response> 주문하기 = OrderApiUtils.주문하기(Long.parseLong(아이템번호), 5);
                results.add(주문하기.statusCode());
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Assertions.assertThat(results.stream().filter(integer -> integer == 200).count())
            .isEqualTo(10);
    }
}
