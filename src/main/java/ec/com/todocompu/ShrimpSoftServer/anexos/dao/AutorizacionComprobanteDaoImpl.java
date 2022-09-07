package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;

@Repository
public class AutorizacionComprobanteDaoImpl extends GenericDaoImpl<AnxAnulados, Integer>
		implements AutorizacionComprobanteDao {

        @Override
	public AnxAnuladosTO getAnxAnuladosTO(Integer secuencia) throws Exception {
		return ConversionesAnexos.convertirAnxAnulados_AnxAnuladosTO(obtenerPorId(AnxAnulados.class, secuencia));
	}

}
