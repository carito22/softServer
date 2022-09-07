package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoCierreTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosAntiguoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConAdjuntosContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhSalarioDigno;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ContableDao extends GenericDao<ConContable, ConContablePK> {

    @Transactional
    public boolean modificarConContableVentasMayorizar(ConContable conContable, List<ConDetalle> listaConDetalle,
            List<ConDetalle> listaConDetalleEliminar, InvVentas invVentas, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean anularConContable(ConContablePK conContablePK, SisSuceso sisSuceso, boolean isbloqueado) throws Exception;

    @Transactional
    public boolean anularConContable(ConContablePK conContablePK, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public String eliminarConContable(ConContablePK conContablePK) throws Exception;

    @Transactional
    public ConContablePK buscarConteoUltimaNumeracion(ConContablePK conContablePK) throws Exception;

    @Transactional
    public boolean insertarTransaccionContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, ConNumeracion conNumeracion, RhVacaciones rhVacaciones, RhSalarioDigno rhSalarioDigno,
            CarPagos carPagos, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes,
            List<CarPagosDetalleCompras> carPagosDetalleCompras, List<CarPagosDetalleForma> carPagosDetalleFormas,
            InvCompras invCompras, CarCobros carCobros, List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes,
            List<CarCobrosDetalleVentas> carCobrosDetalleVentas, List<CarCobrosDetalleForma> carCobrosDetalleFormas,
            List<InvVentas> invVentas, CarPagosAnticipos carPagosAnticipos, CarCobrosAnticipos carCobrosAnticipos,
            BanCheque banCheque, SisInfoTO sisInfoTO) throws Exception;

    public boolean insertarModificarIPP(List<ConContable> listConContable, String sql, List<SisSuceso> sisSuceso)
            throws Exception;

    public boolean insertarModificarContabilizarCorrida(List<ConContable> listConContable, String sql, boolean isCostoVenta, List<SisSuceso> sisSuceso) throws Exception;

    public int buscarConteoUltimaNumeracion(String empCodigo, String perCodigo, String tipCodigo) throws Exception;

    public List<ConContableTO> listaContablesIPPPorFecha(String empCodigo, String contipo, String concepto,
            String fechaDesde, String fechaHasta, String conSector) throws Exception;

    public List<ConListaConsultaConsumosTO> getListaInvConsultaConsumosPendientes(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception;

    public int buscarConteoNumeracionEliminarTipo(String empCodigo, String tipCodigo) throws Exception;

    public Boolean swCobrosAnticiposEliminar(String codEmpresa, String periodo, String tipo, String numeroContable)
            throws Exception;

    public List<ConContableTO> getListaConContableTO(String empresa, String perCodigo, String tipCodigo,
            String numContable) throws Exception;

    public List<ConFunBalanceResultadosNecTO> getConEstadoResultadosIntegralTO(String empresa, String sector,
            String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre)
            throws Exception;

    public List<ConBalanceResultadoComparativoTO> getConBalanceResultadoComparativoTO(String empresa, String sector, String fechaDesde,
            String fechaHasta, String fechaDesde2, String fechaHasta2, Boolean ocultarDetalle, Boolean excluirCierre) throws Exception;

    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<ConFunBalanceGeneralNecTO> getConEstadoSituacionFinancieraTO(String empresa, String sector, String fecha,
            Boolean ocultar, Boolean ocultarDetalle) throws Exception;

    public List<ConFunBalanceGeneralNecTO> getFunCuentasSobregiradasTO(String empresa, String secCodigo, String fecha)
            throws Exception;

    public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(String empresa, String secCodigo,
            String fechaAnterior, String fechaActual, Boolean ocultar) throws Exception;

    public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception;

    public List<ConBalanceResultadoTO> getListaBalanceResultadoTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception;

    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(String empresa, String codigoCuentaDesde,
            String codigoCuentaHasta, String fechaInicio, String fechaFin, String sector, boolean estado) throws Exception;

    public List<ConMayorGeneralTO> getListaMayorGeneralTO(String empresa, String codigoSector, String codigoCuenta,
            String fechaFin) throws Exception;

    public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(String empresa, String codigoTipo, String fechaInicio,
            String fechaFin) throws Exception;

    public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(String empresa,
            String desde, String hasta, String sector) throws Exception;

    public List<ConBalanceResultadosMensualizadosTO> getBalanceResultadoMensualizado(String empresa,
            String codigoSector, String fechaInicio, String fechaFin) throws Exception;

    public List<ConBalanceResultadosMensualizadosAntiguoTO> getBalanceResultadoMensualizadoAntiguo(String empresa,
            String codigoSector, String fechaInicio, String fechaFin) throws Exception;

    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(String empresa) throws Exception;

    @Transactional
    public List<ConFunIPPTO> getFunListaIPP(String empresa, String fechaInicio, String fechaFin, String parametro)
            throws Exception;

    @Transactional
    public String getFunListaIPPComplementoValidar(String empresa, String fechaInicio, String fechaFin,
            String parametro) throws Exception;

    @Transactional
    public List<ConFunIPPComplementoTO> getFunListaIPPComplemento(String empresa, String fechaInicio, String fechaFin,
            String parametro) throws Exception;

    public List<ConBalanceResultadoCierreTO> getfun_balance_resultados_cierre(String empresa, String sector,
            String fecha) throws Exception;

    public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(String empresa,
            String fechaInicio, String fechaFin) throws Exception;

    public List<PersonaTO> getFunPersonaTOs(String empresa, String busquedad) throws Exception;

    public List<ConFunContabilizarComprasDetalleTO> getConFunContabilizarComprasDetalle(String empresa, String periodo,
            String motivo, String numeroCompra, String validar) throws Exception;

    public List<ConFunContabilizarVentasDetalleTO> getConFunContabilizarVentasDetalle(String empresa, String vtaPeriodo,
            String vtaMotivo, String vtaNumero, String validar) throws Exception;

    public String buscarContableTrasferencia(String empCodigo, String contipo, String concepto, String fecha,
            String conSector) throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception;

    public List<ListaConContableTO> getListConContableTO(String empresa, String periodo, String tipo, String busqueda,
            String referencia, String nRegistros) throws Exception;

    public boolean insertarModificarContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso) throws Exception;

    public ConContable getConContable(ConContablePK conContablePK);

    public void mayorizarDesmayorizarSql(ConContablePK conContablePK, boolean pendiente, SisSuceso sisSuceso);

    public void anularReversarSql(ConContablePK conContablePK, boolean anularReversar, boolean eliminarReferencia);

    public void restaurarSql(ConContablePK conContablePK);

    public void ejecutarSQL(String sql);

    public List<String> validarChequesConciliados(String empresa, String periodo, String conTipo, String conNumero)
            throws Exception;

    public List<String> validarPagoCobroRefernciaAnticipo(String empresa, String periodo, String conTipo,
            String conNumero, String bandera);

    public int buscarRegistroCotableRRHH(ConContable conContable) throws Exception;
    
    public boolean activarWispro(SisInfoTO sisSuceso, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentas) throws Exception;

    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacion(String empresa, String fechaInicio, String fechaFin) throws Exception;

    public List<ConDetalleTablaTO> obtenerContableVirtualConsumo(String conEmpresa, String conPeriodo, String conTipo, String conNumero) throws Exception;

    public List<ConDetalleTablaTO> obtenerContableVirtualVenta(String conEmpresa, String conPeriodo, String conTipo, String conNumero) throws Exception;

    public List<ConBalanceResultadosVsInventarioDetalladoTO> getConListaBalanceResultadosVsInventarioDetalladoTO(String empresa, String desde,
            String hasta, String sector, boolean estado) throws Exception;

    public boolean modificarRhVacaciones(RhVacaciones rhVacaciones, ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, SisInfoTO sisInfoTO) throws Exception;

    /*IMAGENES*/
    public List<ConAdjuntosContable> getAdjuntosContable(ConContablePK conContablePK);

    public boolean insertarImagen(ConAdjuntosContable conAdjuntosContable);

    public boolean eliminarImagen(ConAdjuntosContable conAdjuntosContable);

    public boolean mayorizarTransaccionContableCartera(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, CarPagos carPagos, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes,
            List<CarPagosDetalleCompras> carPagosDetalleCompras, List<CarPagosDetalleForma> carPagosDetalleFormas,
            CarCobros carCobros, List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes,
            List<CarCobrosDetalleVentas> carCobrosDetalleVentas, List<CarCobrosDetalleForma> carCobrosDetalleFormas,
            CarPagosAnticipos carPagosAnticipos, CarCobrosAnticipos carCobrosAnticipos, SisInfoTO sisInfoTO) throws Exception;

    public List<ConFunBalanceCentroProduccionTO> getConBalanceBalanceCentroProduccionTO(String empresa, String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre) throws Exception;
}
