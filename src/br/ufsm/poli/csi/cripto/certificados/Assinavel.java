package br.ufsm.poli.csi.cripto.certificados;

import java.io.Serializable;

/**
 * Interface que marca um objeto que admite ser assinado.
 */
public interface Assinavel extends Serializable {

    byte[] getAssinatura();
    void setAssinatura(byte[] assinatura);

}
