package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.math.BigDecimal;

public class PiscinaNombres {

    private String graPiscina;
    private String graLaboratorio;
    private BigDecimal graDensidad;
    private String graEdad;
    private String corFechaSiembra;

    public PiscinaNombres() {
    }

    public PiscinaNombres(String graPiscina, String graLaboratorio, BigDecimal graDensidad, String graEdad, String corFechaSiembra) {
        this.graPiscina = graPiscina;
        this.graEdad = graEdad;
        this.graLaboratorio = graLaboratorio;
        this.graDensidad = graDensidad;
        this.corFechaSiembra = corFechaSiembra;
    }

    public String getCorFechaSiembra() {
        return corFechaSiembra;
    }

    public void setCorFechaSiembra(String corFechaSiembra) {
        this.corFechaSiembra = corFechaSiembra;
    }

    public String getGraEdad() {
        return graEdad;
    }

    public void setGraEdad(String graEdad) {
        this.graEdad = graEdad;
    }

    public String getGraPiscina() {
        return graPiscina;
    }

    public void setGraPiscina(String graPiscina) {
        this.graPiscina = graPiscina;
    }

    public String getGraLaboratorio() {
        return graLaboratorio;
    }

    public void setGraLaboratorio(String graLaboratorio) {
        this.graLaboratorio = graLaboratorio;
    }

    public BigDecimal getGraDensidad() {
        return graDensidad;
    }

    public void setGraDensidad(BigDecimal graDensidad) {
        this.graDensidad = graDensidad;
    }

}
