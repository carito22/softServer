package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class TipoServiceImpl implements TipoService {

    @Autowired
    private TipoDao tipoDao;

    @Autowired
    private ContableDao contableDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public MensajeTO insertarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class,
                new ConTipoPK(conTipoTO.getEmpCodigo(), conTipoTO.getTipCodigo()));
        if (conTipo == null) {
            susClave = conTipoTO.getTipCodigo();
            susDetalle = "Se insertó un 'TIPO DE CONTABLE' (" + conTipoTO.getTipCodigo() + ", " + conTipoTO.getTipDetalle() + ")";
            susSuceso = "INSERT";
            susTabla = "contabilidad.con_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conTipo = ConversionesContabilidad.convertirConTipoTO_ConTipo(conTipoTO);
            if (tipoDao.insertarConTipo(conTipo, sisSuceso)) {
                mensajeTO.setMensaje("T");
            };
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(conTipoTO.getEmpCodigo().trim(), conTipoTO.getTipCodigo().trim()));
        if (conTipo != null) {
            susClave = conTipoTO.getTipCodigo();
            susDetalle = "Se modificó un 'TIPO DE CONTABLE' " + conTipoTO.getTipCodigo();
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            conTipoTO.setUsrInsertaTipo(conTipo.getUsrCodigo());
            conTipoTO.setFechaInsertaTipo(UtilsValidacion.fecha(conTipo.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            conTipo = ConversionesContabilidad.convertirConTipoTO_ConTipo(conTipoTO);
            if (tipoDao.modificarConTipo(conTipo, sisSuceso)) {
                mensajeTO.setMensaje("T");
            }
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarEstadoConTipo(ConTipoTO conTipoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        ConTipo conTipoLocal = tipoDao.obtenerPorId(ConTipo.class,
                new ConTipoPK(conTipoTO.getEmpCodigo().trim(), conTipoTO.getTipCodigo().trim()));
        if (conTipoLocal != null) {
            susClave = conTipoLocal.getConTipoPK().getTipCodigo();
            susDetalle = "Se cambió el estado del tipo contable: " + conTipoLocal.getTipDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conTipoLocal.setTipInactivo(estado);
            comprobar = tipoDao.modificarConTipo(conTipoLocal, sisSuceso);
            if (comprobar) {
                mensajeTO.setMensaje("T");
            };
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO eliminarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConTipo conTipo = null;
        if (contableDao.buscarConteoNumeracionEliminarTipo(conTipoTO.getEmpCodigo(), conTipoTO.getTipCodigo()) == 0) {
            susClave = conTipoTO.getTipCodigo();
            susDetalle = "Se eliminó un 'TIPO DE CONTABLE' (" + conTipoTO.getTipCodigo() + ", " + conTipoTO.getTipDetalle() + ")";
            susSuceso = "DELETE";
            susTabla = "con_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conTipo = ConversionesContabilidad.convertirConTipoTO_ConTipo(conTipoTO);
            if (tipoDao.eliminarConTipo(conTipo, sisSuceso)) {
                mensajeTO.setMensaje("T");
            };
        } else {
            mensajeTO.setMensaje("FEl tipo contable tiene movimientos");
        }
        return mensajeTO;
    }

    @Override
    public List<ConTipoTO> getListaConTipoTO(String empresa) throws Exception {
        return tipoDao.getListaConTipoTO(empresa);
    }

    @Override
    public List<ConTipoTO> getListaConTipoTOFiltrado(String empresa, String filtro) throws Exception {
        return tipoDao.getListaConTipoTOFiltrado(empresa, filtro);
    }

    @Override
    public ConTipoTO getConTipoTO(String empresa, String tipCodigo) throws Exception {
        return tipoDao.getConTipoTO(empresa, tipCodigo);
    }

    @Override
    public List<ConTipoTO> getListaConTipoRrhhTO(String empresa) throws Exception {
        return tipoDao.getListaConTipoRrhhTO(empresa);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCarteraTO(String empresa) throws Exception {
        return tipoDao.getListaConTipoCarteraTO(empresa);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(String empresa) throws Exception {
        return tipoDao.getListaConTipoCarteraAnticiposTO(empresa);
    }

    @Override
    public List<ConTipo> getListaConTipo(String empresa) throws Exception {
        return tipoDao.getListaConTipo(empresa);
    }

    @Override
    public List<ConTipo> getListaConTipo(String empresa, String referencia) {
        return tipoDao.getListaConTipo(empresa, referencia);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCodigo(String empresa, String codigo) throws Exception {
        return tipoDao.getListaConTipoCodigo(empresa, codigo);
    }

}
