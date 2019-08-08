package br.com.wm.socialbooks.aplicacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.wm.socialbooks.client.LivrosClient;
import br.com.wm.socialbooks.client.domain.Livro;

public class Aplicacao {

	public static void main(String[] args) throws ParseException {
		
		LivrosClient cliente = new LivrosClient();
		
		List<Livro> listaLivros = cliente.listar();
		
		for(Livro livro : listaLivros) {
			System.out.println("Livro: " + livro.getNome());
		}
		
		Livro livro = new Livro();
		livro.setNome("Git passo-a-passo");
		livro.setEditora("Livros XRS");
		
		SimpleDateFormat publicacao = new SimpleDateFormat("dd/MM/yyyy");
		livro.setPublicacao(publicacao.parse("01/01/2016"));
		
		livro.setResumo("Este livro aborda técnicas de desenvolvimento de APIs.");
		
		String localizacao = cliente.salvar(livro);
		
		System.out.println("URI do livro salvo: " + localizacao);
		
	}
	
}
