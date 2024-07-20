package com.project.skincare.controller;

import com.project.skincare.entity.Category;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;
import com.project.skincare.model.request.CategoryRequest;
import com.project.skincare.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base.path}")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/category")
    public GeneralResponse<MetadataResponse, Category> create(@RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping(value = "/category/{id}")
    public GeneralResponse<MetadataResponse, Category> get(@PathVariable int id) {
        return categoryService.get(id);
    }

    @GetMapping(value = "/categories")
    public GeneralResponse<MetadataResponse, List<Category>> getCategories() {
        return categoryService.getCategories();
    }

}
