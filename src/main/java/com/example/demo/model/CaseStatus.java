package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CaseStatus {

    @JsonProperty("OPEN")
    OPEN,

    @JsonProperty("IN_PROGRESS")
    IN_PROGRESS,
    @JsonProperty("ASSIGNED")
    ASSIGNED,

    @JsonProperty("CLOSED")
    CLOSED;
}
