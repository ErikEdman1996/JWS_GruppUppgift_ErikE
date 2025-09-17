package org.example.jws_gruppuppgift.services;
import org.example.jws_gruppuppgift.dtos.ExchangeApiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyService
{
    private RestClient restClient;

    @Value("${api.key}")
    private String apiKey;

    public CurrencyService(RestClient.Builder clientBuilder)
    {
        this.restClient = clientBuilder
                .baseUrl("https://api.exchangeratesapi.io/v1/")
                .build();
    }

    @Cacheable("exchangeRate")
    public float getSEKtoEURRate()
    {
        String url = "/latest?access_key=" + apiKey
                + "&base=EUR"
                + "&symbols=SEK";

        ExchangeApiResponseDTO response = restClient.get()
                .uri(url)
                .retrieve()
                .body(ExchangeApiResponseDTO.class);

        if (response != null && response.success)
        {
            return response.rates.get("SEK");
        }
        else
        {
            throw new RuntimeException("Currency conversion failed");
        }
    }

}
