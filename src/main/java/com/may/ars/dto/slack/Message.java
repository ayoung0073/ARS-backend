package com.may.ars.dto.slack;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "channel",
        "blocks"
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Message {

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("tex")
    private String text;

    @JsonProperty("attachments")
    public List<Attachment> attachments = new ArrayList<>();

}
