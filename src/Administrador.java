public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email, String telefone, String cpf, String login, String senha) {
        super(id, nome, email, telefone, cpf, login, senha);
    }

    @Override
    public void exibirMenu() {
        System.out.println("# Informe o servico do qual deseja acessar:\n");

        System.out.println("[1] Criar agendamento\t[2] Excluir Agendamento");
        System.out.println("[3] Adicionar Servico\t[4] Remover Servico");
        System.out.println("[5] Adicioanar Usuario\t[6] Excluir Usuario");
        System.out.println("[7] Consultar agendamento\t[8] Consultar Dados Usuarios ");
        System.out.println("[9] Buscar Servico\t[10] Informacoes do estabelecimento "); 
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome: "+ this.getNome());
        System.out.println("- E-mail: " + this.getEmail());
        System.out.println("- Telefone: " + this.getTelefone());
    }
    
}
