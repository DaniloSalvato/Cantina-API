package com.pb.ProjetoGrupo2.repository;

import com.pb.ProjetoGrupo2.entities.OrderedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Page<OrderedProduct> findByOrderId(Long orderId, Pageable pageable);
    List<OrderedProduct> findByOrderId(Long orderId);
}
