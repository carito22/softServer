package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructura;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class EstructuraServiceImpl implements EstructuraService {

    @Autowired
    private EstructuraDao estructuraDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<ConEstructuraTO> getListaConEstructuraTO(String empresa) throws Exception {
        return estructuraDao.getListaConEstructuraTO(empresa);
    }

    @Override
    public MensajeTO insertarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        susClave = conEstructura.getEmpCodigo();
        susDetalle = "Se insertó la 'ESTRUCTURA' (" + conEstructura.getEmpCodigo() + ", con Grupo1:" + conEstructura.getEstGrupo1()
                + ", con Grupo2:" + conEstructura.getEstGrupo2() + ", con Grupo3:" + conEstructura.getEstGrupo3()
                + ", con Grupo4:" + conEstructura.getEstGrupo4() + ", con Grupo5:" + conEstructura.getEstGrupo5()
                + ", con Grupo6:" + conEstructura.getEstGrupo6() + ")";
        susSuceso = "INSERT";
        susTabla = "contabilidad.con_estructura";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (estructuraDao.insertarConEstructura(conEstructura, sisSuceso)) {
            mensajeTO.setMensaje("T");
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();

        susClave = conEstructura.getEmpCodigo();
        susDetalle = "Se modificó la 'ESTRUCTURA' (" + conEstructura.getEmpCodigo() + ", con Grupo1:" + conEstructura.getEstGrupo1()
                + ", con Grupo2:" + conEstructura.getEstGrupo2() + ", con Grupo3:" + conEstructura.getEstGrupo3()
                + ", con Grupo4:" + conEstructura.getEstGrupo4() + ", con Grupo5:" + conEstructura.getEstGrupo5()
                + ", con Grupo6:" + conEstructura.getEstGrupo6() + ")";
        susSuceso = "UPDATE";
        susTabla = "contabilidad.con_estructura";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (estructuraDao.modificarConEstructura(conEstructura, sisSuceso)) {
            mensajeTO.setMensaje("T");
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO eliminarConEstructura(ConEstructura conEstructura, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        susClave = conEstructura.getEmpCodigo();
        susDetalle = "Se modificó la 'ESTRUCTURA' (" + conEstructura.getEmpCodigo() + ", con Grupo1:" + conEstructura.getEstGrupo1()
                + ", con Grupo2:" + conEstructura.getEstGrupo2() + ", con Grupo3:" + conEstructura.getEstGrupo3()
                + ", con Grupo4:" + conEstructura.getEstGrupo4() + ", con Grupo5:" + conEstructura.getEstGrupo5()
                + ", con Grupo6:" + conEstructura.getEstGrupo6() + ")";
        susSuceso = "DELETE";
        susTabla = "contabilidad.con_estructura";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (estructuraDao.eliminarConEstructura(conEstructura, sisSuceso)) {
            mensajeTO.setMensaje("T");
        }
        return mensajeTO;
    }

}
