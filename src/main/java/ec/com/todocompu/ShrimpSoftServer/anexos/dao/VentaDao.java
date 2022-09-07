package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VentaDao extends GenericDao<AnxVenta, AnxVentaPK> {

    @Transactional
    public AnxVentaTO getAnexoVentaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception;

    public AnxVentaTO getAnexoVentaTO(String empresa, String claveAcceso) throws Exception;

    public AnxVentaElectronica getAnexoVentaElectronicaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception;

    public Boolean eliminarAnxVentas(AnxVenta anxVenta, SisSuceso sisSuceso) throws Exception;

    public Boolean accionAnxVenta(AnxVenta anxVenta, SisSuceso sisSuceso, char accion) throws Exception;

    public String getConteoNumeroRetencionVenta(String empresaCodigo, String numeroRetencion, String cliente)
            throws Exception;

    public boolean comprobarEliminarAnxVentas(String empresa, String ventPeriodo, String ventMotivo, String ventaNumero)
            throws Exception;

    public boolean comprobarAnxVentas(String empresa, String ventPeriodo, String ventMotivo, String ventaNumero)
            throws Exception;

    public List<AnxListaRetencionVentaTO> getAnxListaRetencionVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception;

    public List<AnxEstablecimientoComboTO> getEstablecimientos(String empresa) throws Exception;

    public List<AnxPuntoEmisionComboTO> getPuntosEmision(String empresa, String establecimiento) throws Exception;

    public AnxNumeracionLineaTO getNumeroAutorizacion(String empresa, String numeroRetencion, String numeroComprobante,
            String fechaVencimiento) throws Exception;

    public AnxNumeracion obtenerNumeracionPorTipoYNumeroDocumento(String empresa, String tipoDocumento, String numeroComprobante) throws Exception;

    public String getUltimaNumeracionComprobante(String empresa, String comprobante, String secuencial)
            throws Exception;

    public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(String empresa, String tipoDocumento,
            String establecimiento, String puntoEmision, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxFunListadoDevolucionIvaVentasTO> getAnxFunListadoDevolucionIvaVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public AnxNumeracion obtenerNumeracionPorTipoNumeroDocumentoSector(String empresa, String tipoDocumento, String sector, String numeroComprobante) throws Exception;

    public boolean anularConContableVentas(InvVentasPK pk, SisSuceso sisSuceso) throws Exception;

}
