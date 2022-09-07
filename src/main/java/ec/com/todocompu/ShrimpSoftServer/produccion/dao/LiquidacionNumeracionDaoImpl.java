package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracionPK;

@Repository
public class LiquidacionNumeracionDaoImpl extends GenericDaoImpl<PrdLiquidacionNumeracion, PrdLiquidacionNumeracionPK>
		implements LiquidacionNumeracionDao {

}
