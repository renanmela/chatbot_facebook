package com.example.chatbottest;

import com.example.chatbottest.weather.WeatherWebHook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController()
@RequestMapping("webhook")
public class WebHook {
<<<<<<< HEAD
	private final String PAGE_TOKEN = "EAAKIOzAzo9ABAKvldf1aqOdV0blXvhkM7m3sQxqMEbnRnL6nCnTZBSOQ5QJjkHZBA9wlWZAfVm3QZAEkn3uXd5wZAFLUZCA6m0R4UciBhvIL87wZCkzlLNmhqT7znyR9Si9jyoDsb783rRxswLmWZCZC4stnJUdpvlpFZArYOsHprXyFfcw2TrGtyGe8TUg7jaSt0ZD";
	private final String VERIFY_TOKEN = "xyz";
	private final String FB_MSG_URL="https://graph.facebook.com/v2.6/me/messages?access_token=" + PAGE_TOKEN;
=======
	private final String PAGE_TOKEN = "EAAKIOzAzo9ABAEQorkt0PFxE42qBYuVWw5MK7tjTjsB2SKcfAleQqCQ0AZC4Sb0zyBT5TOeqD6LNqWVkLEtJYT4MekCovMcrPqaaNNRZCWmob2mOkOGoNAHvz6zLcrPb4E8IePF0Wo5AN3elXNLD5ggRB0ub2x2eh0O6u92beoYAf6YQKr2gEZCKQW8kDMZD";
	private final String VERIFY_TOKEN = "xyz";
	// this is for reply messages
	private final String FB_MSG_URL = "https://graph.facebook.com/v2.6/me/messages?access_token=" + PAGE_TOKEN;

	// logger to watch whats happening in our bot
	private final Logger logger = (Logger) LoggerFactory.getLogger(WebHook.class);
>>>>>>> 955fcf9cc52ab2faa4661f3ebe37c1d88399dc07
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
<<<<<<< HEAD
		for (FacebookEntry e : request.getEntry()) {
			for (FacebookMessaging m : e.getMessaging()) {
				String id = m.getSender().get("id");
				String message = m.getMessage().getText();
				Regex regex = new Regex();
				if (regex.findMatcher(message, regex.getIdade())) {
					this.sendReply(id, "Tenho 23 anos");
				}
				else if (regex.findMatcher(message, regex.getNome())){
					this.sendReply(id, "Renan");
				}
				else if (regex.findMatcher(message, regex.getOi())){
					this.sendReply(id, "Ola!");
				}
				else if (regex.findMatcher(message, regex.getClima())){
					String pegaTudo = "(?i).*clima [em|na|de|no]{2} (cidade de |munic[ií]pio de )*([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+).*";
					String nomeCidade = message.replaceAll(pegaTudo, "$2");
					WeatherWebHook weather = new WeatherWebHook();
					this.sendReply(id, weather.getWeather(nomeCidade.replaceAll(" ", "")));
				}
				else this.sendReply(id, "Não entendi sua mensagem, pode tentar de outra maneira?");
			}
		}
=======
		logger.info("Message from chat: {}", request);
		request.getEntry().forEach(e->{
            e.getMessaging().forEach(m->{
                String id = m.getSender().get("id");
                String message = m.getMessage().getText();
                switch (message.toLowerCase()) {
                case "oi":
                	sendReply(id,"Ola!");
                	break;
                case "ola":
                	sendReply(id,"Oiee!");
                	break;
                case "qual a sua idade?":
                	sendReply(id,"23 anos");
                	break;
                case "qual o seu nome?":
                	sendReply(id,"Renan");
                	break;
                default: sendReply(id, "Não entendi, pode repetir por favor?");
                }
            });
        });
>>>>>>> 955fcf9cc52ab2faa4661f3ebe37c1d88399dc07
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
