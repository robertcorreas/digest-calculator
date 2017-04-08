import java.util.ArrayList;
import java.util.List;

public class PreparadorDadosEntrada {

	public DadosDeEntrada GetDadosDeEntrada(String[] args){
		String tipoDigest = args[0].toString();
		
		List<String> arquivos = new ArrayList<String>();
		
		for (int i = 1; i < args.length-1; i++) {
			arquivos.add(args[i].toString());
		}
		
		String caminhoListaDigest = args[args.length-1].toString();
		ListaDigest listaDigest = new ListaDigest(caminhoListaDigest);
		
		DadosDeEntrada dadosDeEntrada = new DadosDeEntrada(tipoDigest, arquivos, listaDigest);
		
		return dadosDeEntrada;
	}
	
}
