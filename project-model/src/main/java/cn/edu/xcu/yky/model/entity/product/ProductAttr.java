package cn.edu.xcu.yky.model.entity.product;

import cn.edu.xcu.yky.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description: 商品属性
 * @author: Keith
 */
@Data
@Schema(description = "商品属性类")
public class ProductAttr extends BaseEntity {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "属性key")
    private String attrKey;

    @Schema(description = "属性value")
    private String attrValue;
}
