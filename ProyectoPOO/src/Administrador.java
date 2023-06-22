
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kmili
 */
public class Administrador {
    private String celular;
    private Date ultimoLogin;

    public Administrador() {
    }

    public Administrador(String celular, Date ultimoLogin) {
        this.celular = celular;
        this.ultimoLogin = ultimoLogin;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
    
    
    
}
