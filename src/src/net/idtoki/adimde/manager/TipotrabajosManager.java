package net.idtoki.adimde.manager;

import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

import net.idtoki.adimde.model.TipotrabajosPeer;

/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Mon Oct 02 10:34:00 CEST 2006]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class TipotrabajosManager
    extends BaseTipotrabajosManager
{


//Borrado de un elemento
	public static boolean borraTipotrabajos(int idBorrar) {

		Criteria c=new Criteria();
		c.add(TipotrabajosPeer.IDTIPOTRABAJO, idBorrar);
		boolean bRet = false;
		try {
			TipotrabajosPeer.doDelete(c);
			bRet = true;
		} catch (TorqueException e) {
			logger.info("No se ha podido borrar en la tabla Tipotrabajos");
			e.printStackTrace();
			bRet = false;
		}
		return bRet;
	}

}


