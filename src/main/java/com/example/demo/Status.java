package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("OPEN")
    OPEN,

    @JsonProperty("IN_PROGRESS")
    IN_PROGRESS,
    @JsonProperty("ASSIGNED")
    ASSIGNED,

    @JsonProperty("CLOSED")
    CLOSED;
}
