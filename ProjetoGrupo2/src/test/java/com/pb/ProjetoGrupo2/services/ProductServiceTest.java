package com.pb.ProjetoGrupo2.services;

import com.pb.ProjetoGrupo2.builder.ProductBuilder;
import com.pb.ProjetoGrupo2.config.validation.ObjectNotFoundException;
import com.pb.ProjetoGrupo2.constants.Type;
import com.pb.ProjetoGrupo2.dto.ProductDTO;
import com.pb.ProjetoGrupo2.dto.ProductFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdateProductStockFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdatedProductFormDTO;
import com.pb.ProjetoGrupo2.entities.Product;
import com.pb.ProjetoGrupo2.repository.ProductRepository;
import com.pb.ProjetoGrupo2.service.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Product Service")
public class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository repository;

    @Test
    @DisplayName("Save Product")
    public void saveProduct() {
        Product product = ProductBuilder.getProduct();

        when(this.repository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = this.productService.postProduct(ProductBuilder.getProductFormDto());

        assertThat(productDTO.getId()).isNotNull();
        assertThat(productDTO.getName()).isEqualTo(product.getName());
        assertThat(productDTO.getType().name()).isEqualTo(product.getType().name());
        assertThat(productDTO.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(productDTO.getUnityPrice()).isEqualTo(product.getUnityPrice());
    }

//    @Test
//    @DisplayName("List all product")
//    public void listProducts() {
//        Product product = ProductBuilder.getProduct();
//        Product productTwo = ProductBuilder.getProductTwo();
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        List<Product> products = new ArrayList<>(Arrays.asList(product, productTwo));
//        Page<Product> page = new PageImpl<>(products, pageRequest, products.size());
//
//        when(this.repository.findAll(any(), any(PageRequest.class))).thenReturn(page);
//
//        Page<ProductDTO> pageProductDTO = this.productService.getAllProducts(Type.ASSADO.name(), pageRequest);
//
//        assertThat(pageProductDTO.getContent()).hasSize(1);
//        assertThat(pageProductDTO.getTotalPages()).isEqualTo(1);
//        assertThat(pageProductDTO.getTotalElements()).isEqualTo(1);
//    }

    @Test
    @DisplayName("FindById products")
    public void findByIdProduct() {
        Product product = ProductBuilder.getProduct();

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO productDTO = this.productService.getProductById(product.getId());

        assertThat(productDTO.getId()).isNotNull();
        assertThat(productDTO.getName()).isEqualTo(product.getName());
        assertThat(productDTO.getType().name()).isEqualTo(product.getType().name());
        assertThat(productDTO.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(productDTO.getUnityPrice()).isEqualTo(product.getUnityPrice());
    }

    @Test
    @DisplayName("findById product not found")
    public void findByIdProduct_NotFound() {
        Product product = ProductBuilder.getProduct();

        when(this.repository.findById(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> this.productService.getProductById(21L));
    }


    @Test
    @DisplayName("Update product")
    public void updateProduct() {
        Product product = ProductBuilder.getProduct();
        UpdatedProductFormDTO updatedProductFormDTO = ProductBuilder.getUpdatedProductFormDTO();
        ProductFormDTO productFormDTO = ProductBuilder.getProductFormDto();
        productFormDTO.setName("Calabresa");
        productFormDTO.setType(Type.valueOf("ASSADO"));

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
        when(this.repository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = this.productService.putProduct(product.getId(), updatedProductFormDTO);

        assertThat(productDTO.getId()).isNotNull();
        assertThat(productDTO.getName()).isEqualTo(productFormDTO.getName());
        assertThat(productDTO.getType().name()).isEqualTo(productFormDTO.getType().name());
        assertThat(productDTO.getQuantity()).isEqualTo(productFormDTO.getQuantity());
        assertThat(productDTO.getUnityPrice()).isEqualTo(productFormDTO.getUnityPrice());
    }

    @Test
    @DisplayName("Update product not found")
    public void updateProduct_NotFound() {
        Product product = ProductBuilder.getProduct();

        when(this.repository.findById(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> this.productService.putProduct(product.getId(), ProductBuilder.getUpdatedProductFormDTO()));

    }

    @Test
    @DisplayName("Put product in stock")
    public void putProductInStock(){
        Product product = ProductBuilder.getProduct();
        UpdatedProductFormDTO updatedProductFormDTO = ProductBuilder.getUpdatedProductFormDTO();
        ProductFormDTO productFormDTO = ProductBuilder.getProductFormDto();
        UpdateProductStockFormDTO updateProductStockFormDTO = ProductBuilder.getUpdateProductStockFormDTO();
        productFormDTO.setName("Coxinha");
        productFormDTO.setQuantity(22);

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(product));
        when(this.repository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = this.productService.putProductInStock(product.getId(), updateProductStockFormDTO);

        assertThat(productDTO.getId()).isNotNull();
        assertThat(productDTO.getName()).isEqualTo(productFormDTO.getName());
        assertThat(productDTO.getType().name()).isEqualTo(productFormDTO.getType().name());
        assertThat(productDTO.getQuantity()).isEqualTo(productFormDTO.getQuantity());
        assertThat(productDTO.getUnityPrice()).isEqualTo(productFormDTO.getUnityPrice());


    }
}