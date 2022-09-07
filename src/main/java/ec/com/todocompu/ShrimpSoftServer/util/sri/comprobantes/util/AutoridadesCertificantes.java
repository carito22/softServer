/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

/**
 *
 * @author VALDIVIEZO
 */
public enum AutoridadesCertificantes {
    UANATACA("UANATACA CA2 2016", "TSP-UANATACA", "UANATACA S.A.", "ES", "1.3.6.1.4.1.47286"),
    ANF("ANF", "ANF Autoridad Intermedia", "ANF AC", "EC", "1.3.6.1.4.1.37442"),
    BANCO_CENTRAL("BANCO CENTRAL DEL ECUADOR", "ENTIDAD DE CERTIFICACION DE INFORMACION-ECIBCE", "BANCO CENTRAL DEL ECUADOR", "EC", "1.3.6.1.4.1.37947"),
    SECURITY_DATA("AUTORIDAD DE CERTIFICACION", "ENTIDAD DE CERTIFICACION DE INFORMACION", "SECURITY DATA S.A.", "EC", "1.3.6.1.4.1.37746"),
    ICERT_EC("ICERT", "SUBDIRECCION NACIONAL DE SEGURIDAD DE LA INFORMACION DNTICS", "CONSEJO DE LA JUDICATURA",
            "EC", "1.3.6.1.4.1.43745");
    private final String cn;
    private final String ou;
    private final String o;
    private final String c;
    private final String oid;

    private AutoridadesCertificantes(String cn, String ou, String o, String c, String oid) {
        this.c = c;
        this.o = o;
        this.cn = cn;
        this.ou = ou;
        this.oid = oid;
    }

    public String getCn() {
        return cn;
    }

    public String getOu() {
        return ou;
    }

    public String getO() {
        return o;
    }

    public String getC() {
        return c;
    }

    public String getOid() {
        return oid;
    }

}
