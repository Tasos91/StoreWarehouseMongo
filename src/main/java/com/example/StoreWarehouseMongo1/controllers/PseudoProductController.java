package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.PseudoProduct;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.model.Store;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.PseudoProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import com.example.StoreWarehouseMongo1.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/pseudo")
@CrossOrigin(origins = "*")
public class PseudoProductController {

    @Autowired
    private PseudoProductRepository pseudoproductrepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private StoreRepository storerepository;

    @Autowired
    private StockRepository stockrepository;

    @RequestMapping(value = "/get/pseudoProducts/{address}", method = GET)
    public List<PseudoProduct> getPseudoProducts(@PathVariable("address") String shop) { //fernei ta pseudoproducts kai kanei kai insert to kathena an den uparxei hdh
        Store store = storerepository.findByaddress(shop).get(0);
        List<PseudoProduct> pseudoProducts = new ArrayList();
        List<Stock> stock = store.getStock();
        for (Stock st : stock) {
            Product pr = productrepository.findByproductcode(st.getProductId()).get(0);
            PseudoProduct psprod = new PseudoProduct(pr, pr.getProductcode(), st);
            try {
                List<PseudoProduct> pseprod = pseudoproductrepository.findByproductcode(psprod.getProductcode());
                if (pseprod.isEmpty()) {
                    pseudoproductrepository.save(psprod);
                }
            } catch (Exception e) {
            }
            pseudoProducts.add(psprod);
        }
        return pseudoProducts;
    }

    @RequestMapping(value = "/get/pseudoProduct/{address}/{productcode}", method = GET)
    public PseudoProduct getPseudoproduct(@PathVariable("address") String shop,
            @PathVariable("productcode") String productcode) {
        return pseudoproductrepository.findByproductcode(productcode).get(0);
    }

    @RequestMapping(value = "/update/pseudoProduct/{productcode}/{quantity}", method = PUT)
    public PseudoProduct updatePseudoproduct(@PathVariable("productcode") String productcode,
            @PathVariable("quantity") Integer quantity) {

        Stock stock = stockrepository.findByproductId(productcode).get(0);
        stock.setQuantity(quantity);
        stockrepository.save(stock);
        PseudoProduct pseudoproduct = pseudoproductrepository.findByproductcode(productcode).get(0);
        pseudoproduct.setStock(stock);
        pseudoproductrepository.save(pseudoproduct);

        return pseudoproductrepository.findByproductcode(productcode).get(0);
    }
}
