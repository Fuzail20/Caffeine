package com.fuzail.caffeine.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuzail.caffeine.model.CurrencyConvertResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FixerService {
    public CurrencyConvertResponse convertCurrency(String toCurrency , String fromCurrency , String amount) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        String url = "https://api.apilayer.com/fixer/convert?";
        url +="to=" + toCurrency +"&from=" + fromCurrency +"&amount="+ amount;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", "V90yOfDTIPhHpGBIoWTwKiqPhQmBHmwD")
                .method("GET",null)
            .build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(response.body().string(), CurrencyConvertResponse.class);
        }catch (Exception e){
            throw new IOException("Exception while fetching data from external client");
        }

    }
}
