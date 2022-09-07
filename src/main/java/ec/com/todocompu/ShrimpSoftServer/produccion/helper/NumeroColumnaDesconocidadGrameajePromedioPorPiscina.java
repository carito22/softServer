package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import java.math.BigDecimal;

public class NumeroColumnaDesconocidadGrameajePromedioPorPiscina {

    private String[] columnas;
    private Object[][] datos;
    private List<String> columnasFaltantes = null;
    private List<PiscinaNombres> listaPiscinaNombres = null;

    public NumeroColumnaDesconocidadGrameajePromedioPorPiscina() {
    }

    public String[] getColumnas() {
        return columnas;
    }

    public Object[][] getDatos() {
        return datos;
    }

    public List<String> getColumnasFaltantes() {
        return columnasFaltantes;
    }

    public void agruparCabeceraColumnas(
            List<PrdListaConsultaGrameajePromedioPorPiscinaTO> prdListaConsultaGrameajePromedioPorPiscinaTOs) {
        boolean encontro = false;
        columnasFaltantes = new ArrayList<>();
        if (prdListaConsultaGrameajePromedioPorPiscinaTOs.size() > 0) {
            columnasFaltantes.add(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraFecha());
            for (int i = 1; i < prdListaConsultaGrameajePromedioPorPiscinaTOs.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraFecha().equals(columnasFaltantes.get(j))) {
                        encontro = true;
                        j = columnasFaltantes.size();

                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    columnasFaltantes.add(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraFecha());
                }
            }
            int numero = 5;
            columnas = new String[numero + columnasFaltantes.size()];
            columnas[0] = "Piscina";
            columnas[1] = "Laboratorio";
            columnas[2] = "Densidad";
            columnas[3] = "Fecha Siembra";
            columnas[4] = "Edad";
            for (int i = 0; i < columnasFaltantes.size(); i++) {
                columnas[i + numero] = columnasFaltantes.get(i);
            }
        }

    }

    public void agruparPisina(List<PrdListaConsultaGrameajePromedioPorPiscinaTO> prdListaConsultaGrameajePromedioPorPiscinaTOs) {
        listaPiscinaNombres = new ArrayList<>();
        boolean encontro = false;
        if (prdListaConsultaGrameajePromedioPorPiscinaTOs.size() > 0) {
            prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).setGraPiscina(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraPiscina() == null ? "" : prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraPiscina());
            listaPiscinaNombres
                    .add(new PiscinaNombres(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraPiscina(),
                            prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraLaboratorio(),
                            prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraDensidad(),
                            prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getGraEdad(),
                            prdListaConsultaGrameajePromedioPorPiscinaTOs.get(0).getCorFechaSiembra()));
            for (int i = 1; i < prdListaConsultaGrameajePromedioPorPiscinaTOs.size(); i++) {
                for (int j = 0; j < listaPiscinaNombres.size(); j++) {
                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).setGraPiscina(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraPiscina() == null ? "" : prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraPiscina());
                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).setGraLaboratorio(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraLaboratorio()== null ? "" : prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraLaboratorio());
                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).setGraDensidad(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraDensidad()== null ? BigDecimal.ZERO: prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraDensidad());
                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).setGraEdad(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraEdad() == null ? "" : prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraEdad());
                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).setCorFechaSiembra(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getCorFechaSiembra() == null ? "" : prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getCorFechaSiembra());
                    if (prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraPiscina()
                            .concat(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraPiscina())
                            .equals(listaPiscinaNombres.get(j).getGraPiscina().concat(listaPiscinaNombres.get(j).getGraPiscina()))) {
                        encontro = true;
                        j = listaPiscinaNombres.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaPiscinaNombres.add(
                            new PiscinaNombres(prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraPiscina(),
                                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraLaboratorio(),
                                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraDensidad(),
                                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getGraEdad(),
                                    prdListaConsultaGrameajePromedioPorPiscinaTOs.get(i).getCorFechaSiembra()));
                }
            }
        }
    }

    public void llenarObjetoParaTabla(List<PrdListaConsultaGrameajePromedioPorPiscinaTO> prdListaConsultaGrameajePromedioPorPiscinaTOs, boolean sobrevivencia) {
        int numero = 5;
        if (prdListaConsultaGrameajePromedioPorPiscinaTOs.size() > 0) {
            datos = new Object[listaPiscinaNombres.size()][columnas.length];
            for (int i = 0; i < listaPiscinaNombres.size(); i++) {
                datos[i][0] = listaPiscinaNombres.get(i).getGraPiscina();
                datos[i][1] = listaPiscinaNombres.get(i).getGraLaboratorio();
                datos[i][2] = listaPiscinaNombres.get(i).getGraDensidad();
                datos[i][3] = listaPiscinaNombres.get(i).getCorFechaSiembra();
                datos[i][4] = listaPiscinaNombres.get(i).getGraEdad();
            }
            for (int h = 0; h < prdListaConsultaGrameajePromedioPorPiscinaTOs.size(); h++) {
                for (int i = 0; i < datos.length; i++) {
                    if (prdListaConsultaGrameajePromedioPorPiscinaTOs.get(h).getGraPiscina().equals(datos[i][0].toString())) {
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (prdListaConsultaGrameajePromedioPorPiscinaTOs.get(h).getGraFecha().equals(columnasFaltantes.get(j))) {
                                if (sobrevivencia) {
                                    datos[i][j + numero] = prdListaConsultaGrameajePromedioPorPiscinaTOs.get(h).getGraSobrevivencia();
                                } else {
                                    datos[i][j + numero] = prdListaConsultaGrameajePromedioPorPiscinaTOs.get(h).getGraTPromedio();
                                }
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
