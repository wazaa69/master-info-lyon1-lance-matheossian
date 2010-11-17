package org.tortue.client.Traitement;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Flux ascendant Client -> Server
 */
@RemoteServiceRelativePath("traitement/gwtservice")
public interface GWTService extends RemoteService {
     public int getId(String s);
}
