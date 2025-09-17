package org.example.jws_gruppuppgift.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeApiResponseDTO
{
    @JsonProperty("success")
    public boolean success;

    @JsonProperty("base")
    public String base;

    @JsonProperty("date")
    public String date;

    @JsonProperty("rates")
    public Map<String, Float> rates;


}
