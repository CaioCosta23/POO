import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Leitor {
    private static final Scanner entrada = new Scanner(System.in);

    public Leitor() {
    }

    public Barbearia leDadosBarbearia() throws Exception {
        Barbearia barbearia = null;

        // Número de linhas que serão ignoradas no arquivo (Linhas de comentarios);
        final int SALTOS = 2;
       
        // Lê o caminho de endereço do arquivo (que neste caso está dentro de uma subpasta);
        InputStream arquivo= new FileInputStream(EnumCaminho.BARBEARIA.getValue());
         try (// Cria um objeto que permite ler os dados do arquivo da "Stream" criada;
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo))) {
            String linha;
            /* 
             * Lê as linhas do arquivo e as consome no buffer (fazendo com que sejam ignoradas), sem afetar leituras futuras;
             * estas linhas são a linha de instruções e uma quebra de linha. Caso elas sejam retiradas do arquivo, pode-se retirar esse trecho 'for';
            */
            for (int l = 0; l < SALTOS; l++) {
                br.readLine();
            }

            while ((linha = br.readLine()) != null) {
                /*
                    * Lê o a(s) linhas do arquivo (de forma parecida com um arquivo .csv) e divide as informações, usando como referência o ";"
                    * indicando onde começa e onde termina cada dado/informação;
                */
                String[] campos = linha.split(";");

                // Valida a quantidade de dados que possui cada linha do arquivo lido;
                ValidacaoQtdDados.validacao(campos, EnumCaminho.BARBEARIA.getValue(), EnumQtdDados.QTD_DADOS_BARBEARIA.getValue());

                // Valida alguns dos dados da barbearia;
                ValidacaoFormato.validacao(campos[2], EnumFormato.EMAIL.FORMATO);
                ValidacaoFormato.validacao(campos[3], EnumFormato.HORARIO.FORMATO);
                ValidacaoFormato.validacao(campos[4], EnumFormato.HORARIO.FORMATO);
                ValidacaoFormato.validacao(campos[5], EnumFormato.CNPJ.FORMATO);

                // Coverte a informação de horário lida de "String" para "LocalTime";
                LocalTime abertura = LocalTime.parse(campos[3], DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime fechamento = LocalTime.parse(campos[4], DateTimeFormatter.ofPattern("HH:mm"));
                    
                // Cria a barbearia com as informações lidas no arquivo (e o endereço será lido em outro método - aqui ele apenas é "inicializado");
                barbearia = new Barbearia(campos[0], this.leEndereco(barbearia), campos[1], campos[2], abertura, fechamento, campos[5], this.lerAdministrador());    
            }
        }

        if (barbearia == null) {
            throw new NullPointerException(" (arquivo) de barbearia");
        }
        return barbearia;
    }



    public Endereco leEndereco(Barbearia barbearia) throws Exception {
        Endereco endereco = null;

        final int SALTOS = 2;

        InputStream arquivo = new FileInputStream(EnumCaminho.ENDERECO.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));

        String linha;
        for (int l = 0; l < SALTOS; l++) {
            br.readLine();
        }
                
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            /*
             * Verifica se é um endereço simples ou um endereço completo e cria o mesmo com as informações retiradas da(s) linha(s) do arquivo;
             * Mesmo que algum dos dados dê algum problema de formato, o endereço ainda é criado, mas com campos 'setados' para valores que representam "vazio";

             * OBS: Neste caso não é possível usar a função de validação de quantidade de dados por ser duas possibilidades quantidade de dados;
            */
            try {
                if (campos.length == EnumQtdDados.QTD_DADOS_ENDERECO_SIMPLES.getValue()) {
                    ValidacaoFormato.validacao(campos[3], EnumFormato.CEP.FORMATO);
                    endereco = new Endereco(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3]);
                }else if (campos.length == EnumQtdDados.QTD_DADOS_EMDERECO_COMPLETO.getValue()) {
                    ValidacaoFormato.validacao(campos[6], EnumFormato.CEP.FORMATO);
                    endereco = new Endereco(campos[0], Integer.parseInt(campos[1]), campos[2], campos[3], campos[4], campos[5], campos[6]);
                }
            }catch (ExceptionFormato c) {
                System.out.println("|AVISO|");
                System.out.println(c.getMessage() + " Erro na validacao do formato de CEP.");
                System.out.println("* O endereco criado com campos nulos/incompletos e sem informacoes.");

                endereco = new Endereco(-1 , "-", "-", "-");
                try {
                    // Cria um tempo de espera (em segundos) no terminal antes de executar as próximas açõe;
                    TimeUnit.SECONDS.sleep(6);
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } catch (IllegalArgumentException q) {
                System.out.println("|AVISO|");
                System.out.println("Erro na leitura de campos de endereco! " + q.getMessage());
                System.out.println("* O endereco criado com campos nulos/incompletos e sem informacoes.");

                endereco = new Endereco(-1 , "-", "-", "-");
                try {
                    TimeUnit.SECONDS.sleep(6);
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        if (endereco == null) {
            throw new ExceptionObjetoInexistente(" Endereco nao lido corretamente.");
        }
        return endereco;
    }



    public int leOpcoes() {
        System.out.printf("\n- Digite o numero da opcao: ");
        int opcao = Integer.parseInt(entrada.nextLine());
        System.out.println("................................................................................................................");
            
        return opcao;
    }



    public String leIdentificadorUsuario() {
        System.out.printf("- Digite o CPF cliente ou prestador de servico: ");
        String identificador = entrada.nextLine();
        System.out.println("................................................................................................................");
            
        return identificador;
    }



    public Map<String, Cliente>lerCliente() throws Exception {
        Map<String, Cliente> clientes = new HashMap<>();

        String caminho = EnumCaminho.CLIENTES.getValue();

        InputStream arquivo = new FileInputStream(caminho);
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            ValidacaoQtdDados.validacao(campos, caminho, EnumQtdDados.QTD_DADOS_CLIENTES.getValue());

            clientes.put(campos[4], new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], LocalDate.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }
        return clientes;
    }



    public Map<String, Barbeiro>lerBarbeiro() throws Exception {
        Map<String, Barbeiro> barbeiros = new HashMap<>();

        InputStream arquivo = new FileInputStream(EnumCaminho.BARBEIROS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            ValidacaoQtdDados.validacao(campos, EnumCaminho.BARBEIROS.getValue(), EnumQtdDados.QTD_DADOS_BARBEIRO.getValue());

            Barbeiro barbeiro = new Barbeiro(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], 
            LocalDate.parse(campos[7], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Float.parseFloat(campos[8]));

            barbeiro.adicionarDisponibilidade(this.leDisponibilidades());

            barbeiros.put(campos[4], barbeiro);
            
            
        }
        return barbeiros;
    }
    

    public Administrador lerAdministrador() throws Exception {
        final int SALTOS = 2;
        Administrador administrador = null;

        InputStream arquivo = new FileInputStream(EnumCaminho.ADMINISTRADOR.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
        for (int l = 0; l < SALTOS; l++) {
            br.readLine();
        }
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            ValidacaoQtdDados.validacao(campos, EnumCaminho.ADMINISTRADOR.getValue(), EnumQtdDados.QTD_DADOS_ADMINISTRADOR.getValue());

            administrador = new Administrador(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6]);
        }
        if (administrador == null) {
            throw new ExceptionObjetoInexistente("O Administrador não foi criado.");
        }
        return administrador;
    }



    public Map<Integer, Servico>lerServico() throws Exception {
        Map<Integer, Servico> servicos = new HashMap<>();

        InputStream arquivo = new FileInputStream(EnumCaminho.SERVICOS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            ValidacaoQtdDados.validacao(campos, EnumCaminho.SERVICOS.getValue(), EnumQtdDados.QTD_DADOS_SERVICOS.getValue());

            servicos.put(Integer.valueOf(campos[0]), new Servico(Integer.parseInt(campos[0]), campos[1], campos[2], Float.parseFloat(campos[3])));
        }
        return servicos;
    }


    public Map<String, Barbeiro> lerEspecialidades(Map<String,Barbeiro> barbeiros, Map<Integer, Servico> servicos) throws Exception{
        InputStream arquivo = new FileInputStream(EnumCaminho.SERVICOS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));

        Map<String, Barbeiro> copiaBarbeiros = new HashMap<>(barbeiros);

        int contador = 0;
        String linha, identificador = "";
        
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (contador % 2 == 0) {
                identificador = campos[0];
            }else {
                for (Map.Entry<Integer, Servico> valor : servicos.entrySet()) {
                    for (String campo : campos) {
                        if (Objects.equals(valor.getKey(), Integer.valueOf(campo))) {
                            copiaBarbeiros.get(identificador).getServicos().put(Integer.valueOf(campo), valor.getValue());
                        }
                    }
                }
            }
            contador++;
        }
        return copiaBarbeiros;
    }



    public List<Disponibilidade> leDisponibilidades() throws Exception {
        InputStream arquivo = new FileInputStream(EnumCaminho.DISPONIBILIDADES.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));

        
        List<Disponibilidade> disponibilidades = new ArrayList<>();

        String linha;
        boolean livre = false;
        
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (campos[0].length() != 11) {
                if (campos[2].equals("DISPONIVEL")) {
                    livre = true;
                }else if (campos[2].equals("INDISPONIVEL")) {
                    livre = false;
                }

                LocalTime horario = LocalTime.parse(campos[1], DateTimeFormatter.ofPattern("HH:mm"));
                disponibilidades.add(new Disponibilidade(LocalDate.parse(campos[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")),horario, 
                                                        horario.plusMinutes(Servico.getDuracao()),  livre));
            }
            
        }
        return disponibilidades;
    }


/*     
    public Map<Integer, Agendamento>lerAgendamento(Map<String, Cliente> clientes, Barbearia barbearia) {
        Map<Integer, Agendamento> agendamentos = new HashMap<>();
        Map<String, Barbeiro> barbeiros = new HashMap<>(barbearia.getBarbeiros());

        try (InputStream arquivoEndereco = new FileInputStream("dados/agendamentos.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(arquivoEndereco));
            
            String linha;
                
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                
                if (clientes.containsKey(campos[1]) && (barbearia.getBarbeiros().containsKey(campos[2]))) {
                    if (barbeiros.get(campos[2]).getServicos().containsKey(Integer.valueOf(campos[3]))) {
                        Cliente cliente = new Cliente(clientes.get(campos[1]));
                        Barbeiro barbeiro = new Barbeiro(barbearia.getBarbeiros().get(campos[2]));
                        Servico servico = new Servico(barbeiros.get(campos[2]).getServicos().get(Integer.valueOf(campos[3])));
                        Disponibilidade disponibilidade = new Disponibilidade(barbeiro.getDisponibilidade().get(0));

                        agendamentos.put(Integer.valueOf(campos[0]), new Agendamento(Integer.parseInt(campos[0]), cliente, 
                        barbeiro, servico, disponibilidade.getData()));
                    }
                }
                
            }
        } catch(IOException c) {
            System.out.println("Erro na leitura de dados e do arquivo da lista de barbeiros");
        }
        return agendamentos;
    }
*/



    public Usuario lerLoginSenhaUsuarios(Object usuarios) {
        Usuario usuario = null;

        System.out.printf("@ Login: ");
        String login = entrada.nextLine();

        System.out.printf("@ Senha: ");
        String senha = entrada.nextLine();

        if (usuarios instanceof Administrador) {
            Administrador administrador = (Administrador)usuarios;
            if (administrador.autenticar(login, senha)) {
                return administrador;
            }
        }else {
            Map<String, ?> mapeados  = (Map<String, ?>)usuarios;
            
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
        }
        return usuario;
    }
}
