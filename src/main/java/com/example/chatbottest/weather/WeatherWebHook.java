package com.example.chatbottest.weather;

import com.example.chatbottest.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("weather")
public class WeatherWebHook {
	private final String PAGE_TOKEN = "8c1b7ca5";
	private String city_name;
	private final String WEATHER_URL="https://api.hgbrasil.com/weather?array_limit=1&fields=only_results,temp,description,city_name,forecast,max,min,date&key=" + PAGE_TOKEN + "&" + city_name;
	private String weather;
	private final RestTemplate template = new RestTemplate();

	public WeatherWebHook(String city_name){
		this.city_name = city_name;
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public String get(@RequestParam(name = "results") String request) {
		return request;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void post(@RequestBody WeatherResults request) {
		for (WeatherForecast f : request.getForecast()) {
			String city_name = request.getCity_name();
			String date = f.getDate();
			String description = request.getDescription();
			int max = f.getMax();
			int min = f.getMin();
			this.weather = construct(city_name, date, description, max, min);
		}
	}

	public String construct(String city, String date, String description, int max, int min) {
		return "O clima em " + city + " na data " + date + " é:\n" + description + " com temperaturas entre: " + max + " e " + min;
	}

	public String getWeather() {
		return weather;
	}
}



