package com.may.ars.dto.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pretext",
        "text"
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Attachment {

    @JsonProperty("pretext")
    public final String pretext = "";

    @JsonProperty("text")
    public String text;

}