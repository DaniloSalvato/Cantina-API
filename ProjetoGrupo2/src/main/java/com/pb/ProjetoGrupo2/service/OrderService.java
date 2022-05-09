package com.pb.ProjetoGrupo2.service;

import com.pb.ProjetoGrupo2.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDTO postOrder(OrderFormDTO orderFormDTO);

    OrderedProductDTO postProductIntoOrder(Long userId, Long orderId, OrderedProductFormDTO orderedProductFormDTO);

    Page<OrderForUserDTO> getUserOrders(Long userId, Pageable pageable);

    Page<OrderDTO> getAllOrders(Pageable pageable);

    Page<OrderedProductDTO> getOrderProduct(Long orderId, Pageable pageable);

    OrderDTO putOrderStatus(Long orderId, OrderStatusUpdateFormDTO statusUpdateFormDTO);

    String deleteProductFromUserOrder(Long orderId, Long orderedId);

    OrderDTO getOrderById(Long id);

    DailyReportDTO generateDailyReport();

    Page<DailyReportDTO> getAllDailyReports(Pageable pageable);
}
