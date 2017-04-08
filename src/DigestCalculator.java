import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestCalculator {
	
	public static void main(String[] args) {

		if(args.length >= 1){
			
			PreparadorDadosEntrada preparador = new PreparadorDadosEntrada();
			DadosDeEntrada dadosDeEntrada = preparador.GetDadosDeEntrada(args);
					
			System.out.println(dadosDeEntrada);
			
			Calculator calculator = new Calculator(dadosDeEntrada);
			
			System.out.println("Acabou");
		}

	}
	


}
