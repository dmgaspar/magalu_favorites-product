package com.magalu.favorites.product.controller;
import java.util.ArrayList;
import java.util.List;

import com.magalu.favorites.product.dto.ProductDTO;
import com.magalu.favorites.product.model.Product;
import com.magalu.favorites.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.favorites.product.exception.*;
import com.magalu.favorites.product.model.Client;
import com.magalu.favorites.product.repository.ClientRepository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public static final String MAGUALU_PRODUCT_URI = "http://challenge-api.luizalabs.com/api/product/";

    @PostMapping("/clients/{clientId}/products")
    public ResponseEntity<Product> createProduct(@PathVariable(value = "clientId") Long clientId,
                                                 @RequestBody Product productRequest) {

         Client clients = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Client with id = " + clientId));

        if(clients.contain(productRequest.getProductid())) {
            throw new ResourceAlreadyExistException
                    ("The product id " + productRequest.getProductid() + " alread exist for this client " + clientId);
        }

        ProductDTO productDTO;
        try{
            RestTemplate restTemplate = new RestTemplate();
            String uri = MAGUALU_PRODUCT_URI + productRequest.getProductid() + "/";
            productDTO = restTemplate.getForObject(uri, ProductDTO.class);
        } catch(HttpStatusCodeException e){
              throw new ResourceNotFoundException("Not found this product id = " + productRequest.getProductid() +
                    " at http://challenge-api.luizalabs.com");
        }

        Product productResult =
                new Product(productDTO.getPrice(),
                        productDTO.getImage(),
                        productDTO.getBrand(),
                        productDTO.getId(),
                        productDTO.getTitle()
                        );

        Product product = clientRepository.findById(clientId).map(client -> {
            client.getProducts().add(productResult);
            return productRepository.save(productResult);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found this product id = " + clientId));

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/clients/{clientId}/products")
    public ResponseEntity<List<Product>> getAllProductsByClientId(@PathVariable(value = "clientId") Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Client with id = " + clientId));

        List<Product> products = new ArrayList<Product>();
        products.addAll(client.getProducts());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/clients/{clientId}/products/{productId}")
    public ResponseEntity<Product> getProductByClientId(@PathVariable("clientId") long clientId,
                                                        @PathVariable("productId") long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + productId));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/clients/{clientId}/products/{productId}")
    public ResponseEntity<Product> updateProductFromClient(@PathVariable("clientId") long clientId,
                                                           @PathVariable("productId") long productId,
                                                           @RequestBody Product productRequest) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Not found Client with id = " + clientId);
        }

        Product product = productRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + productId));

        product.setBrand(productRequest.getBrand());
        product.setTitle(productRequest.getTitle());
        product.setPrice(productRequest.getPrice());
        product.setProductid(productRequest.getProductid());

        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/clients/{clientId}/products/{productId}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("clientId") long clientId,
                                                   @PathVariable("productId") long productId){
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Not found Client with id = " + clientId);
        }
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Not found Product with client id = " + clientId);
        }

        productRepository.deleteById(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
