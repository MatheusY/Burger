package org.mmatsubara.dto;

import lombok.Data;
import org.mmatsubara.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String productType;

    private LocalDateTime createdDate;

}
