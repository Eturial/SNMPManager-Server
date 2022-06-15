package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

import java.util.Arrays;

/**
 * @author Eturial
 * @date 2022/5/28
 */
@Data
public class Trap {
    /**
     * 制造商 ID
     */
    private String enterprise = "";

    /**
     * 产生Trap地址的IP
     */
    private String agentAddr = "";

    /**
     * 通用Trap类型
     */
    private Integer genericTrapValue;
    private String genericTrap;

    /**
     *  企业私有trap信息
     */
    private Integer specificTrap;

    /**
     * 时间戳
     */
    private String timestamp = "";

    public void setGenericTrapValue(Integer genericTrapValue) {
        this.genericTrapValue = genericTrapValue;
        this.genericTrap = UniversalType.genericTrapTypes[genericTrapValue];
    }
}
