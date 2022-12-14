package nl.tudelft.sem.template.user.domain.exceptions;

import nl.tudelft.sem.template.user.domain.NetId;

/**
 * Exception to indicate the NetID is already in use.
 */
public class NetIdAlreadyInUseException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    public NetIdAlreadyInUseException(NetId netId) {
        super(netId.toString());
    }
}
