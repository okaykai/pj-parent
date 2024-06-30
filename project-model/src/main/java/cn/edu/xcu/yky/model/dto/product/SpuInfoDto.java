package cn.edu.xcu.yky.model.dto.product;

import lombok.Data;

import java.util.List;

/**
 * @description: SpuInfoDTO
 * @author: Keith
 */

@Data
public class SpuInfoDto {


    private Long category1Id;
    private Long category2Id;
    private Long category3Id;
    private String name;
    private String proDesc;
    private Long brandId;
    private String spuName;  // 添加spuName字段
    private String description;  // 添加description字段
    private List<SpuImage> spuImageList;
    private List<SpuSaleAttr> spuSaleAttrList;
    private Integer tmId;

    @Data
    public static class SpuImage {
        private String imgName;
        private String imgUrl;
    }

    @Data
    public static class SpuSaleAttr {
        private String baseSaleAttrId;
        private String saleAttrName;
        private List<SpuSaleAttrValue> spuSaleAttrValueList;
        private Boolean flag;
        private String saleAttrValue;
    }

    @Data
    public static class SpuSaleAttrValue {
        private String baseSaleAttrId;
        private String saleAttrValueName;
    }
}