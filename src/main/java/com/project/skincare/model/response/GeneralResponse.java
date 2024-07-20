package com.project.skincare.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponse<T, U> {

    private T metadata;

    private U data;

}
