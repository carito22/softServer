package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;


import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErroresNotificaciones;

@Repository
public class VerificacionNotificacionesDaoImpl extends GenericDaoImpl<ConVerificacionErroresNotificaciones, Integer> implements VerificacionNotificacionesDao {

}
