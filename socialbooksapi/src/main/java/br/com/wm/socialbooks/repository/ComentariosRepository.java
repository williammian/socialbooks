package br.com.wm.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.socialbooks.domain.Comentario;

public interface ComentariosRepository extends JpaRepository<Comentario, Long>{

}
