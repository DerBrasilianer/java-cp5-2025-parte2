package fiap.com.br.brinquedos_revisao.repository;

import fiap.com.br.brinquedos_revisao.model.Brinquedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {

}
