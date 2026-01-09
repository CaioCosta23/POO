import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Leitor {
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


    public int leOpcoes(int qtdOpcoes) {
        int opcao = -1;

        Scanner entrada = new Scanner(System.in);
            try {
                System.out.printf("\n- Digite o numero da opcao: ");
                opcao = Integer.parseInt(entrada.nextLine());
                System.out.println("................................................................................................................");

                // Verifica se uma das opções listadas acima foi digitada, caso contrário, lança uma exceção informando ao usuário o problema;
                if ((opcao < 1) || (opcao > qtdOpcoes)) {
                    //entrada.close();
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException o) {
                System.out.println("* Opcao INVALIDA! Por Favor, digite uma das opcoes listadas acima.");
            }
            //entrada.close();
        return opcao;
    }


    public Cliente cadastrarCliente() {
        Scanner entrada = new Scanner(System.in);

        System.out.printf("- Informe o nome: ");
        String nome = entrada.nextLine();

        System.out.printf("- Insira um endereco de e-mail: ");
        String email = entrada.nextLine();
        //ValidacaoFormato.validacao(email, EnumFormato.EMAIL.FORMATO);

        System.out.printf("- Insira um telefone (com o DDD, como 027, por exemplo, e 9 digitos): ");
        String telefone = entrada.nextLine();

        System.out.printf("- Digite o CPF: ");
        String cpf = entrada.nextLine();

        System.out.printf("- Digite um nome de Login: ");
        String login = entrada.nextLine();

        System.out.printf("- Digite uma senha (com ao menos 5 caracteres): ");
        String senha = entrada.nextLine();

        System.out.printf("- Informe a data de nascimento (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();

        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //entrada.close();
        
        // O ID do cliente será registrado quando for feito o armazenamento do mesmo no arquivo;
        return new Cliente(-1, nome, email, telefone, cpf, login, senha, data);
    }


    public void armazenarUsuario(Usuario usuario) throws IOException {
        Path caminho = Path.of("dados/clientes.txt");
        
        if (!caminho.toFile().exists()) {
            usuario.setId(1);
        }else {
            long qtdUsuarios = Files.lines(caminho).count();
            usuario.setId((int)qtdUsuarios + 1);
        }

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter("dados/clientes.txt", true))) {

            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente)usuario;
                arquivo.write(cliente.toString());
                arquivo.newLine();
            }
            System.out.println("................................................................................................................");
            System.out.println("@ Cadastro realizado com sucesso!");

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel realizar o cadastro.");
        }
    }

    public Set<Cliente>lerCliente() {
        Set<Cliente> clientes = new HashSet<>();

        try (InputStream arquivoEndereco = new FileInputStream("dados/clientes.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));

            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                clientes.add(new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], LocalDate.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                
            }
        } catch(IOException c) {
            System.out.println("Lista de clientes nao encontrada.");
        }

        return clientes;
    }


    public Usuario lerLoginSenhaUsuarios(Object usuarios) {

        try(Scanner entrada = new Scanner(System.in)) {
            System.out.printf("@ Login: ");
            String login = entrada.nextLine();

            System.out.printf("@ Senha: ");
            String senha = entrada.nextLine();

            if (usuarios instanceof Map) {
                Map<String, ?> mapeados  = (Map<String, ?>)usuarios;
                
                for (Object valor : mapeados.entrySet()) {
                    if (valor instanceof Barbeiro) {
                        Barbeiro barbeiro = (Barbeiro)valor;

                        if (barbeiro.autenticar(login, senha)) {
                            return barbeiro;
                        }
                    }
                }
            }else if (usuarios instanceof Set){
                Set<?> mapeados = (Set<?>)usuarios;

                Iterator<?> iterador = mapeados.iterator();

                while(iterador.hasNext()) {
                    Object valor = iterador.next();

                    if (valor instanceof Cliente) {
                        Cliente cliente = (Cliente)valor;
                        if (cliente.autenticar(login, senha)) {
                            //cliente.exibirInformacoes();
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
        }
        return null;
    }
}
