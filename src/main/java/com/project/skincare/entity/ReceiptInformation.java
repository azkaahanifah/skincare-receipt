package com.project.skincare.entity;

import com.project.skincare.model.enumeration.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiptInformation {
    private int ordering;

    private String description;

    private Group group;

}
