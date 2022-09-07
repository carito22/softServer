package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.Map;

@Service
public class VentasMotivoServiceImpl implements VentasMotivoService {

    @Autowired
    private VentasMotivoDao ventasMotivoDao;
    @Autowired
    private TipoComprobanteDao comprobanteDao;
    @Autowired
    private TipoDao tipoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception {
        return ventasMotivoDao.getListaVentaMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public InvVentaMotivoTO getInvVentasMotivoTO(String empresa, String vmCodigo) throws Exception {
        return ventasMotivoDao.getInvVentasMotivoTO(empresa, vmCodigo);
    }

    @Override
    public List<InvVentaMotivoTablaTO> getListaInvVentasMotivoTablaTO(String empresa) throws Exception {
        return ventasMotivoDao.getListaInvVentasMotivoTablaTO(empresa);
    }

    @Override
    public List<InvVentaMotivoTO> getListaInvVentasMotivoTO(String empresa, boolean soloActivos, String tipoDocumento) throws Exception {
        return ventasMotivoDao.getListaInvVentasMotivoTO(empresa, soloActivos, tipoDocumento);
    }

    @Override
    public String insertarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (ventasMotivoDao.getInvVentasMotivo(invVentaMotivoTO.getVmEmpresa(),
                invVentaMotivoTO.getVmCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invVentaMotivoTO.getTipCodigo();
            susDetalle = "El motivo de venta: Detalle " + invVentaMotivoTO.getVmDetalle() + ", se ha guardado correctamente.";
            susSuceso = "INSERT";
            susTabla = "inventario.inv_ventas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invVentaMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

            InvVentasMotivo invVentasMotivo = ConversionesInventario
                    .convertirInvVentasMotivoTO_InvVentasMotivo(invVentaMotivoTO);

            if (ventasMotivoDao.insertarInvVentasMotivo(invVentasMotivo, sisSuceso)) {
                retorno = "TEl motivo de venta: Código " + invVentaMotivoTO.getVmCodigo() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de venta...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de venta que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvVentasMotivo invVentasMotivoAux = ventasMotivoDao.getInvVentasMotivo(invVentaMotivoTO.getVmEmpresa(),
                invVentaMotivoTO.getVmCodigo());
        if (invVentasMotivoAux != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invVentaMotivoTO.getTipCodigo();
            susDetalle = "El motivo de venta: Detalle " + invVentaMotivoTO.getVmDetalle() + ", se ha modificado correctamente.";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_ventas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invVentaMotivoTO.setUsrCodigo(invVentasMotivoAux.getUsrCodigo());
            invVentaMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(invVentasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            InvVentasMotivo invVentasMotivo = ConversionesInventario
                    .convertirInvVentasMotivoTO_InvVentasMotivo(invVentaMotivoTO);

            if (ventasMotivoDao.modificarInvVentasMotivo(invVentasMotivo, sisSuceso)) {
                retorno = "TEl motivo de venta: Código " + invVentaMotivoTO.getVmCodigo() + ", se ha modificado correctamente.";
            } else {
                retorno = "FHubo un error al modificar el motivo de venta...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de venta que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoInvVentaMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvVentasMotivo invVentasMotivoAux = ventasMotivoDao.getInvVentasMotivo(invVentaMotivoTO.getVmEmpresa(),
                invVentaMotivoTO.getVmCodigo());
        if (invVentasMotivoAux != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invVentaMotivoTO.getTipCodigo();
            susDetalle = "El motivo de venta: Detalle " + invVentaMotivoTO.getVmDetalle() + (invVentaMotivoTO.getVmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_ventas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invVentaMotivoTO.setUsrCodigo(invVentasMotivoAux.getUsrCodigo());
            invVentaMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(invVentasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            InvVentasMotivo invVentasMotivo = ConversionesInventario
                    .convertirInvVentasMotivoTO_InvVentasMotivo(invVentaMotivoTO);

            if (ventasMotivoDao.modificarInvVentasMotivo(invVentasMotivo, sisSuceso)) {
                retorno = "TEl motivo de venta: Código " + invVentaMotivoTO.getVmCodigo() + (invVentaMotivoTO.getVmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            } else {
                retorno = "FHubo un error al modificar el motivo de venta...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de venta que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        boolean seguir = ventasMotivoDao.retornoContadoEliminarVentasMotivo(invVentaMotivoTO.getVmEmpresa(),
                invVentaMotivoTO.getVmCodigo());
        InvVentasMotivo invVentasMotivo = null;
        String retorno = "";
        if (seguir) {
            invVentaMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvVentasMotivo invVentasMotivoAux = ventasMotivoDao.getInvVentasMotivo(invVentaMotivoTO.getVmEmpresa(),
                    invVentaMotivoTO.getVmCodigo());
            // SUCESO
            susClave = invVentaMotivoTO.getTipCodigo();
            susTabla = "inventario.inv_compras_motivo";
            susDetalle = "El motivo de venta: Detalle " + invVentaMotivoTO.getVmDetalle() + ", se ha eliminado correctamente.";
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invVentaMotivoTO.setUsrCodigo(invVentasMotivoAux.getUsrCodigo());
            invVentaMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(invVentasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            invVentasMotivo = ConversionesInventario.convertirInvVentasMotivoTO_InvVentasMotivo(invVentaMotivoTO);

            if (ventasMotivoDao.eliminarInvVentasMotivo(invVentasMotivo, sisSuceso)) {
                retorno = "TEl motivo de venta: Código " + invVentaMotivoTO.getVmCodigo() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de venta...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudMotivoVentas(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxTipoComprobanteTO> listaDocumentos = comprobanteDao.getAnexoTipoComprobanteTO();
        List<ConTipoTO> listaTipos = tipoDao.getListaConTipoTO(empresa);
        campos.put("listaDocumentos", listaDocumentos);
        campos.put("listaTipos", listaTipos);
        return campos;
    }

}
