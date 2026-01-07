import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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



        // Número de linhas que serão ignoradas no arquivo de endereco;
        final int SALTOS = 2;



        Barbearia barbearia = null;
        Endereco endereco = null;


        Set <Cliente> clientes = new HashSet<>();
        Map <String, Barbeiro> barbeiros = new HashMap<>();



        // Lê o caminho de endereço do arquivo (que neste caso está dentro de uma subpasta);
        try (InputStream arquivoEndereco = App.class.getResourceAsStream("/dados/endereco.txt")) {
            // Cria um objeto que permite ler os dados do arquivo da "Stream" criada;
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            String linha;


            // Lê as linhas do arquivo e as consome no buffer (fazendo com que sejam ignoradas), sem afetar leituras futuras;
                for (int l = 0; l < SALTOS; l++) {
                    br.readLine();
                }

                
            while ((linha = br.readLine()) != null) {
                /*
                 * Lê o a(s) linhas do arquivo (de forma parecida com um arquivo .csv) e divide as informações, usando como referência o ";"
                 * indicando onde começa e onde termina cada dado/informação;
                */
                String[] campos = linha.split(";");


                // Trata os erros de conversão de informações para os seus respectivos tipos;
                try {

                    // Verifica se é um endereço simples ou um endereço completo e cria o mesmo com as informações retiradas da(s) linha(s) do arquivo;
                    if (campos.length == 4) {
                        endereco = new Endereco(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3]);
                    }else if (campos.length == 7) {
                        endereco = new Endereco(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], campos[5], campos[6]);
                    }


                }catch (NumberFormatException n) {
                    System.out.println("* Entrada INVALIDA! Insira um dado numerico ao numero do endereco.");
                }catch (IllegalArgumentException s) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados descritivos de endereco.");
                }
            }
            


        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }



        // Lê o caminho de endereço do arquivo (que neste caso está dentro de uma subpasta);
        try (InputStream arquivoInformacoes = App.class.getResourceAsStream("/dados/infoBarbearia.txt")) {
            // Cria um objeto que permite ler os dados do arquivo da "Stream" criada;
            BufferedReader info = new BufferedReader(new InputStreamReader(arquivoInformacoes));
            String dados;


            // Lê as linhas do arquivo e as consome no buffer (fazendo com que sejam ignoradas), sem afetar leituras futuras;
                for (int l = 0; l < SALTOS; l++) {
                    info.readLine();
                }


            while ((dados = info.readLine()) != null) {
                /*
                 * Lê o a(s) linhas do arquivo (de forma parecida com um arquivo .csv) e divide as informações, usando como referência o ";"
                 * indicando onde começa e onde termina cada dado/informação;
                */
                String[] informacoes = dados.split(";");


                // Trata os erros de conversão de informações para os seus respectivos tipos;
                try {
                    // Faz a validação dos respectivos dados e verifica se os mesmos estão no formato correto;
                    ValidacaoEmail.validacao(informacoes[2]);
                    ValidacaoHorario.validacao(informacoes[3]);
                    ValidacaoHorario.validacao(informacoes[4]);
                    ValidacaoCnpj.validacao(informacoes[5]);

                    
                    LocalTime abertura = LocalTime.parse(informacoes[3], DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime fechamento = LocalTime.parse(informacoes[4], DateTimeFormatter.ofPattern("HH:mm"));
                    

                    barbearia = new Barbearia(informacoes[0], endereco, informacoes[1], informacoes[2], abertura, fechamento, informacoes[5]);


                }catch (ExceptionFormato em) {
                    System.out.println("* Entrada de dados INVALIDA! " + em.getMessage());
                }catch (IllegalArgumentException h) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados de horarios.");
                }
            }

            
        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }



        try (Scanner entrada = new Scanner(System.in)) {
            try {
                // Verifica se a barbearia existe dentro do sistema;
                if (barbearia == null) {
                    throw new NullPointerException();
                }


                // Menu de exibição;
                System.out.println("===============================================================================================================");
                System.out.println("\t\t\t\t\t" + barbearia.getNome());
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
                                barbearia.exibirInformacoes();
                            } catch (NullPointerException p) {
                                System.out.println("* Informacoes nao encontradas.");
                            }
                            break;
                        default:
                            throw new Exception("* Erro de leitura/entrada.");
                    
                    }
                } catch (IllegalArgumentException o) {
                    System.out.println("* Opcao INVALIDA! Por Favor, digite uma das opcoes listadas acima (1, 2, 3 ou 4).");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                

            } catch (NullPointerException i) {
                System.out.println("* Informacoes do sistema nao encontradas.");
            }
        }
    }
}
