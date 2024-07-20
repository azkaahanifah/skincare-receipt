package com.project.skincare.model.response;

import java.time.LocalDateTime;

public class ResponseBuilder {

    public static <T> GeneralResponse<MetadataResponse, T> responseBuilder(T response) {
        var meta = new MetadataResponse("Your request has been processed successfully", LocalDateTime.now());

        GeneralResponse<MetadataResponse, T> generalResponse = new GeneralResponse<>();
        generalResponse.setMetadata(meta);
        generalResponse.setData(response);

        return generalResponse;
    }

}
