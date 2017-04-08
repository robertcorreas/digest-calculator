
public class ListaDigestItem {

	private String nomeArquivo;
	private String tipoDigest1;
	private String digest1;
	private String tipoDigest2;
	private String digest2;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public String getTipoDigest1() {
		return tipoDigest1;
	}

	public String getDigest1() {
		return digest1;
	}

	public String getTipoDigest2() {
		return tipoDigest2;
	}

	public String getDigest2() {
		return digest2;
	}

	public ListaDigestItem(String nomeArquivo, String tipoDigest1, String digest1) {
		this.nomeArquivo = nomeArquivo;
		this.tipoDigest1 = tipoDigest1;
		this.digest1 = digest1;

		this.tipoDigest2 = null;
		this.digest2 = null;
	}

	public ListaDigestItem(String nomeArquivo, String tipoDigest1, String digest1, String tipoDigest2, String digest2) {
		this(nomeArquivo, tipoDigest1, digest1);

		this.tipoDigest2 = tipoDigest2;
		this.digest2 = digest2;
	}

	public Boolean TemSegundoDigest() {
		return (this.tipoDigest2 != null && this.digest2 != null);
	}

}
