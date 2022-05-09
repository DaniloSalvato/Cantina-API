package com.pb.ProjetoGrupo2.service;

import com.pb.ProjetoGrupo2.constants.Type;
import com.pb.ProjetoGrupo2.dto.ProductDTO;
import com.pb.ProjetoGrupo2.dto.ProductFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdateProductStockFormDTO;
import com.pb.ProjetoGrupo2.dto.UpdatedProductFormDTO;
import com.pb.ProjetoGrupo2.entities.Product;
import com.pb.ProjetoGrupo2.repository.OrderRepository;
import com.pb.ProjetoGrupo2.repository.ProductRepository;
import com.pb.ProjetoGrupo2.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO postProduct(ProductFormDTO productFormDTO) {
        Product product = productRepository.save(modelMapper.map(productFormDTO, Product.class));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> getAllProducts(String type, Pageable pageable) {

        Page<Product> products;
        if(type == null){
            products = productRepository.findAll(pageable);
        }else {
            products = productRepository.findByType(Type.valueOf(type),pageable);
        }

        List<ProductDTO> productDTOList =
                products.stream().map(p -> modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(productDTOList, pageable, productDTOList.size());
    }

    @Override
    public ProductDTO getProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return modelMapper.map(product.get(), ProductDTO.class);
        }
        return null;
    }

    @Override
    public ProductDTO putProduct(Long id, @Valid UpdatedProductFormDTO updatedProductFormDTO) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            Product productUpdated = optionalProduct.get();
            productUpdated.setName(updatedProductFormDTO.getName());
            productUpdated.setType(updatedProductFormDTO.getType());
            productUpdated.setUnityPrice(updatedProductFormDTO.getUnityPrice());
            productRepository.save(productUpdated);
            return modelMapper.map(productUpdated, ProductDTO.class);
        }
        return null;
    }

    @Override
    public ProductDTO putProductInStock(Long id, UpdateProductStockFormDTO updateProductStockFormDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() + updateProductStockFormDTO.getQuantity());
            if (product.getQuantity() < 0){
                product.setQuantity(0);
            }
            productRepository.save(product);
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }
}
