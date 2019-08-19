package br.com.wm.socialbooks.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.wm.socialbooks.domain.Autor;
import br.com.wm.socialbooks.services.AutoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/autores")
@Api(value="API RESTFul Social para livros", description="Operações referentes a autores")
public class AutoresResource {

	@Autowired
	private AutoresService autoresService;
	
	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
	})
	@ApiOperation(value = "Retorna uma lista dos autores disponíveis", response = List.class)
	public ResponseEntity<List<Autor>> listar() {
		List<Autor> autores = autoresService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Cria um autor")
	public ResponseEntity<Void> salvar(@ApiParam(value = "Objeto do autor para persistência", required = true) @Valid @RequestBody Autor autor) {
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um autor a partir do id", response = Autor.class)
	public ResponseEntity<Autor> buscar(@ApiParam(value = "Id do autor para recuperação do objeto", required = true) @PathVariable("id") Long id) {
		Autor autor = autoresService.buscar(id);

		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
	
}
