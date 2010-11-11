/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yournamehere.client.Message;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import org.yournamehere.client.ClickBouton;

/**
 * Example class using the Traitement service.
 *
 */
public class TraitementUsage {


    public TraitementUsage(final Button envoyer, final TextBox champsUtilisateur, final Label texteUtilisateur) {

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                texteUtilisateur.setText(result);
            }

            public void onFailure(Throwable caught) {
                texteUtilisateur.setText("Communication failed");
            }
        };

        //action du bouton
        envoyer.addClickHandler(new ClickBouton(){
            @Override
            public void onClick(ClickEvent event) {
                getService().myMethod(champsUtilisateur.getText(), callback);
            }
        });

    }

    public static TraitementAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.
        return GWT.create(Traitement.class);
    }

}
