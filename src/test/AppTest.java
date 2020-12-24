package test;

import br.ufsm.poli.csi.cripto.certificados.AutoridadeCertificadoraImpl;
import br.ufsm.poli.csi.cripto.certificados.Certificado;
import br.ufsm.poli.csi.cripto.certificados.CertificadoPrivado;
import br.ufsm.poli.csi.cripto.certificados.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class AppTest {

    @Test
    public void verificaAutoridade() throws NoSuchAlgorithmException {
        final var pairGeneratorGen = KeyPairGenerator.getInstance("RSA");
        final var pair = pairGeneratorGen.generateKeyPair();

       final var certificado = new Certificado(pair.getPublic(),
               "Certificado teste",
               "00000", "teste@teste",
               "bol.com",
               Certificado.TipoCertificado.USUARIO_FINAL);

       final var cp = new CertificadoPrivado()
    }

    @Test
    public void assinarCertificado() throws NoSuchAlgorithmException {
        final var cp = new CertificadoPrivado("CA_RAIZ",
                "000000",
                "teste@teste.com",
                "teste",
                Certificado.TipoCertificado.CA_RAIZ);



        final var ca = new AutoridadeCertificadoraImpl(cp);

        final var certificado = new Certificado(ca.getCertificado().getChavePublica(),
                "Fulano",
                "0000",
                "email",
                ".com",
                Certificado.TipoCertificado.USUARIO_FINAL);


        ca.assinaCertificado(certificado);
        System.out.println(certificado.verificaAutenticidade());


    }
}
