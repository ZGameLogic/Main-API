package com.zgamelogic.dashboard.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public record EmitterMessage(
    EmitterMessageType type,
    @JsonInclude(JsonInclude.Include.NON_NULL) Object body
) {}
