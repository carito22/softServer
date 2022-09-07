package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdProductoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PrdProductoServiceImpl implements PrdProductoService {

    @Autowired
    private PrdProductoDao prdProductoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public PrdLiquidacionProductoTO getPrdLiquidacionProductoTO(String empresa,
            PrdLiquidacionProductoTablaTO prdLiquidacionProductoTablaTO) throws Exception {
        return prdProductoDao.getPrdLiquidacionProductoTO(empresa, prdLiquidacionProductoTablaTO);
    }

    @Override
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa) throws Exception {
        return prdProductoDao.getListaPrdLiquidacionProductoTablaTO(empresa);
    }

    @Override
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa, String codigo)
            throws Exception {
        return prdProductoDao.getListaPrdLiquidacionProductoTablaTO(empresa, codigo);
    }

    @Override
    public String insertarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (prdProductoDao.obtenerPorId(PrdProducto.class, new PrdProductoPK(
                prdLiquidacionProductoTO.getProdEmpresa(), prdLiquidacionProductoTO.getProdCodigo())) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = prdLiquidacionProductoTO.getProdCodigo();
            susDetalle = "Se insertó el Producto de la liquidacion: " + prdLiquidacionProductoTO.getProdDetalle();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_liquidacion_Producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionProductoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

            PrdProducto prdLiquidacionProducto = ConversionesProduccion
                    .convertirPrdLiquidacionProductoTO_PrdLiquidacionProducto(prdLiquidacionProductoTO);

            if (prdProductoDao.insertarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidación: Código " + prdLiquidacionProductoTO.getProdCodigo() + ", se ha guardado correctamente";
            } else {
                retorno = "FHubo un error al guardar el producto de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl producto de la liquidacion que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        PrdProducto prdLiquidacionProductoAux = prdProductoDao.obtenerPorId(PrdProducto.class,
                new PrdProductoPK(prdLiquidacionProductoTO.getProdEmpresa(), prdLiquidacionProductoTO.getProdCodigo()));
        if (prdLiquidacionProductoAux != null) {
            susClave = prdLiquidacionProductoTO.getProdCodigo();
            susDetalle = "Se modificó el Producto de la liquidacion: " + prdLiquidacionProductoTO.getProdDetalle();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_liquidacion_Producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionProductoTO.setUsrCodigo(prdLiquidacionProductoAux.getUsrCodigo());
            prdLiquidacionProductoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdProducto prdLiquidacionProducto = ConversionesProduccion
                    .convertirPrdLiquidacionProductoTO_PrdLiquidacionProducto(prdLiquidacionProductoTO);
            if (prdProductoDao.modificarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidación: Código " + prdLiquidacionProductoTO.getProdCodigo() + ", se ha modificado correctamente";
            } else {
                retorno = "Hubo un error al modificar el producto de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl producto de la liquidacion que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO, boolean estado,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        PrdProducto prdLiquidacionProductoAux = prdProductoDao.obtenerPorId(PrdProducto.class,
                new PrdProductoPK(prdLiquidacionProductoTO.getProdEmpresa(), prdLiquidacionProductoTO.getProdCodigo()));
        if (prdLiquidacionProductoAux != null) {
            susClave = prdLiquidacionProductoTO.getProdCodigo();
            susDetalle = "Se modificó el Producto de la liquidacion: " + prdLiquidacionProductoTO.getProdDetalle();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_liquidacion_Producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionProductoTO.setUsrCodigo(prdLiquidacionProductoAux.getUsrCodigo());
            prdLiquidacionProductoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdProducto prdLiquidacionProducto = ConversionesProduccion
                    .convertirPrdLiquidacionProductoTO_PrdLiquidacionProducto(prdLiquidacionProductoTO);
            prdLiquidacionProducto.setProdInactivo(estado);
            String sms = (estado ? "inactivado" : "activado");
            if (prdProductoDao.modificarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidación: Código " + prdLiquidacionProductoTO.getProdCodigo() + ", se ha " + sms + " correctamente";
            } else {
                retorno = "Hubo un error al modificar el producto de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl producto de la liquidacion que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception {
        boolean seguir = prdProductoDao.banderaEliminarLiquidacionProducto(prdLiquidacionProductoTO.getProdEmpresa(),
                prdLiquidacionProductoTO.getProdCodigo());
        PrdProducto prdLiquidacionProducto = null;
        String retorno = "";
        if (seguir) {
            prdLiquidacionProductoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            prdLiquidacionProducto = prdProductoDao.getPrdLiquidacionProducto(
                    prdLiquidacionProductoTO.getProdEmpresa(), prdLiquidacionProductoTO.getProdCodigo());
            // SUCESO
            susClave = prdLiquidacionProductoTO.getProdEmpresa();
            susTabla = "produccion.prd_liquidacion_producto";
            susDetalle = "Se eliminó el producto de liquidacion: " + prdLiquidacionProductoTO.getProdDetalle()
                    + " con código " + prdLiquidacionProductoTO.getProdCodigo();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionProductoTO.setUsrCodigo(prdLiquidacionProducto.getUsrCodigo());
            prdLiquidacionProductoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionProducto.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            prdLiquidacionProducto = ConversionesProduccion
                    .convertirPrdLiquidacionProductoTO_PrdLiquidacionProducto(prdLiquidacionProductoTO);

            if (prdProductoDao.eliminarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidación: Código " + prdLiquidacionProductoTO.getProdCodigo() + ", se ha eliminado correctamente";
            } else {
                retorno = "FHubo un error al eliminar el producto de liquidacion de pesca...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un producto con movimientos";
        }
        return retorno;
    }

    @Override
    public String insertarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        prdLiquidacionProducto.getPrdLiquidacionProductoPK()
                .setProdCodigo(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo().toUpperCase());

        if (prdProductoDao.obtenerPorId(PrdProducto.class,
                prdLiquidacionProducto.getPrdLiquidacionProductoPK()) != null) {
            retorno = "FEl producto de la liquidacion que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdEmpresa());

            susDetalle = "Se insertó el producto de la liquidacion: " + prdLiquidacionProducto.getProdDetalle();
            susClave = prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo();
            susTabla = "produccion.prd_liquidacion_producto";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdLiquidacionProducto.setProdDetalle(prdLiquidacionProducto.getProdDetalle().toUpperCase());
            prdLiquidacionProducto.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdLiquidacionProducto.setUsrCodigo(sisInfoTO.getUsuario());
            prdLiquidacionProducto.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (prdProductoDao.insertarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidacion se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar el producto de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdEmpresa());

        susDetalle = "Se modificó el producto de la liquidacion: " + prdLiquidacionProducto.getProdDetalle();
        susClave = prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_producto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdLiquidacionProducto.setProdDetalle(prdLiquidacionProducto.getProdDetalle().toUpperCase());

        if (prdProductoDao.modificarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
            retorno = "TEl producto de la liquidacion se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el producto de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (!prdProductoDao.banderaEliminarLiquidacionProducto(
                prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdEmpresa(),
                prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo())) {
            retorno = "FNo se puede eliminar el producto de la liquidacion porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdEmpresa());

            susDetalle = "Se eliminó el producto de la liquidacion: " + prdLiquidacionProducto.getProdDetalle();
            susClave = prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_liquidacion_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (prdProductoDao.eliminarPrdLiquidacionProducto(prdLiquidacionProducto, sisSuceso)) {
                retorno = "TEl producto de la liquidacion se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el producto de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProducto(String empresa) throws Exception {
        return prdProductoDao.getListaPrdLiquidacionProducto(empresa);
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, boolean inactivos) throws Exception {
        return prdProductoDao.getListaPrdLiquidacionProductos(empresa, inactivos);
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, char clase, String tipo) throws Exception {
        return prdProductoDao.getListaPrdLiquidacionProductos(empresa, clase, tipo);
    }

}
