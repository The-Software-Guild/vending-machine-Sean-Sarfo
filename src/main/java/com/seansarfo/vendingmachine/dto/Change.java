/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Change {
    public static BigDecimal changeDueInPennies (BigDecimal itemCost, BigDecimal money) {
        BigDecimal changeDueInPennies = (money.subtract(itemCost)).multiply(new BigDecimal("100"));
        System.out.println("Change due: $" + (changeDueInPennies.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP).toString()));
        return changeDueInPennies;
    }
    public static Map<BigDecimal, BigDecimal> changeDuePerCoin (BigDecimal itemCost, BigDecimal money) {
        Coin[] coinEnumArray = Coin.values();
        ArrayList <String> coinStringList = new ArrayList<>();
        for (Coin coin : coinEnumArray) {
            coinStringList.add(coin.toString());
        }

          ArrayList<BigDecimal> coins = new ArrayList<BigDecimal>(); 
          for (String coin:coinStringList) {
              coins.add(new BigDecimal(coin));
          }
          
        BigDecimal changeInPennies = changeDueInPennies(itemCost, money);
        BigDecimal noOfCoin;
        BigDecimal zero = new BigDecimal("0");
        Map <BigDecimal, BigDecimal> amountPerCoin = new HashMap<>();

        for (BigDecimal coin : coins) {
            if (changeInPennies.compareTo(coin) >= 0) {
                if (!changeInPennies.remainder(coin).equals(zero)) {
                    noOfCoin = changeInPennies.divide(coin,0,RoundingMode.DOWN);
                    amountPerCoin.put(coin, noOfCoin);
                    changeInPennies = changeInPennies.remainder(coin);
                    if (changeInPennies.compareTo(zero)<0) {
                        break;
                    }

                } else if (changeInPennies.remainder(coin).equals(zero)) {
                    noOfCoin = changeInPennies.divide(coin,0,RoundingMode.DOWN);
                    amountPerCoin.put(coin, noOfCoin);

                    if ((changeInPennies.compareTo(zero))<0) {
                        break;
                    }
                }
            } else {
            }
        }
        return amountPerCoin;
    }

    
    
    
    
}
