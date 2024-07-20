package com.project.skincare.controller;

import com.project.skincare.entity.Receipt;
import com.project.skincare.model.request.ReceiptRequest;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;
import com.project.skincare.service.ReceiptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base.path}")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/receipt")
    public GeneralResponse<MetadataResponse, Receipt> create(@RequestBody ReceiptRequest request) {
        return receiptService.create(request);
    }

    @GetMapping(value = "/receipt/{code}")
    public GeneralResponse<MetadataResponse, Receipt> get(@PathVariable String code) {
        return receiptService.get(code);
    }

    @GetMapping(value = "/receipts")
    public GeneralResponse<MetadataResponse, List<Receipt>> getAllReceipt() {
        return receiptService.getAllReceipt();
    }

    @PutMapping(value = "/receipt")
    public GeneralResponse<MetadataResponse, Receipt> update(@RequestBody ReceiptRequest request) {
        return receiptService.update(request);
    }

    @DeleteMapping(value = "receipt/{id}")
    public GeneralResponse<MetadataResponse, Receipt> delete(@PathVariable int id) {
        return receiptService.delete(id);
    }

}
