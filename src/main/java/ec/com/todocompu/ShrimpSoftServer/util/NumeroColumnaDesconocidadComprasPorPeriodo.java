package ec.com.todocompu.ShrimpSoftServer.util;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.produccion.helper.CostoProductoSectorPiscinaCorridaSemanalNombres;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasPorPeriodoTO;

public class NumeroColumnaDesconocidadComprasPorPeriodo {

	private List<CostoProductoSectorPiscinaCorridaSemanalNombres> listaProductos = null;
	private String[] columnas;
	private Object[][] datos;

	public NumeroColumnaDesconocidadComprasPorPeriodo() {
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

	private List<String> columnasFaltantes = null;

	public void agruparCabeceraColumnas(List<InvComprasPorPeriodoTO> prdComprasPorPeriodoTOs) {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (prdComprasPorPeriodoTOs.size() > 0) {
			columnasFaltantes.add(prdComprasPorPeriodoTOs.get(0).getConsumoFecha());
			for (int i = 1; i < prdComprasPorPeriodoTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if (prdComprasPorPeriodoTOs.get(i).getConsumoFecha().equals(columnasFaltantes.get(j).toString())) {
						encontro = true;
						j = columnasFaltantes.size();

					} else {
						// contador++;
						encontro = false;
					}
				}
				if (!encontro) {
					columnasFaltantes.add(prdComprasPorPeriodoTOs.get(i).getConsumoFecha());
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

	public void agruparProductos(List<InvComprasPorPeriodoTO> prdComprasPorPeriodoTOs) {
		listaProductos = new ArrayList<CostoProductoSectorPiscinaCorridaSemanalNombres>();
		boolean encontro = false;
		if (prdComprasPorPeriodoTOs.size() > 0) {
			prdComprasPorPeriodoTOs.get(0).setConsumoCodigo(prdComprasPorPeriodoTOs.get(0).getConsumoCodigo() == null
					? "" : prdComprasPorPeriodoTOs.get(0).getConsumoCodigo());
			prdComprasPorPeriodoTOs.get(0)
					.setConsumoProducto(prdComprasPorPeriodoTOs.get(0).getConsumoProducto() == null ? ""
							: prdComprasPorPeriodoTOs.get(0).getConsumoProducto());
			prdComprasPorPeriodoTOs.get(0).setConsumoMedida(prdComprasPorPeriodoTOs.get(0).getConsumoMedida() == null
					? "" : prdComprasPorPeriodoTOs.get(0).getConsumoMedida());

			listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(
					prdComprasPorPeriodoTOs.get(0).getConsumoCodigo(),
					prdComprasPorPeriodoTOs.get(0).getConsumoProducto(),
					prdComprasPorPeriodoTOs.get(0).getConsumoMedida()));
			for (int i = 1; i < prdComprasPorPeriodoTOs.size(); i++) {
				for (int j = 0; j < listaProductos.size(); j++) {
					prdComprasPorPeriodoTOs.get(i)
							.setConsumoCodigo(prdComprasPorPeriodoTOs.get(i).getConsumoCodigo() == null ? ""
									: prdComprasPorPeriodoTOs.get(i).getConsumoCodigo());
					prdComprasPorPeriodoTOs.get(i)
							.setConsumoProducto(prdComprasPorPeriodoTOs.get(i).getConsumoProducto() == null ? ""
									: prdComprasPorPeriodoTOs.get(i).getConsumoProducto());
					prdComprasPorPeriodoTOs.get(i)
							.setConsumoMedida(prdComprasPorPeriodoTOs.get(i).getConsumoMedida() == null ? ""
									: prdComprasPorPeriodoTOs.get(i).getConsumoMedida());
					if (prdComprasPorPeriodoTOs.get(i).getConsumoCodigo()
							.concat(prdComprasPorPeriodoTOs.get(i).getConsumoProducto())
							.equals(listaProductos.get(j).getCodigo().concat(listaProductos.get(j).getProducto()))) {
						encontro = true;
						j = listaProductos.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					listaProductos.add(new CostoProductoSectorPiscinaCorridaSemanalNombres(
							prdComprasPorPeriodoTOs.get(i).getConsumoCodigo(),
							prdComprasPorPeriodoTOs.get(i).getConsumoProducto(),
							prdComprasPorPeriodoTOs.get(i).getConsumoMedida()));
				}
			}
		}
	}

	public void llenarObjetoParaTabla(List<InvComprasPorPeriodoTO> prdComprasPorPeriodoTOs) {
		if (prdComprasPorPeriodoTOs.size() > 0) {
			datos = new Object[listaProductos.size()][columnas.length];
			for (int i = 0; i < listaProductos.size(); i++) {
				datos[i][0] = listaProductos.get(i).getProducto();
				datos[i][1] = listaProductos.get(i).getCodigo();
				datos[i][2] = listaProductos.get(i).getMedida();
			}
			for (int h = 0; h < prdComprasPorPeriodoTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (prdComprasPorPeriodoTOs.get(h).getConsumoCodigo()
							.concat(prdComprasPorPeriodoTOs.get(h).getConsumoProducto())
							.equals(datos[i][1].toString().concat(datos[i][0].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (prdComprasPorPeriodoTOs.get(h).getConsumoFecha()
									.equals(columnasFaltantes.get(j).toString())) {
								datos[i][j + 3] = prdComprasPorPeriodoTOs.get(h).getConsumoTotal();
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
