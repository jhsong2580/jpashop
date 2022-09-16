package jpashop.jpashop.acceptance;

import io.restassured.RestAssured;
import jpashop.jpashop.acceptance.utils.DatabaseCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;
    @Autowired
    private DatabaseCleanup databaseCleanup;


    public void init() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

}
