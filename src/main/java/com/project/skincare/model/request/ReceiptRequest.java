package com.project.skincare.model.request;

import com.project.skincare.model.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceiptRequest extends BaseRequest {

    private String time;

    private String level;

    private List<BaseRequest> categoryId;

    private List<BaseRequest> ingredients;

    private List<BaseRequest> instructions;

}
