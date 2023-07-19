package com.fuzail.caffeine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrencyConvertResponse {
    public boolean success;
    public Query query;
    public Info info;
    public String date;
    public double result;


    public class Info {
        public int timestamp;
        public double rate;
    }

    public class Query {
        public String from;
        @JsonProperty("to")
        public String myto;
        public int amount;
    }
}