/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.delegate.RRHHDelegate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Developer0
 */
public class UtilTransacciones {


    /* RolListadoTrans*/
    public static BigDecimal calcularTotalIngresos(RhRol rhRol) {
        BigDecimal totalIngresos = rhRol.getRolSaldoAnterior()
                .add(rhRol.getRolIngresos())
                .add((rhRol.getRolBonos() == null) ? BigDecimal.ZERO : (rhRol.getRolBonos().add(rhRol.getRolHorasExtras())
                        .add(rhRol.getRolHorasExtras100()).add(rhRol.getRolHorasExtrasExtraordinarias100()).add(rhRol.getRolIngresoVacaciones())
                        .add(rhRol.getRolSaldoHorasExtras50()).add(rhRol.getRolSaldoHorasExtras100()).add(rhRol.getRolSaldoHorasExtrasExtraordinarias100())
                        .add(rhRol.getRolBonosnd().add(rhRol.getRolRecargoJornadaNocturna() == null ? BigDecimal.ZERO : rhRol.getRolRecargoJornadaNocturna()))))
                .add((rhRol.getRolBonoFijo() == null) ? BigDecimal.ZERO : (rhRol.getRolBonoFijo().add(rhRol.getRolBonoFijoNd())))
                .add((rhRol.getRolOtrosIngresos() == null) ? BigDecimal.ZERO : (rhRol.getRolOtrosIngresos().add(rhRol.getRolOtrosIngresosNd())))
                .add(BigDecimal.ZERO)//rolViáticos
                .add(rhRol.getRolFondoReserva());
        if (rhRol.isEmpCancelarXiiiSueldoMensualmente()) {
            totalIngresos = totalIngresos.add(rhRol.getRolXiii());
        }
        if (rhRol.isEmpCancelarXivSueldoMensualmente()) {
            totalIngresos = totalIngresos.add(rhRol.getRolXiv());
        }
        return totalIngresos;
    }

    public static BigDecimal calcularTotalIngresosImprimir(RhRol rhRol) {
        return (rhRol.getRolSaldoAnterior()
                .add(rhRol.getRolIngresos())
                .add((rhRol.getRolBonos() == null) ? BigDecimal.ZERO : (rhRol.getRolBonos().add(rhRol.getRolHorasExtras())
                        .add(rhRol.getRolHorasExtras100()).add(rhRol.getRolHorasExtrasExtraordinarias100()).add(rhRol.getRolIngresoVacaciones())
                        .add(rhRol.getRolSaldoHorasExtras50()).add(rhRol.getRolSaldoHorasExtras100()).add(rhRol.getRolSaldoHorasExtrasExtraordinarias100())
                        .add(rhRol.getRolBonosnd())))
                .add((rhRol.getRolBonoFijo() == null) ? BigDecimal.ZERO : (rhRol.getRolBonoFijo().add(rhRol.getRolBonoFijoNd())))
                .add((rhRol.getRolOtrosIngresos() == null) ? BigDecimal.ZERO : (rhRol.getRolOtrosIngresos().add(rhRol.getRolOtrosIngresosNd())))
                .add(BigDecimal.ZERO)//rolViáticos

                .add(rhRol.getRolFondoReserva()));
    }

    public static BigDecimal calcularTotalEgresos(RhRol rhRol, BigDecimal rolValorPrestamos, BigDecimal PrestamoHipotecario, BigDecimal PrestamoQuirografario) {
        if (rhRol.getRhEmpleado().getEmpSalarioNeto()) {
            return (rhRol.getRolAnticipos()
                    .add(rolValorPrestamos)
                    .add(PrestamoHipotecario)
                    .add(PrestamoQuirografario)
                    .add(rhRol.getRolIessExtension())
                    .add((rhRol.getRolRetencionFuente() == null) ? BigDecimal.ZERO : (rhRol.getRolRetencionFuente().add(BigDecimal.ZERO)))
                    .add((rhRol.getRolDescuentoVacaciones() == null) ? BigDecimal.ZERO : (rhRol.getRolDescuentoVacaciones().add(BigDecimal.ZERO)))
                    .add((rhRol.getRolDescuentoPermisoMedico() == null) ? BigDecimal.ZERO : (rhRol.getRolDescuentoPermisoMedico().add(BigDecimal.ZERO)))
                    .add((!rhRol.isEmpCancelarFondoReservaMensualmente()) ? rhRol.getRolFondoReserva() : BigDecimal.ZERO));
        } else {
            return (rhRol.getRolAnticipos()
                    .add(rhRol.getRolIess())
                    .add(rolValorPrestamos)
                    .add(PrestamoHipotecario)
                    .add(PrestamoQuirografario)
                    .add(rhRol.getRolIessExtension())
                    .add((rhRol.getRolRetencionFuente() == null) ? BigDecimal.ZERO : (rhRol.getRolRetencionFuente().add(BigDecimal.ZERO)))
                    .add((rhRol.getRolDescuentoVacaciones() == null) ? BigDecimal.ZERO : (rhRol.getRolDescuentoVacaciones().add(BigDecimal.ZERO)))
                    .add((rhRol.getRolDescuentoPermisoMedico() == null) ? BigDecimal.ZERO : (rhRol.getRolDescuentoPermisoMedico().add(BigDecimal.ZERO)))
                    .add((!rhRol.isEmpCancelarFondoReservaMensualmente()) ? rhRol.getRolFondoReserva() : BigDecimal.ZERO));
        }
    }

    public static BigDecimal calcularTotalLiquidacion(RhRol rhRol) {
        BigDecimal totalLiquidacion = rhRol.getRolLiqVacaciones()
                .add(rhRol.getRolLiqSalarioDigno())
                .add(rhRol.getRolLiqDesahucio())
                .add(rhRol.getRolLiqDesahucioIntempestivo())
                .add(rhRol.getRolLiqBonificacion())
                .add((rhRol.getRolLiqXiii() == null) ? BigDecimal.ZERO : rhRol.getRolLiqXiii())
                .add((rhRol.getRolLiqXiv() == null) ? BigDecimal.ZERO : rhRol.getRolLiqXiv());
        return totalLiquidacion;
    }

    public static BigDecimal calcularTotalPagar(BigDecimal totalIngresos, BigDecimal totalDescuentos, BigDecimal totalLiquidacion) {
        return (totalIngresos
                .subtract(totalDescuentos)
                .add(totalLiquidacion));
    }

    /*END RolListadoTrans*/
 /*ContableListadoRRHHTrans*/
    public static String obtenerMensajeConContablesNoAptos(List<String> listaConContables, String operacion) {
        String texto;
        if (operacion.equalsIgnoreCase("MAYORIZAR")) {
            texto = "Los siguientes contables no se pueden " + operacion + " por que  que no esta Pendiente. Que esta Reversado o Anulado:\n";
        } else if (!operacion.equalsIgnoreCase("RESTAURAR")) {
            texto = "Los siguientes contables no se pueden " + operacion + " por que están Anulados, Pendientes o Reversados:\n";
        } else {
            texto = "Los siguientes contables no se pueden " + operacion + " por que no estan Anulados o Reversados. Que están Pendientes";
        }
        for (String conContable : listaConContables) {
            texto += (conContable + "\n");
        }
        texto += "Transacción cancelada";
        return texto;
    }

    public static String obtenerMensajeEmpleadosNoPermitidos(List<String> listaConContables, String operacion) {
        String texto = "Los siguientes contables no se pueden " + operacion + " porque los empleados tienen un rol:\n";
        for (String conContable : listaConContables) {
            texto += (conContable + "\n");
        }
        texto += "Transacción cancelada";
        return texto;
    }

    public static List<RhAnticipo> cargarListaRhAnticipo(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        List<RhAnticipo> listAnticipo = RRHHDelegate.getInstance().getListRhAnticipo(conContablePK, usuario);
        for (RhAnticipo ant : listAnticipo) {
            ant.setAntAuxiliar(true);
        }
        return listAnticipo;
    }

    public static List<RhBono> cargarListaRhBono(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        List<RhBono> listBono = RRHHDelegate.getInstance().getListRhBono(conContablePK, usuario);
        for (RhBono bono : listBono) {
            if (bono.getPrdPiscina() == null) {
                bono.setPrdPiscina(new PrdPiscina(listaConContableTO.getEmpCodigo(), bono.getPrdSector().getPrdSectorPK().getSecCodigo(), ""));
            }
            bono.setBonoAuxiliar(true);
        }
        return listBono;
    }

    public static List<RhPrestamo> cargarListaRhPrestamo(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        return RRHHDelegate.getInstance().getListRhPrestamo(conContablePK, usuario);
    }

    public static List<RhRol> cargarListaRhRol(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        List<RhRol> listRol = RRHHDelegate.getInstance().getListRhRol(conContablePK, usuario);
        for (RhRol rol : listRol) {
            if (rol.getRolFormaPago() != null && rol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
                rol.getConCuentas().getConCuentasPK().setCtaCodigo("POR PAGAR");
            }
            rol.setRolAuxiliar(true);
        }
        return listRol;
    }

    public static List<RhUtilidades> cargarListaRhUtilidades(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        try {
            usuario.setEmpresa(listaConContableTO.getEmpCodigo());
            ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
            List<RhUtilidades> lista = RRHHDelegate.getInstance().getListRhUtilidades(conContablePK, usuario);
            return lista;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<RhXiiiSueldo> cargarListaRhXiiiSueldo(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        List<RhXiiiSueldo> listXiiiSueldo = RRHHDelegate.getInstance().getListRhXiiiSueldo(conContablePK, usuario);
        for (RhXiiiSueldo xiiiSueldo : listXiiiSueldo) {
            xiiiSueldo.setXiiiAuxiliar(true);
            xiiiSueldo.getRhEmpleado().setEmpDiasDescanso(xiiiSueldo.getXiiiDiasLaborados());
            xiiiSueldo.getRhEmpleado().setEmpBonoFijo(xiiiSueldo.getXiiiBaseImponible());
            xiiiSueldo.getRhEmpleado().setEmpBonoFijoNd(xiiiSueldo.getXiiiValor());
        }
        return listXiiiSueldo;
    }

    public static List<RhXivSueldo> cargarListaRhXivSueldo(ListaConContableTO listaConContableTO, SisInfoTO usuario) {
        usuario.setEmpresa(listaConContableTO.getEmpCodigo());
        ConContablePK conContablePK = new ConContablePK(listaConContableTO.getEmpCodigo(), listaConContableTO.getPerCodigo(), listaConContableTO.getTipCodigo(), listaConContableTO.getConNumero());
        List<RhXivSueldo> listXivSueldo = RRHHDelegate.getInstance().getListRhXivSueldo(conContablePK, usuario);
        for (RhXivSueldo xivSueldo : listXivSueldo) {
            xivSueldo.setXivAuxiliar(true);
            xivSueldo.getRhEmpleado().setEmpDiasDescanso(xivSueldo.getXivDiasLaboradosEmpleado());
            xivSueldo.getRhEmpleado().setEmpBonoFijo(xivSueldo.getXivBaseImponible());
            xivSueldo.getRhEmpleado().setEmpBonoFijoNd(xivSueldo.getXivValor());
        }
        return listXivSueldo;
    }

    /*END ContableListadoRRHHTrans*/
 /*ProvisionListadoTrans*/
    public static List<RhListaProvisionesTO> obtenerListaImprimirProvision(List<RhListaProvisionesTO> listaAdministrativo, List<RhListaProvisionesTO> listaCampo) {
        BigDecimal cero = new BigDecimal("0.00");
        List<RhListaProvisionesTO> listaRhListaProvisionesTOFinal = new ArrayList<RhListaProvisionesTO>();

        RhListaProvisionesTO rhListaProvisionesTOGeneralTotal = new RhListaProvisionesTO();
        rhListaProvisionesTOGeneralTotal.setProvNombres("TOTAL GENERAL");

        if (!listaAdministrativo.isEmpty()) {
            RhListaProvisionesTO rhListaProvisionesTOAdministrativoTitulo = new RhListaProvisionesTO();
            rhListaProvisionesTOAdministrativoTitulo.setProvNombres("PERSONAL ADMINISTRATIVO");

            RhListaProvisionesTO rhListaProvisionesTOAdministrativoSubtotal = new RhListaProvisionesTO();
            rhListaProvisionesTOAdministrativoSubtotal.setProvCategoria("ADMINISTRATIVO");
            rhListaProvisionesTOAdministrativoSubtotal.setProvNombres("TOTAL PERSONAL ADMINISTRATIVO");
            for (RhListaProvisionesTO item : listaAdministrativo) {
                if (item.getProvAporteIndividual() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvAporteIndividual(rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual() == null ? cero.add(item.getProvAporteIndividual()) : rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual().add(item.getProvAporteIndividual()));
                }
                if (item.getProvAportePatronal() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvAportePatronal(rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal() == null ? cero.add(item.getProvAportePatronal()) : rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal().add(item.getProvAportePatronal()));
                }
                if (item.getProvIece() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvIece(rhListaProvisionesTOAdministrativoSubtotal.getProvIece() == null ? cero.add(item.getProvIece()) : rhListaProvisionesTOAdministrativoSubtotal.getProvIece().add(item.getProvIece()));
                }
                if (item.getProvSecap() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvSecap(rhListaProvisionesTOAdministrativoSubtotal.getProvSecap() == null ? cero.add(item.getProvSecap()) : rhListaProvisionesTOAdministrativoSubtotal.getProvSecap().add(item.getProvSecap()));
                }
                if (item.getProvXiii() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvXiii(rhListaProvisionesTOAdministrativoSubtotal.getProvXiii() == null ? cero.add(item.getProvXiii()) : rhListaProvisionesTOAdministrativoSubtotal.getProvXiii().add(item.getProvXiii()));
                }
                if (item.getProvXiv() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvXiv(rhListaProvisionesTOAdministrativoSubtotal.getProvXiv() == null ? cero.add(item.getProvXiv()) : rhListaProvisionesTOAdministrativoSubtotal.getProvXiv().add(item.getProvXiv()));
                }
                if (item.getProvFondoReserva() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvFondoReserva(rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva() == null ? cero.add(item.getProvFondoReserva()) : rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva().add(item.getProvFondoReserva()));
                }
                if (item.getProvVacaciones() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvVacaciones(rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones() == null ? cero.add(item.getProvVacaciones()) : rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones().add(item.getProvVacaciones()));
                }
                if (item.getProvDesahucio() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvDesahucio(rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio() == null ? cero.add(item.getProvDesahucio()) : rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio().add(item.getProvDesahucio()));
                }
            }

            rhListaProvisionesTOGeneralTotal.setProvAporteIndividual(rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual());
            rhListaProvisionesTOGeneralTotal.setProvAportePatronal(rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal());
            rhListaProvisionesTOGeneralTotal.setProvIece(rhListaProvisionesTOAdministrativoSubtotal.getProvIece());
            rhListaProvisionesTOGeneralTotal.setProvSecap(rhListaProvisionesTOAdministrativoSubtotal.getProvSecap());
            rhListaProvisionesTOGeneralTotal.setProvXiii(rhListaProvisionesTOAdministrativoSubtotal.getProvXiii());
            rhListaProvisionesTOGeneralTotal.setProvXiv(rhListaProvisionesTOAdministrativoSubtotal.getProvXiv());
            rhListaProvisionesTOGeneralTotal.setProvFondoReserva(rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva());
            rhListaProvisionesTOGeneralTotal.setProvVacaciones(rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones());
            rhListaProvisionesTOGeneralTotal.setProvDesahucio(rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio());

            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOAdministrativoTitulo);
            for (RhListaProvisionesTO item : listaAdministrativo) {
                listaRhListaProvisionesTOFinal.add(item);
            }
            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOAdministrativoSubtotal);
            listaRhListaProvisionesTOFinal.add(new RhListaProvisionesTO());
        }

        if (!listaCampo.isEmpty()) {
            RhListaProvisionesTO rhListaProvisionesTOCampoTitulo = new RhListaProvisionesTO();
            rhListaProvisionesTOCampoTitulo.setProvNombres("PERSONAL CAMPO");

            RhListaProvisionesTO rhListaProvisionesTOCampoSubtotal = new RhListaProvisionesTO();
            rhListaProvisionesTOCampoSubtotal.setProvCategoria("CAMPO");
            rhListaProvisionesTOCampoSubtotal.setProvNombres("TOTAL PERSONAL CAMPO");
            for (RhListaProvisionesTO item : listaCampo) {
                if (item.getProvAporteIndividual() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvAporteIndividual(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual() == null ? cero.add(item.getProvAporteIndividual()) : rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual().add(item.getProvAporteIndividual()));
                }
                if (item.getProvAportePatronal() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvAportePatronal(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal() == null ? cero.add(item.getProvAportePatronal()) : rhListaProvisionesTOCampoSubtotal.getProvAportePatronal().add(item.getProvAportePatronal()));
                }
                if (item.getProvIece() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvIece(rhListaProvisionesTOCampoSubtotal.getProvIece() == null ? cero.add(item.getProvIece()) : rhListaProvisionesTOCampoSubtotal.getProvIece().add(item.getProvIece()));
                }
                if (item.getProvSecap() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvSecap(rhListaProvisionesTOCampoSubtotal.getProvSecap() == null ? cero.add(item.getProvSecap()) : rhListaProvisionesTOCampoSubtotal.getProvSecap().add(item.getProvSecap()));
                }
                if (item.getProvXiii() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvXiii(rhListaProvisionesTOCampoSubtotal.getProvXiii() == null ? cero.add(item.getProvXiii()) : rhListaProvisionesTOCampoSubtotal.getProvXiii().add(item.getProvXiii()));
                }
                if (item.getProvXiv() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvXiv(rhListaProvisionesTOCampoSubtotal.getProvXiv() == null ? cero.add(item.getProvXiv()) : rhListaProvisionesTOCampoSubtotal.getProvXiv().add(item.getProvXiv()));
                }
                if (item.getProvFondoReserva() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvFondoReserva(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva() == null ? cero.add(item.getProvFondoReserva()) : rhListaProvisionesTOCampoSubtotal.getProvFondoReserva().add(item.getProvFondoReserva()));
                }
                if (item.getProvVacaciones() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvVacaciones(rhListaProvisionesTOCampoSubtotal.getProvVacaciones() == null ? cero.add(item.getProvVacaciones()) : rhListaProvisionesTOCampoSubtotal.getProvVacaciones().add(item.getProvVacaciones()));
                }
                if (item.getProvDesahucio() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvDesahucio(rhListaProvisionesTOCampoSubtotal.getProvDesahucio() == null ? cero.add(item.getProvDesahucio()) : rhListaProvisionesTOCampoSubtotal.getProvDesahucio().add(item.getProvDesahucio()));
                }
            }

            if (rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual() != null) {
                rhListaProvisionesTOGeneralTotal.setProvAporteIndividual(rhListaProvisionesTOGeneralTotal.getProvAporteIndividual() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual()) : rhListaProvisionesTOGeneralTotal.getProvAporteIndividual().add(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvAportePatronal() != null) {
                rhListaProvisionesTOGeneralTotal.setProvAportePatronal(rhListaProvisionesTOGeneralTotal.getProvAportePatronal() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal()) : rhListaProvisionesTOGeneralTotal.getProvAportePatronal().add(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvIece() != null) {
                rhListaProvisionesTOGeneralTotal.setProvIece(rhListaProvisionesTOGeneralTotal.getProvIece() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvIece()) : rhListaProvisionesTOGeneralTotal.getProvIece().add(rhListaProvisionesTOCampoSubtotal.getProvIece()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvSecap() != null) {
                rhListaProvisionesTOGeneralTotal.setProvSecap(rhListaProvisionesTOGeneralTotal.getProvSecap() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvSecap()) : rhListaProvisionesTOGeneralTotal.getProvSecap().add(rhListaProvisionesTOCampoSubtotal.getProvSecap()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvXiii() != null) {
                rhListaProvisionesTOGeneralTotal.setProvXiii(rhListaProvisionesTOGeneralTotal.getProvXiii() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvXiii()) : rhListaProvisionesTOGeneralTotal.getProvXiii().add(rhListaProvisionesTOCampoSubtotal.getProvXiii()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvXiv() != null) {
                rhListaProvisionesTOGeneralTotal.setProvXiv(rhListaProvisionesTOGeneralTotal.getProvXiv() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvXiv()) : rhListaProvisionesTOGeneralTotal.getProvXiv().add(rhListaProvisionesTOCampoSubtotal.getProvXiv()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvFondoReserva() != null) {
                rhListaProvisionesTOGeneralTotal.setProvFondoReserva(rhListaProvisionesTOGeneralTotal.getProvFondoReserva() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva()) : rhListaProvisionesTOGeneralTotal.getProvFondoReserva().add(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvVacaciones() != null) {
                rhListaProvisionesTOGeneralTotal.setProvVacaciones(rhListaProvisionesTOGeneralTotal.getProvVacaciones() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvVacaciones()) : rhListaProvisionesTOGeneralTotal.getProvVacaciones().add(rhListaProvisionesTOCampoSubtotal.getProvVacaciones()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvDesahucio() != null) {
                rhListaProvisionesTOGeneralTotal.setProvDesahucio(rhListaProvisionesTOGeneralTotal.getProvDesahucio() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvDesahucio()) : rhListaProvisionesTOGeneralTotal.getProvDesahucio().add(rhListaProvisionesTOCampoSubtotal.getProvDesahucio()));
            }

            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOCampoTitulo);
            for (RhListaProvisionesTO item : listaCampo) {
                listaRhListaProvisionesTOFinal.add(item);
            }
            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOCampoSubtotal);
            listaRhListaProvisionesTOFinal.add(new RhListaProvisionesTO());
        }
        listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOGeneralTotal);
        return listaRhListaProvisionesTOFinal;
    }

    /*END ProvisionListadoTrans*/

 /*ChequesNoImpresosTrans*/
    public static ArrayList<String> ponerTextoNumeroLetra(String texto) {//array(0)=valor1 , //array(1)=valor2
        ArrayList<String> valor1Valor2 = new ArrayList<>();
        String numeroLetra = texto;
        String asteriscos = "";
        for (int i = numeroLetra.trim().length(); (asteriscos.length() + texto.length()) < 68; i++) {
            asteriscos += " * ";
        }
        int index = (texto.length() + asteriscos.length()) - 68;
        asteriscos = asteriscos.substring(0, asteriscos.length() - index);
        numeroLetra += asteriscos;

        String aux1 = "";
        String aux2 = "";
        // String numeroLetra = texto;
        if (numeroLetra.trim().length() > 68) {
            aux1 = numeroLetra.substring(0, 69);
            if (aux1.substring(69).trim().isEmpty()) {
                aux2 = numeroLetra.substring(69);
            } else {
                aux2 = numeroLetra.substring(aux1.lastIndexOf(' ') + 1);
                aux1 = aux1.substring(0, aux1.lastIndexOf(' '));
            }
        } else {
            aux1 = numeroLetra;
            if (aux1.length() < 68) {
                String rellenarConLineas = "-";
                for (int i = aux1.length(); i < 55; i++) {
                    rellenarConLineas += "-";
                }
                aux1 += rellenarConLineas;
            }
        }
        if (aux2.length() < 69) {
            String rellenarConLineas = "-";
            for (int i = aux2.length(); i < 69; i++) {
                rellenarConLineas += "-";
            }
            aux2 += rellenarConLineas;
        }
        valor1Valor2.add(aux1);
        valor1Valor2.add(aux2);

        return valor1Valor2;
    }

    /*END ChequesNoImpresosTrans*/
    public UtilTransacciones() {
    }
}
