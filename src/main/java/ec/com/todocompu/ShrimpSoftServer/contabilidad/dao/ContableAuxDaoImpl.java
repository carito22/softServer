package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;

@Repository
public class ContableAuxDaoImpl extends GenericDaoImpl<ConContable, ConContablePK> implements ContableAuxDao {

}