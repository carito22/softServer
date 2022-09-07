package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhOrdenBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.Map;

@Transactional
public interface AnticipoService {

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(String empresa, String fecha,
            String cuenta, String tipoPreAviso, String servicio, String sector, boolean sinCuenta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;

    //listSueldosMachala
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> getRhFunPreavisoAnticiposMachala(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;

    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago) throws Exception;

    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago, String parametro) throws Exception;

    public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public MensajeTO insertarModificarRhAnticipo(ConContable conContable, List<RhAnticipo> listaRhAnticipo,
            SisInfoTO sisInfoTO) throws Exception;

    public List<RhAnticipo> getListRhAnticipo(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public Map<String, Object> obtenerDatosParaCrudAnticipos(Map<String, Object> map) throws Exception;

    public MensajeTO insertarRhAnticipo(String observacionesContable, List<RhAnticipo> listaRhAnticipo, String fechaHasta, 
            List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO sisInfoTO) throws Exception;

    public List<ConContableReporteTO> generarConContableReporte(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String periodo, String tipoContable,
            String numeroContable, SisInfoTO usuario) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudOrdenesBancarias(boolean esCartera) throws Exception;

    public RespuestaWebTO generarListaOrden(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario, boolean sinCuenta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> listaOrdenesAnticipoBancoBolivariano(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario, boolean sinCuenta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesPichinchaInternacional(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> listaOrdenesProduccion(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    //ordenesMachala
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> listaOrdenesBancoMachala(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public MensajeTO insertarRhAnticipoEscritorio(ConContable conContable, List<RhAnticipo> listaRhAnticipo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> listaOrdenesBancoGuayaquil(RhOrdenBancariaTO ordenBancaria, String sector, SisInfoTO usuario) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> getRhFunPreavisoAnticiposGuayaquil(String empresa, String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;
}
