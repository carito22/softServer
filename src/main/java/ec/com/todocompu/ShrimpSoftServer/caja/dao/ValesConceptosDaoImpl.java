package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptos;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ValesConceptosDaoImpl extends GenericDaoImpl<CajValesConceptos, CajValesConceptosPK>
		implements ValesConceptosDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

        @Override
	public List<CajValesConceptosComboTO> getCajValesConceptosComboTOs(String empresa) throws Exception {
		String sql = "SELECT conc_codigo, conc_detalle, conc_empresa, conc_inactivo, usr_empresa, usr_codigo, usr_fecha_inserta "
				+ "FROM caja.caj_vales_conceptos WHERE " + "conc_empresa = '" + empresa
				+ "' AND conc_inactivo = FALSE  ORDER BY conc_codigo";
		return genericSQLDao.obtenerPorSql(sql, CajValesConceptosComboTO.class);
	}

        @Override
	public boolean comprobarCajValesConceptos(String empresa, String codigo) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM caja.caj_vales_conceptos " + "WHERE (conc_empresa = '" + empresa
				+ "' AND conc_codigo = '" + codigo + "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

        @Override
	public Boolean accionCajValesConceptos(CajValesConceptos cajValesConceptos, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(cajValesConceptos);
		if (accion == 'M') {
			actualizar(cajValesConceptos);
		}
		if (accion == 'E') {
			eliminar(cajValesConceptos);
		}
		sisSucesoDao.insertar(sisSuceso);
		return true;
	}
}
