/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.service;

import com.seansarfo.vendingmachine.dao.VendingMachineDao;
import com.seansarfo.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.seansarfo.vendingmachine.dao.VendingMachinePersistenceException;
import com.seansarfo.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author User
 */
public class VendingMachineServiceLayerImplTest {
    VendingMachineDao testDao = new VendingMachineDaoFileImpl("vending_machine_test_file.txt");

    VendingMachineServiceLayer testService = new VendingMachineServiceLayerImpl(testDao);
    
    
    public VendingMachineServiceLayerImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        
    }
    @Test
    public void testCheckIfEnoughMoney() {
        Item hariboClone = new Item("Haribo");
        hariboClone.setCost(new BigDecimal("1.60"));
        hariboClone.setInventory(9);
        
        BigDecimal enoughMoney = new BigDecimal("2.00");
        BigDecimal notEnoughMoney = new BigDecimal("1.59");

        try {
            testService.checkIfEnoughMoney(hariboClone, enoughMoney);
        } catch (InsufficientFundsException e){
            fail("There is sufficient funds, exception should not have been thrown");
        }
        try {
            testService.checkIfEnoughMoney(hariboClone, notEnoughMoney);
            fail("There insufficient funds, exception should have been thrown");
        } catch (InsufficientFundsException e){
        }
    }
    
    @Test
    public void testGetItemsInStockWithCosts() {
        
    }
    
    @Test
    public void testGetChangePerCoin(){
        Item hariboClone = new Item("Haribo");
        hariboClone.setCost(new BigDecimal("1.60"));
        hariboClone.setInventory(9);
        
        BigDecimal money = new BigDecimal("2.50");

        Map<BigDecimal, BigDecimal> expectedChangePerCoin = new HashMap<>();
        expectedChangePerCoin.put(new BigDecimal("0.25"), new BigDecimal("3"));
        expectedChangePerCoin.put(new BigDecimal("0.10"), new BigDecimal("1"));
        expectedChangePerCoin.put(new BigDecimal("0.05"), new BigDecimal("1"));

        Map<BigDecimal, BigDecimal> changePerCoin = testService.getChangePerCoin(hariboClone, money);

        assertEquals(changePerCoin.size(), 3, "There should be three coins");
    }
    
    @Test
    public void testGetItem() throws InsufficientFundsException, VendingMachinePersistenceException, NoItemInventoryException {
        Item snickersClone = new Item("Snickers");
        snickersClone.setCost(new BigDecimal("2.10"));
        snickersClone.setInventory(0);
        BigDecimal money = new BigDecimal("3.00");
        
        Item malteasersClone = new Item("Malteasers");
        malteasersClone.setCost(new BigDecimal("2.10"));
        malteasersClone.setInventory(testDao.getItemInventory("Malteasers"));
        
        Item itemWanted = null;
        try {
            itemWanted = testService.getItem("Snickers", money);
            fail("The item wanted is out of stock.");
        }catch (NoItemInventoryException e) {
        }
        try {
            itemWanted = testService.getItem("Malteasers", money);
        }catch (NoItemInventoryException e) {
            if (testDao.getItemInventory("Malteasers")>0){
            fail("The item wanted is in stock.");
        } 

        assertNotNull(itemWanted, "change should not be null");
        assertEquals(itemWanted, malteasersClone,"The item retrieved should be snickers");
    }
    }
    
    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachinePersistenceException {
        String name = "Snickers";

        try{
            testService.removeOneItemFromInventory(name);
            fail("There are no snickers left, exception should be thrown");
        } catch (NoItemInventoryException e) {  
        }
        
        String malteasers = "Malteasers";
        try{
            testService.removeOneItemFromInventory(malteasers);
        } catch (NoItemInventoryException e) {
            if (testDao.getItemInventory(malteasers)>0){
                fail("Malteasers are in stock, exception should not be thrown");
            }
        } 
    }
    
    
}
