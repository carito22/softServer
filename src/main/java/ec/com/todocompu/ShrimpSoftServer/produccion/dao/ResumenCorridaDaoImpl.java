package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdResumenCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdResumenCorridaPK;

@Repository
public class ResumenCorridaDaoImpl extends GenericDaoImpl<PrdResumenCorrida, PrdResumenCorridaPK>
		implements ResumenCorridaDao {

}
