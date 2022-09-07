package ec.com.todocompu.ShrimpSoftServer.rrhh.helper;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionFechasTO;

public class NumeroColumnaDesconocidadProvisionFechas {
	private String[] columnas;
	private Object[][] datos;
	private List<ProvisionFechasNombre> listaProvisionFechas = null;
	private List<String> columnasFaltantes = null;

	public NumeroColumnaDesconocidadProvisionFechas() {
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

	public List<ProvisionFechasNombre> getListaProvisionFechas() {
		return listaProvisionFechas;
	}

	public void agruparCabeceraColumnas(List<RhProvisionFechasTO> rhProvisionFechasTOs) {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (rhProvisionFechasTOs.size() > 0) {
			columnasFaltantes.add(
					rhProvisionFechasTOs.get(0).getProvMes() == null ? "" : rhProvisionFechasTOs.get(0).getProvMes());
			for (int i = 1; i < rhProvisionFechasTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if ((rhProvisionFechasTOs.get(i) == null ? "" : rhProvisionFechasTOs.get(i).getProvMes())
							.equals(columnasFaltantes.get(j).toString())) {
						encontro = true;
						j = columnasFaltantes.size();

					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					columnasFaltantes.add(rhProvisionFechasTOs.get(i).getProvMes() == null ? ""
							: rhProvisionFechasTOs.get(i).getProvMes());
				}
			}
			columnas = new String[2 + columnasFaltantes.size()];
			columnas[0] = "Cédula";
			columnas[1] = "Nombres";
			for (int i = 0; i < columnasFaltantes.size(); i++) {
				columnas[i + 2] = columnasFaltantes.get(i).toString();
			}
		}

	}

	public void agruparCuentas(List<RhProvisionFechasTO> rhProvisionFechasTOs) {
		listaProvisionFechas = new ArrayList<ProvisionFechasNombre>();
		boolean encontro = false;
		if (rhProvisionFechasTOs.size() > 0) {

			rhProvisionFechasTOs.get(0)
					.setProvId(rhProvisionFechasTOs.get(0) == null ? "" : rhProvisionFechasTOs.get(0).getProvId());
			rhProvisionFechasTOs.get(0)
					.setProvId(rhProvisionFechasTOs.get(0) == null ? "" : rhProvisionFechasTOs.get(0).getProvNombres());

			listaProvisionFechas.add(new ProvisionFechasNombre(rhProvisionFechasTOs.get(0).getProvId(),
					rhProvisionFechasTOs.get(0).getProvNombres()));

			for (int i = 1; i < rhProvisionFechasTOs.size(); i++) {
				for (int j = 0; j < listaProvisionFechas.size(); j++) {

					rhProvisionFechasTOs.get(i).setProvId(rhProvisionFechasTOs.get(i).getProvId() == null ? ""
							: rhProvisionFechasTOs.get(i).getProvId());

					rhProvisionFechasTOs.get(i).setProvNombres(rhProvisionFechasTOs.get(i).getProvNombres() == null ? ""
							: rhProvisionFechasTOs.get(i).getProvNombres());

					if (rhProvisionFechasTOs.get(i).getProvId().concat(rhProvisionFechasTOs.get(i).getProvNombres())
							.equals(listaProvisionFechas.get(j).getCedula()
									.concat(listaProvisionFechas.get(j).getNombre()))) {

						encontro = true;
						j = listaProvisionFechas.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					listaProvisionFechas.add(new ProvisionFechasNombre(rhProvisionFechasTOs.get(i).getProvId(),
							rhProvisionFechasTOs.get(i).getProvNombres()));
				}
			}
		}
	}

	public void llenarObjetoParaTabla(List<RhProvisionFechasTO> rhProvisionFechasTOs) {
		if (rhProvisionFechasTOs.size() > 0) {
			datos = new Object[listaProvisionFechas.size()][columnas.length];
			for (int i = 0; i < listaProvisionFechas.size(); i++) {
				datos[i][0] = listaProvisionFechas.get(i).getCedula();
				datos[i][1] = listaProvisionFechas.get(i).getNombre();

			}

			for (int h = 0; h < rhProvisionFechasTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (rhProvisionFechasTOs.get(h).getProvId().concat(rhProvisionFechasTOs.get(h).getProvNombres())
							.equals(datos[i][0].toString().concat(datos[i][1].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (rhProvisionFechasTOs.get(h).getProvMes().equals(columnasFaltantes.get(j).toString())) {
								datos[i][j + 2] = rhProvisionFechasTOs.get(h).getProvValor();
								j = columnasFaltantes.size();
							}
						}
						i = datos.length;
					}
				}
			}
			for (int i = 0; i < datos.length; i++) {
				datos[i][1] = datos[i][1].toString().substring(datos[i][1].toString().indexOf("|") + 1);
			}
		}
	}
}
