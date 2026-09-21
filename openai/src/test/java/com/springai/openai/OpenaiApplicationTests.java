package com.springai.openai;

import com.springai.openai.controller.ChatController;
import com.springai.openai.controller.ChatTestController;
import org.junit.jupiter.api.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
		"spring.ai.openai.api-key=${OPENAI_API_KEY:test-key}",
		"logging.level.org.springframework.ai=DEBUG"
})
class OpenaiApplicationTests {

	@Autowired
	private ChatTestController chatController;

	@Autowired
	private ChatModel chatModel;

	private ChatClient chatClient;
	private RelevancyEvaluator relevancyEvaluator;

	@BeforeEach
	void setup() {
		ChatClient.Builder builder = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());
		this.chatClient = builder.build();
		this.relevancyEvaluator = new RelevancyEvaluator(builder);

	}

	@Test
	@DisplayName("Should return relevant response to basic geography question")
	@Timeout(value = 30)
	void evaluateChatControllerResponse() {
		String question = "What is the capital of India ?";

		String aiResponse = chatController.getSolution(question);
	}

}
