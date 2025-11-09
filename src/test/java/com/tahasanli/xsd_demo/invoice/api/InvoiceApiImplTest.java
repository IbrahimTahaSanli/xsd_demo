package com.tahasanli.xsd_demo.invoice.api;

import com.tahasanli.xsd_demo.invoice.service.InvoiceService;
import com.tahasanli.xsd_demo.invoice.service.input.InvoiceUploadInput;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceServiceStatus;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceUploadOutput;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvoiceApiImpl.class)
public class InvoiceApiImplTest {
    private final String VALID_REQUEST_XML = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPEZha3R1cmEgeG1sbnM6eHNkPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYSIgeG1sbnM6eHNpPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYS1pbnN0YW5jZSIgeG1sbnM9Imh0dHA6Ly9jcmQuZ292LnBsL3d6b3IvMjAyMy8wNi8yOS8xMjY0OC8iPgogICAgPE5hZ2xvd2VrPgogICAgICAgIDxLb2RGb3JtdWxhcnphIGtvZFN5c3RlbW93eT0iRkEgKDIpIiB3ZXJzamFTY2hlbXk9IjEtMEUiPkZBPC9Lb2RGb3JtdWxhcnphPgogICAgICAgIDxXYXJpYW50Rm9ybXVsYXJ6YT4yPC9XYXJpYW50Rm9ybXVsYXJ6YT4KICAgICAgICA8RGF0YVd5dHdvcnplbmlhRmE+MjAyMy0wOC0yOVQxMjozNDoxMy43ODAyNTcxWjwvRGF0YVd5dHdvcnplbmlhRmE+CiAgICAgICAgPFN5c3RlbUluZm8+U2FtcGxvZmFrdHVyPC9TeXN0ZW1JbmZvPgogICAgPC9OYWdsb3dlaz4KICAgIDxQb2RtaW90MT4KICAgICAgICA8RGFuZUlkZW50eWZpa2FjeWpuZT4KICAgICAgICAgICAgPE5JUD45NzgxMzk5MjU5PC9OSVA+CiAgICAgICAgICAgIDxOYXp3YT5BQkMgQUdEIHNwLiB6IG8uIG8uPC9OYXp3YT4KICAgICAgICA8L0RhbmVJZGVudHlmaWthY3lqbmU+CiAgICAgICAgPEFkcmVzPgogICAgICAgICAgICA8S29kS3JhanU+UEw8L0tvZEtyYWp1PgogICAgICAgICAgICA8QWRyZXNMMT51bC4gS3dpYXRvd2EgMTwvQWRyZXNMMT4KICAgICAgICA8L0FkcmVzPgogICAgICAgIDxEYW5lS29udGFrdG93ZT4KICAgICAgICAgICAgPEVtYWlsPmV4YW1wbGVAZXhhbXBsZS5jb208L0VtYWlsPgogICAgICAgIDwvRGFuZUtvbnRha3Rvd2U+CiAgICA8L1BvZG1pb3QxPgogICAgPFBvZG1pb3QyPgogICAgICAgIDxEYW5lSWRlbnR5ZmlrYWN5am5lPgogICAgICAgICAgICA8TklQPjEyNTA3NTM1MDU8L05JUD4KICAgICAgICAgICAgPE5hendhPkNlRGVFIHMuYy48L05hendhPgogICAgICAgIDwvRGFuZUlkZW50eWZpa2FjeWpuZT4KICAgICAgICA8QWRyZXM+CiAgICAgICAgICAgIDxLb2RLcmFqdT5QTDwvS29kS3JhanU+CiAgICAgICAgICAgIDxBZHJlc0wxPnVsaWNhIGkgbnVtZXI8L0FkcmVzTDE+CiAgICAgICAgPC9BZHJlcz4KICAgIDwvUG9kbWlvdDI+CiAgICA8RmE+CiAgICAgICAgPEtvZFdhbHV0eT5QTE48L0tvZFdhbHV0eT4KICAgICAgICA8UF8xPjIwMjMtMDgtMzE8L1BfMT4KICAgICAgICA8UF8yPkZLMjAyMy8wOC8zMTwvUF8yPgogICAgICAgIDxQXzEzXzE+MDwvUF8xM18xPgogICAgICAgIDxQXzE0XzE+MDwvUF8xNF8xPgogICAgICAgIDxQXzEzXzI+MDwvUF8xM18yPgogICAgICAgIDxQXzE0XzI+MDwvUF8xNF8yPgogICAgICAgIDxQXzEzXzM+MDwvUF8xM18zPgogICAgICAgIDxQXzE0XzM+MDwvUF8xNF8zPgogICAgICAgIDxQXzEzXzQ+MDwvUF8xM180PgogICAgICAgIDxQXzE0XzQ+MDwvUF8xNF80PgogICAgICAgIDxQXzEzXzU+MDwvUF8xM181PgogICAgICAgIDxQXzEzXzc+NDAwMS40OTwvUF8xM183PgogICAgICAgIDxQXzE1PjQwMDEuNDk8L1BfMTU+CiAgICAgICAgPEFkbm90YWNqZT4KICAgICAgICAgICAgPFBfMTY+MjwvUF8xNj4KICAgICAgICAgICAgPFBfMTc+MjwvUF8xNz4KICAgICAgICAgICAgPFBfMTg+MjwvUF8xOD4KICAgICAgICAgICAgPFBfMThBPjI8L1BfMThBPgogICAgICAgICAgICA8WndvbG5pZW5pZT4KICAgICAgICAgICAgICAgIDxQXzE5Tj4xPC9QXzE5Tj4KICAgICAgICAgICAgPC9ad29sbmllbmllPgogICAgICAgICAgICA8Tm93ZVNyb2RraVRyYW5zcG9ydHU+CiAgICAgICAgICAgICAgICA8UF8yMk4+MTwvUF8yMk4+CiAgICAgICAgICAgIDwvTm93ZVNyb2RraVRyYW5zcG9ydHU+CiAgICAgICAgICAgIDxQXzIzPjI8L1BfMjM+CiAgICAgICAgICAgIDxQTWFyenk+CiAgICAgICAgICAgICAgICA8UF9QTWFyenlOPjE8L1BfUE1hcnp5Tj4KICAgICAgICAgICAgPC9QTWFyenk+CiAgICAgICAgPC9BZG5vdGFjamU+CiAgICAgICAgPFJvZHphakZha3R1cnk+VkFUPC9Sb2R6YWpGYWt0dXJ5PgogICAgICAgIDxGYVdpZXJzej4KICAgICAgICAgICAgPE5yV2llcnN6YUZhPjE8L05yV2llcnN6YUZhPgogICAgICAgICAgICA8UF83PlNwcnplZGHFvCB0b3dhcsOzdyAyMyU8L1BfNz4KICAgICAgICAgICAgPFBfOEE+c3p0LjwvUF84QT4KICAgICAgICAgICAgPFBfOEI+Mi4zMjM8L1BfOEI+CiAgICAgICAgICAgIDxQXzlBPjIzNC4yNDwvUF85QT4KICAgICAgICAgICAgPFBfMTE+NTQ0LjE0PC9QXzExPgogICAgICAgICAgICA8UF8xMj56dzwvUF8xMj4KICAgICAgICA8L0ZhV2llcnN6PgogICAgICAgIDxGYVdpZXJzej4KICAgICAgICAgICAgPE5yV2llcnN6YUZhPjI8L05yV2llcnN6YUZhPgogICAgICAgICAgICA8UF83PkdUVV8xPC9QXzc+CiAgICAgICAgICAgIDxQXzhBPi08L1BfOEE+CiAgICAgICAgICAgIDxQXzhCPjIuNTYxPC9QXzhCPgogICAgICAgICAgICA8UF85QT4xMzUwLjAwPC9QXzlBPgogICAgICAgICAgICA8UF8xMT4zNDU3LjM1PC9QXzExPgogICAgICAgICAgICA8UF8xMj56dzwvUF8xMj4KICAgICAgICA8L0ZhV2llcnN6PgogICAgPC9GYT4KPC9GYWt0dXJhPg==";
    private final String NOT_VALID_REQUEST_XML = "L05JUD4KICAgICAgICAgICAgPE5hendhPkFCQyBBR0Qgc3AuIHogby4gby48L05hendhPgogICAgICAgIDwvRGFuZUlkZW50eWZpa2FjeWpuZT4KICAgICAgICA8QWRyZXM+CiAgICAgICAgICAgIDxLb2RLcmFqdT5QTDwvS29kS3JhanU+CiAgICAgICAgICAgIDxBZHJlc0wxPnVsLiBLd2lhdG93YSAxPC9BZHJlc0wxPgogICAgICAgIDwvQWRyZXM+CiAgICAgICAgPERhbmVLb250YWt0b3dlPgogICAgICAgICAgICA8RW1haWw+ZXhhbXBsZUBleGFtcGxlLmNvbTwvRW1haWw+CiAgICAgICAgPC9EYW5lS29udGFrdG93ZT4KICAgIDwvUG9kbWlvdDE+CiAgICA8UG9kbWlvdDI+CiAgICAgICAgPERhbmVJZGVudHlmaWthY3lqbmU+CiAgICAgICAgICAgIDxOSVA+MTI1MDc1MzUwNTwvTklQPgogICAgICAgICAgICA8TmF6d2E+Q2VEZUUgcy5jLjwvTmF6d2E+CiAgICAgICAgPC9EYW5lSWRlbnR5ZmlrYWN5am5lPgogICAgICAgIDxBZHJlcz4KICAgICAgICAgICAgPEtvZEtyYWp1PlBMPC9Lb2RLcmFqdT4KICAgICAgICAgICAgPEFkcmVzTDE+dWxpY2EgaSBudW1lcjwvQWRyZXNMMT4KICAgICAgICA8L0FkcmVzPgogICAgPC9Qb2RtaW90Mj4KICAgIDxGYT4KICAgICAgICA8S29kV2FsdXR5PlBMTjwvS29kV2FsdXR5PgogICAgICAgIDxQXzE+MjAyMy0wOC0zMTwvUF8xPgogICAgICAgIDxQXzI+RksyMDIzLzA4LzMxPC9QXzI+CiAgICAgICAgPFBfMTNfMT4wPC9QXzEzXzE+CiAgICAgICAgPFBfMTRfMT4wPC9QXzE0XzE+CiAgICAgICAgPFBfMTNfMj4wPC9QXzEzXzI+CiAgICAgICAgPFBfMTRfMj4wPC9QXzE0XzI+CiAgICAgICAgPFBfMTNfMz4wPC9QXzEzXzM+CiAgICAgICAgPFBfMTRfMz4wPC9QXzE0XzM+CiAgICAgICAgPFBfMTNfND4wPC9QXzEzXzQ+CiAgICAgICAgPFBfMTRfND4wPC9QXzE0XzQ+CiAgICAgICAgPFBfMTNfNT4wPC9QXzEzXzU+CiAgICAgICAgPFBfMTNfNz40MDAxLjQ5PC9QXzEzXzc+CiAgICAgICAgPFBfMTU+NDAwMS40OTwvUF8xNT4KICAgICAgICA8QWRub3RhY2plPgogICAgICAgICAgICA8UF8xNj4yPC9QXzE2PgogICAgICAgICAgICA8UF8xNz4yPC9QXzE3PgogICAgICAgICAgICA8UF8xOD4yPC9QXzE4PgogICAgICAgICAgICA8UF8xOEE+MjwvUF8xOEE+CiAgICAgICAgICAgIDxad29sbmllbmllPgogICAgICAgICAgICAgICAgPFBfMTlOPjE8L1BfMTlOPgogICAgICAgICAgICA8L1p3b2xuaWVuaWU+CiAgICAgICAgICAgIDxOb3dlU3JvZGtpVHJhbnNwb3J0dT4KICAgICAgICAgICAgICAgIDxQXzIyTj4xPC9QXzIyTj4KICAgICAgICAgICAgPC9Ob3dlU3JvZGtpVHJhbnNwb3J0dT4KICAgICAgICAgICAgPFBfMjM+MjwvUF8yMz4KICAgICAgICAgICAgPFBNYXJ6eT4KICAgICAgICAgICAgICAgIDxQX1BNYXJ6eU4+MTwvUF9QTWFyenlOPgogICAgICAgICAgICA8L1BNYXJ6eT4KICAgICAgICA8L0Fkbm90YWNqZT4KICAgICAgICA8Um9kemFqRmFrdHVyeT5WQVQ8L1JvZHphakZha3R1cnk+CiAgICAgICAgPEZhV2llcnN6PgogICAgICAgICAgICA8TnJXaWVyc3phRmE+MTwvTnJXaWVyc3phRmE+CiAgICAgICAgICAgIDxQXzc+U3ByemVkYcW8IHRvd2Fyw7N3IDIzJTwvUF83PgogICAgICAgICAgICA8UF84QT5zenQuPC9QXzhBPgogICAgICAgICAgICA8UF84Qj4yLjMyMzwvUF84Qj4KICAgICAgICAgICAgPFBfOUE+MjM0LjI0PC9QXzlBPgogICAgICAgICAgICA8UF8xMT41NDQuMTQ8L1BfMTE+CiAgICAgICAgICAgIDxQXzEyPnp3PC9QXzEyPgogICAgICAgIDwvRmFXaWVyc3o+CiAgICAgICAgPEZhV2llcnN6PgogICAgICAgICAgICA8TnJXaWVyc3phRmE+MjwvTnJXaWVyc3phRmE+CiAgICAgICAgICAgIDxQXzc+R1RVXzE8L1BfNz4KICAgICAgICAgICAgPFBfOEE+LTwvUF84QT4KICAgICAgICAgICAgPFBfOEI+Mi41NjE8L1BfOEI+CiAgICAgICAgICAgIDxQXzlBPjEzNTAuMDA8L1BfOUE+CiAgICAgICAgICAgIDxQXzExPjM0NTcuMzU8L1BfMTE+CiAgICAgICAgICAgIDxQXzEyPnp3PC9QXzEyPgogICAgICAgIDwvRmFXaWVyc3o+CiAgICA8L0ZhPgo8L0Zha3R1cmE+";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private InvoiceService service;

    private String createRequest(String xml) {
        return "{\"base64xml\": \"" + xml + "\"}";
    }

    @Test
    @SneakyThrows
    public void whenValidRequest_thenReturnOkay() {
        InvoiceUploadOutput output = InvoiceUploadOutput.builder()
                .status(InvoiceServiceStatus.SUCCESS)
                .message("asd")
                .build();
        when(service.saveInvoice(any(InvoiceUploadInput.class))).thenReturn(output);

        String requestBody = createRequest(VALID_REQUEST_XML);
        mockMvc.perform(post("/api/invoices").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());

        verify(service, times(1)).saveInvoice(any(InvoiceUploadInput.class));
    }

    @Test
    @SneakyThrows
    public void whenNotValidRequest_thenReturn400() {
        InvoiceUploadOutput output = InvoiceUploadOutput.builder()
                .status(InvoiceServiceStatus.BAD_REQUEST)
                .message("asd")
                .build();
        when(service.saveInvoice(any(InvoiceUploadInput.class))).thenReturn(output);

        String requestBody = createRequest(VALID_REQUEST_XML);
        mockMvc.perform(post("/api/invoices").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());

        verify(service, times(1)).saveInvoice(any(InvoiceUploadInput.class));
    }

    @Test
    @SneakyThrows
    public void whenInternalErrorHappen_thenReturn500() {
        InvoiceUploadOutput output = InvoiceUploadOutput.builder()
                .status(InvoiceServiceStatus.INTERNAL_ERROR)
                .message("asd")
                .build();
        when(service.saveInvoice(any(InvoiceUploadInput.class))).thenReturn(output);

        String requestBody = createRequest(VALID_REQUEST_XML);
        mockMvc.perform(post("/api/invoices").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isInternalServerError());

        verify(service, times(1)).saveInvoice(any(InvoiceUploadInput.class));
    }
}
