/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.controller;

import com.seansarfo.vendingmachine.dao.VendingMachinePersistenceException;
import com.seansarfo.vendingmachine.dto.Item;
import com.seansarfo.vendingmachine.service.InsufficientFundsException;
import com.seansarfo.vendingmachine.service.NoItemInventoryException;
import com.seansarfo.vendingmachine.service.VendingMachineServiceLayer;
import com.seansarfo.vendingmachine.ui.UserIO;
import com.seansarfo.vendingmachine.ui.UserIOConsoleImpl;
import com.seansarfo.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineController {
    private UserIO io = new UserIOConsoleImpl();
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        String itemSelection = "";

        BigDecimal inputMoney;
        view.displayMenuBanner();

        try {
            getMenu();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

        inputMoney = getMoney();
        while (keepGoing) {
            try {

                itemSelection = getItemSelection();

                if (itemSelection.equalsIgnoreCase("Exit")) {
                    keepGoing = false;
                    break;
                }

                getItem(itemSelection, inputMoney);
                keepGoing = false;
                break;

            } catch (InsufficientFundsException | NoItemInventoryException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
                view.displayPleaseTryAgainMsg();
            }
        }
        exitMessage();

    }

    private void getMenu()
            throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithCosts = service.getItemsInStockWithCosts();
        view.displayMenu(itemsInStockWithCosts);
    }

    private BigDecimal getMoney() {
        return view.getMoney();
    }

    private String getItemSelection() {
        return view.getItemSelection();
    }

    private void getItem(String name, BigDecimal money)
            throws InsufficientFundsException, NoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = service.getItem(name, money);
        Map<BigDecimal, BigDecimal> changeDuePerCoin = service.getChangePerCoin(wantedItem, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        view.displayEnjoyBanner(name);
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

}
    
    

