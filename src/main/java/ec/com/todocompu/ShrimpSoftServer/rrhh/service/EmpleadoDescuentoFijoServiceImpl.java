package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDescuentosFijosDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;

@Service
public class EmpleadoDescuentoFijoServiceImpl implements EmpleadoDescuentoFijoService {

    @Autowired
    private EmpleadoDescuentosFijosDao empleadoDescuentosFijosDao;

    @Override
    public List<RhEmpleadoDescuentosFijos> getEmpleadoDescuentosFijos(String empresa, String empleado)
            throws Exception {
        return empleadoDescuentosFijosDao.getRhEmpleadoDescuentosFijos(empresa, empleado);
    }

}
