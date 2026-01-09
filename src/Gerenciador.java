import java.util.Map;
import java.util.Set;

public class Gerenciador {
    private final static String CODIGO = "15.457.146/7923-48";
    
    public Gerenciador() {
    }

    public String getCodigo() {
        return CODIGO;
    }

    public void iniciarSistema(Map<String, Barbearia> barbearias) {
        // Verifica se a barbearia existe dentro do sistema;
        try {
            if (barbearias.isEmpty()) {
                throw new Exception("* Barbearia(s) inexistente(s) no sistema. ");
            }

            barbearias.get(CODIGO).exibirMenu();

        }catch (Exception v) {
            System.out.println(v.getMessage() + "Nao existem informacoes no sistema.");
        }
    }

    

    public void executarPrograma(Map <String, Barbearia> barbearias, Set <Cliente> clientes, Leitor leitor) {
        final int QTDOPCOES = 3;


        int opcao = leitor.leOpcoes(QTDOPCOES);
        try {
            if ((opcao < 1) || (opcao > QTDOPCOES)) {
                throw new Exception("* Erro de leitura/entrada.");
            }
            if ((opcao == 1) || (opcao == 2)) {
                // Acesso à area do Pretador de Serviços (Barbeiro ou Administrador - do Sistema);
                System.out.print("\033[H\033[2J");
                System.out.flush();

                if (opcao == 1) {
                    
                }
                if (opcao == 2) {

                }
            }
            if (opcao == 3) {
                // Imprime as informações da barearia (se as mesmas existirem!);
                try {
                    barbearias.get(CODIGO).exibirInformacoes();
                } catch (NullPointerException p) {
                    System.out.println("* Informacoes nao encontradas.");
                }
            }
        } catch (Exception o) {
            System.out.println(o.getMessage());
        }
    }
}
