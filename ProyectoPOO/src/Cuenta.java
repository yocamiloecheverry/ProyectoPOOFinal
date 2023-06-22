/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kmili
 */
public class Cuenta {
    private double saldo;
    private String celular;

    public Cuenta() {
    }

    public Cuenta(double saldo, String celular) {
        this.saldo = saldo;
        this.celular = celular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    
}
