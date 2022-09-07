package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;

public class NumeroColumnaDesconocidadConsumo {
	private String[] columnas;
	private Object[][] datos;
	private List<String> columnasFaltantes = null;
	private List<CostoProductoSectorPiscinaCorridaSemanalNombres> listaProductos;
	private List<PrdConsumosTO> prdConsumosTOs;

	public NumeroColumnaDesconocidadConsumo(List<PrdConsumosTO> prdConsumosTOs) {
		this.prdConsumosTOs = prdConsumosTOs;
		agruparCabeceraColumnas();
		agruparProductos();
		llenarObjetoParaTabla();
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

	public void agruparCabeceraColumnas()  {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (prdConsumosTOs.size() > 0) {
			columnasFaltantes.add(prdConsumosTOs.get(0).getConsumoSector());
			for (int i = 1; i < prdConsumosTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if (prdConsumosTOs.get(i).getConsumoSector().equals(columnasFaltantes.get(j).toString())) {
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
			columnas = new String[3 + columnasFaltantes.size()];
			columnas[0] = "Producto";
			columnas[1] = "Código";
			columnas[2] = "Medida";
			for (int i = 0; i < columnasFaltantes.size(); i++) {
				columnas[i + 3] = columnasFaltantes.get(i).toString();
			}
		}

	}

	public void agruparProductos() {
		listaProductos = new ArrayList<CostoProductoSectorPiscinaCorridaSemanalNombres>();
		boolean encontro = false;
		if (prdConsumosTOs.size() > 0) {
			prdConsumosTOs.get(0).setConsumoCodigo(
					prdConsumosTOs.get(0).getConsumoCodigo() == null ? "" : prdConsumosTOs.get(0).getConsumoCodigo());
			prdConsumosTOs.get(0).setConsumoProducto(prdConsumosTOs.get(0).getConsumoProducto() == null ? ""
					: prdConsumosTOs.get(0).getConsumoProducto());
			prdConsumosTOs.get(0).setConsumoMedida(
					prdConsumosTOs.get(0).getConsumoMedida() == null ? "" : prdConsumosTOs.get(0).getConsumoMedida());

			listaProductos
					.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(prdConsumosTOs.get(0).getConsumoCodigo(),
							prdConsumosTOs.get(0).getConsumoProducto(), prdConsumosTOs.get(0).getConsumoMedida()));
			for (int i = 1; i < prdConsumosTOs.size(); i++) {
				for (int j = 0; j < listaProductos.size(); j++) {
					prdConsumosTOs.get(i).setConsumoCodigo(prdConsumosTOs.get(i).getConsumoCodigo() == null ? ""
							: prdConsumosTOs.get(i).getConsumoCodigo());
					prdConsumosTOs.get(i).setConsumoProducto(prdConsumosTOs.get(i).getConsumoProducto() == null ? ""
							: prdConsumosTOs.get(i).getConsumoProducto());
					prdConsumosTOs.get(i).setConsumoMedida(prdConsumosTOs.get(i).getConsumoMedida() == null ? ""
							: prdConsumosTOs.get(i).getConsumoMedida());
					if (prdConsumosTOs.get(i).getConsumoCodigo().concat(prdConsumosTOs.get(i).getConsumoProducto())
							.equals(listaProductos.get(j).getCodigo().concat(listaProductos.get(j).getProducto()))) {
						encontro = true;
						j = listaProductos.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(
							prdConsumosTOs.get(i).getConsumoCodigo(), prdConsumosTOs.get(i).getConsumoProducto(),
							prdConsumosTOs.get(i).getConsumoMedida()));
				}
			}
		}
	}

	public void llenarObjetoParaTabla() {
		if (prdConsumosTOs.size() > 0) {
			datos = new Object[listaProductos.size()][columnas.length];
			for (int i = 0; i < listaProductos.size(); i++) {
				datos[i][0] = listaProductos.get(i).getProducto();
				datos[i][1] = listaProductos.get(i).getCodigo();
				datos[i][2] = listaProductos.get(i).getMedida();
			}

			for (int h = 0; h < prdConsumosTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (prdConsumosTOs.get(h).getConsumoCodigo().concat(prdConsumosTOs.get(h).getConsumoProducto())
							.equals(datos[i][1].toString().concat(datos[i][0].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (prdConsumosTOs.get(h).getConsumoSector().equals(columnasFaltantes.get(j).toString())) {
								datos[i][j + 3] = prdConsumosTOs.get(h).getConsumoCantidad();
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
