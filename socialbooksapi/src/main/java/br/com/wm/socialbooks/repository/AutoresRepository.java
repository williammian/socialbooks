package br.com.wm.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.socialbooks.domain.Autor;

public interface AutoresRepository extends JpaRepository<Autor, Long>{

}
