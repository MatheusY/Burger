package org.mmatsubara.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long id;

    private ProductDTO product;

    private Integer quantity;

    private BigDecimal subtotal;
}
