import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class App {
    public static void main(String[] args) throws Exception {
        /*
         * Linha de Código que "limpa" o terminal e move o cursor para a primeira linha ao se executar novamente o programa;

         * OBS: Pode não funcionar em alguns consoles ou IDEs mais antigos(as);
         */
        System.out.print("\033[H\033[2J");
        System.out.flush();

        final String CODIGO = "15.457.146/7923-48";

        Map <String, Barbearia> barbearias = new HashMap<>();


        Set <Cliente> clientes = new HashSet<>();
        Map <String, Barbeiro> barbeiros = new HashMap<>();

        Leitor leitor = new Leitor();

        leitor.leArquivos(barbearias);


        try (Scanner entrada = new Scanner(System.in)) {
            try {
                // Verifica se a barbearia existe dentro do sistema;
                if (barbearias.isEmpty()) {
                    throw new Exception();
                }


                // Menu de exibição;
                System.out.println("===============================================================================================================");
                System.out.println("\t\t\t\t\t" + barbearias.get(CODIGO).getNome());
                System.out.println("===============================================================================================================");

                System.out.println("-> Bem-Vindo!\n");

                System.out.println("# Informe o servico do qual deseja acessar:\n");

                System.out.println("\t[1] Acessar area do cliente\t[2] Acessar area do prestador de servico");
                System.out.println("\t[3] Avaliacoes             \t[4] Informacoes");


                try {
                    System.out.printf("\n- Digite o numero da opcao: ");
                    int opcao = entrada.nextInt();
                    System.out.println("................................................................................................................");

                    // Verifica se uma das opções listadas acima foi digitada, caso contrário, lança uma exceção informando ao usuário o problema;
                    if ((opcao < 1) || (opcao > 4)) {
                        throw new IllegalArgumentException();
                    }


                    switch(opcao) {
                        // Registro e consulta de clientes;
                        case 1:
                            try {
                                
                            } catch (NullPointerException c) {
                                System.out.println("ERRO! Falha no registro de cliente!");
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            // Imprime as informações da barearia (se as mesmas existirem!);
                            try {
                                barbearias.get(CODIGO).exibirInformacoes();
                            } catch (NullPointerException p) {
                                System.out.println("* Informacoes nao encontradas.");
                            }
                            break;
                        default:
                            throw new Exception("* Erro de leitura/entrada.");
                    
                    }
                } catch (IllegalArgumentException o) {
                    System.out.println("* Opcao INVALIDA! Por Favor, digite uma das opcoes listadas acima (1, 2, 3 ou 4).");
                }
                

            } catch (Exception b) {
                System.out.println("* O sistema ainda nao possui informacoes.");
            }
        }
    }
}
