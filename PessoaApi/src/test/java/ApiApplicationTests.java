
import br.com.td.person.PessoaApplication;
import br.com.td.person.model.Endereco;
import br.com.td.person.model.Pessoa;
import br.com.td.person.repository.EnderecoRepository;
import br.com.td.person.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PessoaApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc

class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Pessoa pessoa;

    private Endereco endereco;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @BeforeEach
    public void setup() {
        pessoa = new Pessoa();
        pessoa.setNome("Fulano");
        pessoa.setDataNascimento(LocalDate.of(1994, 3, 1));
        pessoaRepository.save(pessoa);

        endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setCidade("teste");
        endereco.setLogradouro("teste");
        endereco.setPessoa(pessoa);
        endereco.setNumero(35);
        enderecoRepository.save(endereco);

    }
    //teste de criar uma pessoa
    @Test
    void createPessoa() throws Exception {
        Pessoa imo = new Pessoa();
        imo.setNome("Teste");
        imo.setDataNascimento(LocalDate.of(1990, 1, 1));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imo)))
                .andExpect(status().isOk());
    }
    //teste de listar todas as pessoas
    @Test
    void getAllPessoas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    //teste de listar uma pessoa
    @Test
    void getByIdPessoas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    //teste de editar pessoa
    @Test
    void updatePessoa() throws Exception {
        pessoa.setNome("Teste Updated");
        pessoa.setDataNascimento(LocalDate.of(2022, 9, 5));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/pessoas/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isOk());
    }
    //teste criaar endereço
    @Test
    void createEnderecoPessoa() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setCep("12345678");
        endereco.setNumero(100);
        endereco.setCidade("São Paulo");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pessoas/{id}/enderecos", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isOk());
    }
   //teste listar endereco de uma pessoaa
    @Test
    void getByIdEnderecosPessoas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/{id}/enderecos", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
   //teste de colocar um endereço como principal
    @Test
    void SetEnderecoPrincial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/pessoas/{id}/enderecos/{id}", 1,1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
