package com.pb.ProjetoGrupo2.services;

import com.pb.ProjetoGrupo2.builder.OrderBuilder;
import com.pb.ProjetoGrupo2.builder.UserBuilder;
import com.pb.ProjetoGrupo2.config.validation.ObjectNotFoundException;
import com.pb.ProjetoGrupo2.dto.OrderDTO;
import com.pb.ProjetoGrupo2.dto.OrderFormDTO;
import com.pb.ProjetoGrupo2.dto.OrderStatusUpdateFormDTO;
import com.pb.ProjetoGrupo2.entities.Order;
import com.pb.ProjetoGrupo2.entities.User;
import com.pb.ProjetoGrupo2.repository.OrderRepository;
import com.pb.ProjetoGrupo2.repository.ProductRepository;
import com.pb.ProjetoGrupo2.repository.UserRepository;
import com.pb.ProjetoGrupo2.service.OrderServiceImpl;
import com.pb.ProjetoGrupo2.service.UserServiceImpl;
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


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Order Service")
public class OrderServiceTest {

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private UserServiceImpl users;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;


    @Test
    @DisplayName("Save order")
    public void postOrder() {
        Order order = OrderBuilder.getOrder();
        User user = UserBuilder.getUser();
        order.setUser(user);

        when(this.orderRepository.save(any(Order.class))).thenReturn(order);

        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getUser().getId()).isEqualTo(1L);
        assertThat(order.getPurchaseDate()).isEqualTo(LocalDate.now());
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal(15));

    }

//    @Test
//    @DisplayName("Save order_ NOT FOUND ")
//    public void postOrderNoutFound() {
//        Order order = OrderBuilder.getOrder();
//
//        when(this.orderRepository.save(any(Order.class))).thenReturn(order);
//
//        assertThat(order.getId()).isEqualTo(4L);
//        assertThat(order.getUser().getId()).isEqualTo(1L);
//        assertThat(order.getPurchaseDate()).isEqualTo(LocalDate.now());
//        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal(7));
//
//    }

    @Test
    @DisplayName("Get all orders")
    public void getAllOrders() {
        Order order = OrderBuilder.getOrder();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(order);
        Page<Order> page = new PageImpl<>(orders, pageRequest, 1);

        when(this.orderRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<OrderDTO> pageOrderDTO = this.orderService.getAllOrders(pageRequest);

        assertThat(pageOrderDTO.getContent()).hasSize(1);
        assertThat(pageOrderDTO.getTotalPages()).isEqualTo(1);
        assertThat(pageOrderDTO.getTotalElements()).isEqualTo(1);

    }

    @Test
    @DisplayName("FindById orders")
    public void findByIdOrder() {
        Order order = OrderBuilder.getOrder();

        when(this.orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        OrderDTO orderDTO = this.orderService.getOrderById(order.getId());

        assertThat(orderDTO.getId()).isNotNull();
        assertThat(orderDTO.getLocalDate()).isEqualTo(order.getPurchaseDate());
        assertThat(orderDTO.getTotalPrice()).isEqualTo(order.getTotalPrice());
        assertThat(orderDTO.getStatus()).isEqualTo(order.getStatus());

    }

    @Test
    @DisplayName("findById Order not found")
    public void findByIdOrder_NotFound() {
        Order order = OrderBuilder.getOrder();

        when(this.orderRepository.findById(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> this.orderService.getOrderById(order.getId()));
    }


    @Test
    @DisplayName("Update order")
    public void updateOrder() {
        Order order = OrderBuilder.getOrder();
        OrderStatusUpdateFormDTO orderStatusUpdateFormDTO = OrderBuilder.getOrderStatusUpdateFormDTO();

        when(this.orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(this.orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO orderDTO = this.orderService.putOrderStatus(order.getId(), orderStatusUpdateFormDTO);

        assertThat(orderDTO.getStatus()).isEqualTo(orderStatusUpdateFormDTO.getStatus());

    }

    @Test
    @DisplayName("Update order not found")
    public void updateOrder_NotFound() {
        Order order = OrderBuilder.getOrder();
        OrderStatusUpdateFormDTO orderStatusUpdateFormDTO = OrderBuilder.getOrderStatusUpdateFormDTO();

        when(this.orderRepository.findById(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> this.orderService.putOrderStatus(order.getId(), orderStatusUpdateFormDTO));
    }

//    @Test
//    @DisplayName("Delete product from user order")
//    public void deleteProductFromUserOrder() {
//        Order order = OrderBuilder.getOrder();
//
//        when(this.orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
//
//        this.orderService.deleteProductFromUserOrder(2L, 2L);
//
//        verify(this.orderRepository, times(1)).deleteById(2L);
//    }
//
//    @Test
//    @DisplayName("Delete order not found")
//    public void deleteOrder_NotFound() {
//        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThatExceptionOfType(ObjectNotFoundException.class)
//                .isThrownBy(() -> this.orderService.deleteById(1L));
//    }
}

