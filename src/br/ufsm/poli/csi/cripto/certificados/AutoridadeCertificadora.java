package br.ufsm.poli.csi.cripto.certificados;

import java.io.Serializable;

/**
 * Interface que representa uma Autoridade de Certificação digital,
 * bem como todos os serviços que esta deverá prover.
 */
public interface AutoridadeCertificadora  {

    /**
     * Assina o certificado recebido por parâmetro.
     *
     * @param certificado
     */
    void assinaCertificado(Certificado certificado);

    /**
     * Verifica se o certificado recebido por parâmetro está revogado.
     * @param certificado
     * @return true se estiver revogado, false caso contrário.
     */
    boolean estaRevogado(Certificado certificado);

    /**
     * Revoga o certificado recebido por parâmetro.
     * @param certificado
     */
    void revogaCertificado(Certificado certificado);

    /**
     * Permite obter o certificado desta autoridade.
     * @return
     */
    Certificado getCertificado();

}
