package ec.com.todocompu.ShrimpSoftServer.util;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosSaldosGeneralTO;

public class NumeroColumnaDesconocidadFunListaProductosSaldosGeneral {
	private List<CostoSaldoBodegasNombres> listaProductos = null;
	private List<String> columnasFaltantes = null;

	private String[] columnas;
	private Object[][] datos;

	public NumeroColumnaDesconocidadFunListaProductosSaldosGeneral() {
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

	public List<CostoSaldoBodegasNombres> getListaProductos() {
		return listaProductos;
	}

	public void agruparCabeceraColumnas(List<InvFunListaProductosSaldosGeneralTO> funListaProductosSaldosGeneralTOs) {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (funListaProductosSaldosGeneralTOs.size() > 0) {
			columnasFaltantes.add(funListaProductosSaldosGeneralTOs.get(0).getLpspBodega());
			for (int i = 1; i < funListaProductosSaldosGeneralTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if (funListaProductosSaldosGeneralTOs.get(i).getLpspBodega()
							.equals(columnasFaltantes.get(j).toString())) {
						encontro = true;
						j = columnasFaltantes.size();

					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					columnasFaltantes.add(funListaProductosSaldosGeneralTOs.get(i).getLpspBodega());
				}
			}
			columnas = new String[2 + columnasFaltantes.size()];
			columnas[0] = "Producto";
			columnas[1] = "Código";
			for (int i = 0; i < columnasFaltantes.size(); i++) {
				columnas[i + 2] = columnasFaltantes.get(i).toString();
			}
		}

	}

	public void agruparProductos(List<InvFunListaProductosSaldosGeneralTO> prdFunListaProductosSaldosGeneralTOs) {
		listaProductos = new ArrayList<CostoSaldoBodegasNombres>();
		boolean encontro = false;
		if (prdFunListaProductosSaldosGeneralTOs.size() > 0) {
			prdFunListaProductosSaldosGeneralTOs.get(0)
					.setLpspCodigoPrincipal(prdFunListaProductosSaldosGeneralTOs.get(0).getLpspCodigoPrincipal() == null
							? "" : prdFunListaProductosSaldosGeneralTOs.get(0).getLpspCodigoPrincipal());

			prdFunListaProductosSaldosGeneralTOs.get(0)
					.setLpspNombre(prdFunListaProductosSaldosGeneralTOs.get(0).getLpspNombre() == null ? ""
							: prdFunListaProductosSaldosGeneralTOs.get(0).getLpspNombre());

			listaProductos.add(
					new CostoSaldoBodegasNombres(prdFunListaProductosSaldosGeneralTOs.get(0).getLpspCodigoPrincipal(),
							prdFunListaProductosSaldosGeneralTOs.get(0).getLpspNombre()));

			for (int i = 1; i < prdFunListaProductosSaldosGeneralTOs.size(); i++) {
				for (int j = 0; j < listaProductos.size(); j++) {
					prdFunListaProductosSaldosGeneralTOs.get(i).setLpspCodigoPrincipal(
							prdFunListaProductosSaldosGeneralTOs.get(i).getLpspCodigoPrincipal() == null ? ""
									: prdFunListaProductosSaldosGeneralTOs.get(i).getLpspCodigoPrincipal());

					prdFunListaProductosSaldosGeneralTOs.get(i)
							.setLpspNombre(prdFunListaProductosSaldosGeneralTOs.get(i).getLpspNombre() == null ? ""
									: prdFunListaProductosSaldosGeneralTOs.get(i).getLpspNombre());

					if (prdFunListaProductosSaldosGeneralTOs.get(i).getLpspCodigoPrincipal()
							.concat(prdFunListaProductosSaldosGeneralTOs.get(i).getLpspNombre())
							.equals(listaProductos.get(j).getCodigo().concat(listaProductos.get(j).getProducto()))) {
						encontro = true;
						j = listaProductos.size();
					} else {
						encontro = false;
					}
				}

				if (!encontro) {
					listaProductos.add(new CostoSaldoBodegasNombres(
							prdFunListaProductosSaldosGeneralTOs.get(i).getLpspCodigoPrincipal(),
							prdFunListaProductosSaldosGeneralTOs.get(i).getLpspNombre()));
				}
			}
		}
	}

	public void llenarObjetoParaTabla(
			List<InvFunListaProductosSaldosGeneralTO> prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs) {
		if (prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs.size() > 0) {
			datos = new Object[listaProductos.size()][columnas.length];
			for (int i = 0; i < listaProductos.size(); i++) {
				datos[i][0] = listaProductos.get(i).getProducto();
				datos[i][1] = listaProductos.get(i).getCodigo();

			}

			for (int h = 0; h < prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs.get(h).getLpspCodigoPrincipal()
							.concat(prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs.get(h)
									.getLpspNombre())
							.equals(datos[i][1].toString().concat(datos[i][0].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs.get(h).getLpspBodega()
									.equals(columnasFaltantes.get(j).toString())) {
								datos[i][j + 2] = prdFunListaProductosSaldosGeneralTOsConsumoPorFechaDesglosadoTOs
										.get(h).getLpspSaldo();
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
