package com.codedecode.order.service;

import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.dto.UserDTO;
import com.codedecode.order.entity.Order;
import com.codedecode.order.mapper.OrderMapper;
import com.codedecode.order.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    SequenceGenerator sequenceGenerator;

    @Autowired
    RestTemplate restTemplate;



    public OrderDTO saveOrderInDb(OrderDTOFromFE orderDetails) {
        //(1) create a new unique orderId
        //(2) get all the info in the OrderDTO,
        // here we save the order entity first, then use mapper to change to orderDTO
        //(3) in order to save order entity, we need 4 things (orderID, a list of foodItemDTO, restaurantDTO, userDTO)
        //(4) save the order entity in the repo
        //(5) return the orderDTO using the mapper
        Integer newOrderID = sequenceGenerator.generateNextOrderId();
//        UserDTO userDTO = null;
        UserDTO userDTO = fetchUserDetailsFromUserId(orderDetails.getUserId());
        Order orderToBeSaved = new Order(newOrderID, orderDetails.getFoodItemsList(), orderDetails.getRestaurantDTO(), userDTO );
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
    }

    private UserDTO fetchUserDetailsFromUserId(Integer userId) {
        //(1) inject the restTemplate
        //(2) update the application file with restTemplate
        // (2a)import org.springframework.web.client.RestTemplate;
        // (2b)
//        use load balance because we could have multiple instances of the microservices where we want to request data
//        @LoadBalanced
//        public RestTemplate getRestTemplate() {
//            return new RestTemplate();
//        }
       return restTemplate.getForObject("http://USER-SERVICE/user/fetchUserById/" + userId, UserDTO.class);
       //(1) first parameter is the url which up the microservice,
        //recommend using the service name rather than the port number
        //(2) second parameter is the type of data that you want to request
        //because user microservice will save user entity to repo, but return the userDTO to other service
        //therefore, we should put userDTO rather than user entity as the seocnd parameter
    }
}
