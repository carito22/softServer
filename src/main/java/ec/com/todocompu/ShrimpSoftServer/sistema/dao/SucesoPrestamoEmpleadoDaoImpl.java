/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPrestamoEmpleado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class SucesoPrestamoEmpleadoDaoImpl extends GenericDaoImpl<SisSucesoPrestamoEmpleado, Integer> implements SucesoPrestamoEmpleadoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisSucesoPrestamoEmpleado> getListaSisSucesoPrestamoEmpleado(Integer suceso) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_suceso_prestamo_empleado WHERE sus_suceso=" + suceso + " ORDER BY sus_secuencial";
        return genericSQLDao.obtenerPorSql(sql, SisSucesoPrestamoEmpleado.class);
    }

}
