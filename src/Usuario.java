import java.util.Scanner;

public abstract class Usuario{
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String senha;

    public Usuario(int id, String nome, String email, String telefone, String cpf, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Usuario(Usuario original) {
        this.id = original.id;
        this.nome = original.nome;
        this.email = original.email;
        this.telefone = original.telefone;
        this.cpf = original.cpf;
        this.senha = original.senha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%s", this.getId(), this.getNome(), this.getEmail(), this.getTelefone(),
                                                     this.getCpf(), this.getSenha());
    }

    // O retorno desa função foi criado com o tipo 'booleano' para que possa ser usado para comparações em outras partes do projeto;
    public boolean autenticar(String login, String senha) {
        boolean logado;

        if (login.equals(this.getCpf()) && senha.equals(this.getSenha())) {
            System.out.println("LOGADO.");
            logado = true;
        }else {
            System.out.println("Senha ou login incorreto(s)!");
            logado = false;
        }
        System.out.println("................................................................................................................");

        return logado;
    }

    public void alterarSenha() {
        /* Try-with-resources: Verifica se o objeto pode ser criado, e o fecha 
         * (neste caso, executa um "entrada.close()" se o objeto for criado ou não) de forma segura.
        */
        try (Scanner entrada = new Scanner(System.in)) {
            if (this.autenticar(this.getCpf(), this.getSenha())) {
                System.out.println("----------------------------------------------------------------------------------------------------------------");
                System.out.printf("# Digite uma nova senha: ");
                this.setSenha(entrada.nextLine());
                System.out.println("- Senha Alterada!");
            }
        }
    }

    public void exibirInformacoes() {
        System.out.println("- Nome:" + this.getNome());
        System.out.println("- E-mail:" + this.getEmail());
        System.out.println("- Telefone:" + this.getTelefone());
        System.out.println("- CPF:" + this.getCpf());
    }
}
