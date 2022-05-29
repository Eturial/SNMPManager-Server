package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

/**
 * SNMP PDU:
 *           请求报文：GetRequest、GetNextRequest 和 SetRequest
 *           应答报文：GetResponse
 * @author Eturial
 * @date 2022/5/4
 */

@Data
public class RequestAndResponse {

    /**
     * 请求标识(Request ID)：
     * SNMP 给每个请求分配全局唯一的 ID，用于匹配请求和响应。
     * 检测由不可靠的传输服务产生的重复报文。
     */
    private Integer requestId;

    /**
     * 错误状态(Error status)：用于表示在处理请求时出现的状况，共有 6 种错误状态：
     * noError(0)、tooBig(1)、noSuchName(2)、badValue(3)、readOnly(4)、genError(5)
     */
    private Integer errorStatusValue;
    private String errorStatus;

    private final String[] status = {
            "noError (1)", "tooBig (2)", "noSuchName (3)",
            "badValue (4)", "readOnly (5)", "genError (6)"};

    /**
     * 错误索引(Error index)：当错误状态非 0 时指向出错的变量。
     */
    private Integer errorIndex;

    public RequestAndResponse(int requestId, int errorStatus, int errorIndex) {
        this.requestId = requestId;

        this.errorStatusValue = errorStatus;
        this.errorStatus = status[errorStatus];

        this.errorIndex = errorIndex;
    }
}
