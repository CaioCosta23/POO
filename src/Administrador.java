public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email, String telefone, String cpf, String login, String senha) {
        super(id, nome, email, telefone, cpf, login, senha);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome: "+ this.getNome());
        System.out.println("- E-mail: " + this.getEmail());
        System.out.println("- Telefone: " + this.getTelefone());
    }
    
}
