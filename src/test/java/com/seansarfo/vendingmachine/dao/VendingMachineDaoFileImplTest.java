/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.dao;

import com.seansarfo.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao = new VendingMachineDaoFileImpl("VendingMachineTestFile.txt");

    public VendingMachineDaoFileImplTest() {
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
    public void testGetItem() throws VendingMachinePersistenceException {
        Item snickersClone = new Item("Snickers");
        snickersClone.setCost(new BigDecimal("2.10"));
        snickersClone.setInventory(0);

        Item retrievedItem = testDao.getItem("Snickers");

        assertNotNull(retrievedItem, "Item should not be null");
        assertEquals(retrievedItem, snickersClone, "The item retrieved should be snickers");

    }

    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachinePersistenceException {
        String itemName = "Malteasers";

        int inventoryBefore = testDao.getItemInventory(itemName);

        testDao.removeOneItem(itemName);

        int inventoryAfter = testDao.getItemInventory(itemName);

        assertTrue(inventoryAfter < inventoryBefore, "The inventory after should be less than before");
        assertEquals(inventoryAfter, inventoryBefore - 1, "The inventory after should be one less than it was"
                + "before");

    }

    @Test
    public void testGetItemInventory() throws VendingMachinePersistenceException {
        String itemName = "Snickers";

        int retrievedInventory = testDao.getItemInventory(itemName);

        assertEquals(retrievedInventory, 0, "There are 0 items of snickers left.");
    }

    @Test
    public void testGetMapOfItemNamesInStockWithCosts() throws VendingMachinePersistenceException {

        Map<String, BigDecimal> itemsInStock = testDao.getMapOfItemNamesInStockWithCosts();

        assertFalse(itemsInStock.containsKey("Snickers"));

        assertEquals(itemsInStock.size(), 6, "The menu list should contain 6 items.");
        assertTrue(itemsInStock.containsKey("Kitkat") &&
                itemsInStock.containsKey("McCoys") &&
                itemsInStock.containsKey("Haribo") &&
                itemsInStock.containsKey("Malteasers") &&
                itemsInStock.containsKey("Starburst") &&
                itemsInStock.containsKey("Cereal bar"));
    }

}
