package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplementoPK;

@Repository
public class VentasComplementoDaoImpl extends GenericDaoImpl<InvVentasComplemento, InvVentasComplementoPK>
		implements VentasComplementoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public InvVentasComplemento buscarVentasComplemento(String empresa, String periodo, String motivo, String numero)
			throws Exception {
		String sql = "SELECT com_empresa, com_periodo, com_motivo, com_numero, com_documento_tipo, "
				+ "com_documento_numero, com_documento_motivo, vta_empresa, vta_periodo, vta_motivo, vta_numero FROM inventario.inv_ventas_complemento "
				+ "WHERE com_empresa = '" + empresa + "' AND com_periodo = '" + periodo + "' AND com_motivo = '"
				+ motivo + "' AND com_numero = '" + numero + "'";
		return genericSQLDao.obtenerObjetoPorSql(sql, InvVentasComplemento.class);
	}

    @Override
    public InvVentasComplementoTO buscarVentasComplementoTO(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT com_empresa, com_periodo, com_motivo, com_numero, com_documento_tipo, "
				+ "com_documento_numero, com_documento_motivo FROM inventario.inv_ventas_complemento "
				+ "WHERE com_empresa = '" + empresa + "' AND com_periodo = '" + periodo + "' AND com_motivo = '"
				+ motivo + "' AND com_numero = '" + numero + "'";
		return genericSQLDao.obtenerObjetoPorSql(sql, InvVentasComplementoTO.class);
    }
}
