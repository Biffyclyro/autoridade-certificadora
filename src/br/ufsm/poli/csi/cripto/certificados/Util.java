package br.ufsm.poli.csi.cripto.certificados;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe com métodos úteis para a certificação e verificação de assinaturas.
 */
public class Util {

    /**
     * Retorna o hash deste objeto, para ser usado na verificação de autenticidade
     * e geração de assinatura.
     * @return byte array do hash deste certificado.
     */
    public static synchronized byte[] getHash(Assinavel objeto) {

        byte[] certData;
        byte[] assinTemp = objeto.getAssinatura();
        objeto.setAssinatura(null);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(objeto);
            oout.close();
            bout.close();
            certData = bout.toByteArray();
            return MessageDigest.getInstance("SHA-256").digest(certData);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            objeto.setAssinatura(assinTemp);
        }
    }

}
