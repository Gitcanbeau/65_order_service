package com.codedecode.order.entity;


import com.codedecode.order.dto.FoodItemsDTO;
import com.codedecode.order.dto.RestaurantDTO;
import com.codedecode.order.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("order")
//When using MongoDB with Spring Data, you don't use
// the @Entity annotation to define your domain objects (entities).
//Instead, you typically use the @Document annotation
// to indicate that a class is a MongoDB document.
public class Order {
    private Integer orderId;
    private List<FoodItemsDTO> foodItemsList;
    private RestaurantDTO restaurantDTO;
    private UserDTO userDTO;

}
