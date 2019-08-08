package br.com.wm.socialbooks.client;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.wm.socialbooks.client.domain.Livro;

public class LivrosClient {
	
	public List<Livro> listar() {
		RestTemplate restTemplate = new RestTemplate();
		
		RequestEntity<Void> request = RequestEntity
				.get(URI.create("http://localhost:8080/livros"))
				.header("Authorization", "Basic dGVzdGU6czNuaDQ=").build();
		
		ResponseEntity<Livro[]> response = restTemplate.exchange(request, Livro[].class);
		
		return Arrays.asList(response.getBody());
	}
	
	public String salvar(Livro livro) {
		RestTemplate restTemplate = new RestTemplate();
		
		RequestEntity<Livro> request = RequestEntity
				.post(URI.create("http://localhost:8080/livros"))
				.header("Authorization", "Basic dGVzdGU6czNuaDQ=")
				.body(livro);
		
		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
		
		return response.getHeaders().getLocation().toString();
	}

}
