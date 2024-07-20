package com.project.skincare.service.impl;

import com.project.skincare.entity.Category;
import com.project.skincare.entity.Receipt;
import com.project.skincare.entity.ReceiptInformation;
import com.project.skincare.exception.NotFoundException;
import com.project.skincare.model.BaseRequest;
import com.project.skincare.model.enumeration.Group;
import com.project.skincare.model.request.ReceiptRequest;
import com.project.skincare.model.response.GeneralResponse;
import com.project.skincare.model.response.MetadataResponse;
import com.project.skincare.repository.CategoryRepository;
import com.project.skincare.repository.ReceiptRepository;
import com.project.skincare.service.ReceiptService;
import com.project.skincare.util.ErrorMessage;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.skincare.model.enumeration.Group.INGREDIENT;
import static com.project.skincare.model.enumeration.Group.INSTRUCTION;
import static com.project.skincare.model.response.ResponseBuilder.responseBuilder;
import static com.project.skincare.util.GeneratorCode.generateReceiptCode;
import static java.lang.String.format;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final CategoryRepository categoryRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, CategoryRepository categoryRepository) {
        this.receiptRepository = receiptRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GeneralResponse<MetadataResponse, Receipt> create(ReceiptRequest request) {
        List<Category> categories = checkAndCollectCategory(request.getCategoryId());
        Receipt receipt = persist(new Receipt(), request, categories);

        return responseBuilder(receipt);
    }

    @Override
    public GeneralResponse<MetadataResponse, Receipt> get(String code) {
        List<Receipt> receipts = receiptRepository.findByCode(code);
        if (receipts.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ERROR_NOT_FOUND);
        }

        return responseBuilder(receipts.get(0));
    }

    @Override
    public GeneralResponse<MetadataResponse, List<Receipt>> getAllReceipt() {
        List<Receipt> receipts = receiptRepository.findAll();
        return responseBuilder(receipts);
    }

    @Override
    public GeneralResponse<MetadataResponse, Receipt> update(ReceiptRequest request) {
        List<Category> categories = checkAndCollectCategory(request.getCategoryId());
        Receipt receipt = persist(checkAndGetReceipt(request.getId()), request, categories);

        return responseBuilder(receipt);
    }

    @Override
    public GeneralResponse<MetadataResponse, Receipt> delete(int id) {
        Optional<Receipt> receipt = receiptRepository.findById((long) id);
        if (receipt.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ERROR_NOT_FOUND);
        }

        removeRelation(receipt.get());

        //Clear the receipt's category set
        receipt.get().getCategories().clear();

        receiptRepository.delete(receipt.get());

        return responseBuilder(null);
    }

    private Receipt persist(Receipt receipt, ReceiptRequest request, List<Category> categories) {
        boolean isNewReceipt = null == receipt.getId();

        receipt.setName(request.getName());
        receipt.setTime(request.getTime());
        receipt.setLevel(request.getLevel());
        receipt.setDescription(request.getDescription());
        receipt.setCategories(categories);
        receipt.setIngredients(getReceiptInformation(request.getIngredients(), INGREDIENT));
        receipt.setInstructions(getReceiptInformation(request.getInstructions(), INSTRUCTION));

        if (isNewReceipt) {
            receipt.setCode(checkAndGenerateCode());
            receipt.setCreatedAt(OffsetDateTime.now());
        }

        receiptRepository.saveAndFlush(receipt);
        return receipt;
    }

    private List<ReceiptInformation> getReceiptInformation(List<BaseRequest> receiptInformations, Group group) {
        List<ReceiptInformation> receiptInformationList = new ArrayList<>();

        receiptInformations.forEach(receiptInformation -> {
            ReceiptInformation receipt = new ReceiptInformation();
            receipt.setOrdering(receiptInformation.getId());
            receipt.setDescription(receiptInformation.getDescription());
            receipt.setGroup(group);

            receiptInformationList.add(receipt);
        });

        return receiptInformationList;
    }

    private List<Category> checkAndCollectCategory(List<BaseRequest> categories) {
        List<Category> collectCategory = new ArrayList<>();

        if (!categories.isEmpty()) {
            /**
             * Use Java Stream to loop
             */
             categories.forEach(category -> {

                 /**
                  * Find category by ID to database
                  * Make sure data is ready on DB
                  * Throw error if data with given ID is not found
                  */
                 Optional<Category> findCategoryById = categoryRepository.findById((long) category.getId());
                 if (findCategoryById.isEmpty()) {
                     throw new NotFoundException(format("%s%s%s", "Category with ID: ", category.getId(), " is Not Found. Please check again!"));
                 }

                 collectCategory.add(findCategoryById.get());
             });
        }

        return collectCategory;
    }

    private String checkAndGenerateCode() {
        String code = generateReceiptCode();

        List<Receipt> fromDB = receiptRepository.findByCode(code);
        if (!fromDB.isEmpty()) {

            /**
             * If code is exist on DB so check and generate again with call the same method
             * Its called with RECURSIVE METHOD
             */
            checkAndGenerateCode();
        } else {
            return code;
        }

        return code;
    }

    private Receipt checkAndGetReceipt(int id) {
        Optional<Receipt> receipt = receiptRepository.findById((long) id);

        if (receipt.isPresent()) {
            return receipt.get();
        } else {
            throw new NotFoundException(format("%s%s%s", "Receipt with ID: ", id, " is Not Found. Please check again!"));
        }
    }

    private void removeRelation(Receipt receipt) {
        for (Category category : receipt.getCategories()) {
            category.getReceipt().remove(receipt);
        }
    }

}
