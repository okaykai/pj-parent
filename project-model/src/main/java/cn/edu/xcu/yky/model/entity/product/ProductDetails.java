package cn.edu.xcu.yky.model.entity.product;

import cn.edu.xcu.yky.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

    private Long productId;
    private String imageUrls;

}