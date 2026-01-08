import java.time.LocalDate;

public class Barbeiro extends Usuario implements Menu{
    private LocalDate contratacao;
    private float salario;
    
    public Barbeiro(int id, String nome, String email, String telefone, String cpf, String login, String senha, LocalDate contratacao, float salario) {
        super(id, nome, email, telefone, cpf, login, senha);
        this.contratacao = contratacao;
        this.salario = salario;
    }
    public Barbeiro(String email, String telefone, String login, String senha, LocalDate contratacao, float salario) {
        super(email, telefone, login, senha);
        this.contratacao = contratacao;
        this.salario = salario;
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

    }
}
