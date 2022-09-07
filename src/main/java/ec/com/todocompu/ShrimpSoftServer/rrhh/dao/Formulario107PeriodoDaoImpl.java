/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107Periodo;

@Repository
public class Formulario107PeriodoDaoImpl extends GenericDaoImpl<RhFormulario107Periodo, String>
		implements Formulario107PeriodoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO() throws Exception {
		String sql = "SELECT * FROM recursoshumanos.rh_formulario_107_periodo";

		return genericSQLDao.obtenerPorSql(sql, RhFormulario107PeriodoFiscalTO.class);
	}
}
