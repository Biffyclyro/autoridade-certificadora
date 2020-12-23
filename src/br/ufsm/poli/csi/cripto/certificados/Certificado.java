package br.ufsm.poli.csi.cripto.certificados;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

/**
 * Classe que representa um certificado público
 */
public class Certificado implements Assinavel {

    public enum TipoCertificado { USUARIO_FINAL, CA, CA_RAIZ }

    private PublicKey chavePublica;
    private String nome;
    private String cpfCnpj;
    private String email;
    private String hostname;
    private Date validade;
    private TipoCertificado tipoCertificado;
    private Certificado certificadoPor;
    private byte[] assinatura;
    private AutoridadeCertificadora autoridadeCertificadora;

    /**
     * Constrói um certificado a partir dos dados passados por parâmetro.
     *
     * @param chavePublica
     * @param nome
     * @param cpfCnpj
     * @param email
     * @param hostname
     * @param tipoCertificado
     */
    public Certificado(PublicKey chavePublica,
                       String nome,
                       String cpfCnpj,
                       String email,
                       String hostname,
                       TipoCertificado tipoCertificado)
    {
        this.chavePublica = chavePublica;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.hostname = hostname;
        this.tipoCertificado = tipoCertificado;
    }

    /**
     * IMPLEMENTAR
     * Verificação de autenticidade deste certificado, onde deve ser verificado: autenticidade da assinatura
     * por parte de quem certificou (CA), data de validade e ausência deste na lista de certificados revogados
     * da CA. Além disto deve verificar também a autenticidade de todos os certificados na cadeia de
     * certificação.
     * @return booleano que representa a autenticidade ou não deste certificado.
     */
    public boolean verificaAutenticidade() {

        if(!this.validade.after(new Date())
                && !this.autoridadeCertificadora.estaRevogado(this.certificadoPor) ) {

            final var assinatura = this.assinatura;
            this.setAssinatura(null);
            final var hash = Util.getHash(this);

            try {
                final var cipherAssinatura = Cipher.getInstance("RSA");
                cipherAssinatura.init(Cipher.DECRYPT_MODE, this.getChavePublica());
                final var assinaturaDecrypt = cipherAssinatura.doFinal(assinatura);
                this.setAssinatura(assinatura);

                return Arrays.equals(hash,assinaturaDecrypt) && this.certificadoPor.verificaAutenticidade();

            } catch (NoSuchPaddingException
                    | NoSuchAlgorithmException
                    | InvalidKeyException
                    | IllegalBlockSizeException
                    | BadPaddingException e) {
                throw new RuntimeException(e);
            }


        }



        return false;
    }

    public PublicKey getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(PublicKey chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public TipoCertificado getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(TipoCertificado tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public Certificado getCertificadoPor() {
        return certificadoPor;
    }

    public void setCertificadoPor(Certificado certificadoPor) {
        this.certificadoPor = certificadoPor;
    }

    @Override
    public byte[] getAssinatura() {
        return assinatura;
    }

    @Override
    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public AutoridadeCertificadora getAutoridadeCertificadora() {
        return autoridadeCertificadora;
    }

    public void setAutoridadeCertificadora(AutoridadeCertificadora autoridadeCertificadora) {
        this.autoridadeCertificadora = autoridadeCertificadora;
    }
}
