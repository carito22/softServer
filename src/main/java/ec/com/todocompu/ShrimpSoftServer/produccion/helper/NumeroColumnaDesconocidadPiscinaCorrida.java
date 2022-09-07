package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.CostoDetalleMultipleCorridaTO;

public class NumeroColumnaDesconocidadPiscinaCorrida {
	private String[] columnas;
	private Object[][] datos;
	private List<String> columnasFaltantes = null;
	private List<CostoProductoSectorPiscinaCorridaSemanalNombres> listaProductos = null;

	public NumeroColumnaDesconocidadPiscinaCorrida() {
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

	public void agruparCabeceraColumnas(List<CostoDetalleMultipleCorridaTO> costoDetalleMultipleCorridaTO) {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		columnasFaltantes.add(costoDetalleMultipleCorridaTO.get(0).getCostoSector().concat(" | ")
				.concat(costoDetalleMultipleCorridaTO.get(0).getCostoPiscina()).concat(" | ")
				.concat(costoDetalleMultipleCorridaTO.get(0).getCostoCorrida()));
		for (int i = 1; i < costoDetalleMultipleCorridaTO.size(); i++) {
			for (int j = 0; j < columnasFaltantes.size(); j++) {
				if (costoDetalleMultipleCorridaTO.get(i).getCostoSector().concat(" | ")
						.concat(costoDetalleMultipleCorridaTO.get(i).getCostoPiscina()).concat(" | ")
						.concat(costoDetalleMultipleCorridaTO.get(i).getCostoCorrida())
						.compareToIgnoreCase(columnasFaltantes.get(j).toString()) == 0) {
					encontro = true;
					j = columnasFaltantes.size();
				} else {
					encontro = false;
				}
			}
			if (!encontro) {

				columnasFaltantes.add(costoDetalleMultipleCorridaTO.get(i).getCostoSector().concat(" | ")
						.concat(costoDetalleMultipleCorridaTO.get(i).getCostoPiscina()).concat(" | ")
						.concat(costoDetalleMultipleCorridaTO.get(i).getCostoCorrida()));
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

	public void agruparProductos(List<CostoDetalleMultipleCorridaTO> costoDetalleMultipleCorridaTO) {
		listaProductos = new ArrayList<CostoProductoSectorPiscinaCorridaSemanalNombres>();
		boolean encontro = false;
		costoDetalleMultipleCorridaTO.get(0)
				.setCostoCodigo(costoDetalleMultipleCorridaTO.get(0).getCostoCodigo() == null ? ""
						: costoDetalleMultipleCorridaTO.get(0).getCostoCodigo());
		listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(
				costoDetalleMultipleCorridaTO.get(0).getCostoCodigo(),
				costoDetalleMultipleCorridaTO.get(0).getCostoProducto(),
				costoDetalleMultipleCorridaTO.get(0).getCostoMedida()));
		for (int i = 1; i < costoDetalleMultipleCorridaTO.size(); i++) {
			for (int j = 0; j < listaProductos.size(); j++) {
				costoDetalleMultipleCorridaTO.get(i)
						.setCostoCodigo(costoDetalleMultipleCorridaTO.get(i).getCostoCodigo() == null ? ""
								: costoDetalleMultipleCorridaTO.get(i).getCostoCodigo());
				if (costoDetalleMultipleCorridaTO.get(i).getCostoCodigo()
						.concat(costoDetalleMultipleCorridaTO.get(i).getCostoProducto()).compareToIgnoreCase(
								listaProductos.get(j).getCodigo().concat(listaProductos.get(j).getProducto())) == 0) {
					encontro = true;
					j = listaProductos.size();
				} else {
					encontro = false;
				}
			}
			if (!encontro) {
				listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(
						costoDetalleMultipleCorridaTO.get(i).getCostoCodigo(),
						costoDetalleMultipleCorridaTO.get(i).getCostoProducto(),
						costoDetalleMultipleCorridaTO.get(i).getCostoMedida()));
			}
		}

	}

	public void llenarObjetoParaTabla(List<CostoDetalleMultipleCorridaTO> costoDetalleMultipleCorridaTO) {
		datos = new Object[listaProductos.size()][columnas.length];
		for (int i = 0; i < listaProductos.size(); i++) {
			datos[i][0] = listaProductos.get(i).getProducto();
			datos[i][1] = listaProductos.get(i).getCodigo();
			datos[i][2] = listaProductos.get(i).getMedida();
		}

		for (int h = 0; h < costoDetalleMultipleCorridaTO.size(); h++) {
			for (int i = 0; i < datos.length; i++) {
				if (costoDetalleMultipleCorridaTO.get(h).getCostoCodigo()
						.concat(costoDetalleMultipleCorridaTO.get(h).getCostoProducto())
						.compareToIgnoreCase(datos[i][1].toString().concat(datos[i][0].toString())) == 0) {
					for (int j = 0; j < columnasFaltantes.size(); j++) {
						if (costoDetalleMultipleCorridaTO.get(h).getCostoSector().concat(" | ")
								.concat(costoDetalleMultipleCorridaTO.get(h).getCostoPiscina()).concat(" | ")
								.concat(costoDetalleMultipleCorridaTO.get(h).getCostoCorrida())
								.compareToIgnoreCase(columnasFaltantes.get(j).toString()) == 0) {
							datos[i][j + 3] = costoDetalleMultipleCorridaTO.get(h).getCostoTotal();
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
