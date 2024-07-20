package com.project.skincare.service;

import com.project.skincare.entity.Receipt;
import com.project.skincare.model.request.ReceiptRequest;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;

import java.util.List;

public interface ReceiptService {

    GeneralResponse<MetadataResponse, Receipt> create(ReceiptRequest request);

    GeneralResponse<MetadataResponse, Receipt> get(String code);

    GeneralResponse<MetadataResponse, List<Receipt>> getAllReceipt();

    GeneralResponse<MetadataResponse, Receipt> update(ReceiptRequest request);

    GeneralResponse<MetadataResponse, Receipt> delete(int id);

}
