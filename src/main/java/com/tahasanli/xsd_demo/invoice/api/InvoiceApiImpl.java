package com.tahasanli.xsd_demo.invoice.api;

import com.tahasanli.xsd_demo.exception.BadRequestException;
import com.tahasanli.xsd_demo.exception.InternalErrorException;
import com.tahasanli.xsd_demo.invoice.api.request.InvoicePostRequest;
import com.tahasanli.xsd_demo.invoice.api.response.InvoicePostSuccessResponse;
import com.tahasanli.xsd_demo.invoice.service.InvoiceService;
import com.tahasanli.xsd_demo.invoice.service.input.InvoiceUploadInput;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceUploadOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class InvoiceApiImpl implements InvoiceApi {
    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<InvoicePostSuccessResponse> postInvoice(InvoicePostRequest request) {
        InvoiceUploadInput input = InvoiceUploadInput.builder()
                .xsdXml(decodeBase64(request.getXmlBase64()))
                .build();
        InvoiceUploadOutput output = invoiceService.saveInvoice(input);

        switch (output.getStatus()) {
            case BAD_REQUEST:
                throw new BadRequestException(output.getMessage());
            case INTERNAL_ERROR:
                throw new InternalErrorException(output.getMessage());
            case SUCCESS:
                break;
            default:
                throw new InternalErrorException(output.getMessage());
        }

        InvoicePostSuccessResponse response = InvoicePostSuccessResponse.builder()
                .message(output.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String decodeBase64(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }
}
