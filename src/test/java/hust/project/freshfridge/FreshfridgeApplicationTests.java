package hust.project.freshfridge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import com.google.firebase.messaging.FirebaseMessaging;

@SpringBootTest
@ActiveProfiles("test")
@EnableScheduling
class FreshfridgeApplicationTests {

	@MockBean
	private FirebaseMessaging firebaseMessaging;

	@Test
	void contextLoads() {
	}

}
