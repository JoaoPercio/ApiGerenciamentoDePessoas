package br.com.td.person.controller;

import br.com.td.person.model.Endereco;
import br.com.td.person.model.Pessoa;
import br.com.td.person.repository.EnderecoRepository;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.td.person.repository.PessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Pessoa listarPeloId(@PathVariable Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        return pessoa;
    }

    @PostMapping
    public Pessoa adicionar(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity editar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return pessoaRepository.findById(id)
                .map(record -> {
                    record.setNome(pessoa.getNome());
                    record.setDataNascimento(pessoa.getDataNascimento());

                    Pessoa pessoaUpdated = pessoaRepository.save(record);
                    return ResponseEntity.ok().body(pessoaUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
