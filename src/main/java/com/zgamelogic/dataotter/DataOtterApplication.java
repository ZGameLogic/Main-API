package com.zgamelogic.dataotter;

public record DataOtterApplication(
        long id,
        String name,
        String description,
        boolean status
) {}
