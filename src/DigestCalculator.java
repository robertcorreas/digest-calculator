public class DigestCalculator {
	
	public static void main(String[] args) {

		if(args.length >= 3){
			
			try {
				PreparadorDadosEntrada preparador = new PreparadorDadosEntrada();
				DadosDeEntrada dadosDeEntrada = preparador.GetDadosDeEntrada(args);
						
				Calculator calculator = new Calculator(dadosDeEntrada);
				calculator.Verificar();
				
				for (ArquivoComDigestCalculado item : calculator.getArquivosComDigestsCalculados()) {
					System.out.println(String.format("%s %s %s (%s)", item.getNomeArquivo(), item.getTipoDigest(), item.getDigest(), item.getStatus()));
				}
				
				calculator.AdicionarArquivoNãoEncontradoAListaDigests(dadosDeEntrada.getListaDigest());
			} catch (Exception e) {
				System.out.println("Ocorreu um erro. Verifique os dados de entrada");
				System.out.println("DigestCalculator tipoDigest(MD5 ou SHA1) caminho_arquivo1 caminho_arquivo2 caminho_arquivo_n listaDigest");
			}
		}
		else{
			System.out.println("DigestCalculator tipoDigest(MD5 ou SHA1) caminho_arquivo1 caminho_arquivo2 caminho_arquivo_n listaDigest");
		}
	}
}
