/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.td.person.controller;

import br.com.td.person.model.Endereco;
import br.com.td.person.model.Pessoa;
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
import br.com.td.person.repository.EnderecoRepository;
import br.com.td.person.repository.PessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pessoas/{pessoaId}/enderecos")// URl da requisição
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    //funçao de criaçao de endereço
    @PostMapping
    public Endereco criarEndereco(@RequestBody Endereco endereco, @PathVariable Long pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        endereco.setPessoa(pessoa);
        return enderecoRepository.save(endereco);
    }

    //funçao para listar endereços
    @GetMapping
    public List<Endereco> listarEnderecos(@PathVariable long pessoaId) {
        return enderecoRepository.findByPessoaId(pessoaId);
    }
    //funçao de colocar endereço como principal
    @PutMapping("/{enderecoId}")
    public Endereco setEnderecoPrincipal(@PathVariable Long pessoaId, @PathVariable Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrada"));

        pessoa.getEnderecos().forEach(e -> e.setPrincipal(false));
        endereco.setPrincipal(true);
        return enderecoRepository.save(endereco);
    }
}
