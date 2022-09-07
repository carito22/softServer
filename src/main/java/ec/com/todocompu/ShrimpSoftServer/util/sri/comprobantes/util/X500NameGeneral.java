/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

import java.util.StringTokenizer;

/**
 *
 * @author VALDIVIEZO
 */
public class X500NameGeneral {

    private String CN = null;
    private String OU = null;
    private String O = null;
    private String L = null;
    private String ST = null;
    private String C = null;

    public X500NameGeneral(String name) {
        StringTokenizer st = new StringTokenizer(name, ",");
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            int idx = token.indexOf("=");
            if (idx >= 0) {
                String label = token.substring(0, idx);
                String value = token.substring(idx + 1);
                if ("CN".equals(label)) {
                    this.CN = value;
                } else if ("OU".equals(label)) {
                    this.OU = value;
                } else if ("O".equals(label)) {
                    this.O = value;
                } else if ("C".equals(label)) {
                    this.C = value;
                } else if ("L".equals(label)) {
                    this.L = value;
                } else if ("ST".equals(label)) {
                    this.ST = value;
                }
            }
        }
    }

    public String getCN() {
        return CN;
    }

    public void setCN(String CN) {
        this.CN = CN;
    }

    public String getOU() {
        return OU;
    }

    public void setOU(String OU) {
        this.OU = OU;
    }

    public String getO() {
        return O;
    }

    public void setO(String O) {
        this.O = O;
    }

    public String getL() {
        return L;
    }

    public void setL(String L) {
        this.L = L;
    }

    public String getST() {
        return ST;
    }

    public void setST(String ST) {
        this.ST = ST;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

}
