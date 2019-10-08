package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.helpers.Filter;
import com.example.StoreWarehouseMongo1.helpers.Pagination;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.model.Token;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import com.example.StoreWarehouseMongo1.repositories.TokenRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/filterBy")
@CrossOrigin(origins = "*")
public class ShowProductsByFiltered {

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private Pagination pagination;

    @PostMapping("/{username}/{password}") //login controller
    public Token doLogin(@PathVariable("username") String username, @PathVariable("password") String password) {

        String alphanumeric = UUID.randomUUID().toString();
        List<Store> stores = storerepository.findByUsernameAndPassword(username, password);
        String usersId = stores.get(0).getId();
        String usernameFromStore = stores.get(0).getUsername();
        Token token = new Token(alphanumeric, usersId, usernameFromStore, stores.get(0).getEnabledUser());
        tokenRepository.save(token);
        return token;
    }

    @RequestMapping(value = "/store/{address}/{pageNumber}", method = GET) //this controller shows all products for the specified store for the first page
    public List<Product> showProductsByPageAndAddress(@PathVariable("address") String address, @PathVariable("pageNumber") int page) {
        List<Product> prPerPage = pagination.paginator(page);
        return Filter.getProductsForThisStore(prPerPage, address);
    }

    @RequestMapping(value = "/category/{address}/{category}/{pageNumber}", method = GET) //this controller shows all products for the specified store for the first page
    public List<Product> showProductsByPageAndAddressAndCategory(@PathVariable("address") String address, @PathVariable("category") String category,
            @PathVariable("pageNumber") int page) {
        List<Product> prPerPage = pagination.paginator(page);
        return Filter.getProductsPerStoreAndCategory(prPerPage, address, category);
    }

    @RequestMapping(value = "/producer/{address}/{producerCode}/{pageNumber}", method = GET) //this controller shows all products for the specified store for the first page
    public List<Product> showProductsByPageAndAddressAndProducerCode(@PathVariable("address") String address, @PathVariable("producerCode") String producerCode,
            @PathVariable("pageNumber") int page) {

        return Filter.getProductsPerStoreAndProducerCode(pagination.paginator(page), address, producerCode);
    }
}
