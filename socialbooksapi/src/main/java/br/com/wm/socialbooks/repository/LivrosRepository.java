package br.com.wm.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.socialbooks.domain.Livro;

public interface LivrosRepository extends JpaRepository<Livro, Long> {

}
