package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostosDiariosTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumeroColumnaDesconocidasCostosDiarios {

    private final List<String> columnas = new ArrayList<>();
    private final List<List<String>> datos = new ArrayList<>();
    private List<String> columnasFaltantes = null;
    private List<ConsumosDiariosNombres> listaProductos;
    private final List<PrdCostosDiariosTO> prdCostosTOs;
    List<String> piscinasSinDuplicados = new ArrayList<>();

    public NumeroColumnaDesconocidasCostosDiarios(List<PrdCostosDiariosTO> prdCostosTOs) {
        this.prdCostosTOs = prdCostosTOs;
        agruparCabeceraColumnas();
        agruparProductos();
        llenarObjetoParaTabla();
    }

    public List<String> getColumnas() {
        return columnas;
    }

    public List<List<String>> getDatos() {
        return datos;
    }

    public List<String> getColumnasFaltantes() {
        return columnasFaltantes;
    }

    public List<String> getPiscinasSinDuplicados() {
        return piscinasSinDuplicados;
    }

    public final void agruparCabeceraColumnas() {
        boolean encontro = false;
        columnasFaltantes = new ArrayList<>();
        if (prdCostosTOs.size() > 0) {
            columnasFaltantes.add(prdCostosTOs.get(0).getCostoSector());
            for (int i = 1; i < prdCostosTOs.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (prdCostosTOs.get(i).getCostoSector().equals(columnasFaltantes.get(j))) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    columnasFaltantes.add(prdCostosTOs.get(i).getCostoSector());
                }
            }
            columnas.add("Piscina");
            columnas.add("Producto");
            columnas.add("Código");
            columnas.add("Medida");
            for (int i = 0; i < columnasFaltantes.size(); i++) {
                columnas.add(columnasFaltantes.get(i));
            }
            columnas.add("Total");
        }

    }

    public final void agruparProductos() {
        listaProductos = new ArrayList<>();
        boolean encontro = false;
        if (prdCostosTOs.size() > 0) {
            prdCostosTOs.get(0).setCostoPiscina(
                    prdCostosTOs.get(0).getCostoPiscina() == null ? "" : prdCostosTOs.get(0).getCostoPiscina());
            prdCostosTOs.get(0).setCostoCodigo(
                    prdCostosTOs.get(0).getCostoCodigo() == null ? "" : prdCostosTOs.get(0).getCostoCodigo());
            prdCostosTOs.get(0).setCostoProducto(
                    prdCostosTOs.get(0).getCostoProducto() == null ? "" : prdCostosTOs.get(0).getCostoProducto());
            prdCostosTOs.get(0).setCostoMedida(
                    prdCostosTOs.get(0).getCostoMedida() == null ? "" : prdCostosTOs.get(0).getCostoMedida());

            listaProductos
                    .add(new ConsumosDiariosNombres(
                            prdCostosTOs.get(0).getCostoPiscina(), prdCostosTOs.get(0).getCostoCodigo(),
                            prdCostosTOs.get(0).getCostoProducto(), prdCostosTOs.get(0).getCostoMedida()));
            for (int i = 1; i < prdCostosTOs.size(); i++) {
                for (int j = 0; j < listaProductos.size(); j++) {
                    prdCostosTOs.get(i).setCostoPiscina(prdCostosTOs.get(i).getCostoPiscina() == null ? ""
                            : prdCostosTOs.get(i).getCostoPiscina());
                    prdCostosTOs.get(i).setCostoCodigo(prdCostosTOs.get(i).getCostoCodigo() == null ? ""
                            : prdCostosTOs.get(i).getCostoCodigo());
                    prdCostosTOs.get(i).setCostoProducto(prdCostosTOs.get(i).getCostoProducto() == null ? ""
                            : prdCostosTOs.get(i).getCostoProducto());
                    prdCostosTOs.get(i).setCostoMedida(prdCostosTOs.get(i).getCostoMedida() == null ? ""
                            : prdCostosTOs.get(i).getCostoMedida());
                    if (prdCostosTOs.get(i).getCostoCodigo().equals(listaProductos.get(j).getCodigo())
                            && prdCostosTOs.get(i).getCostoPiscina().equals(listaProductos.get(j).getPiscina())) {
                        encontro = true;
                        j = listaProductos.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaProductos.add(new ConsumosDiariosNombres(
                            prdCostosTOs.get(i).getCostoPiscina(), prdCostosTOs.get(i).getCostoCodigo(),
                            prdCostosTOs.get(i).getCostoProducto(), prdCostosTOs.get(i).getCostoMedida()));
                }
            }
        }
    }

    public final void llenarObjetoParaTabla() {
        if (prdCostosTOs.size() > 0) {
            piscinasSinDuplicados = listaProductos.stream()
                    .map(item -> item.getPiscina())
                    .distinct()
                    .collect(Collectors.toList());
            for (int i = 0; i < listaProductos.size(); i++) {
                datos.add(i, Arrays.asList(new String[columnas.size()]));
                datos.get(i).set(0, listaProductos.get(i).getPiscina());
                datos.get(i).set(1, listaProductos.get(i).getProducto());
                datos.get(i).set(2, listaProductos.get(i).getCodigo());
                datos.get(i).set(3, listaProductos.get(i).getMedida());
            }

            for (int h = 0; h < prdCostosTOs.size(); h++) {
                for (int i = 0; i < datos.size(); i++) {
                    if (prdCostosTOs.get(h).getCostoCodigo().equals(datos.get(i).get(2))
                            && prdCostosTOs.get(h).getCostoPiscina().equals(datos.get(i).get(0))) {
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (prdCostosTOs.get(h).getCostoSector().equals(columnasFaltantes.get(j))) {
                                datos.get(i).set(j + 4, prdCostosTOs.get(h).getCostoCantidad() + "");
                                j = columnasFaltantes.size();
                            }
                        }
                        i = datos.size();
                    }
                }
            }

            int i = 0;
            while (i < datos.size()) {
                BigDecimal total = BigDecimal.ZERO;
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (datos.get(i).get(j + 4) != null && !datos.get(i).get(j + 4).equals("")) {
                        total = total.add(new BigDecimal(datos.get(i).get(j + 4)));
                    }
                }
                datos.get(i).set(columnasFaltantes.size() + 4, total + "");
                i++;
            }
        }
    }
}
