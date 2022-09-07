package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.ItemListaRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleViaticosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoXiiiSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoXivSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaAnticiposLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteConsultaBonosLote;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface ReporteRrhhService {

    public byte[] generarReporteConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO) throws Exception;

    public byte[] generarReporteListaDetalleVacaionesPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String desde, String hasta, String empSector,
            List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO) throws Exception;

    public byte[] generarReporteListaDetalleVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String desde, String hasta, String empSector,
            List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO, String nombreReporte) throws Exception;

    public byte[] generarReporteDetalleAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empId,
            String empCodigo, String empCategoria, String fechaDesde, String fechaHasta,
            List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO, String nombre) throws Exception;

    public byte[] generarReporteDetalleAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId,
            List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO, String nombre) throws Exception;

    public byte[] generarReporteDetallePrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empCodigo,
            String fechaDesde, String fechaHasta, String empCategoria, String empId,
            List<RhListaDetallePrestamosTO> listaDetallePrestamosTO) throws Exception;

    public byte[] generarReporteDetalleBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleBonosTO> listaDetalleBonosTO) throws Exception;

    public byte[] generarReporteDetalleBonosLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo,
            String tipo, String numero, List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO, String nombre)
            throws Exception;

    public byte[] generarReporteDetalleBonosLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaBonosLote> reporteConsultaBonosLotes) throws Exception;

    public byte[] generarReporteDetalleBonosLoteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaBonosLote> lista) throws Exception;

    public byte[] generarReporteDetalleViaticos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleViaticosTO> listaDetalleViaticosTO) throws Exception;

    public byte[] generarReporteConsolidadoBonosViatico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO)
            throws Exception;

    public byte[] generarReporteListadoRolesPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaDetalleRolesTO> listaDetalleRolesTO) throws Exception;

    public byte[] generarReporteConsolidadoRoles(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String empCategoria, String nombreEmpleado,
            List<RhListaConsolidadoRolesTO> listaConsolidadoRolesTO, boolean excluirLiquidacion) throws Exception;

    public byte[] generarReporteSaldoIndividualAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, String empId,
            List<RhListaSaldoIndividualAnticiposPrestamosTO> listaSaldoIndividualAnticiposPrestamosTO) throws Exception;

    public byte[] generarReporteSaldoConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta,
            List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO)
            throws Exception;

    public byte[] generarReporteSaldoConsolidadoBonosViaticos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta, List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO)
            throws Exception;

    public byte[] generarReporteSaldoConsolidadoSeparandoAnticiposYPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta,
            List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO, String documento)
            throws Exception;

    public byte[] generarReporteSaldoConsolidadoSueldosPorPagar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaHasta, List<RhListaSaldoConsolidadoSueldosPorPagarTO> listaSaldoConsolidadoSueldosPorPagarTO)
            throws Exception;

    public byte[] generarReporteXiiiSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO) throws Exception;

    public byte[] generarReporteXivSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO) throws Exception;

    public byte[] generarReporteUtilidadesPreCalculo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector,
            String periodo, String fechaDesde, String fechaHasta, String fechaMaxima,
            List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs) throws Exception;

    public byte[] generarReporteProvisionesComprobanteContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String periodo, String tipo, String numero, List<RhListaProvisionesTO> listaProvisionesTO) throws Exception;

    public byte[] generarReporteFormulario107(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaEntrega,
            String rucEmpleador, String razonSocial, String rucContador, RhFormulario107TO rhFormulario107TOGuardar)
            throws Exception;

    public byte[] generarReporteXIIISueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo) throws Exception;

    public byte[] generarReporteXIVSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIVSueldo) throws Exception;

    public byte[] generarReporteUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteUtilidades) throws Exception;

    public byte[] generarReporteAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos) throws Exception;

    public byte[] generarReporteAnticipoDetalleAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, SisInfoTO usuario) throws Exception;

    public byte[] generarReporteAnticipoContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposTO> listaAnticipoDetalleSeleccion, SisInfoTO usuario) throws Exception;

    public byte[] generarReporteConsultaAnticiposLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaAnticiposLote> lista, String nombre) throws Exception;

    public byte[] generarReporteConsultaAnticiposLoteWeb(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<RhListaDetalleAnticiposLoteTO> lista, String nombre, String periodo, String tipoContable, String numeroContable) throws Exception;

    public byte[] generarReporteConsultaAnticiposLoteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsultaAnticiposLote> lista) throws Exception;

    public byte[] generarReportePrestamoVista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteAnticipoPrestamoXIIISueldo> lista) throws Exception;

    public byte[] generarReporteRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception;

    public byte[] generarReporteLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception;

    public byte[] generarReporteRolLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception;

    public byte[] generarReporteConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<RhListaConsolidadoHorasExtrasTO> listaConsolidadoBonosViaticosTO)
            throws Exception;

    //cambios
    public byte[] generarReporteRolLoteSoporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportesRol> lista)
            throws Exception;

    public byte[] generarReporteEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteEmpleado> listReporteEmpleado) throws Exception;

    public byte[] generarReporteRol2(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<AuxiliarReporteDetalleRoles> lista);

    public byte[] generarReporteRhUtilidadMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhUtilidadMotivo> lista);

    public byte[] generarReporteRhXivSueldoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXivSueldoMotivo> lista);

    public byte[] generarReporteMotivoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhBonoMotivo> lista)
            throws Exception;

    public byte[] generarReporteConceptoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaBonoConceptoTO> lista)
            throws Exception;

    public byte[] generarReporteMotivoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhPrestamoMotivo> lista)
            throws Exception;

    public byte[] generarReporteMotivoRolPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolMotivo> lista)
            throws Exception;

    public byte[] generarReporteMotivoXiiiSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoMotivo> lista)
            throws Exception;

    public byte[] generarReportePeriodosUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhUtilidadesPeriodoTO> lista) throws Exception;

    public byte[] generarReporteXiiiSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXiiiSueldoPeriodoTO> lista) throws Exception;

    public byte[] generarReporteXivSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhXivSueldoPeriodoTO> lista) throws Exception;

    public byte[] generarReporteRhFormaPagoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhListaFormaPagoTO> listado) throws Exception;

    public byte[] generarReporteComprobanteVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String vacacionesDias, String vacacionesDesde, String vacacionHasta, String empleadoNombre, String empleadoApellido, String idEmpleado, String gerente) throws Exception;

    public byte[] generarReporteRhFormaPagoBeneficiosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhListaFormaPagoBeneficioSocialTO> listado) throws Exception;

    public byte[] generarReporteRhAnticipoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhAnticipoMotivo> listado) throws Exception;

    public byte[] generarReporteRhCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhCategoriaTO> listado) throws Exception;

    public byte[] generarReporteRhEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listado) throws Exception;

    public byte[] generarReporteRhEmpleadoConsuta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhFunListadoEmpleadosTO> listadoC) throws Exception;

    public byte[] generarReporteRhXiiiSueldoXiiiSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable conContable, List<RhXiiiSueldoXiiiSueldoCalcular> lista) throws Exception;

    public byte[] generarReporteRhXiiiSueldoXiiiSueldoCalcularDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception;

    public byte[] generarReporteRhXivSueldoXivSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable conContable, List<RhXivSueldoXivSueldoCalcular> lista) throws Exception;

    public byte[] generarReporteRhXivSueldoXivSueldoCalcularDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception;

    public byte[] generarReporteComprobanteAnticipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhAnticipo> listaRhAnticipo, String nombreReporte) throws Exception;

    public byte[] generarReporteComprobanteAnticipoDeVariosContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception;

    public byte[] generarReporteComprobanteUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContable contable, List<RhUtilidades> listaRhUtilidades, String nombreReporte) throws Exception;

    public byte[] generarReporteComprobanteUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunUtilidadesConsultarTO> listaRhUtilidades, String nombreReporte) throws Exception;

    public byte[] generarReporteComprobanteRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, ConContablePK contablePK) throws Exception;

    public byte[] generarReporteComprobanteRolDeVarioContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<ConContablePK> pks) throws Exception;

    public byte[] generarReporteRhEmpleadoDatosPersonales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listadoC) throws Exception;

//Exportar
    public Map<String, Object> exportarReporteRhUtilidadMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidadMotivo> lista) throws Exception;

    public Map<String, Object> exportarReporteRhXivSueldoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoMotivo> lista) throws Exception;

    public Map<String, Object> exportarReporteMotivoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhBonoMotivo> listado) throws Exception;

    public Map<String, Object> exportarReporteConceptoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaBonoConceptoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoBonosTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteMotivoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhPrestamoMotivo> listado) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoHorasExtrasTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteMotivoRolPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolMotivo> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoXiiiSueldo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoMotivo> listado) throws Exception;

    public Map<String, Object> exportarReportePeriodosUtilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidadesPeriodoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteXiiiSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoPeriodoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteXivSueldoPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoPeriodoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteXiiiSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunXiiiSueldoConsultarTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoConsolidadoSueldosPorPagar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta, List<RhListaSaldoConsolidadoSueldosPorPagarTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoRolesPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String empCategoria, String empSector, List<RhListaDetalleRolesTO> listado, String filtro) throws Exception;

    public Map<String, Object> exportarReporteProvisionesComprobanteContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String numero, String tipo, List<RhListaProvisionesTO> listado) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoIngresosTabulado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String sector, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoRoles(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String categoria, String empleado, List<RhListaConsolidadoRolesTO> listado, boolean excluirLiquidacion) throws Exception;

    public Map<String, Object> exportarReporteSaldoConsolidadoAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoConsolidadoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoConsolidadoPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String hasta, List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldosIndividualAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String tituloReporte, List<RhListaSaldoIndividualAnticiposPrestamosTO> listado) throws Exception;

    public Map<String, Object> exportarExcelDetalleVacacionesPagadasGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String tipo, String fechaDesde, String fechaHasta,
            List<RhDetalleVacionesPagadasGozadasTO> listadoDetalleVacionesPagadasGozadas) throws Exception;

    public Map<String, Object> exportarReporteProvisionPorFechas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String titulo, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarRhAnticipoMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhAnticipoMotivo> listado) throws Exception;

    public Map<String, Object> exportarReporteXivSueldoConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunXivSueldoConsultarTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCalcularUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunUtilidadesCalcularTO> listado) throws Exception;

    public Map<String, Object> exportarRhListaFormaPagoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaFormaPagoTO> listado) throws Exception;

    public Map<String, Object> exportarRhListaFormaPagoBeneficioSocialTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaFormaPagoBeneficioSocialTO> listado) throws Exception;

    //nuevo parametro para exportar
    public Map<String, Object> exportarRhEmpleado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhEmpleado> listado, List<DetalleExportarFiltrado> listadoFiltrado, boolean filtrarPorGrupo) throws Exception;

    public Map<String, Object> exportarRhFunListadoEmpleadosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunListadoEmpleadosTO> listado, List<DetalleExportarFiltrado> listadoFiltrado, boolean filtrarPorGrupo) throws Exception;

    public Map<String, Object> exportarRhCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhCategoriaTO> listado) throws Exception;

    public Map<String, Object> generarReporteAnticipoDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteAnticipoDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposLoteTO> listado, String tipoContable, String numeroContable, String periodo) throws Exception;

    public Map<String, Object> generarReportePrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetallePrestamosTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteDetalleDeAnticipoPrestamo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleAnticiposPrestamosTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteDetalleDeBono(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleBonosTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteConsolidadosDeIngresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhFunFormulario107_2013TO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteConsolidadosAnticiposPrestamos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoAnticiposPrestamosTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteSaldosConsolidadosDeBonos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaSaldoConsolidadoBonosTO> listado, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteBonoDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleBonosLoteTO> listado) throws Exception;

    public Map<String, Object> exportarReporteRhXiiiSueldoXiiiSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXiiiSueldoXiiiSueldoCalcular> lista) throws Exception;

    public Map<String, Object> exportarReporteRhXivSueldoXivSueldoCalcular(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhXivSueldoXivSueldoCalcular> lista) throws Exception;

    public ReporteAnticipoPrestamoXIIISueldo convertirRhFunXiiiSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(RhFunXiiiSueldoConsultarTO item, String fechaMaxima);

    public ReporteAnticipoPrestamoXIIISueldo convertirRhFunXivSueldoConsultarTOAReporteAnticipoPrestamoXIIISueldo(RhFunXivSueldoConsultarTO item, String fechaMaxima);

    public Map<String, Object> exportarRhUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhUtilidades> listaRhUtilidades) throws Exception;

    public Map<String, Object> exportarValoresCalculadosRol(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemListaRolTO> lista) throws Exception;

    public byte[] generarReporteComprobanteRolLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String empresa, List<RhListaDetalleRolesTO> listaDetalleRolesTO) throws Exception;

    public Map<String, Object> generarReporteOrdenBanco(RhOrdenBancariaTO ordenBancaria, String sector, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO usuario, boolean cartera, Map<String, Object> parametros, boolean sinCuenta) throws Exception;

    //ordenesBancarias
    public void generarArchivoOrdenBanco(RhOrdenBancariaTO ordenBancaria, String sector, HttpServletResponse response, SisInfoTO usuario, boolean esCartera, Map<String, Object> parametros, boolean sinCuenta) throws Exception;

    public byte[] generarReporteComprobanteColectivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemStatusItemListaConContableTO> listado) throws Exception;

    //reportSoporte
    public byte[] generarReporteSoporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ItemStatusItemListaConContableTO> listado) throws Exception;

    public byte[] generarListaConContableReporteRRHH(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHIndividualLiquidacion(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialXIV(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialXIII(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialAnticipo(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialPrestamo(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialRolDePagos(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialUtilidades(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteRRHHMatricialLiquidacion(List<ItemStatusItemListaConContableTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public void exportarReporteTXTConsolidadosDeIngresos(List<RhFunFormulario107_2013TO> listaConsolidadoIngresos, HttpServletResponse response, String fechaHasta) throws Exception;

    public Map<String, Object> exportarPlantilla(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRol> lista) throws Exception;

    public byte[] generarCertificadoTrabajo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhEmpleado> listado, String gerente) throws Exception;

    public byte[] generarReporteMotivoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasMotivo> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoHorasExtrass(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasMotivo> listado) throws Exception;

    public byte[] generarReporteConceptoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasConcepto> listado) throws Exception;

    public Map<String, Object> exportarReporteConceptoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhHorasExtrasConcepto> listado) throws Exception;

    //expotar parametros configuracion
    public Map<String, Object> exportarReporteParametros(List<RhParametros> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO);

    public byte[] generarReporteDetalleHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String empCategoria, String nombreEmpleado, List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO) throws Exception;

    public Map<String, Object> exportarReporteDetalleDeHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleHorasExtrasTO> listaDetalleHorasExtrasTO, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteHorasExtrasDetalleLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaDetalleHorasExtrasLoteTO> listaSoporteContableHorasExtrasTO) throws Exception;

    public byte[] generarReporteDetalleHorasExtrasLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String tipo, String numero, List<RhListaDetalleHorasExtrasLoteTO> listaDetalleHorasExtrasLoteTO, String nombre) throws Exception;

    public byte[] generarReporteSaldoConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta, List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras) throws Exception;

    public Map<String, Object> exportarReporteSaldoConsolidadoHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaSaldoConsolidadoHorasExtrasTO> listaSaldoConsolidadoHorasExtras, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteUtilidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhFunUtilidadesConsultarTO> listado) throws Exception;

    public Map<String, Object> exportarVacacionesGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhVacacionesGozadas> listado) throws Exception;

    public Map<String, Object> exportarVacaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhVacaciones> listado) throws Exception;

    public byte[] generarReporteListaVacacionesGozadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde, String hasta, String empSector, List<RhVacacionesGozadas> lista, String nombre) throws Exception;

    public byte[] generarReporteListaVacacionesGozadasIndividual(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhVacacionesGozadas> lista, String nombre) throws Exception;

    public Map<String, Object> listarSoporteBeneficios(String empresa, String periodo, String tipo, String numero) throws Exception;

    public Map<String, Object> exportarReporteProvisionDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String titulo, List<RhProvisionDetalladoTO> datos) throws Exception;

    public byte[] generarReporteConsolidadoBonosHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosViaticosTO) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoBonosHorasExtras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhListaConsolidadoBonosHorasExtrasTO> listaConsolidadoBonosTO, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolEmpTO> listado) throws Exception;

    public byte[] generarReporteListadoLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<RhRolEmpTO> listado) throws Exception;

    public Map<String, Object> exportarRolNotificaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RhRolPagoNotificaciones> lista, String fechaDesde, String fechaHasta) throws Exception;

}
