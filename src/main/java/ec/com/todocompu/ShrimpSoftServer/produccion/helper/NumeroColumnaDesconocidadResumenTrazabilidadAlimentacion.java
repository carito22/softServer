/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenTrazabilidadAlimentacionTO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public class NumeroColumnaDesconocidadResumenTrazabilidadAlimentacion {

    private String[] columnas;
    private Object[][] datos;
    private List<String> columnasFaltantes = null;
    private List<PrdResumenTrazabilidadAlimentacionTO> listaPrdResumenTrazabilidadAlimentacionTO;
    private List<ResumenTrazabilidadAlimentacionNombres> listaPrdResumenNombres;

    public NumeroColumnaDesconocidadResumenTrazabilidadAlimentacion(List<PrdResumenTrazabilidadAlimentacionTO> listaPrdResumenTrazabilidadAlimentacionTO) {
        this.listaPrdResumenTrazabilidadAlimentacionTO = listaPrdResumenTrazabilidadAlimentacionTO;
        agruparCabeceraColumnas();
        agruparFilas();
        llenarObjetoParaTabla();
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public Object[][] getDatos() {
        return datos;
    }

    public void setDatos(Object[][] datos) {
        this.datos = datos;
    }

    public List<String> getColumnasFaltantes() {
        return columnasFaltantes;
    }

    public void setColumnasFaltantes(List<String> columnasFaltantes) {
        this.columnasFaltantes = columnasFaltantes;
    }

    public void agruparCabeceraColumnas() {
        boolean encontro = false;
        columnasFaltantes = new ArrayList<String>();

        if (listaPrdResumenTrazabilidadAlimentacionTO.size() > 0) {
            //CORRIDA ACTUAL
            columnasFaltantes.add(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoCodigo().concat("|A"));
            for (int i = 1; i < listaPrdResumenTrazabilidadAlimentacionTO.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo() != null
                            && !listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo().equals("")
                            && columnasFaltantes.get(j) != null
                            && !columnasFaltantes.get(j).equals("")
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo().concat("|A").equals(columnasFaltantes.get(j).toString())) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo() != null) {
                    columnasFaltantes.add(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo() + "|A");
                }
            }
            //TRANSFERENCIA
            if (listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoTransferidoCodigo() != null) {
                columnasFaltantes.add(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoTransferidoCodigo().concat("|T"));
            }
            int inicial = listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoTransferidoCodigo() != null ? 1 : 0;

            for (int i = inicial; i < listaPrdResumenTrazabilidadAlimentacionTO.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo() != null
                            && !listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo().equals("")
                            && columnasFaltantes.get(j) != null
                            && !columnasFaltantes.get(j).equals("")
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo().concat("|T").equals(columnasFaltantes.get(j).toString())) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo() != null) {
                    columnasFaltantes.add(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo() + "|T");
                }
            }
            //*******************
            int totalColumnasFaltantes = columnasFaltantes.size() * 2; // costo y cantidad
            columnas = new String[6 + totalColumnasFaltantes];
            columnas[0] = "Sector";
            columnas[1] = "Piscina";
            columnas[2] = "Corrida";
            columnas[3] = "Hectareaje";
            columnas[4] = "Fecha inicio";
            columnas[5] = "Fecha siembra";
            int cont = 0;
            int i = 0;
            while (cont < columnasFaltantes.size()) {
                columnas[i + 6] = columnasFaltantes.get(cont).toString();
                columnas[i + 7] = columnasFaltantes.get(cont).toString() + " $";
                i = i + 2;
                cont++;
            }
            //columnas faltantes
            i = 6;
            columnasFaltantes = new ArrayList<String>();
            while (i < columnas.length) {
                columnasFaltantes.add(columnas[i]);
                i++;
            }

        }
    }

    public void agruparFilas() {
        listaPrdResumenNombres = new ArrayList<ResumenTrazabilidadAlimentacionNombres>();
        boolean encontro = false;
        if (listaPrdResumenTrazabilidadAlimentacionTO.size() > 0) {
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcSector(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcSector() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcSector() : "");
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcPiscina(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcPiscina() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcPiscina() : "");
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcCorrida(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcCorrida() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcCorrida() : "");
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcHectareaje(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcHectareaje() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcHectareaje() : new BigDecimal(BigInteger.ZERO));
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcFechaDesde(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaDesde() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaDesde() : "");
            listaPrdResumenTrazabilidadAlimentacionTO.get(0).setRcFechaSiembra(listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaSiembra() != null
                    ? listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaSiembra() : "");

            listaPrdResumenNombres.add(new ResumenTrazabilidadAlimentacionNombres(
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcSector(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcPiscina(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcCorrida(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcHectareaje(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaDesde(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcFechaSiembra(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoCodigo(),
                    listaPrdResumenTrazabilidadAlimentacionTO.get(0).getRcBalanceadoTransferidoCodigo()));

            for (int i = 1; i < listaPrdResumenTrazabilidadAlimentacionTO.size(); i++) {
                for (int j = 0; j < listaPrdResumenNombres.size(); j++) {
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcSector(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcSector() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcSector() : "");
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcPiscina(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcPiscina() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcPiscina() : "");
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcCorrida(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcCorrida() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcCorrida() : "");
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcHectareaje(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcHectareaje() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcHectareaje() : new BigDecimal(BigInteger.ZERO));
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcFechaDesde(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaDesde() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaDesde() : "");
                    listaPrdResumenTrazabilidadAlimentacionTO.get(i).setRcFechaSiembra(listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaSiembra() != null
                            ? listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaSiembra() : "");

                    if (listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcSector().equals(listaPrdResumenNombres.get(j).getSector())
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcPiscina().equals(listaPrdResumenNombres.get(j).getPiscina())
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcCorrida().equals(listaPrdResumenNombres.get(j).getCorrida())
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcHectareaje().equals(listaPrdResumenNombres.get(j).getHectareaje())
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaDesde().equals(listaPrdResumenNombres.get(j).getFechaInicio())
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaSiembra().equals(listaPrdResumenNombres.get(j).getFechaSiembra())) {
                        encontro = true;
                        j = listaPrdResumenNombres.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaPrdResumenNombres.add(new ResumenTrazabilidadAlimentacionNombres(
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcSector(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcPiscina(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcCorrida(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcHectareaje(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaDesde(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcFechaSiembra(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoCodigo(),
                            listaPrdResumenTrazabilidadAlimentacionTO.get(i).getRcBalanceadoTransferidoCodigo()));
                }
            }
        }

    }

    public void llenarObjetoParaTabla() {
        if (listaPrdResumenTrazabilidadAlimentacionTO.size() > 0) {
            datos = new Object[listaPrdResumenNombres.size()][columnas.length];
            for (int i = 0; i < listaPrdResumenNombres.size(); i++) {
                datos[i][0] = listaPrdResumenNombres.get(i).getSector();
                datos[i][1] = listaPrdResumenNombres.get(i).getPiscina();
                datos[i][2] = listaPrdResumenNombres.get(i).getCorrida();
                datos[i][3] = listaPrdResumenNombres.get(i).getHectareaje();
                datos[i][4] = listaPrdResumenNombres.get(i).getFechaInicio();
                datos[i][5] = listaPrdResumenNombres.get(i).getFechaSiembra();
            }

            for (int h = 0; h < listaPrdResumenTrazabilidadAlimentacionTO.size(); h++) {
                for (int i = 0; i < datos.length; i++) {
                    if (listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcSector().equals(datos[i][0])
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcPiscina().equals(datos[i][1])
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcCorrida().equals(datos[i][2])
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcHectareaje().equals(datos[i][3])
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcFechaDesde().equals(datos[i][4])
                            && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcFechaSiembra().equals(datos[i][5])) {
                        //CORRIDA ACTUAL
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoCodigo() != null
                                    && !listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoCodigo().equals("")
                                    && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoCodigo().concat("|A").equals(columnasFaltantes.get(j))) {
                                datos[i][j + 6] = listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoCantidad();
                                datos[i][j + 7] = listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoCosto();
                                j = columnasFaltantes.size();
                            }
                        }
                        //TRANSFERIDO
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoTransferidoCodigo() != null
                                    && !listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoTransferidoCodigo().equals("")
                                    && listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoTransferidoCodigo().concat("|T").equals(columnasFaltantes.get(j))) {
                                datos[i][j + 6] = listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoTransferidoCantidad();
                                datos[i][j + 7] = listaPrdResumenTrazabilidadAlimentacionTO.get(h).getRcBalanceadoTransferidoCosto();
                                j = columnasFaltantes.size();
                            }
                        }

                        i = datos.length;
                    }
                }
            }
        }
    }
}
