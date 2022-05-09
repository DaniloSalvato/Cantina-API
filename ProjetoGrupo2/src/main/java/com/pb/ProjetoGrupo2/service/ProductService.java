package com.pb.ProjetoGrupo2.service;

import com.pb.ProjetoGrupo2.dto.ProductDTO;
import com.pb.ProjetoGrupo2.dto.ProductFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdateProductStockFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdatedProductFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDTO getProductById(Long id);

    ProductDTO putProduct(Long id, UpdatedProductFormDTO productFormDto);

    ProductDTO postProduct(ProductFormDTO productFormDTO);

    Page<ProductDTO> getAllProducts(String type, Pageable pageable);

    ProductDTO putProductInStock(Long id, UpdateProductStockFormDTO updateProductStockFormDTO);
}
