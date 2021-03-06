import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListaDigest {

	private List<ListaDigestItem> itens;
	private String caminhoArquivo;

	public List<ListaDigestItem> getItens() {
		return itens;
	}

	public ListaDigest(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
		this.itens = new ArrayList<ListaDigestItem>();

		CarregarArquivo();
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	private void criarItem(String[] linha) {

		if (linha.length == 3) {
			String nomeArquivo = linha[0];
			String tipoDigest1 = linha[1];
			String digest1 = linha[2];
			this.Adicionar(nomeArquivo, tipoDigest1, digest1);
		} else {
			String nomeArquivo = linha[0];
			String tipoDigest1 = linha[1];
			String digest1 = linha[2];
			String tipoDigest2 = linha[3];
			String digest2 = linha[4];
			this.Adicionar(nomeArquivo, tipoDigest1, digest1, tipoDigest2, digest2);
		}
	}

	private void CarregarArquivo() {
		try {
			FileReader arquivo = new FileReader(this.caminhoArquivo);
			BufferedReader reader = new BufferedReader(arquivo);

			String linha = reader.readLine();

			while (linha != null) {
				String[] itensDaLinha = linha.split("[ ]");
				criarItem(itensDaLinha);
				linha = reader.readLine();
			}

			reader.close();
			arquivo.close();

		} catch (IOException e) {
			System.out.println("\nProblemas ao ler aquivo de lista de digest\n");
		}
	}

	private void Adicionar(String nomeArquivo, String tipoDigest, String digest) {
		itens.add(new ListaDigestItem(nomeArquivo, tipoDigest, digest));
	}

	private void Adicionar(String nomeArquivo, String tipoDigest1, String digest1, String tipoDigest2, String digest2) {
		itens.add(new ListaDigestItem(nomeArquivo, tipoDigest1, digest1, tipoDigest2, digest2));
	}

	public String toString() {
		String toStringFormatada = "\nLista de Digest";

		for (ListaDigestItem item : this.itens) {

			if (item.TemSegundoDigest()) {
				toStringFormatada += "\n" + item.getNomeArquivo() + " " + item.getTipoDigest1() + " "
						+ item.getDigest1() + " " + item.getTipoDigest2() + " " + item.getDigest2();
			} else {
				toStringFormatada += "\n" + item.getNomeArquivo() + " " + item.getTipoDigest1() + " "
						+ item.getDigest1();
			}
		}

		return toStringFormatada;
	}

	public Boolean cont�mDigest(ArquivoComDigestCalculado digestCalculado) {

		for (ListaDigestItem item : itens) {

			if (item.getTipoDigest1().equals(digestCalculado.getTipoDigest())
					&& item.getDigest1().equals(digestCalculado.getDigest())
					&& item.getNomeArquivo().equals(digestCalculado.getNomeArquivo())) {
				return true;
			}

			if (item.TemSegundoDigest()) {
				if (item.getTipoDigest2().equals(digestCalculado.getTipoDigest())
						&& item.getDigest2().equals(digestCalculado.getDigest())
						&& item.getNomeArquivo().equals(digestCalculado.getNomeArquivo())) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean est�PresenteNaLista(String nomeArquivo, String tipoDigest) {

		for (ListaDigestItem item : itens) {
			if (item.getNomeArquivo().equals(nomeArquivo)){
			
				if(item.getTipoDigest1().equals(tipoDigest))
					return true;
				
				if(item.TemSegundoDigest()){
					if(item.getTipoDigest2().equals(tipoDigest))
						return true;
				}
			}
		}

		return false;
	}

	public boolean cont�mColis�o(ArquivoComDigestCalculado digestCalculado) {

		for (ListaDigestItem item : itens) {

			if (item.getNomeArquivo().equals(digestCalculado.getNomeArquivo()))
				continue;

			if (item.getTipoDigest1().equals(digestCalculado.getTipoDigest())
					&& item.getDigest1().equals(digestCalculado.getDigest())) {
				return true;
			}

			if (item.TemSegundoDigest()) {
				if (item.getTipoDigest2().equals(digestCalculado.getTipoDigest())
						&& item.getDigest2().equals(digestCalculado.getDigest())) {
					return true;
				}
			}
		}
		return false;
	}

}
