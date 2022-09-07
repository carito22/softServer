package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErrores;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VerificacionDaoImpl extends GenericDaoImpl<ConVerificacionErrores, Integer> implements VerificacionDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertar(ConVerificacionErrores conTipo, SisSuceso sisSuceso) throws Exception {
        insertar(conTipo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public ConVerificacionErrores obtener(int secuencial) throws Exception {
        return obtenerPorId(ConVerificacionErrores.class, secuencial);
    }
}
