public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email, String telefone, String cpf, String login, String senha) {
        super(id, nome, email, telefone, cpf, login, senha);
    }

    @Override
    public void exibirMenu() {
        System.out.println("# Informe o servico do qual deseja acessar:\n");

        System.out.println("[1] Adicionar agendamento\t\t[2] Excluir agendamento \t\t[3] Buscar agendamento");
        System.out.println("[4] Adicionar servico\t\t\t[5] Remover servico \t\t\t[6] Buscar servico");
        System.out.println("[7] Adicioanar usuario\t\t\t[8] Excluir usuario \t\t\t[9] Buscar usuarios");
        System.out.println("[10] Visualizar lista de usuarios\t[11] Visualizar lista de servicos ");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome: "+ this.getNome());
        System.out.println("- E-mail: " + this.getEmail());
        System.out.println("- Telefone: " + this.getTelefone());
    }
    
}
