package com.personal.enums;


/**
 * 币种枚举类<br/>
 * 类RelationEnum
 * 
 * @author za-mengxinwei 2017�?6�?29�? 下午9:07:22
 */
public enum CurrencyEnum implements KeyValueEnum {
    RMB(1, "人民币"),
    USD(2, "美元"),
    EUR(3, "欧元"),
    JPY(4, "日元"),
    HKD(5, "港元"),
    GBP(6, "英镑"),
    OTHOER(0, "其它");

    private Integer code;

    private String  desc;

    private CurrencyEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
