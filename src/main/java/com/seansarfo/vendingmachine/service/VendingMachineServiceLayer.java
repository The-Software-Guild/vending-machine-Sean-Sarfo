/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.service;

import com.seansarfo.vendingmachine.dao.VendingMachinePersistenceException;
import com.seansarfo.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineServiceLayer {

    void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws
            InsufficientFundsException;

    void removeOneItem(String name) throws
            NoItemInventoryException,
            VendingMachinePersistenceException;

    Map<String, BigDecimal> getItemsInStockWithCosts() throws
            VendingMachinePersistenceException;

    Item getItem(String name, BigDecimal inputMoney) throws
            InsufficientFundsException,
            NoItemInventoryException,
            VendingMachinePersistenceException;

    Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money);

}
