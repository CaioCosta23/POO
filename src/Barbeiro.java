import java.time.LocalDate;

public class Barbeiro extends Usuario{
    private LocalDate contratacao;
    private float salario;
    
    public Barbeiro(int id, String nome, String email, String telefone, String cpf, String login, String senha, LocalDate contratacao, float salario) {
        super(id, nome, email, telefone, cpf, login, senha);
        this.contratacao = contratacao;
        this.salario = salario;
    }
    public Barbeiro(String nome, String email, String telefone, String login, String senha, float salario) {
        super(nome, email, telefone, login, senha);
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

    @Override
    public void exibirMenu() {

        System.out.println("@ Digite a opcao que deseja executar no momento:\n");

        System.out.println("[A] Consultar Dados de Agendamento\t[B] Editar Servicos");
        System.out.println("[C]Alterar Senha de Login\t[D] Visualizar Dados Cadastrais");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        super.exibirInformacoes();
        System.out.printf("- Salario: R$%.2f\n" , this.getSalario());
        System.out.printf("- Data de Admissao: %02d/%02d/%04d\n", this.getContratacao().getDayOfMonth(),
                                                                        this.getContratacao().getMonthValue(), getContratacao().getYear());
    }
}
