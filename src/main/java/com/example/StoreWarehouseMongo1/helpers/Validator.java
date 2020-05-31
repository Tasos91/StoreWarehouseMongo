package com.example.StoreWarehouseMongo1.helpers;

import org.springframework.stereotype.Component;

@Component
public class Validator {

    public boolean validateSKU(String sku) {
        return sku.matches("^[a-zA-Z0-9]{5,5}$");
    }

    public boolean validateProductId(String productId) {
        return productId.matches("^[a-zA-Z0-9]{24,24}$");
    }

    public boolean validateCategoryId(String categoryId) {
        return categoryId.matches("^[a-zA-Z0-9]{24,24}$");
    }

    public boolean validateProducerId(String producerId) {
        return producerId.matches("^[a-zA-Z0-9]{24,24}$");
    }

    public boolean validateAddress(String address) {
        return address.matches("^[a-zA-Z]{2,20}$");
    }

    public boolean validateLimit(String limit) {
        boolean isLimitOk = true;
        if (!limit.equals("12")) {
            isLimitOk = false;
        }
        if (!limit.equals("16")) {
            isLimitOk = false;
        }
        if (!limit.equals("20")) {
            isLimitOk = false;
        }
        if (!limit.equals("24")) {
            isLimitOk = false;
        }
        if (!limit.equals("28")) {
            isLimitOk = false;
        }
        return isLimitOk;
    }

    public boolean validatePage(String page) {
        return page.matches("^(10000|[0-9]?[0-9]?[0-9]?[0-9]?)$");
    }

    public boolean validateQuantity(String quantity) {
        return quantity.matches("^(1000|[0-9]?[0-9]?[0-9])$");
    }


}
