package com.pb.ProjetoGrupo2.controller;

import com.pb.ProjetoGrupo2.dto.*;
import com.pb.ProjetoGrupo2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> postOrder(@RequestBody OrderFormDTO orderFormDTO){
        try {
            OrderDTO order = orderService.postOrder(orderFormDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{orderId}/user/{userId}")
    public ResponseEntity<Object> postProductIntoOrder
            (@PathVariable Long userId,
             @PathVariable Long orderId,
             @RequestBody OrderedProductFormDTO orderedProductFormDTO){

        try {
            OrderedProductDTO orderedProduct =
                    orderService.postProductIntoOrder(userId, orderId, orderedProductFormDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderedProduct);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/report")
    public ResponseEntity<DailyReportDTO> generateDailyReport(){
        DailyReportDTO dailyReport = orderService.generateDailyReport();
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyReport);

    }

    @GetMapping
    public Page<OrderDTO> getAllOrders
            (@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<OrderDTO> orders = orderService.getAllOrders(pageable);
        return orders;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        OrderDTO order = orderService.getOrderById(id);
        if (order != null){
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/product")
    public Page<OrderedProductDTO> getOrderProduct
            (@PathVariable Long orderId,
             @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<OrderedProductDTO> orderedProducts = orderService.getOrderProduct(orderId, pageable);
        return orderedProducts;
    }

    @GetMapping("/user/{userId}")
    public Page<OrderForUserDTO> getUserOrder
            (@PathVariable Long userId,
             @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<OrderForUserDTO> userOrders = orderService.getUserOrders(userId, pageable);
        return userOrders;
    }

    @GetMapping("/report")
    public Page<DailyReportDTO> getAllDailyReports
            (@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<DailyReportDTO> dailyReports = orderService.getAllDailyReports(pageable);
        return dailyReports;
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> putOrderStatus
            (@PathVariable Long orderId, @RequestBody OrderStatusUpdateFormDTO statusUpdateFormDTO){

        OrderDTO order = orderService.putOrderStatus(orderId ,statusUpdateFormDTO);
        if (order != null){
            return ResponseEntity.ok().body(order);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}/ordered/{orderedId}")
    public ResponseEntity<?> deleteProductFromUserOrder
            (@PathVariable Long orderId,
             @PathVariable Long orderedId){

        try {
            String response = orderService.deleteProductFromUserOrder(orderId, orderedId);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
