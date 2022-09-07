/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaSubcategoriaTO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public class NumeroColumnaDesconocidaResumenCorridaSubcategoria {

    private String[] columnas;
    private Object[][] datos;
    private List<String> columnasFaltantes = null;
    private List<PrdResumenCorridaSubcategoriaTO> listaPrdResumenCorridaSubcategoriaTO;
    private List<ResumenCorridaSubcategoriaNombres> listaPrdResumenNombres;

    public NumeroColumnaDesconocidaResumenCorridaSubcategoria() {
    }

    public NumeroColumnaDesconocidaResumenCorridaSubcategoria(List<PrdResumenCorridaSubcategoriaTO> listaPrdResumenCorridaSubcategoriaTO) {
        this.listaPrdResumenCorridaSubcategoriaTO = listaPrdResumenCorridaSubcategoriaTO;
        agruparCabeceraColumnas();
        agruparFilas();
        llenarObjetoParaTabla();
    }

    public void agruparCabeceraColumnas() {
        boolean encontro = false;
        columnasFaltantes = new ArrayList<String>();

        if (listaPrdResumenCorridaSubcategoriaTO.size() > 0) {
            //CORRIDA ACTUAL
            columnasFaltantes.add(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaCodigo().concat("|A"));
            for (int i = 1; i < listaPrdResumenCorridaSubcategoriaTO.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo() != null
                            && !listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo().equals("")
                            && columnasFaltantes.get(j) != null
                            && !columnasFaltantes.get(j).equals("")
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo().concat("|A").equals(columnasFaltantes.get(j).toString())) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo() != null) {
                    columnasFaltantes.add(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo() + "|A");
                }
            }
            //TRANSFERENCIA
            if (listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaTransferidoCodigo() != null) {
                columnasFaltantes.add(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaTransferidoCodigo().concat("|T"));
            }
            int inicial = listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaTransferidoCodigo() != null ? 1 : 0;

            for (int i = inicial; i < listaPrdResumenCorridaSubcategoriaTO.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo() != null
                            && !listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo().equals("")
                            && columnasFaltantes.get(j) != null
                            && !columnasFaltantes.get(j).equals("")
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo().concat("|T").equals(columnasFaltantes.get(j).toString())) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo() != null) {
                    columnasFaltantes.add(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo() + "|T");
                }
            }
            //*******************
            columnas = new String[8 + columnasFaltantes.size()];
            columnas[0] = "Sector";
            columnas[1] = "Piscina";
            columnas[2] = "Corrida";
            columnas[3] = "Hectareaje";
            columnas[4] = "Fecha inicio";
            columnas[5] = "Fecha siembra";
            columnas[6] = "Fecha pesca";
            columnas[7] = "Fecha hasta";
            int i = 0;
            while (i < columnasFaltantes.size()) {
                columnas[i + 8] = columnasFaltantes.get(i).toString() + " $";
                i++;
            }

        }
    }

    public void agruparFilas() {
        listaPrdResumenNombres = new ArrayList<ResumenCorridaSubcategoriaNombres>();
        boolean encontro = false;
        if (listaPrdResumenCorridaSubcategoriaTO.size() > 0) {
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcSector(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSector() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSector() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcPiscina(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcPiscina() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcPiscina() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcCorrida(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcCorrida() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcCorrida() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcHectareaje(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcHectareaje() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcHectareaje() : new BigDecimal(BigInteger.ZERO));
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcFechaDesde(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaDesde() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaDesde() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcFechaSiembra(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaSiembra() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaSiembra() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcFechaPesca(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaPesca() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaPesca() : "");
            listaPrdResumenCorridaSubcategoriaTO.get(0).setRcFechaHasta(listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaHasta() != null
                    ? listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaHasta() : "");

            listaPrdResumenNombres.add(new ResumenCorridaSubcategoriaNombres(
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSector(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcPiscina(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcCorrida(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcHectareaje(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaDesde(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaSiembra(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaPesca(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcFechaHasta(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaCodigo(),
                    listaPrdResumenCorridaSubcategoriaTO.get(0).getRcSubcategoriaTransferidoCodigo()));

            for (int i = 1; i < listaPrdResumenCorridaSubcategoriaTO.size(); i++) {
                for (int j = 0; j < listaPrdResumenNombres.size(); j++) {
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcSector(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSector() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSector() : "");
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcPiscina(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcPiscina() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcPiscina() : "");
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcCorrida(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcCorrida() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcCorrida() : "");
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcHectareaje(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcHectareaje() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcHectareaje() : new BigDecimal(BigInteger.ZERO));
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcFechaDesde(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaDesde() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaDesde() : "");
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcFechaSiembra(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaSiembra() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaSiembra() : "");

                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcFechaPesca(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaPesca() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaPesca() : "");
                    listaPrdResumenCorridaSubcategoriaTO.get(i).setRcFechaHasta(listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaHasta() != null
                            ? listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaHasta() : "");

                    if (listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSector().equals(listaPrdResumenNombres.get(j).getSector())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcPiscina().equals(listaPrdResumenNombres.get(j).getPiscina())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcCorrida().equals(listaPrdResumenNombres.get(j).getCorrida())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcHectareaje().equals(listaPrdResumenNombres.get(j).getHectareaje())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaDesde().equals(listaPrdResumenNombres.get(j).getFechaInicio())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaSiembra().equals(listaPrdResumenNombres.get(j).getFechaSiembra())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaPesca().equals(listaPrdResumenNombres.get(j).getFechaPesca())
                            && listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaHasta().equals(listaPrdResumenNombres.get(j).getFechaHasta())) {
                        encontro = true;
                        j = listaPrdResumenNombres.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaPrdResumenNombres.add(new ResumenCorridaSubcategoriaNombres(
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSector(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcPiscina(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcCorrida(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcHectareaje(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaDesde(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaSiembra(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaPesca(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcFechaHasta(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaCodigo(),
                            listaPrdResumenCorridaSubcategoriaTO.get(i).getRcSubcategoriaTransferidoCodigo()));
                }
            }
        }
    }

    public void llenarObjetoParaTabla() {
        if (listaPrdResumenCorridaSubcategoriaTO.size() > 0) {
            datos = new Object[listaPrdResumenNombres.size()][columnas.length];
            for (int i = 0; i < listaPrdResumenNombres.size(); i++) {
                datos[i][0] = listaPrdResumenNombres.get(i).getSector();
                datos[i][1] = listaPrdResumenNombres.get(i).getPiscina();
                datos[i][2] = listaPrdResumenNombres.get(i).getCorrida();
                datos[i][3] = listaPrdResumenNombres.get(i).getHectareaje();
                datos[i][4] = listaPrdResumenNombres.get(i).getFechaInicio();
                datos[i][5] = listaPrdResumenNombres.get(i).getFechaSiembra();
                datos[i][6] = listaPrdResumenNombres.get(i).getFechaPesca();
                datos[i][7] = listaPrdResumenNombres.get(i).getFechaHasta();
            }

            for (int h = 0; h < listaPrdResumenCorridaSubcategoriaTO.size(); h++) {
                for (int i = 0; i < datos.length; i++) {
                    if (listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSector().equals(datos[i][0])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcPiscina().equals(datos[i][1])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcCorrida().equals(datos[i][2])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcHectareaje().equals(datos[i][3])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcFechaDesde().equals(datos[i][4])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcFechaSiembra().equals(datos[i][5])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcFechaPesca().equals(datos[i][6])
                            && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcFechaHasta().equals(datos[i][7])) {
                        //CORRIDA ACTUAL
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaCodigo() != null
                                    && !listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaCodigo().equals("")
                                    && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaCodigo().concat("|A").equals(columnasFaltantes.get(j))) {
                                datos[i][j + 8] = listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaCosto();
                                j = columnasFaltantes.size();
                            }
                        }
                        //TRANSFERIDO
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaTransferidoCodigo() != null
                                    && !listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaTransferidoCodigo().equals("")
                                    && listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaTransferidoCodigo().concat("|T").equals(columnasFaltantes.get(j))) {
                                datos[i][j + 8] = listaPrdResumenCorridaSubcategoriaTO.get(h).getRcSubcategoriaTransferidoCosto();
                                j = columnasFaltantes.size();
                            }
                        }

                        i = datos.length;
                    }
                }
            }
        }
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

    public List<PrdResumenCorridaSubcategoriaTO> getListaPrdResumenCorridaSubcategoriaTO() {
        return listaPrdResumenCorridaSubcategoriaTO;
    }

    public void setListaPrdResumenCorridaSubcategoriaTO(List<PrdResumenCorridaSubcategoriaTO> listaPrdResumenCorridaSubcategoriaTO) {
        this.listaPrdResumenCorridaSubcategoriaTO = listaPrdResumenCorridaSubcategoriaTO;
    }

    public List<ResumenCorridaSubcategoriaNombres> getListaPrdResumenNombres() {
        return listaPrdResumenNombres;
    }

    public void setListaPrdResumenNombres(List<ResumenCorridaSubcategoriaNombres> listaPrdResumenNombres) {
        this.listaPrdResumenNombres = listaPrdResumenNombres;
    }

}
