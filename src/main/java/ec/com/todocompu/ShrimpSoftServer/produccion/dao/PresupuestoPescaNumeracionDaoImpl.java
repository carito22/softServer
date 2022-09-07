package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaNumeracionPK;

@Repository
public class PresupuestoPescaNumeracionDaoImpl
		extends GenericDaoImpl<PrdPresupuestoPescaNumeracion, PrdPresupuestoPescaNumeracionPK>
		implements PresupuestoPescaNumeracionDao {

}
