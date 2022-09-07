package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDiariosTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumeroColumnaDesconocidasConsumosDiarios {

    private final List<String> columnas = new ArrayList<>();
    private final List<List<String>> datos = new ArrayList<>();
    private List<String> columnasFaltantes = null;
    private List<ConsumosDiariosNombres> listaProductos;
    private final List<PrdConsumosDiariosTO> prdConsumosTOs;
    List<String> piscinasSinDuplicados = new ArrayList<>();

    public NumeroColumnaDesconocidasConsumosDiarios(List<PrdConsumosDiariosTO> prdConsumosTOs) {
        this.prdConsumosTOs = prdConsumosTOs;
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
        if (prdConsumosTOs.size() > 0) {
            columnasFaltantes.add(prdConsumosTOs.get(0).getConsumoSector());
            for (int i = 1; i < prdConsumosTOs.size(); i++) {
                for (int j = 0; j < columnasFaltantes.size(); j++) {
                    if (prdConsumosTOs.get(i).getConsumoSector().equals(columnasFaltantes.get(j))) {
                        encontro = true;
                        j = columnasFaltantes.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    columnasFaltantes.add(prdConsumosTOs.get(i).getConsumoSector());
                }
            }
            columnas.add("Piscina");
            columnas.add("Producto");
            columnas.add("Código");
            columnas.add("Medida");
            for (int i = 0; i < columnasFaltantes.size(); i++) {
                columnas.add(columnasFaltantes.get(i));
            }
        }

    }

    public final void agruparProductos() {
        listaProductos = new ArrayList<>();
        boolean encontro = false;
        if (prdConsumosTOs.size() > 0) {
            prdConsumosTOs.get(0).setConsumoPiscina(
                    prdConsumosTOs.get(0).getConsumoPiscina() == null ? "" : prdConsumosTOs.get(0).getConsumoPiscina());
            prdConsumosTOs.get(0).setConsumoCodigo(
                    prdConsumosTOs.get(0).getConsumoCodigo() == null ? "" : prdConsumosTOs.get(0).getConsumoCodigo());
            prdConsumosTOs.get(0).setConsumoProducto(
                    prdConsumosTOs.get(0).getConsumoProducto() == null ? "" : prdConsumosTOs.get(0).getConsumoProducto());
            prdConsumosTOs.get(0).setConsumoMedida(
                    prdConsumosTOs.get(0).getConsumoMedida() == null ? "" : prdConsumosTOs.get(0).getConsumoMedida());

            listaProductos
                    .add(new ConsumosDiariosNombres(
                            prdConsumosTOs.get(0).getConsumoPiscina(), prdConsumosTOs.get(0).getConsumoCodigo(),
                            prdConsumosTOs.get(0).getConsumoProducto(), prdConsumosTOs.get(0).getConsumoMedida()));
            for (int i = 1; i < prdConsumosTOs.size(); i++) {
                for (int j = 0; j < listaProductos.size(); j++) {
                    prdConsumosTOs.get(i).setConsumoPiscina(prdConsumosTOs.get(i).getConsumoPiscina() == null ? ""
                            : prdConsumosTOs.get(i).getConsumoPiscina());
                    prdConsumosTOs.get(i).setConsumoCodigo(prdConsumosTOs.get(i).getConsumoCodigo() == null ? ""
                            : prdConsumosTOs.get(i).getConsumoCodigo());
                    prdConsumosTOs.get(i).setConsumoProducto(prdConsumosTOs.get(i).getConsumoProducto() == null ? ""
                            : prdConsumosTOs.get(i).getConsumoProducto());
                    prdConsumosTOs.get(i).setConsumoMedida(prdConsumosTOs.get(i).getConsumoMedida() == null ? ""
                            : prdConsumosTOs.get(i).getConsumoMedida());
                    if (prdConsumosTOs.get(i).getConsumoCodigo().equals(listaProductos.get(j).getCodigo())
                            && prdConsumosTOs.get(i).getConsumoPiscina().equals(listaProductos.get(j).getPiscina())) {
                        encontro = true;
                        j = listaProductos.size();
                    } else {
                        encontro = false;
                    }
                }
                if (!encontro) {
                    listaProductos.add(new ConsumosDiariosNombres(
                            prdConsumosTOs.get(i).getConsumoPiscina(), prdConsumosTOs.get(i).getConsumoCodigo(),
                            prdConsumosTOs.get(i).getConsumoProducto(), prdConsumosTOs.get(i).getConsumoMedida()));
                }
            }
        }
    }

    public final void llenarObjetoParaTabla() {
        if (prdConsumosTOs.size() > 0) {
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

            for (int h = 0; h < prdConsumosTOs.size(); h++) {
                for (int i = 0; i < datos.size(); i++) {
                    if (prdConsumosTOs.get(h).getConsumoCodigo().equals(datos.get(i).get(2))
                            && prdConsumosTOs.get(h).getConsumoPiscina().equals(datos.get(i).get(0))) {
                        for (int j = 0; j < columnasFaltantes.size(); j++) {
                            if (prdConsumosTOs.get(h).getConsumoSector().equals(columnasFaltantes.get(j))) {
                                datos.get(i).set(j + 4, prdConsumosTOs.get(h).getConsumoCantidad() + "");
                                j = columnasFaltantes.size();
                            }
                        }
                        i = datos.size();
                    }
                }
            }
        }
    }
}
