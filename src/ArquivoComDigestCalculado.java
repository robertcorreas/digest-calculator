
public class ArquivoComDigestCalculado {

	private String nomeArquivo;
	private String tipoDigest;
	private String digest;
	private String status;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public String getTipoDigest() {
		return tipoDigest;
	}

	public String getDigest() {
		return digest;
	}

	public ArquivoComDigestCalculado(String nomeArquivo, String tipoDigest, String digest) {
		this.nomeArquivo = nomeArquivo;
		this.tipoDigest = tipoDigest;
		this.digest = digest;
	}

	public String toString() {
		return String.format("Nome do arquivo: %s\nTipo do Digest: %s\nDigest: %s", this.nomeArquivo, this.tipoDigest,
				this.digest);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
