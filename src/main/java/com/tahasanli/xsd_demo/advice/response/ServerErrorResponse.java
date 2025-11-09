package com.tahasanli.xsd_demo.advice.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerErrorResponse {
    private String message;
}
