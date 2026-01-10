import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Leitor {
    private static final Scanner entrada = new Scanner(System.in);

    public Leitor() {
    }

    public Barbearia leDadosBarbearia(Barbearia barbearia) {
        // Número de linhas que serão ignoradas no arquivo de endereco;
        final int SALTOS = 2;
       
        // Lê o caminho de endereço do arquivo (que neste caso está dentro de uma subpasta);
        try (InputStream arquivoInformacoes = new FileInputStream("dados/infoBarbearia.txt")) {
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

                // Trata os erros de conversão e erros de leituras de informações e para os seus respectivos tipos;
                try {
                    ValidacaoFormato.validacao(informacoes[2], EnumFormato.EMAIL.FORMATO);
                    ValidacaoFormato.validacao(informacoes[3], EnumFormato.HORARIO.FORMATO);
                    ValidacaoFormato.validacao(informacoes[4], EnumFormato.HORARIO.FORMATO);
                    ValidacaoFormato.validacao(informacoes[5], EnumFormato.CNPJ.FORMATO);

                    // Coverte a informação de horário lida de "String" para "LocalTime";
                    LocalTime abertura = LocalTime.parse(informacoes[3], DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime fechamento = LocalTime.parse(informacoes[4], DateTimeFormatter.ofPattern("HH:mm"));
                    
                    // Cria a barbearia com as informações lidas no arquivo (e o endereço será lido em outro método - aqui ele apenas é "inicializado");
                    barbearia = new Barbearia(informacoes[0], null, informacoes[1], informacoes[2], abertura, fechamento, informacoes[5]);
                }catch (ExceptionFormato em) {
                    System.out.println(em.getMessage());
                }catch (IllegalArgumentException h) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados de horarios.");
                }catch (NullPointerException b) {
                    System.out.println("Erro! Dados do sistema da barbearia nao encontrados.");
                }
            }
        // Trata os erros de leitura do arquivo;
        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }
        return barbearia;
    }


    public void leEndereco(Barbearia barbearia) {
        final int SALTOS = 2;

        try (InputStream arquivoEndereco = new FileInputStream("dados/endereco.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));

            String linha;

            for (int l = 0; l < SALTOS; l++) {
                br.readLine();
            }
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                
                try {
                    /*
                     * Verifica se é um endereço simples ou um endereço completo e cria o mesmo com as informações retiradas da(s) linha(s) do arquivo;

                     *OBS: Verifique o arquivo de entrada "endereco.txt" para entender melhor o formato dos dados;
                    */
                    if (campos.length == 4) {
                        ValidacaoFormato.validacao(campos[3], EnumFormato.CEP.FORMATO);
                        barbearia.setEndereco(new Endereco(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3]));
                    }else if (campos.length == 7) {
                        ValidacaoFormato.validacao(campos[6], EnumFormato.CEP.FORMATO);
                        barbearia.setEndereco(new Endereco(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], campos[5], campos[6]));
                    }
                }catch (NumberFormatException n) {
                    System.out.println("* Entrada INVALIDA! Insira um dado numerico ao numero do endereco.");
                }catch (IllegalArgumentException s) {
                    System.out.println("Entrada INVALIDA! Erro de leitura para os dados descritivos de endereco.");
                }catch (ExceptionFormato c) {
                    System.out.println("|AVISO|");
                    System.out.println(c.getMessage() + " Erro na validacao do formato de CEP.");
                    System.out.println("* O endereco criado com campos nulos/incompletos e sem informacoes.");
                    barbearia.setEndereco(new Endereco(-1 , "-", "-", "-"));
                    
                    try {
                        // Cria um tempo de espera (em segundos) no terminal antes de executar as próximas açõe;
                        TimeUnit.SECONDS.sleep(6);

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException i) {
            System.out.println("* Erro na leitura/exibicao (do arquivo) de informacoes.");
        }
    }


    public int leOpcoes() {
        System.out.printf("\n- Digite o numero da opcao: ");
        int opcao = Integer.parseInt(entrada.nextLine());
        System.out.println("................................................................................................................");
            
        return opcao;
    }

    public String leIdentificadorUsuario() {
        System.out.printf("\n- Digite o CPF (caso cliente) ou CNPJ (caso prestador de servico): ");
        String identificador = entrada.nextLine();
        System.out.println("................................................................................................................");
            
        return identificador;
    }    


    public Map<String, Cliente>lerCliente() throws ExceptionObjetoInexistente {
        Map<String, Cliente> clientes = new HashMap<>();

        try (InputStream arquivoEndereco = new FileInputStream("dados/clientes.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                clientes.put(campos[4], new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], LocalDate.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                
            }
        } catch(IOException c) {
            System.out.println("................................................................................................................");
            System.out.println("\nt* Lista de clientes vazia! Nenhum cliente registrado ate o momento.\n");
        }
        return clientes;
    }

    public Map<String, Barbeiro>lerBarbeiro() {
        Map<String, Barbeiro> barbeiros = new HashMap<>();

        try (InputStream arquivoEndereco = new FileInputStream("dados/barbeiros.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                barbeiros.put(campos[4], new Barbeiro(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], LocalDate.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Float.parseFloat(campos[9])));
                
            }
        } catch(IOException c) {
            System.out.println("................................................................................................................");
            System.out.println("\n* Lista de barbeiros vazia! Nenhum barbeiro foi registrado ate o momento.");
        }
        return barbeiros;
    }

    public Administrador lerAdministrador() throws Exception {
        final int SALTOS = 2;
        Administrador administrador = null;

        try (InputStream arquivoEndereco = new FileInputStream("dados/administrador.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;

            for (int l = 0; l < SALTOS; l++) {
                br.readLine();
            }
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                administrador = new Administrador(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6]);
            }
            if (administrador == null) {
                throw new ExceptionObjetoInexistente("");
            }
        } catch(IOException c) {
            System.out.println("Administrador nao registrado.");
        }
        return administrador;
    }



    public Map<Integer, Servico>lerServico() {
        Map<Integer, Servico> servicos = new HashMap<>();

        try (InputStream arquivoEndereco = new FileInputStream("dados/servicos.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                servicos.put(Integer.valueOf(campos[0]), new Servico(Integer.parseInt(campos[0]), campos[1], campos[2], Float.parseFloat(campos[3])));
                
            }
        } catch(IOException c) {
            System.out.println("Lista de servicos vazia! Nenhum servico registrado ate o momento.");
        }
        return servicos;
    }
/* 
    public Map<Integer, Agendamento>lerAgendamento() {
        Map<Integer, Agendamento> agendamentos = new HashMap<>();

        try (InputStream arquivoEndereco = new FileInputStream("dados/clientes.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                servicos.put(Integer.valueOf(campos[0]), new Agendamento(Integer.parseInt(), cliente, barbeiro, servico, disponibilidade.getData(), EnumStatusAgend.AGENDADO));
                
            }
        } catch(IOException c) {
            System.out.println("Erro na leitura de dados e do arquivo da lista de barbeiros");
        }
        return servicos;
    }
*/

    public Usuario lerLoginSenhaUsuarios(Object usuarios) {

        System.out.printf("@ Login: ");
        String login = entrada.nextLine();

        System.out.printf("@ Senha: ");
        String senha = entrada.nextLine();

        if (usuarios instanceof Map) {
            Map<Integer, ?> mapeados  = (Map<Integer, ?>)usuarios;
            
            for (Object valor : mapeados.entrySet()) {
                if (valor instanceof Barbeiro) {
                    Barbeiro barbeiro = (Barbeiro)valor;

                    if (barbeiro.autenticar(login, senha)) {
                        return barbeiro;
                    }
                }else if (valor instanceof Cliente) {
                    Cliente cliente = (Cliente)valor;

                    if (cliente.autenticar(login, senha)) {
                        return cliente;
                    }
                }
            }
        }else if (usuarios instanceof Administrador) {
            Administrador administrador = (Administrador)usuarios;
            if (administrador.autenticar(login, senha)) {
                return administrador;
            }
        }
        return null;
    }
}
