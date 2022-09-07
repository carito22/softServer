package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.VerificacionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErrores;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class VerificacionServiceImpl implements VerificacionService {

    @Autowired
    private VerificacionDao verificacionDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean insertar(ConVerificacionErrores conTipoTO, SisInfoTO sisInfoTO) throws Exception {
        ConVerificacionErrores conTipo = verificacionDao.obtenerPorId(ConVerificacionErrores.class, conTipoTO.getVeSecuencial());
        if (conTipo == null) {
            susClave = conTipoTO.getVeSecuencial() + "";
            susDetalle = "Se insertó un 'Registro de verificacion de errores' (" + conTipoTO.getVeSecuencial() + ", " + conTipoTO.getVeUrlEvidencias() + ")";
            susSuceso = "INSERT";
            susTabla = "contabilidad.con_verificacion_errores";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            return verificacionDao.insertar(conTipoTO, sisSuceso);
        }
        return false;
    }

    @Override
    public ConVerificacionErrores obtener(int secuencial) throws Exception {
        return verificacionDao.obtener(secuencial);
    }

}
