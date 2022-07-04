/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine;

import com.seansarfo.vendingmachine.controller.VendingMachineController;
import com.seansarfo.vendingmachine.dao.VendingMachineDao;
import com.seansarfo.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.seansarfo.vendingmachine.service.VendingMachineServiceLayer;
import com.seansarfo.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.seansarfo.vendingmachine.ui.UserIO;
import com.seansarfo.vendingmachine.ui.UserIOConsoleImpl;
import com.seansarfo.vendingmachine.ui.VendingMachineView;

public class App {
    public static void main(String[] args) {

        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineDao dao = new VendingMachineDaoFileImpl();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);
        
        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();
    }
}
