package br.com.wm.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.wm.socialbooks.domain.Comentario;
import br.com.wm.socialbooks.domain.Livro;
import br.com.wm.socialbooks.services.LivrosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/livros")
@Api(value="API RESTFul Social para livros", description="Operações referentes a livros e comentários")
public class LivrosResources {
	
	@Autowired
	private LivrosService livrosService;
	
	@CrossOrigin //permitir requisição fora do domínio da aplicação
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Retorna uma lista dos livros disponíveis", response = List.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista recuperada com sucesso"),
            @ApiResponse(code = 401, message = "Você não está autorizado a visualizar o recurso"),
            @ApiResponse(code = 403, message = "O recurso que você estava tentando acessar é proibido"),
            @ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado")
    })
	public ResponseEntity<List<Livro>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Cria um livro")
	public ResponseEntity<Void> salvar(@ApiParam(value = "Objeto do livro para persistência", required = true) @Valid @RequestBody Livro livro) {
		livro = livrosService.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(livro.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um livro a partir do id", response = Livro.class)
	public ResponseEntity<?> buscar(@ApiParam(value = "Id do livro para recuperação do objeto", required = true) @PathVariable("id") Long id) {
		Livro livro = livrosService.buscar(id);
		
		CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Exclui um livro a partir do id")
	public ResponseEntity<Void> deletar(@ApiParam(value = "Id do livro para exclusão do objeto", required = true) @PathVariable("id") Long id) {
		livrosService.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza um livro")
	public ResponseEntity<Void> atualizar(@ApiParam(value = "Objeto do livro para atualização", required = true) @RequestBody Livro livro, 
										  @ApiParam(value = "Id do livro para atualização do objeto", required = true) @PathVariable("id") Long id) {
		livro.setId(id);
		livrosService.atualizar(livro);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	@ApiOperation(value = "Cria um comentário para livro")
	public ResponseEntity<Void> adicionarComentario(@ApiParam(value = "Id do livro para criação do comentário", required = true) @PathVariable("id") Long livroId, 
													@ApiParam(value = "Objeto do comentário para criação", required = true) @RequestBody Comentario comentario) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(livroId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna uma lista de comentários a partir do id do livro", response = List.class)
	public ResponseEntity<List<Comentario>> listarComentarios(@ApiParam(value = "Id do livro para obtenção dos comentários", required = true) @PathVariable("id") Long livroId) {
		List<Comentario> comentarios = livrosService.listarComentarios(livroId);
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);
	}

}
