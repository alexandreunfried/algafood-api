package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email and (:id is null or u.id <> :id)")
    boolean existsByEmailAndIdNot(String email, Long id);

}
