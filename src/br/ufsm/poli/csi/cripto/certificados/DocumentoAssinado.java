package br.ufsm.poli.csi.cripto.certificados;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

/**
 * Classe que representa um documento assinado, juntamente com o certificado associado.
 */
public class DocumentoAssinado implements Assinavel {

    private byte[] dadosDocumento;
    private String mimeType;
    private String nomeDocumento;
    private byte[] assinatura;
    private Certificado certificado;

    /**
     * IMPLEMENTAR
     *
     * Verificação de autenticidade deste documento, onde deve ser verificado: autenticidade da assinatura
     * e autenticidade do certificado utilizado.
     * @return booleano que representa a autenticidade ou não deste documento.
     */
    public boolean verificaAutenticidade() {
        final var assinatura = this.assinatura;
        this.setAssinatura(null);

        var validadeAssinatura = false;
        try {
            final var chipherAss= Cipher.getInstance("RSA");
            chipherAss.init(Cipher.DECRYPT_MODE, this.certificado.getChavePublica());
            final var assinaturaDecript = chipherAss.doFinal(assinatura);
            final var hash = Util.getHash(this);

            if (Arrays.equals(assinaturaDecript, hash)) {
                validadeAssinatura = true;
             }


        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {

            throw new RuntimeException(e);
        }
        this.setAssinatura(assinatura);
        return this.certificado.verificaAutenticidade() && validadeAssinatura;
    }

    public byte[] getDadosDocumento() {
        return dadosDocumento;
    }

    public void setDadosDocumento(byte[] dadosDocumento) {
        this.dadosDocumento = dadosDocumento;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public byte[] getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }


}
