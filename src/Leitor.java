import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
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
        InputStream arquivo = new FileInputStream(EnumCaminho.BARBEARIA.getValue());
        // Cria um objeto que permite ler os dados do arquivo da "Stream" criada;
         try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo))) {
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
                }else if (campos.length == EnumQtdDados.QTD_DADOS_ENDERECO_COMPLETO.getValue()) {
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

        File file = new File(EnumCaminho.CLIENTES.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.CLIENTES.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            ValidacaoQtdDados.validacao(campos, EnumCaminho.CLIENTES.getValue(), EnumQtdDados.QTD_DADOS_CLIENTES.getValue());

            clientes.put(campos[4], new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], LocalDate.parse(campos[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }

        return clientes;
    }



    public Map<String, Barbeiro>lerBarbeiro() throws Exception {
        Map<String, Barbeiro> barbeiros = new HashMap<>();

        File file = new File(EnumCaminho.BARBEIROS.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.BARBEIROS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            ValidacaoQtdDados.validacao(campos, EnumCaminho.BARBEIROS.getValue(), EnumQtdDados.QTD_DADOS_BARBEIRO.getValue());
            
            Barbeiro barbeiro = new Barbeiro(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], 
            LocalDate.parse(campos[6], DateTimeFormatter.ofPattern("dd/MM/yyyy")), Float.parseFloat(campos[7]));

            barbeiro.adicionarDisponibilidade(this.lerDisponibilidades(barbeiro.getCpf()));
            barbeiros.put(barbeiro.getCpf(), barbeiro);
        }
        this.lerEspecialidades(barbeiros, this.lerServico());

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

            administrador = new Administrador(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5]);
        }
        if (administrador == null) {
            throw new ExceptionObjetoInexistente("O Administrador não foi criado.");
        }
        return administrador;
    }



    public Map<Integer, Servico>lerServico() throws Exception {
        Map<Integer, Servico> servicos = new TreeMap<>();

        File file = new File(EnumCaminho.SERVICOS.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.SERVICOS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            ValidacaoQtdDados.validacao(campos, EnumCaminho.SERVICOS.getValue(), EnumQtdDados.QTD_DADOS_SERVICOS.getValue());

            servicos.put(Integer.valueOf(campos[0]), new Servico(Integer.parseInt(campos[0]), campos[1], campos[2], Float.parseFloat(campos[3])));
        }
        this.lerRecursosPorServico(servicos, this.lerRecurso());

        return servicos;
    }

    public Map<Integer, Recurso>lerRecurso() throws Exception {
        Map<Integer, Recurso> recursos = new TreeMap<>();

        File file = new File(EnumCaminho.RECURSOS.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.RECURSOS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
        boolean disponivel = true;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            ValidacaoQtdDados.validacao(campos, EnumCaminho.RECURSOS.getValue(), EnumQtdDados.QTD_DADOS_RECURSOS.getValue());
            
            if (campos[3].equals("DISPONIVEL")) {
                disponivel = true;
            }else if(campos[3].equals("INDISPONIVEL")){
                disponivel = false;
            }
            recursos.put(Integer.valueOf(campos[0]), new Recurso(Integer.parseInt(campos[0]), campos[1], campos[2], disponivel));
        }
        return recursos;
    }



    public void lerEspecialidades(Map<String,Barbeiro> barbeiros, Map<Integer, Servico> servicos) throws Exception{
        File file = new File(EnumCaminho.ESPECIALIDADES.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.ESPECIALIDADES.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        int contador = 0;
        String linha, identificador = "";
        
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (contador % 2 == 0) {
                identificador = linha;
            }else {
                if (barbeiros.containsKey(identificador)) {
                    Map<Integer, Servico> especialidades = new HashMap<>();
                    for (Map.Entry<Integer, Servico> valor : servicos.entrySet()) {                  
                        for (String id : campos) {
                            if (Objects.equals(valor.getKey(), Integer.valueOf(id))) {
                                especialidades.put(valor.getKey(), new Servico(valor.getValue()));
                            }
                        }
                    }
                    Barbeiro barbeiro = new Barbeiro(barbeiros.get(identificador));
                    barbeiro.adicionarEspecialidades(especialidades);

                    barbeiros.replace(identificador, barbeiro);
                }
            }
            contador++;
        }
    }

    public void lerRecursosPorServico(Map<Integer,Servico> servicos, Map<Integer, Recurso> recursos) throws Exception{
        File file = new File(EnumCaminho.RECURSOS.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.RECURSOS.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        int contador = 0;
        String linha, identificador = "";
        
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (contador % 2 == 0) {
                identificador = linha;
            }else {
                if (recursos.containsKey(Integer.valueOf(identificador))) {
                    Map<Integer, Recurso> recursosPorServico = new TreeMap<>();
                    for (Map.Entry<Integer, Recurso> valor : recursos.entrySet()) {                  
                        for (String id : campos) {
                            if (Objects.equals(valor.getKey(), Integer.valueOf(id))) {
                                recursosPorServico.put(valor.getKey(), new Recurso(valor.getValue()));
                            }
                        }
                    }
                    Servico servico = new Servico(servicos.get(Integer.valueOf(identificador)));
                    servico.adicionarRecursos(recursosPorServico);

                    servicos.replace(Integer.valueOf(identificador), servico);
                }
            }
            contador++;
        }
    }


    public List<Disponibilidade> lerDisponibilidades(String identificador) throws Exception {
        File file = new File(EnumCaminho.DISPONIBILIDADES.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        InputStream arquivo = new FileInputStream(EnumCaminho.DISPONIBILIDADES.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));

        long linhas = Files.lines(Path.of(EnumCaminho.DISPONIBILIDADES.getValue())).count();
        
        List<Disponibilidade> disponibilidades = new ArrayList<>();

        String linha, prestadorServicos = "";
        boolean livre = false;
        int contador = 0;
        
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            
            if (contador % 17 != 0) {
                if (prestadorServicos.equals(identificador)) {
                    if (campos[2].equals("DISPONIVEL")) {
                        livre = true;
                    }else if (campos[2].equals("INDISPONIVEL")) {
                        livre = false;
                    }
                    LocalTime horario = LocalTime.parse(campos[1], DateTimeFormatter.ofPattern("HH:mm"));
                    disponibilidades.add(new Disponibilidade(LocalDate.parse(campos[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")),horario, 
                                                            horario.plusMinutes(Servico.getDuracao()),  livre));
                }else {
                    contador += 16;
                }
            }else {
                prestadorServicos = campos[0];
            }
            
            if (contador >= linhas) {
                break;
            }
            contador++;
        }
        return disponibilidades;
    }


    
    public Map<Integer, Agendamento>lerAgendamento(Barbearia barbearia) throws Exception {
        File file = new File(EnumCaminho.AGENDAMENTO.getValue());

        if (!file.exists()) {
            file.createNewFile();          // cria o arquivo
        }

        Map<Integer, Agendamento> agendamentos = new HashMap<>();
        Map<String, Barbeiro> barbeiros = new HashMap<>(barbearia.getBarbeiros());

        InputStream arquivo = new FileInputStream(EnumCaminho.AGENDAMENTO.getValue());
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo));
        
        String linha;
            
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (barbearia.getClientes().containsKey(campos[1]) && (barbearia.getBarbeiros().containsKey(campos[2]))) {
                if (barbeiros.get(campos[2]).getEspecialidades().containsKey(Integer.valueOf(campos[3]))) {
                    Cliente cliente = new Cliente(barbearia.getClientes().get(campos[1]));
                    Barbeiro barbeiro = new Barbeiro(barbearia.getBarbeiros().get(campos[2]));
                    Servico servico = new Servico(barbeiros.get(campos[2]).getEspecialidades().get(Integer.valueOf(campos[3])));

                    agendamentos.put(Integer.valueOf(campos[0]), new Agendamento(Integer.parseInt(campos[0]), cliente, 
                    barbeiro, servico, LocalDate.parse(campos[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.parse(campos[5], DateTimeFormatter.ofPattern("HH:mm"))));
                }
            }
        }
        return agendamentos;
    }
}
