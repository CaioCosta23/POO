import java.util.Set;
public class Gerenciador {

    
    public Gerenciador() {
    }

    public void iniciarSistema(Barbearia barbearia)throws ExceptionObjetoInexistente {
        // Verifica se a barbearia existe dentro do sistema;
        if (barbearia == null) {
            throw new ExceptionObjetoInexistente("* Informacoes faltantes ou incompletos na criacao da barbearia. ");
        }
        Leitor leitor = new Leitor();
        leitor.leEndereco(barbearia);

        barbearia.exibirMenu();
    }


    public void executarPrograma(Barbearia barbearia, Set <Cliente> clientes, Leitor leitor) throws Exception {
        final int QTDOPCOES = 3;

        int opcao = leitor.leOpcoes(QTDOPCOES);

        if ((opcao < 1) || (opcao > QTDOPCOES)) {
            throw new IllegalArgumentException("* Erro de leitura/entrada.");
        }
        if ((opcao == 1) || (opcao == 2)) {
            // Acesso à area do Pretador de Serviços (Barbeiro ou Administrador - do Sistema);
            System.out.print("\033[H\033[2J");
            System.out.flush();  

            if (opcao == 1) {
                Cliente cliente = (Cliente)leitor.lerLoginSenhaUsuarios(clientes);
                if (cliente == null) {
                    throw new ExceptionObjetoInexistente("Usuario ou objeto nao encontrado.");
                }
            }
            if (opcao == 2) {
                Barbeiro barbeiro = (Barbeiro)leitor.lerLoginSenhaUsuarios(barbearia.getBarbeiros());
                if (barbeiro == null) {
                    throw new ExceptionObjetoInexistente("Usuario ou objeto nao encontrado.");
                }
            }
        }
        if (opcao == 3) {
            // Imprime as informações da barearia (se as mesmas existirem!);
            try {
                barbearia.exibirInformacoes();
            } catch (NullPointerException p) {
                System.out.println("* Informacoes nao encontradas.");
            }
        }
    }
}
