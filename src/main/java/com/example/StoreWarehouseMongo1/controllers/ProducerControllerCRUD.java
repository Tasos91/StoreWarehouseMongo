package com.example.StoreWarehouseMongo1.controllers;

import com.example.StoreWarehouseMongo1.model.Producer;
import com.example.StoreWarehouseMongo1.model.Product;
import com.example.StoreWarehouseMongo1.model.Stock;
import com.example.StoreWarehouseMongo1.repositories.ProducerRepository;
import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
import com.example.StoreWarehouseMongo1.repositories.StockRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tasos
 */
@RestController
@RequestMapping("/api/producer")
@CrossOrigin(origins = "*")
public class ProducerControllerCRUD {

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StockRepository stockRepository;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProducer(@RequestBody Producer producer) {
        try {
            String producerCode = producer.getProducerCode();
            for (Producer pro : producerRepository.findAll()) {
                if (pro.getProducerCode().equals(producerCode)) {
                    return new ResponseEntity<>("This producer already exists", HttpStatus.CONFLICT);
                } else {
                    producerRepository.save(producer);
                }
            }
            producerRepository.save(producer);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
        return null;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<Producer> categories = producerRepository.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("producerId") String producerId) {
        try {
            return new ResponseEntity<>(producerRepository.findById(producerId).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Producer not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<?> updateProducer(@RequestBody Producer producer, @RequestParam("producerId") String producerId) {
        try {
            String producerCode = producer.getProducerCode();
            String producerName = producer.getProducerName();
            for (Producer pr : producerRepository.findAll()) {
                if ((pr.getId().equals(producerId))) {
                    Producer producerTosave = producerRepository.findById(producerId).get();
                    producerTosave.setProducerCode(producerCode);
                    producerTosave.setProducerName(producerName);
                    producerRepository.save(producerTosave);
                    List<Product> products = productrepository.findAll();
                    if (products != null && products.size() > 0) {
                        for (Product product : products) {
                            if (product.getProducer().getId().equals(producerId)) {
                                product.setProducer(producerTosave);
                                String id = productrepository.findById(product.getId()).get().getId();
                                product.setId(id);
                                productrepository.save(product);
                            }
                        }
                    }
                    Producer producer1 = producerRepository.findById(producerId).get();
                    for (Stock stock : stockRepository.findAll()) {
                        if (stock.getProducerId().equals(producerId)) {
                            stock.setProducerId(producer1.getId());
                            String id = stockRepository.findById(stock.getId()).get().getId();
                            stock.setId(id);
                            stockRepository.save(stock);
                        }
                    }
                    return new ResponseEntity<>("Category updated", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("This kind of category not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("producerId") String producerId) {
        try {
            Producer producer = producerRepository.findById(producerId).get();
            producerRepository.delete(producer);
            return new ResponseEntity<>("Producer with producer code " + producer.getProducerCode() + " is succesfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("This producer not found", HttpStatus.NOT_FOUND);
        }
    }
}
