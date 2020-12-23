package br.ufsm.poli.csi.cripto.certificados;

import java.io.*;
import java.lang.reflect.Field;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/*
    Classe que permite assinar documentos e armazenar/obter a chave privada.
 */
public class CertificadoPrivado {

    private final PrivateKey chavePrivada;
    private final Certificado certificado;

    /**
     * IMPLEMENTAR
     * Encontra e recupera um certificado completo (privado e público)
     * a partir de um arquivo do sistema de arquivos.
     * @param nomeArquivo nome do arquivo
     * @return certificado completo (privado e público)
     */
    public static CertificadoPrivado findCertificado(String nomeArquivo) {

        try(
                final var fileIn = new FileInputStream(nomeArquivo);
                final var objIn = new ObjectInputStream(new BufferedInputStream(fileIn));
        ) {

            return (CertificadoPrivado) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Cria um certificado completo novo (público e privado) a partir dos dados passados.
        Este certificado não será válido pois precisa receber a assinatura de uma autoridade
        certificadora.
     */
    public CertificadoPrivado(String nome,
                              String cpfCnpj,
                              String email,
                              String hostname,
                              Certificado.TipoCertificado tipoCertificado) throws NoSuchAlgorithmException
    {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        this.certificado = new Certificado(kp.getPublic(), nome, cpfCnpj, email, hostname, tipoCertificado);
        this.chavePrivada = kp.getPrivate();
    }
     public void save() {
        try (
                final var fileOut = new FileOutputStream(this.certificado.getNome());
                final var objOut = new ObjectOutputStream(new BufferedOutputStream(fileOut))
        ) {
            objOut.writeObject(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

    /**
     * IMPLEMENTAR
     *
     * Cria um documento assinado com base nos dados recebidos.
     *
     * @param dados dados do documento
     * @param nomeArquivo nome do arquivo deste documento
     * @param mimeType tipificação do arquivo (RFC 2046)
     * @return o documento assinado
     */

    public DocumentoAssinado assinaDocumento(byte[] dados, String nomeArquivo, String mimeType) {
        return null;
    }

    public PrivateKey getChavePrivada() {
        return chavePrivada;
    }

    public Certificado getCertificado() {
        return certificado;
    }
}
