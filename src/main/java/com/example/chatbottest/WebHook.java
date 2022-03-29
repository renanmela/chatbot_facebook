package com.example.chatbottest;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Logger;

@RestController()
@RequestMapping("webhook")
public class WebHook {
	private final String PAGE_TOKEN = "EAAKIOzAzo9ABAKvldf1aqOdV0blXvhkM7m3sQxqMEbnRnL6nCnTZBSOQ5QJjkHZBA9wlWZAfVm3QZAEkn3uXd5wZAFLUZCA6m0R4UciBhvIL87wZCkzlLNmhqT7znyR9Si9jyoDsb783rRxswLmWZCZC4stnJUdpvlpFZArYOsHprXyFfcw2TrGtyGe8TUg7jaSt0ZD";
	private final String VERIFY_TOKEN = "xyz";
	private final String FB_MSG_URL="https://graph.facebook.com/v2.6/me/messages?access_token=" + PAGE_TOKEN;
	private final RestTemplate template = new RestTemplate();

	// This is necessary for register a webhook in facebook
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public String get(@RequestParam(name = "hub.verify_token") String token,
			@RequestParam(name = "hub.challenge") String challenge) {
		if (token != null && !token.isEmpty() && token.equals(VERIFY_TOKEN)) {
			return challenge;
		} else {
			return "Wrong Token";
		}
	}

	// This method reply all messages with: 'This is a test message'
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void post(@RequestBody FacebookHookRequest request) {
		request.getEntry().forEach(e -> {
			e.getMessaging().forEach(m -> {
				String id = m.getSender().get("id");
				String message = m.getMessage().getText().toLowerCase();
				if(message.matches("(?i)sua idade|(?i)(voc[eê] tem).*idade|(?i)idade.*(voc[eê] tem)|(?i)(voc[eê] tem).*anos|(?i)anos.*(tem voc[eê])|(?i)anos.*(voc[eê] tem)")){
					this.sendReply(id, "Tenho 23 anos");
				}
				else if(message.matches("(?i)seu nome|(?i)voc[eê]\\sse\\schama|(?i)nome\\s(tem\\s)voc[eê]|(?i)nome\\svoc[eê]\\stem")){
					this.sendReply(id, "Renan");
				}
				else if(message.matches("(?i)oi|(?i)ol[aá]|(?i)esta\\sai?")){
					this.sendReply(id, "Ola!");
				}
				else this.sendReply(id, "Não entendi sua mensagem, pode tentar de outra maneira?");
			});
		});
	}

	private void sendReply(String id, String text) {
		FacebookMessageResponse response = new FacebookMessageResponse();
		response.setMessage_type("text");
		response.getRecipient().put("id", id);
		response.getMessage().put("text", text);
		HttpEntity<FacebookMessageResponse> entity = new HttpEntity<>(response);
		String result = template.postForEntity(FB_MSG_URL, entity, String.class).getBody();

	}
}
