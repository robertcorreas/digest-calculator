public class DigestCalculator {
	
	public static void main(String[] args) {

		if(args.length >= 1){
			
			PreparadorDadosEntrada preparador = new PreparadorDadosEntrada();
			DadosDeEntrada dadosDeEntrada = preparador.GetDadosDeEntrada(args);
					
			System.out.println(dadosDeEntrada);
		}

	}

}
