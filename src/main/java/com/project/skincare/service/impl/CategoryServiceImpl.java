package com.project.skincare.service.impl;

import com.project.skincare.entity.Category;
import com.project.skincare.exception.BusinessException;
import com.project.skincare.exception.NotFoundException;
import com.project.skincare.model.request.CategoryRequest;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;
import com.project.skincare.repository.CategoryRepository;
import com.project.skincare.service.CategoryService;
import com.project.skincare.util.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.skincare.model.response.ResponseBuilder.responseBuilder;
import static com.project.skincare.util.GeneratorCode.generateCategoryCode;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GeneralResponse<MetadataResponse, Category> create(CategoryRequest request) {
        if (null == request.getName() || request.getName().isEmpty()) {
            throw new BusinessException(ErrorMessage.ERROR_MANDATORY_FIELD);
        }

        Category category = persist(request);

        return responseBuilder(category);
    }

    @Override
    public GeneralResponse<MetadataResponse, Category> get(int id) {
        Optional<Category> categories = categoryRepository.findById((long) id);
        if (categories.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ERROR_NOT_FOUND);
        }

        return responseBuilder(categories.get());
    }

    @Override
    public GeneralResponse<MetadataResponse, List<Category>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return responseBuilder(categories);
    }

    private Category persist(CategoryRequest request) {
        var prefixCategoryName = request.getName().toUpperCase().substring(0, 1);
        var categoryCode = checkAndGenerateCode(prefixCategoryName);

        Category category = new Category();
        category.setCode(categoryCode);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedAt(OffsetDateTime.now());

        categoryRepository.saveAndFlush(category);

        return category;
    }

    private String checkAndGenerateCode(String prefix) {
        String code = generateCategoryCode(prefix);

        List<Category> fromDB = categoryRepository.findByCode(code);
        if (!fromDB.isEmpty()) {
            checkAndGenerateCode(prefix);
        } else {
            return code;
        }

        return code;
    }

}
