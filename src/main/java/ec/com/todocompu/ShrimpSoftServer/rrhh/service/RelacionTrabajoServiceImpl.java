package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.RelacionTrabajoDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import java.util.List;

@Service
public class RelacionTrabajoServiceImpl implements RelacionTrabajoService {

    @Autowired
    private RelacionTrabajoDao relacionTrabajoDao;

    @Override
    public List<RhRelacionTrabajo> listarRhRelacionTrabajo() throws Exception {
        return relacionTrabajoDao.listaRelacionTrabajo();
    }

}
