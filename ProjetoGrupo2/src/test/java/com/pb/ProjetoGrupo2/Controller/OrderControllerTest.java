package com.pb.ProjetoGrupo2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pb.ProjetoGrupo2.builder.OrderBuilder;
import com.pb.ProjetoGrupo2.builder.UserBuilder;
import com.pb.ProjetoGrupo2.dto.*;
import com.pb.ProjetoGrupo2.entities.DailyReport;
import com.pb.ProjetoGrupo2.entities.Order;
import com.pb.ProjetoGrupo2.entities.User;
import com.pb.ProjetoGrupo2.repository.OrderRepository;
import com.pb.ProjetoGrupo2.service.OrderService;
import org.aspectj.weaver.ast.Or;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void postOrder_ShouldReturn_SUCCESS() throws Exception{

        Order order = OrderBuilder.getOrder();
        OrderDTO orderDto = OrderBuilder.getOrderDto();
        User user = UserBuilder.getUser();
        UserOrderDTO userOrderDTO = UserBuilder.getUserOrderDTO();
        order.setUser(user);
        orderDto.setUser(userOrderDTO);

        when(orderService.postOrder(any())).thenReturn(orderDto);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.id").value(order.getUser().getId()))
                .andExpect(jsonPath("$.user.name").value(order.getUser().getName()))
                .andDo(print());
    }

    @Test
    void postProductIntoOrder_ShouldReturn_SUCCESS() throws Exception{


        Order order = OrderBuilder.getOrder();
        User user = UserBuilder.getUser();
        OrderedProductFormDTO orderedProductFormDTO = OrderBuilder.getOrderedProductFormDTO();
        OrderedProductDTO orderedProductDTO = OrderBuilder.getOrderedProductDTO();

        when(orderService
                .postProductIntoOrder(anyLong(), anyLong(), any(OrderedProductFormDTO.class)))
                .thenReturn(orderedProductDTO);

        mockMvc.perform(post("/order/{orderId}/user/{userId}", order.getId(), user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedProductFormDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Coxinha"))
                .andDo(print());
    }

    @Test
    void generateDailyReport_ShouldReturn_SUCCESS() throws Exception{

        DailyReport dailyReport = OrderBuilder.getDailyReport();
        DailyReportDTO dailyReportDTO = OrderBuilder.getDailyReportDTO();

        when(orderService.generateDailyReport()).thenReturn(dailyReportDTO);

        mockMvc.perform(post("/order/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailyReportDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dailyReportDTO.getId()))
                .andExpect(jsonPath("$.totalAmountSold").value(dailyReportDTO.getTotalAmountSold()))
                .andDo(print());
    }

    @Test
    void getAllOrders_ShouldReturn_SUCCESS() throws Exception{

        Order order = OrderBuilder.getOrder();
        Order orderTwo = OrderBuilder.getOrderTwo();

        List<OrderDTO> orderDtoList = new ArrayList<>(
                Arrays.asList(OrderBuilder.getOrderDto(), OrderBuilder.getOrderDtoTwo())
        );

        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<OrderDTO> productDtoPage = new PageImpl<>(orderDtoList, pageRequest, orderDtoList.size());

        when(orderService.getAllOrders(any(PageRequest.class))).thenReturn(productDtoPage);

        mockMvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(order.getId()))
                .andExpect(jsonPath("$.content.[1].id").value(orderTwo.getId()))
                .andDo(print());
    }

    @Test
    void getOrderById_ShouldReturn_SUCCESS() throws Exception{

        Order order = OrderBuilder.getOrder();
        OrderDTO orderDto = OrderBuilder.getOrderDto();

        when(orderService.getOrderById(order.getId())).thenReturn(orderDto);

        long id = 1;

        mockMvc.perform(get("/order/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andDo(print());

    }
//
//    @Test
//    void updateOrder() throws Exception{
//
//        Order order = OrderBuilder.getOrder();
//        OrderFormDto orderFormDto = OrderBuilder.getOrderFormDto();
//        OrderDto orderDto = OrderBuilder.getOrderDto();
//
//        order.setQuantity(10);
//        orderFormDto.setIdUser(1L);
//        orderDto.setDeliveryDate(LocalDateTime.parse("2022-04-26T22:00:00"));
//
//        when(orderService.update(anyLong(), any(OrderFormDto.class))).thenReturn(orderDto);
//
//        mockMvc.perform(put("/order/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderFormDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.products").value(orderFormDto.getProducts()))
//                .andDo(print());
//    }
}