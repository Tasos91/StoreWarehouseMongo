package com.example.StoreWarehouseMongo1.helpers;

import com.example.StoreWarehouseMongo1.Exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public boolean validateProductId(String productId) {
        return productId.matches("^[a-zA-Z0-9]{24,24}$");
    }

    public boolean validateCategoryId(String categoryId) {
        return categoryId.matches("^[a-zA-Z0-9]{0,24}$");
    }

    public boolean validateStoreId(String storeId) {
        return storeId.matches("^[a-zA-Z0-9]{24,24}$");
    }

    public boolean validateProducerId(String producerId) {
        return producerId.matches("^[a-zA-Z0-9]{0,24}$");
    }

    public boolean validateUserPassword(String producerId) {
        return producerId.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&+*()!_=])(?=\\S+$).{6,10}$");
    }

    public boolean validateAddress(String address) {
        return address.matches("^[a-zA-Z]{2,20}$");
    }

    public boolean validatePage(String page) {
        return page.matches("^(10000|[0-9]?[0-9]?[0-9]?[0-9]?)$");
    }

    public boolean validateDate(String date) {
        return date.matches("[0-9]{4}[/](0[1-9]|1[0-2])[/](0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$");
    }

    public boolean validateQuantity(String quantity) {
        return quantity.matches("^(1000|[0-9]?[0-9]?[0-9])$");
    }

    public void validateParamsOfGetProducts(String page, String categoryId, String producerId, String address, String limit) {
        if (validatePage(page) == false) {
            throw new PageNotValidException("Page is not valid");
        }
        if (validateCategoryId(categoryId) == false) {
            throw new CategoryIdNotValidException("CategoryId is not valid");
        }
        if (validateProducerId(producerId) == false) {
            throw new ProducerIdNotValidException("ProducerId is not valid");
        }
        if (validateAddress(address) == false) {
            throw new AddressNotValidException("Address is not valid");
        }
        if (validateLimit(limit) == false) {
            throw new LimitNotValidException("Limit is not valid");
        }
    }

    public void validateParamsOfGetHistory(String productId, String address, String startDate, String endDate, String limitString, String page) {
        if (validatePage(page) == false) {
            throw new PageNotValidException("Page is not valid");
        }
        if (validateProductId(productId) == false) {
            throw new ProductNotFoundException("ProductId is not valid");
        }
        if (validateAddress(address) == false) {
            throw new AddressNotValidException("Address is not valid");
        }
        if (validateLimit(limitString) == false) {
            throw new LimitNotValidException("Limit is not valid");
        }
        if (validateDate(startDate) == false) {
            throw new DateNotValidException("Start Date is not valid");
        }
        if (validateDate(endDate) == false) {
            throw new DateNotValidException("End Date is not valid");
        }
    }

    public boolean validateLimit(String limit) {
        boolean limitIsOk = true;
        if (limit.equals("12")) {
            return limitIsOk;
        }
        if (limit.equals("16")) {
            return limitIsOk;
        }
        if (limit.equals("20")) {
            return limitIsOk;
        }
        if (limit.equals("24")) {
            return limitIsOk;
        }
        if (limit.equals("28")) {
            return limitIsOk;
        } else {
            return false;
        }
    }

    public boolean validateSKU(String sku) {
        return sku.matches("^[a-zA-Z0-9]{5,5}$");
    }

    public boolean validateSKUComingFromDB(String sku) {
        if (sku.matches("^[a-zA-Z0-9-]{7,7}$") && sku.contains("-")) {
            return true;
        } else {
            return false;
        }
    }

}
