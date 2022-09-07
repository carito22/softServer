/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoContratoServiceImpl implements TipoContratoService {

    @Autowired
    private SucesoDao sucesoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericoDao<InvClienteContratoTipo, InvClienteContratoTipoPK> invTipoContratoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public InvClienteContratoTipo obtenerInvTipoContrato(String empresa, String codigo) throws Exception {
        InvClienteContratoTipo tipo = invTipoContratoDao.obtener(InvClienteContratoTipo.class, new InvClienteContratoTipoPK(codigo, empresa));
        return tipo;
    }

    @Override
    public InvClienteContratoTipo insertarInvTipoContrato(InvClienteContratoTipo invTipoContrato, SisInfoTO sisInfoTO) throws Exception {
        if (invTipoContratoDao.obtener(InvClienteContratoTipo.class, invTipoContrato.getInvClienteContratoTipoPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }

        susClave = invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa();
        susDetalle = "Se insertó registro con el código" + invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa();
        susSuceso = "INSERT";
        susTabla = "inventario.inv_cliente_contrato_tipo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invTipoContrato.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invTipoContrato = invTipoContratoDao.insertar(invTipoContrato);
        sucesoDao.insertar(sisSuceso);

        return invTipoContrato;
    }

    @Override
    public List<InvClienteContratoTipo> getListarInvTipoContrato(String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente_contrato_tipo WHERE cli_empresa = '" + empresa + "';";
        List<InvClienteContratoTipo> i = genericSQLDao.obtenerPorSql(sql, InvClienteContratoTipo.class);
        return i;
    }

    @Override
    public InvClienteContratoTipo modificarInvTipoContrato(InvClienteContratoTipo invTipoContrato, SisInfoTO sisInfoTO) throws Exception {
        susDetalle = "El registro con el código " + invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa() + ", se ha modificado correctamente";
        susClave = invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa();
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_cliente_contrato_tipo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invTipoContrato.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invTipoContrato = invTipoContratoDao.actualizar(invTipoContrato);
        sucesoDao.insertar(sisSuceso);
        return invTipoContrato;
    }

    @Override
    public boolean eliminarInvTipoContrato(InvClienteContratoTipoPK pk, SisInfoTO sisInfoTO) throws Exception {
        InvClienteContratoTipo invTipoContrato = invTipoContratoDao.obtener(InvClienteContratoTipo.class, pk);
        if (invTipoContrato != null) {
            susClave = invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa();
            susDetalle = "Se eliminó el registro  " + invTipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + " - " + invTipoContrato.getInvClienteContratoTipoPK().getCliEmpresa();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_cliente_contrato_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invTipoContratoDao.eliminar(invTipoContrato);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El tipo contrato ya no esta disponible.", "No se puede crear existente");
        }
    }

}
