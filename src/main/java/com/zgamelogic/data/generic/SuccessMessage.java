package com.zgamelogic.data.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessMessage {
    private boolean Success;
    private String message;
}
