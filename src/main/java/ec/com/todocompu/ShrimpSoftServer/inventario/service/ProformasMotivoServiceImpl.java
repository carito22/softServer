package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProformasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ProformasMotivoServiceImpl implements ProformasMotivoService {

    @Autowired
    private ProformasMotivoDao proformasMotivoDao;
    @Autowired
    private GenericoDao<InvProformasMotivo, InvProformasMotivoPK> proformaMotivoDaoCriteria;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        return proformasMotivoDao.getListaProformaMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public InvProformaMotivoTO getInvProformasMotivoTO(String empresa, String pmCodigo) throws Exception {
        return proformasMotivoDao.getInvProformasMotivoTO(empresa, pmCodigo);
    }

    @Override
    public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(String empresa) throws Exception {
        return proformasMotivoDao.getListaInvProformaMotivoTablaTO(empresa);
    }

    @Override
    public String insertarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (proformasMotivoDao.getInvProformasMotivo(invProformaMotivoTO.getpmEmpresa(),
                invProformaMotivoTO.getpmCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invProformaMotivoTO.getpmCodigo();
            susDetalle = "El motivo de proforma: Detalle " + invProformaMotivoTO.getpmDetalle() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
            susTabla = "inventario.inv_proformas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invProformaMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvProformasMotivo invProformasMotivo = ConversionesInventario.convertirInvProformasMotivoTO_InvProformasMotivo(invProformaMotivoTO);
            if (proformasMotivoDao.insertarInvProformaMotivo(invProformasMotivo, sisSuceso)) {
                retorno = "TEl motivo de proforma: Código " + invProformaMotivoTO.getpmCodigo() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de proforma...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de proforma que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        InvProformasMotivo invProformasMotivoAux = proformasMotivoDao
                .getInvProformasMotivo(invProformaMotivoTO.getpmEmpresa(), invProformaMotivoTO.getpmCodigo());
        if (invProformasMotivoAux != null) {
            susClave = invProformaMotivoTO.getpmCodigo();
            susDetalle = "El motivo de proforma: Detalle " + invProformaMotivoTO.getpmDetalle() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_proformas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invProformaMotivoTO.setUsrCodigo(invProformasMotivoAux.getUsrCodigo());
            invProformaMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(invProformasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            InvProformasMotivo invProformasMotivo = ConversionesInventario
                    .convertirInvProformasMotivoTO_InvProformasMotivo(invProformaMotivoTO);

            if (proformasMotivoDao.modificarInvProformaMotivo(invProformasMotivo, sisSuceso)) {
                retorno = "TEl motivo de proforma: Código " + invProformaMotivoTO.getpmCodigo() + ", se ha modificado correctamente.";
            } else {
                retorno = "Hubo un error al modificar el motivo de la proforma...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de proforma que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        boolean seguir = proformasMotivoDao.retornoContadoEliminarProformasMotivo(invProformaMotivoTO.getpmEmpresa(),
                invProformaMotivoTO.getpmCodigo());
        String retorno = "";
        if (seguir) {
            InvProformasMotivo invProformasMotivoAux = proformasMotivoDao
                    .getInvProformasMotivo(invProformaMotivoTO.getpmEmpresa(), invProformaMotivoTO.getpmCodigo());
            if (invProformasMotivoAux != null) {
                susClave = invProformaMotivoTO.getpmCodigo();
                susDetalle = "El motivo de proforma: Detalle " + invProformaMotivoTO.getpmDetalle() + ", se ha eliminado correctamente";
                susSuceso = "DELETE";
                susTabla = "inventario.inv_proformas_motivo";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProformaMotivoTO.setUsrCodigo(invProformasMotivoAux.getUsrCodigo());
                invProformaMotivoTO.setUsrFechaInserta(
                        UtilsValidacion.fecha(invProformasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

                InvProformasMotivo invProformasMotivo = ConversionesInventario
                        .convertirInvProformasMotivoTO_InvProformasMotivo(invProformaMotivoTO);

                if (proformasMotivoDao.eliminarInvProformaMotivo(invProformasMotivo, sisSuceso)) {
                    retorno = "TEl motivo de proforma: Código " + invProformaMotivoTO.getpmCodigo() + ", se ha eliminado correctamente.";
                } else {
                    retorno = "Hubo un error al ó el motivo de la proforma...\nIntente de nuevo o contacte con el administrador";
                }
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    /*Listado de proformaTO*/
    @Override
    public List<InvProformaMotivoTO> getListaInvProformaMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception {
        return proformasMotivoDao.getListaInvProformaMotivoTO(empresa, filtrarInactivos, busqueda);
    }

    @Override
    public boolean modificarEstadoInvProformaMotivo(InvProformasMotivoPK invProformasMotivoPK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException {
        InvProformasMotivo invProformaMotivo = proformaMotivoDaoCriteria.obtener(InvProformasMotivo.class, invProformasMotivoPK);
        if (invProformaMotivo != null) {
            susClave = invProformaMotivo.getUsrCodigo();
            susDetalle = "El motivo de proforma: Detalle " + invProformaMotivo.getPmDetalle() + (invProformaMotivo.getPmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_proformas_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invProformaMotivo.setPmInactivo(estado);
            proformasMotivoDao.actualizar(invProformaMotivo);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El motivo de proforma ya no esta disponible.");
        }

    }

}
