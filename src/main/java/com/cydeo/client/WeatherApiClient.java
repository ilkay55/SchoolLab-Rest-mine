package com.cydeo.client;

import com.cydeo.dto.weather.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://api.weatherstack.com",name = "WEATHER-CLIENT")
public interface WeatherApiClient {
//  http://api.weatherstack.com --> url
// /current -->endpoint
// ? -> end of the url
// access_key=87e785a9468054d73eaf989af1e4a99a
// &
// query=London

    @GetMapping("/current")
    WeatherResponse getCurrentWeather(@RequestParam(value = "access_key") String accessKey,
                                      @RequestParam(value = "query") String city);
}
