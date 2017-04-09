import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

	private DadosDeEntrada dadosDeEntrada;
	private List<ArquivoComDigestCalculado> arquivosComDigestsCalculados = new ArrayList<ArquivoComDigestCalculado>();

	public List<ArquivoComDigestCalculado> getArquivosComDigestsCalculados() {
		return arquivosComDigestsCalculados;
	}

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

	public void Verificar() {
		ListaDigest listaDigests = this.dadosDeEntrada.getListaDigest();

		for (ArquivoComDigestCalculado digestCalculado : arquivosComDigestsCalculados) {

			if (this.contémColisãoComDemaisDigestDeEntrada(digestCalculado)
					|| listaDigests.contémColisão(digestCalculado)) {
				digestCalculado.setStatus("COLISION");
				continue;
			}

			if (!listaDigests.estáPresenteNaLista(digestCalculado.getNomeArquivo(), digestCalculado.getTipoDigest())) {
				digestCalculado.setStatus("NOT FOUND");
				continue;
			}

			if (!listaDigests.contémDigest(digestCalculado)) {
				digestCalculado.setStatus("NOT OK");
				continue;
			}
			digestCalculado.setStatus("OK");
		}
	}

	public void AdicionarArquivoNãoEncontradoAListaDigests(ListaDigest listaDigest) {

		List<ArquivoComDigestCalculado> notFounds = new ArrayList<ArquivoComDigestCalculado>();

		for (ArquivoComDigestCalculado item : arquivosComDigestsCalculados) {
			if (item.getStatus().equals("NOT FOUND")) {
				notFounds.add(item);
			}
		}

		try {
			FileWriter arq;
			arq = new FileWriter(listaDigest.getCaminhoArquivo());
			PrintWriter gravarArq = new PrintWriter(arq);
			for (ListaDigestItem item : listaDigest.getItens()) {

				String linha;

				if (item.TemSegundoDigest()) {
					linha = String.format("%s %s %s %s %s", item.getNomeArquivo(), item.getTipoDigest1(),
							item.getDigest1(), item.getTipoDigest2(), item.getDigest2());
					gravarArq.println(linha);
				}else {
					
					// CompletaUmExistente
					Boolean completou = false;
					for (ArquivoComDigestCalculado notFound : notFounds) {
						if (item.getNomeArquivo().equals(notFound.getNomeArquivo())) {
							linha = String.format("%s %s %s %s %s", item.getNomeArquivo(), item.getTipoDigest1(),
									item.getDigest1(), notFound.getTipoDigest(), notFound.getDigest());
							gravarArq.println(linha);
							completou = true;
						}
					}
					
					if(!completou){
						linha = String.format("%s %s %s", item.getNomeArquivo(), item.getTipoDigest1(), item.getDigest1());
						gravarArq.println(linha);	
					}
				} 
			}
			
			for (ArquivoComDigestCalculado notFound : notFounds) {
				
				Boolean contém = false;
				
				for (ListaDigestItem item : listaDigest.getItens()) {
					if(item.getNomeArquivo().equals(notFound.getNomeArquivo()))
						contém = true;
				}
				
				if(!contém){
					String linha = String.format("%s %s %s", notFound.getNomeArquivo(), notFound.getTipoDigest(), notFound.getDigest());
					gravarArq.println(linha);
				}
			}
			
			gravarArq.close();
			arq.close();
		} catch (Exception e) {

		}
	}

	private Boolean contémColisãoComDemaisDigestDeEntrada(ArquivoComDigestCalculado digestCalculado) {

		for (ArquivoComDigestCalculado item : arquivosComDigestsCalculados) {
			if (item == digestCalculado)
				continue;

			if (item.getDigest().equals(digestCalculado.getDigest())
					&& item.getTipoDigest().equals(digestCalculado.getTipoDigest())) {
				return true;
			}
		}
		return false;
	}

	private void CalcularDigestDosArquivosDeEntrada() {
		try {

			String tipoDigest = this.dadosDeEntrada.getTipoDigest();

			for (String arquivo : this.dadosDeEntrada.getArquivos()) {

				MessageDigest messageDigest = MessageDigest.getInstance(tipoDigest);
				InputStream file = new FileInputStream(arquivo);
				BufferedInputStream byteReader = new BufferedInputStream(file);

				byte[] dataAsByte = new byte[1024];
				int data = byteReader.read(dataAsByte);
				while (data != -1) {
					messageDigest.update(dataAsByte, 0, data);
					data = byteReader.read(dataAsByte);
				}

				String nomeDoArquivo = getNomeArquivo(arquivo);
				String digestEmHex = toHex(messageDigest.digest());

				ArquivoComDigestCalculado arquivoComDigestCalculado = new ArquivoComDigestCalculado(nomeDoArquivo,
						tipoDigest, digestEmHex);
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
