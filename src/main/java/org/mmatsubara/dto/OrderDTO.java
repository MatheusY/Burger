package org.mmatsubara.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    private List<ItemDTO> items;

    private LocalDateTime orderedDate;

    private String orderStatus;

    private UserDTO customer;
}
