import java.util.List;

public class DadosDeEntrada {

	private String tipoDigest;
	private List<String> arquivos;
	private ListaDigest listaDigest;

	public ListaDigest getListaDigest() {
		return listaDigest;
	}

	public List<String> getArquivos() {
		return arquivos;
	}

	public String getTipoDigest() {
		return tipoDigest;
	}

	public DadosDeEntrada(String tipoDigest, List<String> arquivos, ListaDigest listaDigest) {
		this.tipoDigest = tipoDigest;
		this.arquivos = arquivos;
		this.listaDigest = listaDigest;
	}

	public String toString() {

		String toStringFormatada = "Tipo Digest: "+this.tipoDigest + "\n";

		toStringFormatada+="\nArquivos de Entrada\n";
		for (String arquivo : arquivos) {
			toStringFormatada += arquivo + "\n";
		}

		toStringFormatada += this.listaDigest.toString();

		return toStringFormatada;
	}
}
