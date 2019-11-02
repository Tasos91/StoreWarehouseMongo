package com.example.StoreWarehouseMongo1.helpers;

import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tasos
 */
@Component
public class Pagination {

    @Autowired
    private ProductRepository productrepository;

    
    public List<Product> paginator(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Product> pageproduct = productrepository.findAll(pageable).getContent();
        return pageproduct;
    }

}
