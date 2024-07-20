package com.project.skincare.service;

import com.project.skincare.entity.Category;
import com.project.skincare.model.request.CategoryRequest;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;

import java.util.List;

public interface CategoryService {

    GeneralResponse<MetadataResponse, Category> create(CategoryRequest request);

    GeneralResponse<MetadataResponse, Category> get(int id);

    GeneralResponse<MetadataResponse, List<Category>> getCategories();
}
