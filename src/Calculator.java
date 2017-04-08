import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

	private DadosDeEntrada dadosDeEntrada;
	private List<ArquivoComDigestCalculado> arquivosComDigestsCalculados = new ArrayList<ArquivoComDigestCalculado>();

	public Calculator(DadosDeEntrada dadosDeEntrada) {
		this.dadosDeEntrada = dadosDeEntrada;
		CalcularDigestDosArquivosDeEntrada();

		System.out.println("\n===Arquivos de entrada com digests calculados===\n");
		for (ArquivoComDigestCalculado arquivoComDigestCalculado : arquivosComDigestsCalculados) {
			System.out.println(arquivoComDigestCalculado);
			System.out.println("\n");
		}
		
		System.out.println("\n===Fim dos Arquivos de entrada com digests calculados===\n");
	}

	private void CalcularDigestDosArquivosDeEntrada() {
		try {

			String tipoDigest = this.dadosDeEntrada.getTipoDigest();
			MessageDigest messageDigest = MessageDigest.getInstance(tipoDigest);

			for (String arquivo : this.dadosDeEntrada.getArquivos()) {
				BufferedInputStream byteReader = new BufferedInputStream(new FileInputStream(arquivo));

				int data = byteReader.read();
				while (data != -1) {
					messageDigest.update((byte) data);
					data = byteReader.read();
				}

				ArquivoComDigestCalculado arquivoComDigestCalculado = new ArquivoComDigestCalculado(
						getNomeArquivo(arquivo), tipoDigest, toHex(messageDigest.digest()));
				this.arquivosComDigestsCalculados.add(arquivoComDigestCalculado);
				byteReader.close();
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private String getNomeArquivo(String caminho) {
		Path p = Paths.get(caminho);
		return p.getFileName().toString().split("[.]")[0];
	}

	private String toHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1);
			buf.append((hex.length() < 2 ? "0" : "") + hex);
		}

		return buf.toString();
	}

}
