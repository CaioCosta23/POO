import java.time.LocalDate;

public class Cliente extends Usuario {
    private LocalDate nascimento;
    
    public Cliente(int id, String nome, String email, String telefone, String cpf, String login, String senha, LocalDate nascimento) {
        super(id, nome, email, telefone, cpf, login, senha);
        this.nascimento= nascimento;
    }

    public Cliente(String email, String telefone, String login, String senha, LocalDate nascimento) {
        super(email, telefone, login, senha);
        this.nascimento = nascimento;
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public void exibirMenu() {
        System.out.println("@ Selecione uma das opcoes abaixo:");
        
        System.out.println(("\t[1] Agendar Servico  \t[2] Consultar Agendamento"));
        System.out.println(("\t[3] Consultar Servico\t[4] Cancelar Agendamento "));
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome: " + this.getNome());
        System.out.println("- E-mail: " + this.getEmail());
        System.out.println("- Telefone: " + this.getTelefone());
        System.out.println("- CPF: " + this.getCpf());
        System.out.printf("- Data de Nascimento: %02d/%02d/%04d", this.getNascimento().getDayOfMonth(), this.getNascimento().getMonthValue(),
                                                                          this.getNascimento().getYear());
    }

}
