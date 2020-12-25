package test;

import br.ufsm.poli.csi.cripto.certificados.*;
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
    public void testar() throws NoSuchAlgorithmException {


       final var cpRaiz = new CertificadoPrivado("Teste Raiz",
                                            "00000000",
                                            "teste@raiz",
                                            "bol.com",
                                            Certificado.TipoCertificado.CA_RAIZ);

       final var CA_RAIZ = new AutoridadeCertificadoraImpl(cpRaiz);

       final var cp_CA = new CertificadoPrivado("CA teste",
                                                "1111111",
                                                "teste@CA",
                                                "aol.com",
                                                Certificado.TipoCertificado.CA);

       final var CA = new AutoridadeCertificadoraImpl(cp_CA);

       CA_RAIZ.assinaCertificado(CA.getCertificado());

       final var cpUsuario = new CertificadoPrivado("Usuario final",
                                                    "22222222",
                                                    "fulano@teste",
                                                    "funalaners.com",
                                                    Certificado.TipoCertificado.USUARIO_FINAL);
       CA.assinaCertificado(cpUsuario.getCertificado());

       final var documento = cpUsuario.assinaDocumento(new byte[10],
               "Arquivo_teste",
               "RFC 2046");

        Assertions.assertNotNull(documento.getAssinatura() );

        Assertions.assertTrue(documento.verificaAutenticidade());

        cpUsuario.save();

        final var certSalvo = CertificadoPrivado.findCertificado("Usuario final");

        Assertions.assertTrue(certSalvo.getCertificado().verificaAutenticidade());



        CA.revogaCertificado(cpUsuario.getCertificado());

        Assertions.assertTrue(CA.estaRevogado(cpUsuario.getCertificado()));


    }


}
