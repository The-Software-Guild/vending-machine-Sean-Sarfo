/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.dao;

import com.seansarfo.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VendingMachineDao {

    void removeOneItem(String name) throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException ;

    int getItemInventory(String name) throws VendingMachinePersistenceException;

    Item getItem(String name)throws VendingMachinePersistenceException;

    Map<String,BigDecimal> getMapOfItemNamesInStockWithCosts() throws VendingMachinePersistenceException;

}





