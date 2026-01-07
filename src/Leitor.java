import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Leitor {
    public Leitor() {
    }

    public void leEndereco(Map <String, Barbearia> barbearias, String chave) {
        final int SALTOS = 2;

         Endereco endereco = null;

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
                        ValidacaoCep.validacao(campos[3]);
                        endereco = new Endereco(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3]);
                    }else if (campos.length == 7) {
                        ValidacaoCep.validacao(campos[6]);
                        endereco = new Endereco(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], campos[5], campos[6]);
                    }

                    barbearias.get(chave).setEndereco(endereco);


                }catch (NumberFormatException n) {
                    System.out.println("* Entrada INVALIDA! Insira um dado numerico ao numero do endereco.");
                }catch (IllegalArgumentException s) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados descritivos de endereco.");
                }catch (ExceptionFormato c) {
                    System.out.println("* Entrada de dados INVALIDA! " + c.getMessage());
                }
            }
            


        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }
    }

    public void leDadosBarbearia(Map<String, Barbearia> barbearias) {
        // Número de linhas que serão ignoradas no arquivo de endereco;
        final int SALTOS = 2;



        Barbearia barbearia;
       
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
                    

                    barbearia = new Barbearia(informacoes[0], null, informacoes[1], informacoes[2], abertura, fechamento, informacoes[5]);

                    barbearias.put(informacoes[5], barbearia);


                }catch (ExceptionFormato em) {
                    System.out.println("* Entrada de dados INVALIDA! " + em.getMessage());
                }catch (IllegalArgumentException h) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados de horarios.");
                }catch (NullPointerException b) {
                    System.out.println("Erro! Dados do sistema da barbearia nao encontrados.");
                }
            }

            
        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }
    }
}
