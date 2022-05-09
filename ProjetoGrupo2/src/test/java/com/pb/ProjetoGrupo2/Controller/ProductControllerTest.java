package com.pb.ProjetoGrupo2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pb.ProjetoGrupo2.builder.ProductBuilder;
import com.pb.ProjetoGrupo2.constants.Type;
import com.pb.ProjetoGrupo2.dto.ProductDTO;
import com.pb.ProjetoGrupo2.dto.UpdateProductStockFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdatedProductFormDTO;
import com.pb.ProjetoGrupo2.entities.Product;
import com.pb.ProjetoGrupo2.repository.ProductRepository;
import com.pb.ProjetoGrupo2.service.ProductService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void postProduct_shouldReturn_SUCCESS() throws Exception{

        Product product = ProductBuilder.getProductTwo();
        ProductDTO productDto = ProductBuilder.getProductDtoTwo();

        when(productService.postProduct(any())).thenReturn(productDto);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andDo(print());
    }

    @Test
    void getAllProducts_ShouldReturn_SUCCESS() throws Exception{

        List<ProductDTO> productDtoList = new ArrayList<>(
                Arrays.asList(ProductBuilder.getProductDto(), ProductBuilder.getProductDtoTwo())
        );

        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<ProductDTO> productDtoPage = new PageImpl<>(productDtoList, pageRequest, productDtoList.size());

        when(productService.getAllProducts(any(), any(PageRequest.class))).thenReturn(productDtoPage);

        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].name").value("Coxinha"))
                .andExpect(jsonPath("$.content.[1].name").value("Calabresa"))
                .andDo(print());

    }

    @Test
    void getProductById_ShouldReturn_SUCCESS() throws Exception{

        Product product = ProductBuilder.getProduct();
        ProductDTO productDto = ProductBuilder.getProductDto();

        when(productService.getProductById(product.getId())).thenReturn(productDto);

        long id = 1;

        mockMvc.perform(get("/product/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andDo(print());

    }

    @Test
    void putProduct_ShouldReturn_SUCCESS() throws Exception{

        Product product = ProductBuilder.getProduct();
        UpdatedProductFormDTO updatedProductFormDTO = ProductBuilder.getUpdatedProductFormDTOTwo();
        ProductDTO productDto = ProductBuilder.getProductDto();

        product.setName("Fanta");
        productDto.setName("Fanta");

        when(productService.putProduct(anyLong(), any(UpdatedProductFormDTO.class))).thenReturn(productDto);

        mockMvc.perform(put("/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductFormDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedProductFormDTO.getName()))
                .andDo(print());

    }

    @Test
    void putProductInStock_ShouldReturn_SUCCESS() throws Exception{

        Product product = ProductBuilder.getProduct();
        UpdateProductStockFormDTO updateProductStockFormDTO = ProductBuilder.getUpdateProductStockFormDTO();
        ProductDTO productDto = ProductBuilder.getProductDto();
        productDto.setQuantity(30);

        when(productService.putProductInStock(anyLong(), any(UpdateProductStockFormDTO.class))).thenReturn(productDto);

        mockMvc.perform(put("/product/productStock/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductStockFormDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andDo(print());
    }
}

