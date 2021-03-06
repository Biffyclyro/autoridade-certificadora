package br.ufsm.poli.csi.cripto.certificados;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AutoridadeCertificadoraImpl implements AutoridadeCertificadora, Serializable {
    private final CertificadoPrivado cp;
    private final List<Certificado> revogados = new ArrayList<>();

    public AutoridadeCertificadoraImpl(CertificadoPrivado cp) {
        this.cp = cp;
    }

    @Override
    public void assinaCertificado(Certificado certificado) {
        if (this.getCertificado().getTipoCertificado().ordinal()
                > certificado.getTipoCertificado().ordinal()) {
            certificado.setAutoridadeCertificadora(this);
            certificado.setCertificadoPor(cp.getCertificado());
            certificado.setValidade(new Date());
            var hash = Util.getHash(certificado);

            try {
                final var cipherAss = Cipher.getInstance("RSA");
                cipherAss.init(Cipher.ENCRYPT_MODE, this.cp.getChavePrivada());
                final var assinatura = cipherAss.doFinal(hash);
                certificado.setAssinatura(assinatura);
            } catch (NoSuchAlgorithmException
                    | NoSuchPaddingException
                    | InvalidKeyException
                    | IllegalBlockSizeException
                    | BadPaddingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean estaRevogado(Certificado certificado) {
        return this.revogados.contains(certificado);
    }

    @Override
    public void revogaCertificado(Certificado certificado) {
        this.revogados.add(certificado);
    }

    @Override
    public Certificado getCertificado() {
        return this.cp.getCertificado();
    }
}
