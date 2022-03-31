package com.example.chatbottest.weather;

import com.example.chatbottest.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

	public String construct(String city, String date, String description, int max, int min) {
		weather = "O clima em " + city + " na data " + date + " é:\n" + description + " com temperaturas entre: " + max + " e " + min;
		return weather;
	}

	public String getWeather() {
		ResponseEntity<WeatherResults> entity = template.getForEntity(WEATHER_URL, WeatherResults.class);
		String result = "Desculpe não encontrei";
		for (WeatherForecast wf : entity.getBody().getForecast()) {
			String city = entity.getBody().getCity_name();
			String date = wf.getDate();
			String description = entity.getBody().getDescription();
			int max = wf.getMax();
			int min = wf.getMin();
			result = construct(city, date, description, max, min);
		}
		return result;
	}
}



