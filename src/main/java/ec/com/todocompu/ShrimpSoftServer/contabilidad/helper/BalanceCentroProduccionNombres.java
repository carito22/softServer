/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.helper;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public class BalanceCentroProduccionNombres {

    private String[] columnas;
    private Object[][] datos;
    private java.util.List<String> columnasFaltantes = null;
    private java.util.List<CuentasContablesConNombre> listaCuentas = null;

    public BalanceCentroProduccionNombres() {
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

    public List<CuentasContablesConNombre> getListaCP() {
        return listaCuentas;
    }

    public void setListaCP(List<CuentasContablesConNombre> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public void agruparCabeceraColumnas(java.util.List<ConFunBalanceCentroProduccionTO> lista) {
        boolean encontro = false;
        columnasFaltantes = new java.util.ArrayList();
        if (lista.size() > 0) {
            columnasFaltantes.add(lista.get(0).getBrcCentroProduccion());
            for (int i = 1; i < lista.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (lista.get(i).getBrcCentroProduccion()
                            .equals(columnasFaltantes.get(j))) {
                        encontro = true;
                        j = columnasFaltantes.size();

                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    columnasFaltantes.add(lista.get(i).getBrcCentroProduccion());
                }
            }
            columnas = new String[2 + columnasFaltantes.size()];
            columnas[0] = "Cuenta";
            columnas[1] = "Detalle";
            for (int i = 0; i < columnasFaltantes.size(); i++) {
                columnas[i + 2] = columnasFaltantes.get(i);
            }
        }
    }

    public void agruparCuentas(java.util.List<ConFunBalanceCentroProduccionTO> lista) {
        listaCuentas = new java.util.ArrayList();
        boolean encontro = false;
        if (lista.size() > 0) {
            lista.get(0).setBrcCuenta(lista.get(0).getBrcCuenta() == null ? "" : lista.get(0).getBrcCuenta());
            listaCuentas.add(new CuentasContablesConNombre(lista.get(0).getBrcCuenta(), lista.get(0).getBrcDetalle()));
            for (int i = 1; i < lista.size(); i++) {
                for (int j = 0; j < listaCuentas.size(); j++) {
                    lista.get(i).setBrcCuenta(lista.get(i).getBrcCuenta() == null ? "" : lista.get(i).getBrcCuenta());
                    if (lista.get(i).getBrcCuenta() != null && lista.get(i).getBrcCuenta().equals(listaCuentas.get(j).getCuenta())) {
                        encontro = true;
                        j = listaCuentas.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaCuentas.add(new CuentasContablesConNombre(lista.get(i).getBrcCuenta(), lista.get(i).getBrcDetalle()));
                }
            }
        }
    }

    public void llenarObjetoParaTabla(
            java.util.List<ConFunBalanceCentroProduccionTO> lista) {
        if (lista.size() > 0) {
            datos = new Object[listaCuentas.size()][columnas.length];
            for (int i = 0; i < listaCuentas.size(); i++) {
                datos[i][0] = listaCuentas.get(i).getCuenta();//CP
                datos[i][1] = listaCuentas.get(i).getNombre();//Detalle
            }

            for (int h = 0; h < lista.size(); h++) {
                for (int i = 0; i < datos.length; i++) {
                    if (lista.get(h).getBrcCuenta()!= null && lista.get(h).getBrcCuenta().equals(datos[i][0])) {
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (lista.get(h).getBrcCentroProduccion().equals(columnasFaltantes.get(j))) {
                                datos[i][j + 2] = lista.get(h).getBrcSaldo();
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
