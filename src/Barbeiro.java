import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Barbeiro extends Usuario{
    private LocalDate contratacao;
    private float salario;
    private final Map <Integer, Servico> servicos = new HashMap<>();
    private final List<Disponibilidade> disponibilidades = new ArrayList<>();
    
    public Barbeiro(int id, String nome, String email, String telefone, String cpf, String login, String senha, LocalDate contratacao, float salario) {
        super(id, nome, email, telefone, cpf, login, senha);
        this.contratacao = contratacao;
        this.salario = salario;
    }

    // Realiza uma cópia profunda de todos os dados de um objeto para outro (sem ligar a referência de um objeto ao outro);
    public Barbeiro(Barbeiro original) {
        super(original);
        this.contratacao = original.getContratacao();
        this.salario = original.getSalario();
    }

    public LocalDate getContratacao() {
        return this.contratacao;
    }

    public void setContratacao(LocalDate contratacao) {
        this.contratacao = contratacao;
    }

    public float getSalario() {
        return this.salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public Map<Integer, Servico> getServicos() {
        return new HashMap<>(this.servicos);
    }

    public List<Disponibilidade> getDisponibilidade() {
        return new ArrayList<>(this.disponibilidades);
    }

    public void adicionarServicos(Map<Integer, Servico>servicos) {
        this.servicos.putAll(servicos);
    }

    public void adicionarDisponibilidade(List<Disponibilidade>disponibilidades) {
        this.disponibilidades.addAll(disponibilidades);
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s;%s;%02d/%02d/%04d", this.getId(), this.getNome(), this.getEmail(), 
                                                                                    this.getTelefone(), this.getCpf(), this.getLogin(),
                                                                                    this.getSenha(), this.contratacao.getDayOfMonth(),
                                                                                    this.contratacao.getMonthValue(),this.contratacao.getYear());
    }

    @Override
    public void exibirMenu() {
        System.out.println("* Escolha uma das opcoes abaixo:");
        
        System.out.println("[1] Criar agendamento\t[2]Excluir Agendamento");
        System.out.println("[3] Adicionar Especialidade\t[4] Remover Especialidade");
        System.out.println("[5] Alterar senha de usuario\t[6]Consultar Dados Cadastrais");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        super.exibirInformacoes();
        System.out.printf("- Salario: R$%.2f\n" , this.getSalario());
        System.out.printf("- Data de Admissao: %02d/%02d/%04d\n", this.getContratacao().getDayOfMonth(),
                                                                        this.getContratacao().getMonthValue(), getContratacao().getYear());
        Consulta consulta = new Consulta();

        consulta.exibirServicos(this.servicos);
    }
}
