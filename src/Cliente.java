import java.time.LocalDate;

public class Cliente extends Usuario {
    private LocalDate nascimento;
    
    public Cliente(int id, String nome, String email, String telefone, String cpf, String login, String senha, LocalDate nascimento) {
        super(id, nome, email, telefone, cpf, login, senha);
        this.nascimento = nascimento;
    }

    public Cliente(Cliente original) {
        super(original);
        this.nascimento = original.getNascimento();
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s;%s;%02d/%02d/%04d", this.getId(), this.getNome(), this.getEmail(), this.getTelefone(),
                                                     this.getCpf(), this.getLogin(), this.getSenha(),
                                                     this.nascimento.getDayOfMonth(), this.nascimento.getMonthValue(),
                                                     this.nascimento.getYear());
    }

    @Override
    public void exibirMenu() {
        System.out.println("[1] Cadastrar-se\t[2] Fazer Agendamento");
        System.out.println("[3]Consultar Servicos\t[4] Cancelar Agendamento");
        System.out.println("[5] Consultar Dados Pessoais\t [6] Alterar Senha de Usuario");
        System.out.println("[7] Excluir Usuario");
    }


    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome: " + this.getNome());
        System.out.println("- E-mail: " + this.getEmail());
        System.out.println("- Telefone: " + this.getTelefone());
        System.out.println("- CPF: " + this.getCpf());
        System.out.printf("- Data de Nascimento: %02d/%02d/%04d\n", this.getNascimento().getDayOfMonth(), this.getNascimento().getMonthValue(),
                                                                          this.getNascimento().getYear());
    }

}
