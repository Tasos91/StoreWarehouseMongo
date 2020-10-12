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

    public boolean validateCost(String cost) {
        if (cost.length() == 3 || cost.length() == 4 || cost.length() == 5 || cost.length() == 6 || cost.length() == 7 || cost.length() == 8) {
            return cost.matches("^(\\d{1,5}\\.)\\d+$");
        } else {
            return false;
        }
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

    public boolean validateColor(String color) {
        return color.matches("^[a-zA-Z]{4,6}$");
    }

    public boolean validateDescription(String description) {
        return description.matches("^[a-zA-Z0-9_ (?)-? ?]{0,500}$");
    }

    public boolean validateGoldWeight(String goldWeight) {
        if (goldWeight.matches("^(\\d{1,2}\\.)\\d+$") && goldWeight.length() == 3
                || goldWeight.length() == 4 || goldWeight.length() == 5) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateKarats(Integer karats) {
        if (karats == 6 || karats == 8 || karats == 9 || karats == 18 || karats == 22 || karats == 24) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePrice(String price) {
        if (price.matches("^(\\d{1,6}\\.)\\d+$") && (price.length() == 3 || price.length() == 4 ||
                price.length() == 5 || price.length() == 6 || price.length() == 7 || price.length() == 8 || price.length() == 9)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateOtherStoneWeight(String otherStoneWeight) {
        if (otherStoneWeight.matches("^(\\d{1,2}\\.)\\d+$") && (otherStoneWeight.length() == 3 || otherStoneWeight.length() == 4 ||
                otherStoneWeight.length() == 5)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateDiamondWeight(String diamondWeight) {
        if (diamondWeight.matches("^(\\d{1,2}\\.)\\d+$") && (diamondWeight.length() == 3 || diamondWeight.length() == 4 ||
                diamondWeight.length() == 5)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateQuantity(Integer quantity) {
        if (quantity >= 0 && quantity <= 1000) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateSKUComingFromDB(String sku) {
        if (sku.matches("^[a-zA-Z0-9-]{7,7}$") && sku.contains("-")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateOtherStone(String otherStone) {
        return otherStone.matches("^[a-zA-Z0-9, ?]{0,500}$");
    }

}
