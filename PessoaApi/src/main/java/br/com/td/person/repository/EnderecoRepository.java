package br.com.td.person.repository;

import br.com.td.person.model.Endereco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    public List<Endereco> findByPessoaId(long pessoaId);

}
