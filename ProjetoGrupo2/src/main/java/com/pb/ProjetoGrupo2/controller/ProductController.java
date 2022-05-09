package com.pb.ProjetoGrupo2.controller;

import com.pb.ProjetoGrupo2.dto.ProductDTO;
import com.pb.ProjetoGrupo2.dto.ProductFormDTO;

import com.pb.ProjetoGrupo2.dto.UpdateProductStockFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdatedProductFormDTO;
import com.pb.ProjetoGrupo2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@RequestBody @Valid ProductFormDTO productFormDTO){
        ProductDTO product = productService.postProduct(productFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public Page<ProductDTO> getAllProducts
            (@RequestParam(required = false) String type,
             @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ProductDTO> products = productService.getAllProducts(type, pageable);
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO product = productService.getProductById(id);
        if (product != null){
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> putProduct
            (@PathVariable Long id, @RequestBody @Valid UpdatedProductFormDTO updatedProductFormDTO) {
        ProductDTO product = productService.putProduct(id, updatedProductFormDTO);
        if (product != null){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/productStock/{id}")
    public ResponseEntity<ProductDTO> putProductInStock
            (@PathVariable Long id,
             @RequestBody UpdateProductStockFormDTO updateProductStockFormDTO){
        ProductDTO product = productService.putProductInStock(id, updateProductStockFormDTO);
        if (product != null){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.noContent().build();
    }
}
