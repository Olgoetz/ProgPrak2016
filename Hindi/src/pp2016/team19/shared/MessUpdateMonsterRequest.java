package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>The MessUpdateMonsterRequest for the server.</h1>
 * Monsters need to be updated if they move as well as loose health.
 * @author Oliver Goetz, 5961343
 *
 */
public class MessUpdateMonsterRequest extends Message implements Serializable {

	private static final long serialVersionUID = 7145790507327923681L;
	
	
	/**
	 * The constructor of the class.
	 * @author Oliver Goetz, 5961343
	 * @param type the maintype (=2) of the message
	 * @param subType the subtype (=2) of te message
	 */
	public MessUpdateMonsterRequest( int type, int subType) {
		super(type, subType);
	}
}
