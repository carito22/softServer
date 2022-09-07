package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPagoBeneficiosSociales;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107PK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhSalarioDigno;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadesPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadesPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoPeriodo;

public class ConversionesRRHH {

    public static RhCategoria convertirRhCategoriaTO_RhCategoria(RhCategoriaTO rhCategoriaTO) throws Exception {
        RhCategoria rhCategoria = new RhCategoria();
        rhCategoria.setCtaDescuentoVacaciones(rhCategoriaTO.getCtaDescuentoVacaciones());
        rhCategoria.setRhCategoriaPK(new RhCategoriaPK(rhCategoriaTO.getEmpCodigo(), rhCategoriaTO.getCatNombre()));
        rhCategoria.setCtaAnticipos(rhCategoriaTO.getCtaAnticipos());
        rhCategoria.setCtaPrestamos(rhCategoriaTO.getCtaPrestamos());
        rhCategoria.setCtaPorPagarBonos(rhCategoriaTO.getCtaPorPagarBonos());
        rhCategoria.setCtaPorPagarSueldo(rhCategoriaTO.getCtaPorPagarSueldo());
        rhCategoria.setCtaPorPagarImpuestoRenta(rhCategoriaTO.getCtaPorPagarImpuestoRenta());
        rhCategoria.setCtaPorPagarUtilidades(rhCategoriaTO.getCtaPorPagarUtilidades());
        rhCategoria.setCtaGastoHorasExtras(rhCategoriaTO.getCtaGastoHorasExtras());
        rhCategoria.setCtaGastoHorasExtras100(rhCategoriaTO.getCtaGastoHorasExtras100());
        rhCategoria.setCtaGastoHorasExtrasExtraordinarias100(rhCategoriaTO.getCtaGastoHorasExtrasExtraordinarias100());
        rhCategoria.setCtaSaldoHorasExtras50(rhCategoriaTO.getCtaSaldoHorasExtras50());
        rhCategoria.setCtaSaldoHorasExtras100(rhCategoriaTO.getCtaSaldoHorasExtras100());
        rhCategoria.setCtaSaldoHorasExtrasExtraordinarias100(rhCategoriaTO.getCtaSaldoHorasExtrasExtraordinarias100());
        rhCategoria.setCtaGastoBonos(rhCategoriaTO.getCtaGastoBonos());
        rhCategoria.setCtaGastoBonosNd(rhCategoriaTO.getCtaGastoBonosNd());
        rhCategoria.setCtaGastoBonoFijo(rhCategoriaTO.getCtaGastoBonoFijo());
        rhCategoria.setCtaGastoBonoFijoNd(rhCategoriaTO.getCtaGastoBonoFijoNd());
        rhCategoria.setCtaGastoOtrosIngresos(rhCategoriaTO.getCtaGastoOtrosIngresos());
        rhCategoria.setCtaGastoOtrosIngresosNd(rhCategoriaTO.getCtaGastoOtrosIngresosNd());
        rhCategoria.setCtaGastoSueldos(rhCategoriaTO.getCtaGastoSueldos());
        rhCategoria.setCtaAportepersonal(rhCategoriaTO.getCtaAportepersonal());
        rhCategoria.setCtaAportepatronal(rhCategoriaTO.getCtaAportepatronal());
        rhCategoria.setCtaIece(rhCategoriaTO.getCtaIece());
        rhCategoria.setCtaSecap(rhCategoriaTO.getCtaSecap());
        rhCategoria.setCtaXiii(rhCategoriaTO.getCtaXiii());
        rhCategoria.setCtaXiv(rhCategoriaTO.getCtaXiv());
        rhCategoria.setCtaFondoreserva(rhCategoriaTO.getCtaFondoreserva());
        rhCategoria.setCtaVacaciones(rhCategoriaTO.getCtaVacaciones());
        rhCategoria.setCtaDesahucio(rhCategoriaTO.getCtaDesahucio());
        rhCategoria.setCtaPrestamoQuirografario(rhCategoriaTO.getCtaPrestamoQuirografario());
        rhCategoria.setCtaPrestamoHipotecario(rhCategoriaTO.getCtaPrestamoHipotecario());
        rhCategoria.setCtaPermisoMedico(rhCategoriaTO.getCtaPermisoMedico());
        rhCategoria.setCtaGastoAporteindividual(rhCategoriaTO.getCtaGastoAporteindividual());
        rhCategoria.setCtaAporteExtension(rhCategoriaTO.getCtaAporteExtension());
        rhCategoria.setCtaGastoAportepatronal(rhCategoriaTO.getCtaGastoAportepatronal());
        rhCategoria.setCtaGastoIece(rhCategoriaTO.getCtaGastoIece());
        rhCategoria.setCtaGastoSecap(rhCategoriaTO.getCtaGastoSecap());
        rhCategoria.setCtaGastoXiii(rhCategoriaTO.getCtaGastoXiii());
        rhCategoria.setCtaGastoXiv(rhCategoriaTO.getCtaGastoXiv());
        rhCategoria.setCtaGastoFondoreserva(rhCategoriaTO.getCtaGastoFondoreserva());
        rhCategoria.setCtaGastoVacaciones(rhCategoriaTO.getCtaGastoVacaciones());
        rhCategoria.setCtaGastoSalarioDigno(rhCategoriaTO.getCtaGastoSalarioDigno());
        rhCategoria.setCtaGastoLiquidacionBono(rhCategoriaTO.getCtaGastoBonoLiquidacion());
        rhCategoria.setCtaGastoDesahucio(rhCategoriaTO.getCtaGastoDesahucio());
        rhCategoria.setCtaGastoDesahucioIntempestivo(rhCategoriaTO.getCtaGastoDesahucioIntempestivo());
        rhCategoria.setCtaRecargoJornadaNocturna(rhCategoriaTO.getCtaRecargoJornadaNocturna());
        rhCategoria.setTipEmpresa(rhCategoriaTO.getTipCodigo() == null ? null : rhCategoriaTO.getEmpCodigo());
        rhCategoria.setTipCodigo(rhCategoriaTO.getTipCodigo());
        rhCategoria.setUsrEmpresa(rhCategoriaTO.getEmpCodigo());
        rhCategoria.setUsrCodigo(rhCategoriaTO.getUsrInsertaCategoria());
        rhCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(rhCategoriaTO.getUsrFechaInsertaCategoria()));
        return rhCategoria;
    }

    public static RhEmpleado convertirRhEmpleadoTO_RhEmpleado(RhEmpleadoTO rhEmpleadoTO) throws Exception {
        RhEmpleado rhEmpleado = new RhEmpleado();
        rhEmpleado.setRhEmpleadoPK(new RhEmpleadoPK(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId()));
        rhEmpleado.setRhCategoria(new RhCategoria(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getCatNombre()));
        rhEmpleado.setEmpResidenciaTipo(rhEmpleadoTO.getEmpResidenciaTipo());
        rhEmpleado.setEmpResidenciaPais(rhEmpleadoTO.getEmpResidenciaPais());
        rhEmpleado.setEmpConvenio(rhEmpleadoTO.getEmpConvenio());
        rhEmpleado.setEmpTipoId(rhEmpleadoTO.getEmpTipoId());
        rhEmpleado.setEmpApellidos(rhEmpleadoTO.getEmpApellidos());
        rhEmpleado.setEmpNombres(rhEmpleadoTO.getEmpNombres());
        rhEmpleado.setEmpGenero(rhEmpleadoTO.getEmpGenero());
        rhEmpleado.setEmpLugarNacimiento(rhEmpleadoTO.getEmpLugarNacimiento());
        rhEmpleado.setEmpFechaNacimiento(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaNacimiento(), "yyyy-MM-dd"));
        rhEmpleado.setEmpEstadoCivil(rhEmpleadoTO.getEmpEstadoCivil());
        rhEmpleado.setEmpCargasFamiliares(rhEmpleadoTO.getEmpCargasFamiliares());
        rhEmpleado.setEmpProvincia(rhEmpleadoTO.getEmpProvincia());
        rhEmpleado.setEmpCanton(rhEmpleadoTO.getEmpCanton());
        rhEmpleado.setEmpDomicilio(rhEmpleadoTO.getEmpDomicilio());
        rhEmpleado.setEmpNumero(rhEmpleadoTO.getEmpNumero());
        rhEmpleado.setEmpTelefono(rhEmpleadoTO.getEmpTelefono());
        rhEmpleado.setEmpCorreoElectronico(rhEmpleadoTO.getEmpEmail());
        rhEmpleado.setPrdSector(new PrdSector(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getSecCodigo()));
        rhEmpleado.setEmpFechaPrimerIngreso(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaPrimerIngreso(), "yyyy-MM-dd"));
        rhEmpleado.setEmpFechaPrimeraSalida(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
        rhEmpleado.setEmpMotivoSalida(rhEmpleadoTO.getEmpMotivoSalida());
        rhEmpleado.setEmpFechaUltimoIngreso(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaUltimoIngreso(), "yyyy-MM-dd"));
        rhEmpleado.setEmpFechaUltimaSalida(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaUltimaSalida(), "yyyy-MM-dd"));
        rhEmpleado.setEmpFechaAfiliacionIess(UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaAfiliacionIess(), "yyyy-MM-dd"));
        rhEmpleado.setEmpCodigoAfiliacionIess(rhEmpleadoTO.getEmpCodigoAfiliacionIess());
        rhEmpleado.setEmpCodigoCargo(rhEmpleadoTO.getEmpCodigoCargo());
        rhEmpleado.setEmpCargo(rhEmpleadoTO.getEmpCargo());
        rhEmpleado.setEmpSueldoIess(rhEmpleadoTO.getEmpSueldoIess());
        rhEmpleado.setEmpFormaPago(rhEmpleadoTO.getEmpFormaPago());
        rhEmpleado.setEmpDiasTrabajados(rhEmpleadoTO.getEmpDiasTrabajados());
        rhEmpleado.setEmpDiasDescanso(rhEmpleadoTO.getEmpDiasDescanso());
        rhEmpleado.setEmpBonoFijo(rhEmpleadoTO.getEmpBonoFijo());
        rhEmpleado.setEmpBonoFijoNd(rhEmpleadoTO.getEmpBonoFijoNd());
        rhEmpleado.setEmpOtrosIngresos(rhEmpleadoTO.getEmpOtrosIngresos());
        rhEmpleado.setEmpOtrosIngresosNd(rhEmpleadoTO.getEmpOtrosIngresosNd());
        rhEmpleado.setEmpCancelarXiiiSueldoMensualmente(rhEmpleadoTO.getEmpCancelarXiiiSueldoMensualmente());
        rhEmpleado.setEmpCancelarXivSueldoMensualmente(rhEmpleadoTO.getEmpCancelarXivSueldoMensualmente());
        rhEmpleado.setEmpCancelarFondoReservaMensualmente(rhEmpleadoTO.getEmpCancelarFondoReservaMensualmente());
        rhEmpleado.setEmpRebajaDiscapacidad(rhEmpleadoTO.getEmpRebajaDiscapacidad());
        rhEmpleado.setEmpRebajaTerceraEdad(rhEmpleadoTO.getEmpRebajaTerceraEdad());
        rhEmpleado.setEmpOtrasDeducciones(rhEmpleadoTO.getEmpOtrasDeducciones());
        rhEmpleado.setEmpOtrasRebajas(rhEmpleadoTO.getEmpOtrasRebajas());
        rhEmpleado.setEmpRetencion(rhEmpleadoTO.getEmpRetencion());
        rhEmpleado.setEmpEducacion(rhEmpleadoTO.getEmpEducacion());
        rhEmpleado.setEmpAlimentacion(rhEmpleadoTO.getEmpAlimentacion());
        rhEmpleado.setEmpSalud(rhEmpleadoTO.getEmpSalud());
        rhEmpleado.setEmpVivienda(rhEmpleadoTO.getEmpVivienda());
        rhEmpleado.setEmpVestuario(rhEmpleadoTO.getEmpVestuario());
        rhEmpleado.setEmpSueldoOtraCompania(rhEmpleadoTO.getEmpSueldoOtraCompania());
        rhEmpleado.setEmpUtilidades(rhEmpleadoTO.getEmpUtilidades());
        rhEmpleado.setEmpExtensionCoberturaIess(rhEmpleadoTO.getEmpExtensionCoberturaIess());
        rhEmpleado.setEmpObservaciones(rhEmpleadoTO.getEmpObservaciones());
        rhEmpleado.setEmpDiscapacidadTipo(rhEmpleadoTO.getEmpDiscapacidadTipo());
        rhEmpleado.setEmpDiscapacidadPorcentaje(rhEmpleadoTO.getEmpDiscapacidadPorcentaje());
        rhEmpleado.setEmpDiscapacidadIdTipo(rhEmpleadoTO.getEmpDiscapacidadIdTipo());
        rhEmpleado.setEmpDiscapacidadIdNumero(rhEmpleadoTO.getEmpDiscapacidadIdNumero());
        rhEmpleado.setEmpBanco(rhEmpleadoTO.getEmpBanco());
        rhEmpleado.setEmpCuentaTipo(rhEmpleadoTO.getEmpCuentaTipo());
        rhEmpleado.setEmpCuentaNumero(rhEmpleadoTO.getEmpCuentaNumero());
        rhEmpleado.setEmpInactivo(rhEmpleadoTO.getEmpInactivo());

        rhEmpleado.setEmpSaldoAnterior(rhEmpleadoTO.getEmpSaldoAnterior());
        rhEmpleado.setEmpSaldoAnticipos(rhEmpleadoTO.getEmpSaldoAnticipos());
        rhEmpleado.setEmpSaldoBonos(rhEmpleadoTO.getEmpSaldoBonos());
        rhEmpleado.setEmpSaldoBonosNd(rhEmpleadoTO.getEmpSaldoBonosNd());
        // emp_saldo_bonos_nd
        rhEmpleado.setEmpSaldoPrestamos(rhEmpleadoTO.getEmpSaldoPrestamos());
        rhEmpleado.setEmpSaldoCuotas(rhEmpleadoTO.getEmpSaldoCuotas());
        rhEmpleado.setEmpFechaUltimoSueldo(rhEmpleadoTO.getEmpFechaUltimoSueldo() == null ? null
                : UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaUltimoSueldo(), "yyyy-MM-dd"));

        rhEmpleado.setUsrEmpresa(rhEmpleadoTO.getEmpCodigo());
        rhEmpleado.setUsrCodigo(rhEmpleadoTO.getUsrInsertaEmpleado());
        rhEmpleado.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaString_Date(rhEmpleadoTO.getUsrFechaInsertaEmpleado())));
        rhEmpleado.setEmpSalarioNeto(rhEmpleadoTO.getEmpSalarioNeto());
        rhEmpleado.setEmpAnticipoQuincena(rhEmpleadoTO.getEmpAnticipoQuincena());
        rhEmpleado.setEmpPrestamoHipotecario(rhEmpleadoTO.getEmpPrestamoHipotecario());
        rhEmpleado.setEmpPrestamoQuirografario(rhEmpleadoTO.getEmpPrestamoQuirografario());
        rhEmpleado.setEmpSaldoHorasExtras50(rhEmpleadoTO.getEmpSaldoHorasExtras50());
        rhEmpleado.setEmpSaldoHorasExtras100(rhEmpleadoTO.getEmpSaldoHorasExtras100());
        rhEmpleado.setEmpSaldoHorasExtrasExtraordinarias100(rhEmpleadoTO.getEmpSaldoHorasExtrasExtraordinarias100());
        rhEmpleado.setEmpRelacionTrabajo(new RhRelacionTrabajo(rhEmpleadoTO.getEmpRelacionTrabajo()));
        rhEmpleado.setEmpDescripcion(rhEmpleadoTO.getEmpDescripcion());

        rhEmpleado.setEmpMaternidad(rhEmpleadoTO.isEmpMaternidad());
        rhEmpleado.setEmpLactancia(rhEmpleadoTO.isEmpLactancia());
        rhEmpleado.setEmpFechaDesdeLactancia(rhEmpleadoTO.getEmpFechaDesdeLactancia() != null
                ? UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaDesdeLactancia(), "yyyy-MM-dd") : null);
        rhEmpleado.setEmpFechaHastaLactancia(rhEmpleadoTO.getEmpFechaHastaLactancia() != null
                ? UtilsValidacion.fecha(rhEmpleadoTO.getEmpFechaHastaLactancia(), "yyyy-MM-dd") : null);

        return rhEmpleado;
    }

    public static RhEmpleadoTO convertirRhEmpleado_RhEmpleadoTO(RhEmpleado rhEmpleado) {
        RhEmpleadoTO rhEmpleadoTO = new RhEmpleadoTO();
        rhEmpleadoTO.setEmpCodigo(rhEmpleado.getRhEmpleadoPK().getEmpEmpresa());
        rhEmpleadoTO.setEmpResidenciaTipo(rhEmpleado.getEmpResidenciaTipo());
        rhEmpleadoTO.setEmpResidenciaPais(rhEmpleado.getEmpResidenciaPais());
        rhEmpleadoTO.setEmpConvenio(rhEmpleado.getEmpConvenio());
        rhEmpleadoTO.setEmpTipoId(rhEmpleado.getEmpTipoId());
        rhEmpleadoTO.setEmpId(rhEmpleado.getRhEmpleadoPK().getEmpId());
        rhEmpleadoTO.setEmpApellidos(rhEmpleado.getEmpApellidos());
        rhEmpleadoTO.setEmpNombres(rhEmpleado.getEmpNombres());
        rhEmpleadoTO.setEmpGenero(rhEmpleado.getEmpGenero());
        rhEmpleadoTO.setEmpLugarNacimiento(rhEmpleado.getEmpLugarNacimiento());
        rhEmpleadoTO.setEmpFechaNacimiento(UtilsValidacion.fecha(rhEmpleado.getEmpFechaNacimiento(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpEstadoCivil(rhEmpleado.getEmpEstadoCivil());
        rhEmpleadoTO.setEmpCargasFamiliares(rhEmpleado.getEmpCargasFamiliares());
        rhEmpleadoTO.setEmpProvincia(rhEmpleado.getEmpProvincia());
        rhEmpleadoTO.setEmpCanton(rhEmpleado.getEmpCanton());
        rhEmpleadoTO.setEmpDomicilio(rhEmpleado.getEmpDomicilio());
        rhEmpleadoTO.setEmpNumero(rhEmpleado.getEmpNumero());
        rhEmpleadoTO.setEmpTelefono(rhEmpleado.getEmpTelefono());
        rhEmpleadoTO.setSecCodigo(rhEmpleado.getPrdSector().getPrdSectorPK().getSecCodigo());
        rhEmpleadoTO.setEmpEmail(rhEmpleado.getEmpCorreoElectronico());
        rhEmpleadoTO.setEmpFechaPrimerIngreso(UtilsValidacion.fecha(rhEmpleado.getEmpFechaPrimerIngreso(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpFechaPrimeraSalida(UtilsValidacion.fecha(rhEmpleado.getEmpFechaPrimeraSalida(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpFechaUltimoIngreso(UtilsValidacion.fecha(rhEmpleado.getEmpFechaUltimoIngreso(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpFechaUltimaSalida(UtilsValidacion.fecha(rhEmpleado.getEmpFechaUltimaSalida(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpMotivoSalida(rhEmpleado.getEmpMotivoSalida());
        rhEmpleadoTO.setEmpFechaAfiliacionIess(UtilsValidacion.fecha(rhEmpleado.getEmpFechaAfiliacionIess(), "yyyy-MM-dd"));
        rhEmpleadoTO.setEmpCodigoAfiliacionIess(rhEmpleado.getEmpCodigoAfiliacionIess());
        rhEmpleadoTO.setEmpCodigoCargo(rhEmpleado.getEmpCodigoCargo());
        rhEmpleadoTO.setEmpCargo(rhEmpleado.getEmpCargo());
        rhEmpleadoTO.setEmpSueldoIess(rhEmpleado.getEmpSueldoIess());
        rhEmpleadoTO.setEmpFormaPago(rhEmpleado.getEmpFormaPago());
        rhEmpleadoTO.setEmpDiasTrabajados(rhEmpleado.getEmpDiasTrabajados());
        rhEmpleadoTO.setEmpDiasDescanso(rhEmpleado.getEmpDiasDescanso());
        rhEmpleadoTO.setEmpBonoFijo(rhEmpleado.getEmpBonoFijo());
        rhEmpleadoTO.setEmpBonoFijoNd(rhEmpleado.getEmpBonoFijoNd());
        rhEmpleadoTO.setEmpOtrosIngresos(rhEmpleado.getEmpOtrosIngresos());
        rhEmpleadoTO.setEmpOtrosIngresosNd(rhEmpleado.getEmpOtrosIngresosNd());
        rhEmpleadoTO.setEmpCancelarXiiiSueldoMensualmente(rhEmpleado.getEmpCancelarXiiiSueldoMensualmente());
        rhEmpleadoTO.setEmpCancelarXivSueldoMensualmente(rhEmpleado.getEmpCancelarXivSueldoMensualmente());
        rhEmpleadoTO.setEmpCancelarFondoReservaMensualmente(rhEmpleado.getEmpCancelarFondoReservaMensualmente());
        rhEmpleadoTO.setEmpRebajaDiscapacidad(rhEmpleado.getEmpRebajaDiscapacidad());
        rhEmpleadoTO.setEmpRebajaTerceraEdad(rhEmpleado.getEmpRebajaTerceraEdad());
        rhEmpleadoTO.setEmpOtrasDeducciones(rhEmpleado.getEmpOtrasDeducciones());
        rhEmpleadoTO.setEmpOtrasRebajas(rhEmpleado.getEmpOtrasRebajas());
        rhEmpleadoTO.setEmpRetencion(rhEmpleado.getEmpRetencion());
        rhEmpleadoTO.setEmpEducacion(rhEmpleado.getEmpEducacion());
        rhEmpleadoTO.setEmpAlimentacion(rhEmpleado.getEmpAlimentacion());
        rhEmpleadoTO.setEmpSalud(rhEmpleado.getEmpSalud());
        rhEmpleadoTO.setEmpVivienda(rhEmpleado.getEmpVivienda());
        rhEmpleadoTO.setEmpVestuario(rhEmpleado.getEmpVestuario());
        rhEmpleadoTO.setEmpSueldoOtraCompania(rhEmpleado.getEmpSueldoOtraCompania());
        rhEmpleadoTO.setEmpUtilidades(rhEmpleado.getEmpUtilidades());
        rhEmpleadoTO.setEmpExtensionCoberturaIess(rhEmpleado.getEmpExtensionCoberturaIess());
        rhEmpleadoTO.setEmpObservaciones(rhEmpleado.getEmpObservaciones());
        rhEmpleadoTO.setEmpDiscapacidadTipo(rhEmpleado.getEmpDiscapacidadTipo());
        rhEmpleadoTO.setEmpDiscapacidadPorcentaje(rhEmpleado.getEmpDiscapacidadPorcentaje());
        rhEmpleadoTO.setEmpDiscapacidadIdTipo(rhEmpleado.getEmpDiscapacidadIdTipo());
        rhEmpleadoTO.setEmpDiscapacidadIdNumero(rhEmpleado.getEmpDiscapacidadIdNumero());
        rhEmpleadoTO.setEmpBanco(rhEmpleadoTO.getEmpBanco());
        rhEmpleadoTO.setEmpCuentaTipo(rhEmpleadoTO.getEmpCuentaTipo());
        rhEmpleadoTO.setEmpCuentaNumero(rhEmpleadoTO.getEmpCuentaNumero());
        rhEmpleadoTO.setEmpInactivo(rhEmpleado.getEmpInactivo());
        rhEmpleadoTO.setCatNombre(rhEmpleado.getRhCategoria().getRhCategoriaPK().getCatNombre());
        rhEmpleadoTO.setUsrInsertaEmpleado(rhEmpleado.getUsrCodigo());
        rhEmpleadoTO.setUsrFechaInsertaEmpleado(UtilsValidacion.fecha(rhEmpleado.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
        rhEmpleadoTO.setEmpSalarioNeto(rhEmpleado.getEmpSalarioNeto());
        rhEmpleadoTO.setEmpSaldoHorasExtras50(rhEmpleado.getEmpSaldoHorasExtras50());
        rhEmpleadoTO.setEmpSaldoHorasExtras100(rhEmpleado.getEmpSaldoHorasExtras100());
        rhEmpleadoTO.setEmpSaldoHorasExtrasExtraordinarias100(rhEmpleado.getEmpSaldoHorasExtrasExtraordinarias100());
        rhEmpleadoTO.setEmpRelacionTrabajo(rhEmpleado.getEmpRelacionTrabajo() != null ? rhEmpleado.getEmpRelacionTrabajo().getRtCodigo() : null);
        rhEmpleadoTO.setEmpDescripcion(rhEmpleado.getEmpDescripcion());

        rhEmpleadoTO.setEmpMaternidad(rhEmpleado.isEmpMaternidad());
        rhEmpleadoTO.setEmpLactancia(rhEmpleado.isEmpLactancia());
        rhEmpleadoTO.setEmpFechaDesdeLactancia(rhEmpleado.getEmpFechaDesdeLactancia() != null
                ? UtilsValidacion.fecha(rhEmpleado.getEmpFechaDesdeLactancia(), "yyyy-MM-dd") : null);
        rhEmpleadoTO.setEmpFechaHastaLactancia(rhEmpleado.getEmpFechaHastaLactancia() != null
                ? UtilsValidacion.fecha(rhEmpleado.getEmpFechaHastaLactancia(), "yyyy-MM-dd") : null);
        return rhEmpleadoTO;
    }

    public static RhFormaPago convertirRhFormaPagoTO_RhFormaPago(RhFormaPagoTO rhFormaPagoTO) throws Exception {
        RhFormaPago rhFormaPago = new RhFormaPago();
        rhFormaPago.setFpSecuencial(rhFormaPagoTO.getFpSecuencial());
        rhFormaPago.setFpDetalle(rhFormaPagoTO.getFpDetalle());
        rhFormaPago.setFpInactivo(rhFormaPagoTO.getFpInactivo());
        rhFormaPago.setSecEmpresa(rhFormaPagoTO.getUsrEmpresa());
        rhFormaPago.setSecCodigo(rhFormaPagoTO.getSecCodigo());
        rhFormaPago.setCtaEmpresa(rhFormaPagoTO.getUsrEmpresa());
        rhFormaPago.setCtaCodigo(rhFormaPagoTO.getCtaCodigo());
        rhFormaPago.setUsrEmpresa(rhFormaPagoTO.getUsrEmpresa());
        rhFormaPago.setUsrCodigo(rhFormaPagoTO.getUsrCodigo());
        rhFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(rhFormaPagoTO.getUsrFechaInserta()));
        return rhFormaPago;
    }

    public static RhFormaPagoBeneficiosSociales convertirRhFormaPagoBeneficioSocialTO_RhFormaPagoBeneficioSocial(
            RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO) throws Exception {
        RhFormaPagoBeneficiosSociales rhFormaPagoBeneficiosSociales = new RhFormaPagoBeneficiosSociales();
        rhFormaPagoBeneficiosSociales.setFpSecuencial(rhFormaPagoBeneficioSocialTO.getFpSecuencial());
        rhFormaPagoBeneficiosSociales.setFpDetalle(rhFormaPagoBeneficioSocialTO.getFpDetalle());
        rhFormaPagoBeneficiosSociales.setFpCodigoMinisterial(rhFormaPagoBeneficioSocialTO.getFpCodigoMinisterial());
        rhFormaPagoBeneficiosSociales.setFpInactivo(rhFormaPagoBeneficioSocialTO.getFpInactivo());
        rhFormaPagoBeneficiosSociales.setSecEmpresa(rhFormaPagoBeneficioSocialTO.getUsrEmpresa());
        rhFormaPagoBeneficiosSociales.setSecCodigo(rhFormaPagoBeneficioSocialTO.getSecCodigo());
        rhFormaPagoBeneficiosSociales.setCtaEmpresa(rhFormaPagoBeneficioSocialTO.getUsrEmpresa());
        rhFormaPagoBeneficiosSociales.setCtaCodigo(rhFormaPagoBeneficioSocialTO.getCtaCodigo());
        rhFormaPagoBeneficiosSociales.setUsrEmpresa(rhFormaPagoBeneficioSocialTO.getUsrEmpresa());
        rhFormaPagoBeneficiosSociales.setUsrCodigo(rhFormaPagoBeneficioSocialTO.getUsrCodigo());
        rhFormaPagoBeneficiosSociales.setUsrFechaInserta(
                UtilsValidacion.fechaString_Date(rhFormaPagoBeneficioSocialTO.getUsrFechaInserta()));
        return rhFormaPagoBeneficiosSociales;
    }

    public static RhVacaciones convertirRhVacacionesTO_RhVacaciones(RhVacacionesTO rhVacacionesTO) throws Exception {
        RhVacaciones rhVacaciones = new RhVacaciones();
        rhVacaciones.setVacSecuencial(rhVacacionesTO.getVacSecuencial());
        rhVacaciones.setVacDesde(UtilsValidacion.fecha(rhVacacionesTO.getVacDesde(), "yyyy-MM-dd"));
        rhVacaciones.setVacHasta(UtilsValidacion.fecha(rhVacacionesTO.getVacHasta(), "yyyy-MM-dd"));
        rhVacaciones.setVacValor(rhVacacionesTO.getVacValor());
        rhVacaciones.setVacFormaPago(rhVacacionesTO.getVacFormaPago());
        rhVacaciones.setVacAuxiliar(rhVacacionesTO.getReversar());
        rhVacaciones.setRhEmpleado(new RhEmpleado(rhVacacionesTO.getEmpEmpresa(), rhVacacionesTO.getEmpId()));
        rhVacaciones.setVacGozadas(rhVacacionesTO.getVacGozadas());
        rhVacaciones.setVacGozadasDesde(UtilsValidacion.fecha(rhVacacionesTO.getVacGozadasDesde(), "yyyy-MM-dd"));
        rhVacaciones.setVacGozadasHasta(UtilsValidacion.fecha(rhVacacionesTO.getVacGozadasHasta(), "yyyy-MM-dd"));
        rhVacaciones.setSecEmpresa(rhVacacionesTO.getEmpEmpresa());
        rhVacaciones.setSecCodigo(rhVacacionesTO.getSecCodigo());
        rhVacaciones.setConEmpresa(rhVacacionesTO.getEmpEmpresa());
        rhVacaciones.setConPeriodo(rhVacacionesTO.getConPeriodo());
        rhVacaciones.setConTipo(rhVacacionesTO.getConTipo());
        rhVacaciones.setConNumero(rhVacacionesTO.getConNumero());
        return rhVacaciones;
    }

    public static RhVacacionesTO convertirRhVacaciones_RhVacacionesTO(RhVacaciones rhVacaciones) throws Exception {
        RhVacacionesTO rhVacacionesTO = new RhVacacionesTO();
        rhVacacionesTO.setVacSecuencial(rhVacaciones.getVacSecuencial());
        rhVacacionesTO.setVacDesde(UtilsValidacion.fecha(rhVacaciones.getVacDesde(), "yyyy-MM-dd"));
        rhVacacionesTO.setVacHasta(UtilsValidacion.fecha(rhVacaciones.getVacHasta(), "yyyy-MM-dd"));
        rhVacacionesTO.setVacValor(rhVacaciones.getVacValor());
        rhVacacionesTO.setVacFormaPago(rhVacaciones.getVacFormaPago());
        rhVacacionesTO.setReversar(rhVacaciones.getVacAuxiliar());
        rhVacacionesTO.setEmpId(rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        rhVacacionesTO.setEmpEmpresa(rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa());
        rhVacacionesTO.setEmpApellidosNombres(rhVacaciones.getRhEmpleado().getEmpApellidos() + " " + rhVacaciones.getRhEmpleado().getEmpNombres());
        rhVacacionesTO.setVacGozadas(rhVacaciones.getVacGozadas());
        rhVacacionesTO.setVacGozadasDesde(UtilsValidacion.fecha(rhVacaciones.getVacGozadasDesde(), "yyyy-MM-dd"));
        rhVacacionesTO.setVacGozadasHasta(UtilsValidacion.fecha(rhVacaciones.getVacGozadasHasta(), "yyyy-MM-dd"));
        rhVacacionesTO.setSecCodigo(rhVacaciones.getSecCodigo());
        rhVacacionesTO.setConPeriodo(rhVacaciones.getConPeriodo());
        rhVacacionesTO.setConTipo(rhVacaciones.getConTipo());
        rhVacacionesTO.setConNumero(rhVacaciones.getConNumero());
        return rhVacacionesTO;
    }

    public static RhBonoConcepto convertirRhBonoConceptoTO_RhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO)
            throws Exception {
        RhBonoConcepto rhBonoConcepto = new RhBonoConcepto();

        rhBonoConcepto.setBcSecuencial(rhBonoConceptoTO.getBcSecuencia());
        rhBonoConcepto.setBcDetalle(rhBonoConceptoTO.getBcDetalle());
        rhBonoConcepto.setBcValor(rhBonoConceptoTO.getBcValor());
        rhBonoConcepto.setBcValorFijo(rhBonoConceptoTO.getBcValorFijo());
        rhBonoConcepto.setBcInactivo(rhBonoConceptoTO.getBcInactivo());

        rhBonoConcepto.setUsrEmpresa(rhBonoConceptoTO.getEmpCodigo());
        rhBonoConcepto.setUsrCodigo(rhBonoConceptoTO.getUsrInsertaBonoConcepto());
        rhBonoConcepto.setUsrFechaInserta(UtilsValidacion.fechaString_Date(rhBonoConceptoTO.getUsrFechaInsertaBonoConcepto()));
        return rhBonoConcepto;
    }

    public static RhUtilidadesPeriodo convertirRhUtilidadesPeriodoTO_RhUtilidadesPeriodo(
            RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO) {
        RhUtilidadesPeriodo rhUtilidadesPeriodo = new RhUtilidadesPeriodo();
        rhUtilidadesPeriodo.setRhUtilidadesPeriodoPK(new RhUtilidadesPeriodoPK(rhUtilidadesPeriodoTO.getUtiEmpresa(), rhUtilidadesPeriodoTO.getUtiDescripcion()));
        rhUtilidadesPeriodo.setUtiDesde(UtilsValidacion.fecha(rhUtilidadesPeriodoTO.getUtiDesde(), "yyyy-MM-dd"));
        rhUtilidadesPeriodo.setUtiHasta(UtilsValidacion.fecha(rhUtilidadesPeriodoTO.getUtiHasta(), "yyyy-MM-dd"));
        rhUtilidadesPeriodo.setUtiFechaMaximaPago(
                UtilsValidacion.fecha(rhUtilidadesPeriodoTO.getUtiFechaMaximaPago(), "yyyy-MM-dd"));
        rhUtilidadesPeriodo.setUtiTotalDias(new Long(rhUtilidadesPeriodoTO.getUtiTotalDias()));
        rhUtilidadesPeriodo.setUtiTotalCargas(new Long(rhUtilidadesPeriodoTO.getUtiTotalCargas()));
        rhUtilidadesPeriodo.setUtiTotalPagar(rhUtilidadesPeriodoTO.getUtiTotalPagar());
        return rhUtilidadesPeriodo;
    }

    public static RhFormulario107TO convertirRhFormulario107_RhFormulario107TO(RhFormulario107 rhFormulario107)
            throws Exception {
        RhFormulario107TO rhFormulario107TO;
        if (rhFormulario107 != null) {
            rhFormulario107TO = new RhFormulario107TO();
            rhFormulario107TO.setF107Empresa(rhFormulario107.getRhFormulario107PK().getF107Empresa());
            rhFormulario107TO.setF107Anio(rhFormulario107.getRhFormulario107PK().getF107Anio());
            rhFormulario107TO.setF107Tipo(rhFormulario107.getF107Tipo());
            rhFormulario107TO.setF107Id(rhFormulario107.getRhFormulario107PK().getF107Id());
            rhFormulario107TO.setF107Nombres(rhFormulario107.getF107Nombres());
            rhFormulario107TO.setF107Direccion(rhFormulario107.getF107Direccion());
            rhFormulario107TO.setF107Numero(rhFormulario107.getF107Direccion());
            rhFormulario107TO.setF107Canton(rhFormulario107.getF107Canton());
            rhFormulario107TO.setF107Provincia(rhFormulario107.getF107Provincia());
            rhFormulario107TO.setF107Telefono(rhFormulario107.getF107Telefono());
            rhFormulario107TO.setF107SalarioNeto(rhFormulario107.getF107SalarioNeto());
            rhFormulario107TO.setF107Sueldo(rhFormulario107.getF107Sueldo());
            rhFormulario107TO.setF107Bonos(rhFormulario107.getF107Bonos());
            rhFormulario107TO.setF107XiiiSueldo(rhFormulario107.getF107XiiiSueldo());
            rhFormulario107TO.setF107XivSueldo(rhFormulario107.getF107XivSueldo());
            rhFormulario107TO.setF107FondoReserva(rhFormulario107.getF107FondoReserva());
            rhFormulario107TO.setF107SalarioDigno(rhFormulario107.getF107SalarioDigno());
            rhFormulario107TO.setF107Utilidades(rhFormulario107.getF107Utilidades());
            rhFormulario107TO.setF107Desahucio(rhFormulario107.getF107Desahucio());
            rhFormulario107TO.setF107Iess(rhFormulario107.getF107Iess());
            rhFormulario107TO.setF107Vivienda(rhFormulario107.getF107Vivienda());
            rhFormulario107TO.setF107Salud(rhFormulario107.getF107Salud());
            rhFormulario107TO.setF107Educacion(rhFormulario107.getF107Educacion());
            rhFormulario107TO.setF107Alimentacion(rhFormulario107.getF107Alimentacion());
            rhFormulario107TO.setF107Vestuario(rhFormulario107.getF107Vestuario());
            rhFormulario107TO.setF107RebajaDiscapacitado(rhFormulario107.getF107RebajaDiscapacitado());
            rhFormulario107TO.setF107RebajaTerceraEdad(rhFormulario107.getF107RebajaTerceraEdad());
            rhFormulario107TO.setF107ImpuestoAsumido(rhFormulario107.getF107ImpuestoAsumido());
            rhFormulario107TO.setF107Subtotal(rhFormulario107.getF107Subtotal());
            rhFormulario107TO.setF107MesesTrabajados(rhFormulario107.getF107MesesTrabajados());
            rhFormulario107TO.setF107OtrosIngresos(rhFormulario107.getF107OtrosIngresos());
            rhFormulario107TO.setF107OtrasDeducciones(rhFormulario107.getF107OtrasDeducciones());
            rhFormulario107TO.setF107OtrasRebajas(rhFormulario107.getF107OtrasRebajas());
            rhFormulario107TO.setF107BaseImponible(rhFormulario107.getF107BaseImponible());
            rhFormulario107TO.setF107ImpuestoCausado(rhFormulario107.getF107ImpuestoCausado());
            rhFormulario107TO.setF107ValorRetenido(rhFormulario107.getF107ValorRetenido());
            rhFormulario107TO.setF107NumeroRetenciones(rhFormulario107.getF107NumeroRetenciones());
            rhFormulario107TO.setF107Impuesto(rhFormulario107.getF107Impuesto());
            rhFormulario107TO.setF107AnioConsulta(rhFormulario107.getRhFormulario107PK().getF107Anio());
            rhFormulario107TO.setUsrEmpresa(rhFormulario107.getUsrEmpresa());
            rhFormulario107TO.setUsrCodigo(rhFormulario107.getUsrCodigo());
            rhFormulario107TO
                    .setUsrFechaInserta(UtilsValidacion.fecha(rhFormulario107.getUsrFechaInserta(), "yyyy-MM-dd"));
        } else {
            rhFormulario107TO = null;
        }
        return rhFormulario107TO;
    }

    public static RhFormulario107 convertirRhFormulario107TO_RhFormulario107(RhFormulario107TO rhFormulario107TO)
            throws Exception {
        RhFormulario107 rhFormulario107;

        rhFormulario107 = new RhFormulario107();

        rhFormulario107.setRhFormulario107PK(new RhFormulario107PK(rhFormulario107TO.getF107Empresa(),
                rhFormulario107TO.getF107Anio(), rhFormulario107TO.getF107Id().trim()));

        rhFormulario107.setF107Tipo(rhFormulario107TO.getF107Tipo());

        rhFormulario107.setF107Nombres(rhFormulario107TO.getF107Nombres());
        rhFormulario107.setF107Direccion(rhFormulario107TO.getF107Direccion());
        // rhFormulario107.setF107Numero(rhFormulario107TO.getF107Numero());
        rhFormulario107.setF107Canton(rhFormulario107TO.getF107Canton());
        rhFormulario107.setF107Provincia(rhFormulario107TO.getF107Provincia());
        rhFormulario107.setF107Telefono(rhFormulario107TO.getF107Telefono());
        rhFormulario107.setF107SalarioNeto(rhFormulario107TO.getF107SalarioNeto());
        rhFormulario107.setF107Sueldo(rhFormulario107TO.getF107Sueldo());
        rhFormulario107.setF107Bonos(rhFormulario107TO.getF107Bonos());
        rhFormulario107.setF107XiiiSueldo(rhFormulario107TO.getF107XiiiSueldo());
        rhFormulario107.setF107XivSueldo(rhFormulario107TO.getF107XivSueldo());
        rhFormulario107.setF107FondoReserva(rhFormulario107TO.getF107FondoReserva());
        rhFormulario107.setF107SalarioDigno(rhFormulario107TO.getF107SalarioDigno());
        rhFormulario107.setF107Utilidades(rhFormulario107TO.getF107Utilidades());
        rhFormulario107.setF107Desahucio(rhFormulario107TO.getF107Desahucio());
        rhFormulario107.setF107Iess(rhFormulario107TO.getF107Iess());
        rhFormulario107.setF107Vivienda(rhFormulario107TO.getF107Vivienda());
        rhFormulario107.setF107Salud(rhFormulario107TO.getF107Salud());
        rhFormulario107.setF107Educacion(rhFormulario107TO.getF107Educacion());
        rhFormulario107.setF107Alimentacion(rhFormulario107TO.getF107Alimentacion());
        rhFormulario107.setF107Vestuario(rhFormulario107TO.getF107Vestuario());
        rhFormulario107.setF107RebajaDiscapacitado(rhFormulario107TO.getF107RebajaDiscapacitado());
        rhFormulario107.setF107RebajaTerceraEdad(rhFormulario107TO.getF107RebajaTerceraEdad());
        rhFormulario107.setF107ImpuestoAsumido(rhFormulario107TO.getF107ImpuestoAsumido());
        rhFormulario107.setF107Subtotal(rhFormulario107TO.getF107Subtotal());
        rhFormulario107.setF107MesesTrabajados(rhFormulario107TO.getF107MesesTrabajados());
        rhFormulario107.setF107OtrosIngresos(rhFormulario107TO.getF107OtrosIngresos());
        rhFormulario107.setF107OtrasDeducciones(rhFormulario107TO.getF107OtrasDeducciones());
        rhFormulario107.setF107OtrasRebajas(rhFormulario107TO.getF107OtrasRebajas());
        rhFormulario107.setF107BaseImponible(rhFormulario107TO.getF107BaseImponible());
        rhFormulario107.setF107ImpuestoCausado(rhFormulario107TO.getF107ImpuestoCausado());
        rhFormulario107.setF107ValorRetenido(rhFormulario107TO.getF107ValorRetenido());
        rhFormulario107.setF107NumeroRetenciones(rhFormulario107TO.getF107NumeroRetenciones());
        rhFormulario107.setF107Impuesto(rhFormulario107TO.getF107Impuesto());
        rhFormulario107.setUsrEmpresa(rhFormulario107TO.getUsrEmpresa());
        rhFormulario107.setUsrCodigo(rhFormulario107TO.getUsrCodigo());
        rhFormulario107.setUsrFechaInserta(UtilsValidacion.fecha(rhFormulario107TO.getUsrFechaInserta(), "yyyy-MM-dd"));

        return rhFormulario107;
    }

    public static RhEmpleadoDescuentosFijos convertirRhEmpleadoDescuentosFijosTO_RhEmpleadoDescuentosFijos(
            RhEmpleadoDescuentosFijosTO rhEmpleadoDescuentosFijosTO) throws Exception {
        RhEmpleadoDescuentosFijos obj = new RhEmpleadoDescuentosFijos();
        obj.setDescSecuencial(rhEmpleadoDescuentosFijosTO.getDescSecuencial());
        obj.setDescValor(rhEmpleadoDescuentosFijosTO.getDescValor());
        obj.setRhAnticipoMotivo(new RhAnticipoMotivo(new RhAnticipoMotivoPK(
                rhEmpleadoDescuentosFijosTO.getMot_empresa(), rhEmpleadoDescuentosFijosTO.getMot_detalle())));
        obj.setRhEmpleado(new RhEmpleado(new RhEmpleadoPK(rhEmpleadoDescuentosFijosTO.getEmp_empresa(),
                rhEmpleadoDescuentosFijosTO.getEmp_id())));
        return obj;
    }

    public static RhAnticipoMotivoTO convertirRhAnticipoMotivo_RhAnticipoMotivoTO(RhAnticipoMotivo rhAnticipoMotivo)
            throws Exception {

        RhAnticipoMotivoTO obj = new RhAnticipoMotivoTO();
        obj.setMotEmpresa(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotEmpresa());
        obj.setMotDetalle(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle());
        obj.setMotInactivo(rhAnticipoMotivo.getMotInactivo());
        obj.setTipEmpresa(rhAnticipoMotivo.getConTipo().getConTipoPK().getTipEmpresa());
        obj.setTipCodigo(rhAnticipoMotivo.getConTipo().getConTipoPK().getTipCodigo());
        obj.setUsrEmpresa(rhAnticipoMotivo.getUsrEmpresa());
        obj.setUsrCodigo(rhAnticipoMotivo.getUsrCodigo());
        obj.setUsrFechaInserta(rhAnticipoMotivo.getUsrFechaInserta().toString());

        return obj;
    }

    public static RhAnticipoMotivo convertirRhAnticipoMotivoTO_RhAnticipoMotivo(RhAnticipoMotivoTO rhAnticipoMotivoTO)
            throws Exception {
        RhAnticipoMotivo obj = new RhAnticipoMotivo();
        obj.setRhAnticipoMotivoPK(
                new RhAnticipoMotivoPK(rhAnticipoMotivoTO.getMotEmpresa(), rhAnticipoMotivoTO.getMotDetalle()));
        obj.setConTipo(new ConTipo(rhAnticipoMotivoTO.getTipEmpresa(), rhAnticipoMotivoTO.getTipCodigo()));
        obj.setMotInactivo(rhAnticipoMotivoTO.isMotInactivo());
        obj.setUsrEmpresa(rhAnticipoMotivoTO.getUsrEmpresa());
        obj.setUsrCodigo(rhAnticipoMotivoTO.getUsrCodigo());
        obj.setUsrFechaInserta(
                UtilsDate.timestamp(UtilsValidacion.fechaString_Date(rhAnticipoMotivoTO.getUsrFechaInserta())));
        return obj;
    }

    public static RhXiiiSueldoPeriodo convertirRhXiiiSueldoPeriodoTO_RhXiiiSueldoPeriodo(
            RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO) {
        RhXiiiSueldoPeriodo RhXiiiSueldoPeriodo = new RhXiiiSueldoPeriodo();
        RhXiiiSueldoPeriodo.setXiiiSecuencial(rhXiiiSueldoPeriodoTO.getPeriodoSecuencial());
        RhXiiiSueldoPeriodo.setXiiiDescripcion(rhXiiiSueldoPeriodoTO.getXiiiDescripcion());
        RhXiiiSueldoPeriodo.setXiiiDesde(UtilsValidacion.fecha(rhXiiiSueldoPeriodoTO.getXiiiDesde(), "yyyy-MM-dd"));
        RhXiiiSueldoPeriodo.setXiiiHasta(UtilsValidacion.fecha(rhXiiiSueldoPeriodoTO.getXiiiHasta(), "yyyy-MM-dd"));
        RhXiiiSueldoPeriodo.setXiiiFechaMaximaPago(
                UtilsValidacion.fecha(rhXiiiSueldoPeriodoTO.getXiiiFechaMaximaPago(), "yyyy-MM-dd"));
        return RhXiiiSueldoPeriodo;
    }

    public static RhXivSueldoPeriodo convertirRhXivSueldoPeriodoTO_RhXivSueldoPeriodo(
            RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO) {
        RhXivSueldoPeriodo rhXivSueldoPeriodo = new RhXivSueldoPeriodo();
        rhXivSueldoPeriodo.setXivSecuencial(rhXivSueldoPeriodoTO.getPeriodoSecuencial());
        rhXivSueldoPeriodo.setXivDescripcion(rhXivSueldoPeriodoTO.getXivDescripcion());
        rhXivSueldoPeriodo.setXivDesde(UtilsValidacion.fecha(rhXivSueldoPeriodoTO.getXivDesde(), "yyyy-MM-dd"));
        rhXivSueldoPeriodo.setXivHasta(UtilsValidacion.fecha(rhXivSueldoPeriodoTO.getXivHasta(), "yyyy-MM-dd"));
        rhXivSueldoPeriodo.setXivFechaMaximaPago(
                UtilsValidacion.fecha(rhXivSueldoPeriodoTO.getXivFechaMaximaPago(), "yyyy-MM-dd"));
        return rhXivSueldoPeriodo;
    }

    /*
	 *
	 *
	 *
	 *
	 *
	 * sec_empresa character(7) NOT NULL, sec_codigo character(7) NOT NULL,
	 * con_empresa character(7) NOT NULL, con_periodo character(7) NOT NULL,
	 * con_tipo character(7) NOT NULL, con_numero character(7) NOT NULL,
     */
    public static RhSalarioDigno convertirRhSalarioDignoTO_RhSalarioDigno(RhSalarioDignoTO rhSalarioDignoTO) {
        RhEmpleado rhEmpleado = new RhEmpleado(
                new RhEmpleadoPK(rhSalarioDignoTO.getEmpEmpresa(), rhSalarioDignoTO.getEmpId()));

        RhSalarioDigno rhSalarioDigno = new RhSalarioDigno();
        rhSalarioDigno.setSdigSecuencial(rhSalarioDignoTO.getSdigSecuencial());
        rhSalarioDigno.setSdigDesde(UtilsValidacion.fecha(rhSalarioDignoTO.getSdigDesde(), "yyyy-MM-dd"));
        rhSalarioDigno.setSdigHasta(UtilsValidacion.fecha(rhSalarioDignoTO.getSdigHasta(), "yyyy-MM-dd"));
        rhSalarioDigno.setSdigValor(rhSalarioDignoTO.getSdigValor());
        rhSalarioDigno.setSdigFormaPago(rhSalarioDignoTO.getSdigFormaPago());
        rhSalarioDigno.setSdigAuxiliar(rhSalarioDignoTO.getSdigReversado());
        rhSalarioDigno.setRhEmpleado(rhEmpleado);
        rhSalarioDigno.setSecEmpresa(rhSalarioDignoTO.getEmpEmpresa());
        rhSalarioDigno.setSecCodigo(rhSalarioDignoTO.getSecCodigo());
        rhSalarioDigno.setConEmpresa(rhSalarioDignoTO.getConEmpresa());
        rhSalarioDigno.setConPeriodo(rhSalarioDignoTO.getConPeriodo());
        rhSalarioDigno.setConTipo(rhSalarioDignoTO.getConTipo());
        rhSalarioDigno.setConNumero(rhSalarioDignoTO.getConNumero());
        return rhSalarioDigno;
    }

    public static RhParametros convertirRhParametros_RhParametrosTO(RhParametros rhParametro)
            throws Exception {

        RhParametros rhParametros = new RhParametros();
        rhParametros.setParSecuencial(rhParametros.getParSecuencial());
        rhParametros.setParDesde(rhParametros.getParDesde());
        rhParametros.setParHasta(rhParametros.getParHasta());
        rhParametros.setParIessPorcentajeAporteIndividual(rhParametros.getParIessPorcentajeAporteIndividual());
        rhParametros.setParIessPorcentajeAporteExtendido(rhParametros.getParIessPorcentajeAporteExtendido());
        rhParametros.setParIessPorcentajeAportePatronal(rhParametros.getParIessPorcentajeAportePatronal());
        rhParametros.setParIessPorcentajeIece(rhParametros.getParIessPorcentajeIece());
        rhParametros.setParIessPorcentajeSecap(rhParametros.getParIessPorcentajeSecap());
        rhParametros.setParSalarioMinimoVital(rhParametros.getParSalarioMinimoVital());

        return rhParametros;
    }

    public static RhRol convertirRhRol_RhRol(RhRol rhRolAux) throws Exception {
        RhRol rol = new RhRol();
        rol.setRolSecuencial(rhRolAux.getRolSecuencial());
        rol.setRolDesde(rhRolAux.getRolDesde());
        rol.setRolHasta(rhRolAux.getRolHasta());
        rol.setRolFechaUltimoSueldo(rhRolAux.getRolFechaUltimoSueldo());
        rol.setRolDiasLaboradosReales(rhRolAux.getRolDiasLaboradosReales());
        rol.setRolDiasFaltasReales(rhRolAux.getRolDiasFaltasReales());
        rol.setRolDiasExtrasReales(rhRolAux.getRolDiasExtrasReales());
        rol.setRolDiasDescansoReales(rhRolAux.getRolDiasDescansoReales());
        rol.setRolDiasPagadosReales(rhRolAux.getRolDiasPagadosReales());
        rol.setRolDiasPermisoMedico(rhRolAux.getRolDiasPermisoMedico());
        rol.setRolDiasVacaciones(rhRolAux.getRolDiasVacaciones());
        rol.setRolSaldoAnterior(rhRolAux.getRolSaldoAnterior());
        rol.setRolIngresos(rhRolAux.getRolIngresos());
        rol.setRolHorasExtras(rhRolAux.getRolHorasExtras());
        rol.setRolHorasExtras100(rhRolAux.getRolHorasExtras100());
        rol.setRolHorasExtrasExtraordinarias100(rhRolAux.getRolHorasExtrasExtraordinarias100());
        rol.setRolSaldoHorasExtras50(rhRolAux.getRolSaldoHorasExtras50());
        rol.setRolSaldoHorasExtras100(rhRolAux.getRolSaldoHorasExtras100());
        rol.setRolSaldoHorasExtrasExtraordinarias100(rhRolAux.getRolSaldoHorasExtrasExtraordinarias100());
        rol.setRolBonos(rhRolAux.getRolBonos());
        rol.setRolBonosnd(rhRolAux.getRolBonosnd());
        rol.setRolBonoFijo(rhRolAux.getRolBonoFijo());
        rol.setRolBonoFijoNd(rhRolAux.getRolBonoFijoNd());
        rol.setRolOtrosIngresos(rhRolAux.getRolOtrosIngresos());
        rol.setRolOtrosIngresosNd(rhRolAux.getRolOtrosIngresosNd());
        rol.setRolLiqFondoReserva(rhRolAux.getRolLiqFondoReserva());
        rol.setRolLiqXiii(rhRolAux.getRolLiqXiii());
        rol.setRolLiqXiv(rhRolAux.getRolLiqXiv());
        rol.setRolLiqVacaciones(rhRolAux.getRolLiqVacaciones());
        rol.setRolLiqSalarioDigno(rhRolAux.getRolLiqSalarioDigno());
        rol.setRolLiqDesahucio(rhRolAux.getRolLiqDesahucio());
        rol.setRolLiqDesahucioIntempestivo(rhRolAux.getRolLiqDesahucioIntempestivo());
        rol.setRolLiqBonificacion(rhRolAux.getRolLiqBonificacion());
        rol.setRolAnticipos(rhRolAux.getRolAnticipos());
        rol.setRolPrestamos(rhRolAux.getRolPrestamos());
        rol.setRolPrestamoQuirografario(rhRolAux.getRolPrestamoQuirografario());
        rol.setRolPrestamoHipotecario(rhRolAux.getRolPrestamoHipotecario());
        rol.setRolIess(rhRolAux.getRolIess());
        rol.setRolIessExtension(rhRolAux.getRolIessExtension());
        rol.setRolRetencionFuente(rhRolAux.getRolRetencionFuente());
        rol.setRolDescuentoPermisoMedico(rhRolAux.getRolDescuentoPermisoMedico());
        rol.setRolDescuentoVacaciones(rhRolAux.getRolDescuentoVacaciones());
        rol.setRolTotal(rhRolAux.getRolTotal());
        rol.setRolFormaPago(rhRolAux.getRolFormaPago());
        rol.setRolDocumento(rhRolAux.getRolDocumento());
        rol.setRolObservaciones(rhRolAux.getRolObservaciones());
        rol.setRolAportePatronal(rhRolAux.getRolAportePatronal());
        rol.setRolIece(rhRolAux.getRolIece());
        rol.setRolSecap(rhRolAux.getRolSecap());
        rol.setRolXiii(rhRolAux.getRolXiii());
        rol.setRolXiv(rhRolAux.getRolXiv());
        rol.setRolFondoReserva(rhRolAux.getRolFondoReserva());
        rol.setRolVacaciones(rhRolAux.getRolVacaciones());
        rol.setRolDesahucio(rhRolAux.getRolDesahucio());
        rol.setRolAuxiliar(rhRolAux.isRolAuxiliar());
        rol.setEmpCargo(rhRolAux.getEmpCargo());
        rol.setCatEmpresa(rhRolAux.getCatEmpresa());
        rol.setCatNombre(rhRolAux.getCatNombre());
        rol.setEmpSueldo(rhRolAux.getEmpSueldo());
        rol.setEmpBonoFijo(rhRolAux.getEmpBonoFijo());
        rol.setEmpBonoFijoNd(rhRolAux.getEmpBonoFijoNd());
        rol.setEmpOtrosIngresos(rhRolAux.getEmpOtrosIngresos());
        rol.setEmpOtrosIngresosNd(rhRolAux.getEmpOtrosIngresosNd());
        rol.setRolIngresoVacaciones(rhRolAux.getRolIngresoVacaciones());
        rol.setEmpDiasLaborados(rhRolAux.getEmpDiasLaborados());
        rol.setEmpDiasDescanso(rhRolAux.getEmpDiasDescanso());
        rol.setEmpCancelarXiiiSueldoMensualmente(rhRolAux.isEmpCancelarXiiiSueldoMensualmente());
        rol.setEmpCancelarXivSueldoMensualmente(rhRolAux.isEmpCancelarXivSueldoMensualmente());
        rol.setEmpCancelarFondoReservaMensualmente(rhRolAux.isEmpCancelarFondoReservaMensualmente());
        rol.setEmpSalarioNeto(rhRolAux.isEmpSalarioNeto());
        rol.setPrdSector(rhRolAux.getPrdSector());
        rol.setConCuentas(rhRolAux.getConCuentas());
        rol.setConContable(rhRolAux.getConContable());
        rol.setConContableProvision(rhRolAux.getConContableProvision());
        rol.setRhEmpleado(rhRolAux.getRhEmpleado());
        rol.setEmpActualizar(rhRolAux.isEmpActualizar());
        rol.setRolImpuestoRentaModificado(rhRolAux.isRolImpuestoRentaModificado());
        rol.setRolRelacionTrabajo(rhRolAux.getRolRelacionTrabajo());
        rol.setRolRecargoJornadaNocturna(rhRolAux.getRolRecargoJornadaNocturna());

        return rol;
    }

    public static RhAnticipo convertirRhAnticipo_RhAnticipo(RhAnticipo rhAnticipoAux)
            throws Exception {

        RhAnticipo rhAnticipo = new RhAnticipo();
        rhAnticipo.setAntSecuencial(rhAnticipoAux.getAntSecuencial());
        rhAnticipo.setAntValor(rhAnticipoAux.getAntValor());
        rhAnticipo.setAntDocumento(rhAnticipoAux.getAntDocumento());
        rhAnticipo.setAntObservaciones(rhAnticipoAux.getAntObservaciones());
        rhAnticipo.setAntAuxiliar(rhAnticipoAux.isAntAuxiliar());
        rhAnticipo.setPrdSector(rhAnticipoAux.getPrdSector());
        rhAnticipo.setConCuentas(rhAnticipoAux.getConCuentas());
        rhAnticipo.setConContable(rhAnticipoAux.getConContable());
        rhAnticipo.setRhEmpleado(rhAnticipoAux.getRhEmpleado());

        return rhAnticipo;
    }
}
