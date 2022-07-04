/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seansarfo.vendingmachine.dto;

public enum Coin {
    QUARTER(25), DIME(10), NICKEL(5), PENNY(1);
    private final int value;
    
    Coin (int value) {
        this.value = value;
    }
    private int getValue() {
        return value;
    }
         
    public String toString(){
        switch (this) {
            case QUARTER:
                return "25";
            case DIME:
                return "10";
            case NICKEL:
                return "5";
            case PENNY:
                return "1";
        }
        return null;
    }
}