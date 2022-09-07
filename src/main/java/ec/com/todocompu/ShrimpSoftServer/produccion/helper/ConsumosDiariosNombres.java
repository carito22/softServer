package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

public class ConsumosDiariosNombres {

    private String piscina;
    private String codigo;
    private String producto;
    private String medida;

    public ConsumosDiariosNombres() {
    }

    public ConsumosDiariosNombres(String piscina, String codigo, String producto, String medida) {
        this.piscina = piscina;
        this.codigo = codigo;
        this.producto = producto;
        this.medida = medida;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPiscina() {
        return piscina;
    }

    public void setPiscina(String piscina) {
        this.piscina = piscina;
    }

}
