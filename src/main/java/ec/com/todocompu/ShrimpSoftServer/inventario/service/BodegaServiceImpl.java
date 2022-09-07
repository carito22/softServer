package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class BodegaServiceImpl implements BodegaService {

    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            if (!bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo(),
                    invBodegaTO.getBodNombre())) {
                susClave = invBodegaTO.getBodCodigo();
                susDetalle = "Se ingresó la bodega" + invBodegaTO.getBodNombre() + " con código "
                        + invBodegaTO.getBodCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_bodega";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invBodegaTO.setUsrFechaInsertaBodega(UtilsValidacion.fechaSistema());
                InvBodega invBodega = ConversionesInventario.convertirInvBodegaTO_InvBodega(invBodegaTO);

                if (bodegaDao.insertarInvBodega(invBodega, sisSuceso)) {
                    retorno = "TBodega ingresada correctamente...";
                } else {
                    retorno = "FHubo un error al ingresar la Bodega...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FEl nombre de la bodega que va a ingresar ya existe...\nIntente con otro...";
            }

        } else {
            retorno = "FEl código de la bodega que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoInvBodegaTO(InvBodegaTO invBodegaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvBodega invBodegaAux = bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo());
        if (invBodegaAux != null) {
            susClave = invBodegaTO.getBodCodigo();
            susDetalle = "Se cambió el estado de la bodega: " + invBodegaTO.getBodNombre() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_bodega";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invBodegaAux.setBodInactiva(estado);
            InvBodega invBodega = ConversionesInventario.convertirInvBodegaTO_InvBodega(invBodegaTO);
            if (bodegaDao.modificarInvBodega(invBodega, sisSuceso)) {
                retorno = "TLa bodega se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar la bodega...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl código del bodega que va a modificar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvBodega invBodegaAux = bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo());
        if (invBodegaAux != null) {
            if (!bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo(),
                    invBodegaTO.getBodNombre())) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invBodegaTO.getBodCodigo();
                susDetalle = "Se modificó la bodega " + invBodegaTO.getBodNombre() + " con código "
                        + invBodegaTO.getBodCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_bodega";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invBodegaTO.setUsrInsertaBodega(invBodegaAux.getUsrCodigo());
                invBodegaTO.setUsrFechaInsertaBodega(
                        UtilsValidacion.fecha(invBodegaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvBodega invBodega = ConversionesInventario.convertirInvBodegaTO_InvBodega(invBodegaTO);
                if (bodegaDao.modificarInvBodega(invBodega, sisSuceso)) {
                    retorno = "TLa bodega se modificó correctamente...";
                } else {
                    retorno = "FHubo un error al modificar la bodega...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FEl nombre de la bodega que va a modificar ya existe...\nIntente con otro...";
            }

        } else {
            retorno = "FEl código del bodega que va a modificar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        Boolean puedeEliminar = false;
        puedeEliminar = bodegaDao.getPuedeEliminarBodega(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo());
        if (puedeEliminar) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invBodegaTO.getBodCodigo();
            susDetalle = "Se eliminó la bodega " + invBodegaTO.getBodNombre() + " con código "
                    + invBodegaTO.getBodCodigo();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_bodega";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            InvBodega invBodega = ConversionesInventario.convertirInvBodegaTO_InvBodega(invBodegaTO);
            InvBodega invBodegaAux = bodegaDao.buscarInvBodega(invBodegaTO.getEmpCodigo(),
                    invBodegaTO.getBodCodigo());

            invBodegaTO.setUsrInsertaBodega(invBodegaAux.getUsrCodigo());
            invBodegaTO.setUsrFechaInsertaBodega(
                    UtilsValidacion.fecha(invBodegaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            if (bodegaDao.eliminarInvBodega(invBodega, sisSuceso)) {
                retorno = "TLa bodega se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar esta bodega...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar esta bodega debido a que tiene movimientos en Inventarios.\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public List<InvListaBodegasTO> getListaBodegasTO(String empresa, boolean inacivo) throws Exception {
        return bodegaDao.getListaBodegasTO(empresa, inacivo);
    }

    @Override
    public List<InvBodega> getListaBodegas(String empresa, boolean inacivo) throws Exception {
        return bodegaDao.getListaBodegas(empresa, inacivo);
    }

    @Override
    public List<InvComboBodegaTO> getInvComboBodegaTO(String empresa) throws Exception {
        return bodegaDao.getInvComboBodegaTO(empresa);
    }

    @Override
    public List<InvBodegaTO> getBodegaTO(String empresa, String codigo) throws Exception {
        return bodegaDao.getBodegaTO(empresa, codigo);
    }

    @Override
    public List<SaldoBodegaTO> getListaSaldoBodegaTO(String empresa, String bodega, String hasta, String categoria) throws Exception {
        return bodegaDao.getListaSaldoBodegaTO(empresa, bodega, hasta, categoria);
    }

    @Override
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(String empresa, String bodega,
            String desde, String hasta) throws Exception {
        return bodegaDao.getInvFunSaldoBodegaComprobacionTO(empresa, bodega, desde, hasta);
    }

    @Override
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadesTO(String empresa, String bodega,
            String desde, String hasta) throws Exception {
        return bodegaDao.getInvFunSaldoBodegaComprobacionCantidadesTO(empresa, bodega, desde, hasta);
    }

    @Override
    public List<InvListaBodegasTO> buscarBodegasTO(String empresa, boolean inactivo, String busqueda) throws Exception {
        return bodegaDao.buscarBodegasTO(empresa, inactivo, busqueda);
    }

    @Override
    public List<InvListaBodegasTO> buscarBodegasPorSector(String empresa, boolean inactivo, String sector) throws Exception {
        String existeSector = sector!=null ? "AND sec_codigo = '" + sector + "'" : "";
        String sql = "SELECT " + "bod_codigo, " + "bod_nombre, "
                + "sis_usuario.usr_apellido ||' '||sis_usuario.usr_nombre bod_responsable, " + "bod_inactiva, "
                + "sec_codigo " + "FROM inventario.inv_bodega LEFT JOIN sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_usuario "
                + "ON sis_usuario_detalle.usr_codigo = sis_usuario.usr_codigo "
                + "ON inv_bodega.det_empresa = sis_usuario_detalle.det_empresa  AND "
                + "sis_usuario_detalle.det_usuario = inv_bodega.det_usuario  " + "WHERE (bod_empresa = '" + empresa + "' " + existeSector + ")";
        return genericSQLDao.obtenerPorSql(sql, InvListaBodegasTO.class);
    }

    @Override
    public InvBodega buscarInvBodega(String empresa, String codigoBodega) throws Exception {
        return bodegaDao.buscarInvBodega(empresa, codigoBodega);
    }

}
