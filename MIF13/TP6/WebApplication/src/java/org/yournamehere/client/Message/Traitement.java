package org.yournamehere.client.Message;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 */
@RemoteServiceRelativePath("message/traitement")
public interface Traitement extends RemoteService {
    public String myMethod(String s);
}
