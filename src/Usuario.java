import java.util.Scanner;

public abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String login;
    private String senha;

    public Usuario(int id, String nome, String email, String telefone, String cpf, String login, String senha) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
        setCpf(cpf);
        setLogin(login);
        setSenha(senha);
    }

    public Usuario(String email, String cpf, String login, String senha) {
        setEmail(email);
        setTelefone(telefone);
        setLogin(login);
        setSenha(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // O retorno desa função foi criado com o tipo 'booleano' para que possa ser usado para comparações em outras partes do projeto;
    public boolean autenticar(String login, String senha) {
        if (login.equals(this.getLogin()) && senha.equals(this.getSenha())) {
            System.out.println("LOGADO.");
            return true;
        }else {
            System.out.println("Senha ou login incorreto(s)!");
            return false;
        }
    }

    public void alterarSenha() {
        /* Try-with-resources: Verifica se o objeto pode ser criado, e o fecha 
         * (neste caso, executa um "entrada.close()" se o objeto for criado ou não) de forma segura.
        */
        try (Scanner entrada = new Scanner(System.in)) {
            if (this.autenticar(entrada.nextLine(), entrada.nextLine())) {
                System.out.printf("# Digite uma nova senha: ");
                this.setSenha(entrada.nextLine());
                System.out.println("- Senha Alterada!");
            }
        }
    }
}
