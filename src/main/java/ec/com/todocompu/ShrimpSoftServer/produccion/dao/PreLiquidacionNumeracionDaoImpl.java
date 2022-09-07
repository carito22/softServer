package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionNumeracionPK;

@Repository
public class PreLiquidacionNumeracionDaoImpl
		extends GenericDaoImpl<PrdPreLiquidacionNumeracion, PrdPreLiquidacionNumeracionPK>
		implements PreLiquidacionNumeracionDao {

}
