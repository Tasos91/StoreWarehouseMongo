//package com.example.StoreWarehouseMongo1.controllers;
//
//import com.example.StoreWarehouseMongo1.model.Color;
//import com.example.StoreWarehouseMongo1.model.Product;
//import com.example.StoreWarehouseMongo1.model.Stock;
//import com.example.StoreWarehouseMongo1.model.Stone;
//import com.example.StoreWarehouseMongo1.model.Store;
//import com.example.StoreWarehouseMongo1.repositories.ProductRepository;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// *
// * @author Tasos
// */
//@RestController
//@RequestMapping("/save")
//@CrossOrigin(origins = "*")
//public class SaveProductController {
//
//    private static final String UPLOAD_DIRECTORY = "C:/Tomcat/webapps/images/";
//
//    @Autowired
//    private ProductRepository productrepo;
//
//    @PostMapping("/{productcode}/{category}/{address1}/{address2}/{address3}/{blackq3}/{whiteq1}/{price}/{silverweight}/{stonedescr}/{bronzeq2}/{blackq1}/{blackq2}/{costeu}/{karats}/{whiteq2}/{whiteq3}/{goldweight}/{costusd}/{yelloq3}/{yelloq2}/{quantstones}/{descr}/{producercode}/{yelloq1}/{bronzeq3}/{bronzeq1}/{stoneweight}")//na svisw tis parametrous afou ola tha mpoune me @request param
//    public Product saveproduct(@PathVariable(value = "productcode") String productcode, @PathVariable(value = "costusd") String cost_usd, @PathVariable(value = "costeu") String cost_eu, @PathVariable(value = "price") String price,
//            @PathVariable(value = "producercode") String producer_code, @PathVariable(value = "goldweight") String gold_weight, @PathVariable(value = "silverweight") String silver_weight,
//            @PathVariable(value = "descr") String descr, @PathVariable(value = "karats") String karats, @PathVariable(value = "stoneweight") String stoneWeight, @PathVariable(value = "stonedescr") String stone_desc,
//            @PathVariable(value = "quantstones") String quantitysStones, @PathVariable(value = "yelloq1") int YelloQ1, @PathVariable(value = "whiteq1") int whiteQ1, @PathVariable(value = "blackq1") int blackQ1, @PathVariable(value = "bronzeq1") int bronzeQ1, @PathVariable(value = "address1") String address1,
//            @PathVariable(value = "yelloq2") int YelloQ2, @PathVariable(value = "whiteq2") int whiteQ2, @PathVariable(value = "blackq2") int blackQ2, @PathVariable(value = "bronzeq2") int bronzeQ2, @PathVariable(value = "address2") String address2,
//            @PathVariable(value = "yelloq3") int YelloQ3, @PathVariable(value = "whiteq3") int whiteQ3, @PathVariable(value = "blackq3") int blackQ3, @PathVariable(value = "bronzeq3") int bronzeQ3, @PathVariable(value = "address3") String address3, @PathVariable(value = "category") String category) {
//        //na prosthesw to file!!!
//        //List<Product> products = addProduct(productcode, cost_usd, cost_eu, price,
////                producer_code, gold_weight, silver_weight,
////                descr, karats, stoneWeight, stone_desc,
////                quantitysStones, YelloQ1, whiteQ1, blackQ1, bronzeQ1, address1,
////                YelloQ2, whiteQ2, blackQ2, bronzeQ2, address2,
////                YelloQ3, whiteQ3, blackQ3, bronzeQ3, address3, category);
//        List<Product> myprs = null;
////        for (Product pr : products) {
//////            myprs = productrepo.findByproductcode(productcode);
//////            //productrepo.save(pr);
//////            if (myprs.isEmpty()) {
//////                productrepo.save(pr);// tha tsekarw ena pedio boolean mesa stp product opote etsi me to pou ginetai to save prwth fora auto tha allazei
//////                //return pr;
//////                productcode=productcode+"";
//////            } else {
//////                //return new Product();
//////            }
//        }
//
//        Pageable pageable = PageRequest.of(0, 5); //pagination
//        Page<Product> pageproduct = productrepo.findAll(pageable); //pagination
//        List<Product> productsPerPage = pageproduct.getContent(); //pagination
//
//        return null;
//    }
//
////    public List<Product> addProduct(String productcode, String cost_usd, String cost_eu, String price,
////            String producer_code, String gold_weight, String silver_weight,
////            String descr, String karats, String stoneWeight, String stone_desc,
////            String quantitysStones, int goldQ1, int whiteQ1, int blackQ1, int bronzeQ1, String address1,
////            int goldQ2, int whiteQ2, int blackQ2, int bronzeQ2, String address2,
////            int goldQ3, int whiteQ3, int blackQ3, int bronzeQ3, String address3, String category) {
////
////
////        return products;
////
////    }
//
////    @PostMapping("/addfilepath")//na svisw tis parametrous afou ola tha mpoune me @request param
////    public void savefilePath(@RequestParam MultipartFile file) throws IOException {
////
////        String filename = file.getOriginalFilename();
////        if (!file.isEmpty()) {
////            byte[] bytes = file.getBytes();
////            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
////                    new File(UPLOAD_DIRECTORY + File.separator + filename))); //anti gia UPLOAD_DIRECTORY evaze to path giati??
////
////            stream.write(bytes);
////            stream.flush();
////            stream.close();
////        }
////
//    }
//
//}
