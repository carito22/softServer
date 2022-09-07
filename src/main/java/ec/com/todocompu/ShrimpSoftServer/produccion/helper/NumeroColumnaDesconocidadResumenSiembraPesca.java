package ec.com.todocompu.ShrimpSoftServer.produccion.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;

public class NumeroColumnaDesconocidadResumenSiembraPesca {
    /*
	private String[] columnas;
	private String[] columnasTmp;
	private Object[][] datos;
	private List<String> columnasFaltantes = null;
	private List<ResumenPescaSiembraNombres> listaResumenSiembra;
	private List<PrdResumenPescaSiembraTO> prdResumenPescaSiembraTOs;
	private int siembraPesca = 0;

	// siembraPesca ? siembra = false : pesca = true
	public NumeroColumnaDesconocidadResumenSiembraPesca(boolean siembraPesca,
			List<PrdResumenPescaSiembraTO> prdResumenPescaSiembraTOs) {
		if (siembraPesca == true)
			this.siembraPesca = 1;
		this.prdResumenPescaSiembraTOs = prdResumenPescaSiembraTOs;
		agruparCabeceraColumnas();
		agruparResumenPesca();
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

	private void agruparCabeceraColumnas() {
		boolean encontro = false;
		columnasFaltantes = new ArrayList<String>();
		if (prdResumenPescaSiembraTOs.size() > 0) {
			columnasFaltantes.add(prdResumenPescaSiembraTOs.get(0).getRcTipo());
			for (int i = 1; i < prdResumenPescaSiembraTOs.size(); i++) {
				for (int j = 0; j < columnasFaltantes.size(); j++) {
					if (prdResumenPescaSiembraTOs.get(i).getRcTipo().equals(columnasFaltantes.get(j).toString())) {
						encontro = true;
						j = columnasFaltantes.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					columnasFaltantes.add(prdResumenPescaSiembraTOs.get(i).getRcTipo());
				}
			}

			columnas = new String[15 + siembraPesca + columnasFaltantes.size()];
			columnas[0] = "Sector";
			columnas[1] = "Piscina";
			columnas[2] = "Corrida";
			columnas[3] = "Hectareaje";
			columnas[4] = "Siembra";
			if (siembraPesca == 1)
				columnas[5] = "Pesca";
			columnas[5 + siembraPesca] = "Días Cultivo";
			columnas[6 + siembraPesca] = "Días Inactivo";
			columnas[7 + siembraPesca] = "Densidad";
			columnas[8 + siembraPesca] = "Laboratorio";
			columnas[9 + siembraPesca] = "Nauplio";
			columnas[10 + siembraPesca] = "Biomasa " + (siembraPesca == 0 ? "Proy." : "Real");
			columnas[11 + siembraPesca] = "Conv. Aliment.";
			columnas[12 + siembraPesca] = "Gramos";
			columnas[13 + siembraPesca] = "Sobrevivencia";

			
			columnasTmp = new String[16 + siembraPesca + columnasFaltantes.size()];
			columnasTmp[0] = "Sector";
			columnasTmp[1] = "Piscina";
			columnasTmp[2] = "Corrida";
			columnasTmp[3] = "Hectareaje";
			columnasTmp[4] = "Siembra";
			if (siembraPesca == 1)
				columnasTmp[5] = "Pesca";
			columnasTmp[5 + siembraPesca] = "Días Cultivo";
			columnasTmp[6 + siembraPesca] = "Días Inactivo";
			columnasTmp[7 + siembraPesca] = "Densidad";
			columnasTmp[8 + siembraPesca] = "Laboratorio";
			columnasTmp[9 + siembraPesca] = "Nauplio";
			columnasTmp[10 + siembraPesca] = "Biomasa " + (siembraPesca == 0 ? "Proy." : "Real");
			columnasTmp[11 + siembraPesca] = "Conv. Aliment.";
			columnasTmp[12 + siembraPesca] = "Gramos";
			columnasTmp[13 + siembraPesca] = "Sobrevivencia";
			// //////////PARTE "DINAMICA"
			columnasTmp[14 + siembraPesca] = "Directo";
			columnasTmp[15 + siembraPesca] = "Indirecto";
			columnasTmp[16 + siembraPesca] = "Subtotal";
			columnasTmp[17 + siembraPesca] = "Adm.";
			columnasTmp[18 + siembraPesca] = "Fin.";
			columnasTmp[19 + siembraPesca] = "GND";
			columnasTmp[20 + siembraPesca] = "Subtotal";
			columnasTmp[21 + siembraPesca] = "TOTAL";

			for (int i = 0; i < columnasFaltantes.size(); i++) {
				columnas[i + 14 + siembraPesca] = columnasFaltantes.get(i).toString();
			}
		}

	}

	private void agruparResumenPesca() {
		listaResumenSiembra = new ArrayList<ResumenPescaSiembraNombres>();
		boolean encontro = false;
		if (prdResumenPescaSiembraTOs.size() > 0) {
			listaResumenSiembra.add(new ResumenPescaSiembraNombres(prdResumenPescaSiembraTOs.get(0).getRcSector(),
					prdResumenPescaSiembraTOs.get(0).getRcPiscina(), prdResumenPescaSiembraTOs.get(0).getRcCorrida(),
					prdResumenPescaSiembraTOs.get(0).getRcHectareaje(),
					prdResumenPescaSiembraTOs.get(0).getRcFechaDesde(),
					prdResumenPescaSiembraTOs.get(0).getRcFechaSiembra(),
					prdResumenPescaSiembraTOs.get(0).getRcFechaPesca(),
					prdResumenPescaSiembraTOs.get(0).getRcFechaHasta(), prdResumenPescaSiembraTOs.get(0).getRcEdad(),
					prdResumenPescaSiembraTOs.get(0).getRcDiasMuertos(),
					prdResumenPescaSiembraTOs.get(0).getRcNumeroLarvas(),
					prdResumenPescaSiembraTOs.get(0).getRcDensidad(),
					prdResumenPescaSiembraTOs.get(0).getRcLaboratorio(),
					prdResumenPescaSiembraTOs.get(0).getRcNauplio(), prdResumenPescaSiembraTOs.get(0).getRcBalanceado(),
					prdResumenPescaSiembraTOs.get(0).getRcBiomasa(),
					prdResumenPescaSiembraTOs.get(0).getRcBiomasaReal(),
					prdResumenPescaSiembraTOs.get(0).getRcConversion(),
					prdResumenPescaSiembraTOs.get(0).getRcTallaPromedio(),
					prdResumenPescaSiembraTOs.get(0).getRcPesoIdeal(),
					prdResumenPescaSiembraTOs.get(0).getRcSobrevivencia()));

			for (int i = 1; i < prdResumenPescaSiembraTOs.size(); i++) {
				for (int j = 0; j < listaResumenSiembra.size(); j++) {
					if (prdResumenPescaSiembraTOs.get(i).getRcSector()
							.concat(prdResumenPescaSiembraTOs.get(i).getRcPiscina())
							.concat(prdResumenPescaSiembraTOs.get(i).getRcCorrida())
							.equals(listaResumenSiembra.get(j).getRcSector()
									.concat(listaResumenSiembra.get(j).getRcPiscina())
									.concat(listaResumenSiembra.get(j).getRcCorrida()))) {
						encontro = true;
						j = listaResumenSiembra.size();
					} else {
						encontro = false;
					}
				}
				if (!encontro) {
					listaResumenSiembra
							.add(new ResumenPescaSiembraNombres(prdResumenPescaSiembraTOs.get(i).getRcSector(),
									prdResumenPescaSiembraTOs.get(i).getRcPiscina(),
									prdResumenPescaSiembraTOs.get(i).getRcCorrida(),
									prdResumenPescaSiembraTOs.get(i).getRcHectareaje(),
									prdResumenPescaSiembraTOs.get(i).getRcFechaDesde(),
									prdResumenPescaSiembraTOs.get(i).getRcFechaSiembra(),
									prdResumenPescaSiembraTOs.get(i).getRcFechaPesca(),
									prdResumenPescaSiembraTOs.get(i).getRcFechaHasta(),
									prdResumenPescaSiembraTOs.get(i).getRcEdad(),
									prdResumenPescaSiembraTOs.get(i).getRcDiasMuertos(),
									prdResumenPescaSiembraTOs.get(i).getRcNumeroLarvas(),
									prdResumenPescaSiembraTOs.get(i).getRcDensidad(),
									prdResumenPescaSiembraTOs.get(i).getRcLaboratorio(),
									prdResumenPescaSiembraTOs.get(i).getRcNauplio(),
									prdResumenPescaSiembraTOs.get(i).getRcBalanceado(),
									prdResumenPescaSiembraTOs.get(i).getRcBiomasa(),
									prdResumenPescaSiembraTOs.get(i).getRcBiomasaReal(),
									prdResumenPescaSiembraTOs.get(i).getRcConversion(),
									prdResumenPescaSiembraTOs.get(i).getRcTallaPromedio(),
									prdResumenPescaSiembraTOs.get(i).getRcPesoIdeal(),
									prdResumenPescaSiembraTOs.get(i).getRcSobrevivencia()));
				}
			}
		}
	}

	private void llenarObjetoParaTabla() {
		if (prdResumenPescaSiembraTOs.size() > 0) {
			datos = new Object[listaResumenSiembra.size()][columnas.length];

			for (int i = 0; i < listaResumenSiembra.size(); i++) {
				datos[i][0] = listaResumenSiembra.get(i).getRcSector();
				datos[i][1] = listaResumenSiembra.get(i).getRcPiscina();
				datos[i][2] = listaResumenSiembra.get(i).getRcCorrida();
				datos[i][3] = listaResumenSiembra.get(i).getRcHectareaje();
				datos[i][4] = listaResumenSiembra.get(i).getRcFechaSiembra();
				if (siembraPesca == 1)
					datos[i][5] = listaResumenSiembra.get(i).getRcFechaPesca();
				datos[i][5 + siembraPesca] = listaResumenSiembra.get(i).getRcEdad();
				datos[i][6 + siembraPesca] = listaResumenSiembra.get(i).getRcDiasMuertos();
				datos[i][7 + siembraPesca] = listaResumenSiembra.get(i).getRcDensidad();
				datos[i][8 + siembraPesca] = listaResumenSiembra.get(i).getRcLaboratorio();
				datos[i][9 + siembraPesca] = listaResumenSiembra.get(i).getRcNauplio();
				datos[i][10 + siembraPesca] = siembraPesca == 0 ? listaResumenSiembra.get(i).getRcBiomasa()
						: listaResumenSiembra.get(i).getRcBiomasaReal();
				datos[i][11 + siembraPesca] = listaResumenSiembra.get(i).getRcConversion();
				datos[i][12 + siembraPesca] = listaResumenSiembra.get(i).getRcTallaPromedio();
				datos[i][13 + siembraPesca] = listaResumenSiembra.get(i).getRcSobrevivencia();

			}

			for (int h = 0; h < prdResumenPescaSiembraTOs.size(); h++) {
				for (int i = 0; i < datos.length; i++) {
					if (prdResumenPescaSiembraTOs.get(h).getRcSector()
							.concat(prdResumenPescaSiembraTOs.get(h).getRcPiscina())
							.concat(prdResumenPescaSiembraTOs.get(h).getRcCorrida()).equals(datos[i][0].toString()
									.concat(datos[i][1].toString()).concat(datos[i][2].toString()))) {
						for (int j = 0; j < columnasFaltantes.size(); j++) {
							if (prdResumenPescaSiembraTOs.get(h).getRcTipo().equals(columnasFaltantes.get(j))) {
								datos[i][j + 14 + siembraPesca] = prdResumenPescaSiembraTOs.get(h).getRcTotal();
								j = columnasFaltantes.size();
							}
						}
						i = datos.length;
					}
				}
			}

			Object[][] datosTmp = new Object[listaResumenSiembra.size()][columnasTmp.length];
			for (int i = 0; i < datos.length; i++) {
				datosTmp[i][0] = datos[i][0];
				datosTmp[i][1] = datos[i][1];
				datosTmp[i][2] = datos[i][2];
				datosTmp[i][3] = datos[i][3];
				datosTmp[i][4] = datos[i][4];
				if (siembraPesca == 1)
					datosTmp[i][5] = datos[i][5];
				datosTmp[i][5 + siembraPesca] = new BigDecimal(datos[i][5 + siembraPesca].toString()).intValue();
				datosTmp[i][6 + siembraPesca] = new BigDecimal(datos[i][6 + siembraPesca].toString()).intValue();
				datosTmp[i][7 + siembraPesca] = datos[i][7 + siembraPesca];
				datosTmp[i][8 + siembraPesca] = datos[i][8 + siembraPesca];
				datosTmp[i][9 + siembraPesca] = datos[i][9 + siembraPesca];
				datosTmp[i][10 + siembraPesca] = datos[i][10 + siembraPesca];
				datosTmp[i][11 + siembraPesca] = datos[i][11 + siembraPesca];
				datosTmp[i][12 + siembraPesca] = datos[i][12 + siembraPesca];
				datosTmp[i][13 + siembraPesca] = datos[i][13 + siembraPesca];
				// //////////AQUI EMPIEZA LO "DINAMICO"
				datosTmp[i][14 + siembraPesca] = datos[i][14 + siembraPesca];
				datosTmp[i][15 + siembraPesca] = datos[i][18 + siembraPesca];
				datosTmp[i][16 + siembraPesca] = new BigDecimal(datos[i][14 + siembraPesca].toString())
						.add(new BigDecimal(datos[i][18 + siembraPesca].toString()));
				datosTmp[i][17 + siembraPesca] = datos[i][15 + siembraPesca];
				datosTmp[i][18 + siembraPesca] = datos[i][16 + siembraPesca];
				datosTmp[i][19 + siembraPesca] = datos[i][17 + siembraPesca];
				datosTmp[i][20 + siembraPesca] = new BigDecimal(datos[i][15 + siembraPesca].toString())
						.add(new BigDecimal(datos[i][16 + siembraPesca].toString()))
						.add(new BigDecimal(datos[i][17 + siembraPesca].toString()));
				datosTmp[i][21 + siembraPesca] = datos[i][19 + siembraPesca];
			}

			datos = new Object[listaResumenSiembra.size()][columnasTmp.length];
			datos = datosTmp;

			columnas = new String[16 + siembraPesca + columnasFaltantes.size()];
			columnas = columnasTmp;

		}
	}
        */
}
