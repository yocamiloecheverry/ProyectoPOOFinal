/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kmili
 */
public class Movimientos {
    private String celular;
    private String fechaMovimiento;
    private double valorMovimiento;

    public Movimientos() {
    }

    public Movimientos(String celular, String fechaMovimiento, double valorMovimiento) {
        this.celular = celular;
        this.fechaMovimiento = fechaMovimiento;
        this.valorMovimiento = valorMovimiento;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the fechaMovimiento
     */
    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * @param fechaMovimiento the fechaMovimiento to set
     */
    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * @return the valorMovimiento
     */
    public double getValorMovimiento() {
        return valorMovimiento;
    }

    /**
     * @param valorMovimiento the valorMovimiento to set
     */
    public void setValorMovimiento(double valorMovimiento) {
        this.valorMovimiento = valorMovimiento;
    }
    
    
}
