package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoTO;

public class NumeroColumnaDesconocidadCosto {
	private String[] columnas;
	private Object[][] datos;
	private List<String> columnasFaltantes = null;
	private List<CostoProductoSectorPiscinaCorridaSemanalNombres> listaProductos;
	private List<PrdCostoTO> prdCostoTOs;

	public NumeroColumnaDesconocidadCosto(List<PrdCostoTO> prdCostoTOs) {
		this.prdCostoTOs = prdCostoTOs;
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

	public List<CostoProductoSectorPiscinaCorridaSemanalNombres> getListaProductos() {
		return listaProductos;
	}

	private void agruparCabeceraColumnas() {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (prdCostoTOs.size() > 0) {
			columnasFaltantes.add(prdCostoTOs.get(0).getCostoSectorPiscina());
			for (int i = 1; i < prdCostoTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if (prdCostoTOs.get(i).getCostoSectorPiscina().equals(columnasFaltantes.get(j).toString())) {
						encontro = true;
						j = columnasFaltantes.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					columnasFaltantes.add(prdCostoTOs.get(i).getCostoSectorPiscina());
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

	private void agruparProductos() {
		listaProductos = new ArrayList<CostoProductoSectorPiscinaCorridaSemanalNombres>();
		boolean encontro = false;
		if (prdCostoTOs.size() > 0) {
			prdCostoTOs.get(0).setCostoCodigo(
					prdCostoTOs.get(0).getCostoCodigo() == null ? "" : prdCostoTOs.get(0).getCostoCodigo());
			prdCostoTOs.get(0).setCostoProducto(
					prdCostoTOs.get(0).getCostoProducto() == null ? "" : prdCostoTOs.get(0).getCostoProducto());
			prdCostoTOs.get(0).setCostoMedida(
					prdCostoTOs.get(0).getCostoMedida() == null ? "" : prdCostoTOs.get(0).getCostoMedida());

			listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(prdCostoTOs.get(0).getCostoCodigo(),
					prdCostoTOs.get(0).getCostoProducto(), prdCostoTOs.get(0).getCostoMedida()));
			for (int i = 1; i < prdCostoTOs.size(); i++) {
				for (int j = 0; j < listaProductos.size(); j++) {
					prdCostoTOs.get(i).setCostoCodigo(
							prdCostoTOs.get(i).getCostoCodigo() == null ? "" : prdCostoTOs.get(i).getCostoCodigo());
					prdCostoTOs.get(i).setCostoProducto(
							prdCostoTOs.get(i).getCostoProducto() == null ? "" : prdCostoTOs.get(i).getCostoProducto());
					prdCostoTOs.get(i).setCostoMedida(
							prdCostoTOs.get(i).getCostoMedida() == null ? "" : prdCostoTOs.get(i).getCostoMedida());
					if (prdCostoTOs.get(i).getCostoCodigo().concat(prdCostoTOs.get(i).getCostoProducto())
							.equals(listaProductos.get(j).getCodigo().concat(listaProductos.get(j).getProducto()))) {
						encontro = true;
						j = listaProductos.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					listaProductos.add(
							new CostoProductoSectorPiscinaCorridaSemanalNombres(prdCostoTOs.get(i).getCostoCodigo(),
									prdCostoTOs.get(i).getCostoProducto(), prdCostoTOs.get(i).getCostoMedida()));
				}
			}
		}
	}

	private void llenarObjetoParaTabla() {
		if (prdCostoTOs.size() > 0) {
			datos = new Object[listaProductos.size()][columnas.length];
			for (int i = 0; i < listaProductos.size(); i++) {
				datos[i][0] = listaProductos.get(i).getProducto();
				datos[i][1] = listaProductos.get(i).getCodigo();
				datos[i][2] = listaProductos.get(i).getMedida();
			}

			for (int h = 0; h < prdCostoTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (prdCostoTOs.get(h).getCostoCodigo().concat(prdCostoTOs.get(h).getCostoProducto())
							.equals(datos[i][1].toString().concat(datos[i][0].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (prdCostoTOs.get(h).getCostoSectorPiscina()
									.equals(columnasFaltantes.get(j).toString())) {
								datos[i][j + 3] = prdCostoTOs.get(h).getCostoTotal();
								j = columnasFaltantes.size();
							}
						}
						i = datos.length;
					}
				}
			}
			for (int i = 0; i < datos.length; i++) {
				datos[i][0] = datos[i][0].toString().substring(datos[i][0].toString().indexOf("|") + 1);
			}
		}
	}
}
