public class DigestCalculator {
	
	public static void main(String[] args) {

		if(args.length >= 1){
			
			PreparadorDadosEntrada preparador = new PreparadorDadosEntrada();
			DadosDeEntrada dadosDeEntrada = preparador.GetDadosDeEntrada(args);
					
			System.out.println(dadosDeEntrada);
			
			Calculator calculator = new Calculator(dadosDeEntrada);
			calculator.Verificar();
			
			for (ArquivoComDigestCalculado item : calculator.getArquivosComDigestsCalculados()) {
				System.out.println(String.format("%s %s %s (%s)", item.getNomeArquivo(), item.getTipoDigest(), item.getDigest(), item.getStatus()));
			}
			
			calculator.AdicionarArquivoNãoEncontradoAListaDigests(dadosDeEntrada.getListaDigest());
			
			System.out.println("Acabou");
		}

	}
	


}
