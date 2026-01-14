import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Barbeiro extends Usuario{
    private LocalDate contratacao;
    private float salario;
    private final Map <Integer, Servico> especialidades = new HashMap<>();
    private final List<Disponibilidade> disponibilidades = new ArrayList<>();
    
    public Barbeiro(int id, String nome, String email, String telefone, String cpf, String senha, LocalDate contratacao, float salario) {
        super(id, nome, email, telefone, cpf, senha);
        this.contratacao = contratacao;
        this.salario = salario;
    }

    // Realiza uma cópia profunda de todos os dados de um objeto para outro (sem ligar a referência de um objeto ao outro);
    public Barbeiro(Barbeiro original) {
        super(original);
        this.contratacao = original.contratacao;
        this.salario = original.salario;
        this.especialidades.putAll(original.especialidades);
        this.disponibilidades.addAll(original.disponibilidades);
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

    public Map<Integer, Servico> getEspecialidades() {
        return new HashMap<>(this.especialidades);
    }

    public List<Disponibilidade> getDisponibilidade() {
        return new ArrayList<>(this.disponibilidades);
    }

    public void adicionarDisponibilidade(List<Disponibilidade>disponibilidades) {
        this.disponibilidades.addAll(disponibilidades);
    }

    public void adicionarEspecialidades(Map<Integer, Servico>servicos) {
        this.especialidades.putAll(servicos);
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s;%02d/%02d/%04d;%d", this.getId(), this.getNome(), this.getEmail(), 
                                                                                this.getTelefone(), this.getCpf(),
                                                                                this.getSenha(), this.contratacao.getDayOfMonth(),
                                                                                this.contratacao.getMonthValue(),this.contratacao.getYear(),
                                                                                (int)this.getSalario());
    }


    public static void exibirMenu() {
        System.out.println("* Escolha uma das opcoes abaixo:");
        
        System.out.println("[1] Criar agendamento\t\t[2] Excluir Agendamento");
        System.out.println("[3] Adicionar Especialidade\t[4] Remover Especialidade");
        System.out.println("[5] Verificar especialidades\t[6] Consultar Dados Cadastrais");
        System.out.println("[7] Verificar Disponibilidades\t[8] Alterar disponibilidade");
        System.out.println("[9] Confirmar Agendamento\t[10] Alterar senha de usuario\t");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        super.exibirInformacoes();
        System.out.printf("- Salario: R$%.2f\n" , this.getSalario());
        System.out.printf("- Data de Admissao: %02d/%02d/%04d\n", this.getContratacao().getDayOfMonth(),
                                                                        this.getContratacao().getMonthValue(), getContratacao().getYear());
        
        Consulta consulta = new Consulta();

        System.out.println("- Especialidades:");
        consulta.exibirServicos(this.getEspecialidades());
        System.out.println("- Disponibilidades para agendamento de servico:");
        consulta.exibirDisponibilidade(this.getDisponibilidade());
    }
}
