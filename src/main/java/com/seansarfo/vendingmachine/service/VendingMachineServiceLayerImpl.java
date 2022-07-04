/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.service;

import com.seansarfo.vendingmachine.dao.VendingMachineDao;
import com.seansarfo.vendingmachine.dao.VendingMachinePersistenceException;
import com.seansarfo.vendingmachine.dto.Change;
import com.seansarfo.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    private VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws InsufficientFundsException {

        if (item.getCost().compareTo(inputMoney) == 1) {
            throw new InsufficientFundsException("You do not have enough funds");
        }
    }

    @Override
    public Map<String, BigDecimal> getItemsInStockWithCosts()
            throws VendingMachinePersistenceException {

        Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfItemNamesInStockWithCosts();
        return itemsInStockWithCosts;
    }

    @Override
    public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        BigDecimal itemCost = item.getCost();
        Map<BigDecimal, BigDecimal> changeDuePerCoin = Change.changeDuePerCoin(itemCost, money);
        return changeDuePerCoin;
    }

    @Override
    public Item getItem(String name, BigDecimal inputMoney)
            throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = dao.getItem(name);

        if (wantedItem == null) {
            throw new NoItemInventoryException(
                    "Could not find this item");
        }

        checkIfEnoughMoney(wantedItem, inputMoney);

        removeOneItem(name);
        return wantedItem;


        public void removeOneItemFromInventory (String name) throws
        NoItemInventoryException, VendingMachinePersistenceException {
            if (dao.getItemInventory(name) > 0) {
                dao.removeOneItem(name);
            } else {
                throw new NoItemInventoryException(
                        "Item is out of stock.");
            }
        }

    }
    
    
