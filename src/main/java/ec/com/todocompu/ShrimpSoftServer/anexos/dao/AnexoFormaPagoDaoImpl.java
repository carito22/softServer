package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;

@Repository
public class AnexoFormaPagoDaoImpl extends GenericDaoImpl<AnxFormaPago, String> implements AnexoFormaPagoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<AnxFormaPagoTO> getAnexoFormaPagoTO(Date fechaFactura) throws Exception {
		String fecha = UtilsDate.fechaFormatoString(fechaFactura, "yyyy-MM-dd");
		String sql = "SELECT fp_codigo, fp_detalle FROM anexo.anx_forma_pago " + "where fp_desde<='" + fecha
				+ "' and coalesce(fp_hasta, '" + fecha + "')>='" + fecha + "' ORDER BY fp_codigo";

		return genericSQLDao.obtenerPorSql(sql, AnxFormaPagoTO.class);
	}

}
