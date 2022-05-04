package com.eturial.SNMPManager.server.entity.dataparams;

import lombok.Data;

import java.util.ArrayList;

/**
 * 变量绑定表(Variable bindings)：变量绑定列表，由变量名和变量值对组成。
 * 在检索请求报文中，变量的值应为 0。
 *
 * @author Eturial
 * @date 2022/5/4
 */

@Data
public class VariableBindings {

    private ArrayList<Variable> variable;

}
