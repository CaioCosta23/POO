import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;


public class Registrador {
    private static final Scanner entrada = new Scanner(System.in);
    private static int geradorId;

    public Registrador() {   
        UUID uuid = UUID.randomUUID();
        Registrador.geradorId = uuid.hashCode() ^ (int) System.currentTimeMillis();

        if (Registrador.geradorId < 0) {
            Registrador.geradorId *= -1;
        }
    }

    public Cliente cadastrarCliente() {
        System.out.printf("- Informe o nome: ");
        String nome = entrada.nextLine();

        System.out.printf("- Insira um endereco de e-mail: ");
        String email = entrada.nextLine();
        //ValidacaoFormato.validacao(email, EnumFormato.EMAIL.FORMATO);

        System.out.printf("- Insira um telefone (com o DDD, como 027, por exemplo, e 9 digitos): ");
        String telefone = entrada.nextLine();

        System.out.printf("- Digite o CPF: ");
        String cpf = entrada.nextLine();

        System.out.printf("- Digite uma senha (com ao menos 5 caracteres): ");
        String senha = entrada.nextLine();

        System.out.printf("- Informe a data de nascimento (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();

        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        // O ID do cliente será registrado quando for feito o armazenamento do mesmo no arquivo;
        return new Cliente(-1, nome, email, telefone, cpf, senha, data);
    }


    public Barbeiro cadastrarBarbeiro() {
        System.out.printf("- Informe o nome: ");
        String nome = entrada.nextLine();

        System.out.printf("- Insira um endereco de e-mail: ");
        String email = entrada.nextLine();
        //ValidacaoFormato.validacao(email, EnumFormato.EMAIL.FORMATO);

        System.out.printf("- Insira um telefone (com o DDD, como 027, por exemplo, e 9 digitos): ");
        String telefone = entrada.nextLine();

        System.out.printf("- Digite o CPF: ");
        String cpf = entrada.nextLine();

        System.out.printf("- Digite uma senha (com ao menos 5 caracteres): ");
        String senha = entrada.nextLine();

        System.out.printf("- Informe a data de admissao (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();
        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.printf("- Digite o valor do salario a ser recebido (em R$): ");
        Float salario = Float.valueOf(entrada.nextLine());

        //entrada.close();

        Barbeiro barbeiro = new Barbeiro(-1, nome, email, telefone, cpf, senha, data, salario);

        barbeiro.adicionarDisponibilidade(criarDisponibilidade());
        
        // O ID do barbeiro será registrado quando for feito o armazenamento do mesmo no arquivo;
        return barbeiro;
    }



    public Barbeiro cadastrarEspecialidade(Barbeiro barbeiro, Map<Integer, Servico> servicos) throws Exception{
        Map<Integer, Servico> especialidades = new HashMap<>();

        System.out.printf("- Digite o numero do ID do servico: ");
        int idServico = Integer.parseInt(entrada.nextLine());

        if (servicos.containsKey(idServico)) {
            Servico servico = servicos.get(idServico);
            especialidades.put(idServico, servico);
            barbeiro.adicionarEspecialidades(especialidades);
        }else {
            System.out.println("* Servico nao disponivel no catalogo.");
        }
        return barbeiro;
    }



    public List<Disponibilidade> criarDisponibilidade() {
        LocalTime horario = LocalTime.of(Barbearia.getAbertura().getHour(), Barbearia.getAbertura().getMinute());
        LocalTime ultimoHorario = LocalTime.of(Barbearia.getFechamento().getHour(), Barbearia.getFechamento().getMinute());

        LocalDate data = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<Disponibilidade> disponibilidades = new ArrayList<>();
        boolean disponivel = true;

        while(!(horario.isAfter(ultimoHorario))) {
            if (LocalTime.now().isAfter(horario)) {
                disponivel = false;
            }
            Disponibilidade disponibilidade = new Disponibilidade(data, horario, horario.plusMinutes(Servico.getDuracao()), disponivel);
            disponibilidades.add(disponibilidade);
            horario = horario.plusMinutes(Servico.getDuracao());
        }
        return disponibilidades;
    }



    public Servico cadastrarServico() {
        System.out.printf("- Informe o nome: ");
        String nome = entrada.nextLine();

        System.out.printf("- Insira a descricao do servico: ");
        String descricao = entrada.nextLine();
        //ValidacaoFormato.validacao(email, EnumFormato.EMAIL.FORMATO);

        System.out.printf("- Digite o preco do servico: ");
        float preco = Float.parseFloat(entrada.nextLine());

        return new Servico(-1, nome, descricao, preco);
    }



    public Agendamento criarAgendamento(Barbearia barbearia) throws Exception {
        Map<String, Barbeiro> barbeiros = new HashMap<>(barbearia.getBarbeiros());
        Map<Integer, Servico> servicos = new HashMap<>(barbearia.getServicos());


        Leitor leitor = new Leitor();

        String numeroCliente = leitor.leIdentificadorUsuario();
        Cliente cliente = null;
        
        if (barbearia.getClientes().containsKey(numeroCliente)) {
            cliente = new Cliente(barbearia.getClientes().get(numeroCliente));
        }else {
            throw new ExceptionObjetoInexistente(" cliente nao registrado.");
        }


        System.out.println("* Selecione o servico a ser prestado: ");
        for(Map.Entry<Integer, Servico> valor : servicos.entrySet()) {
            valor.getValue().exibirInformacoes();
            System.out.println();
        }

        int opcaoServico = (int)leitor.leOpcoes();
        if (servicos.containsKey(opcaoServico)) {
            Servico servico = new Servico(servicos.get(opcaoServico));

            System.out.println("* Selecione o prestador de servico que desejado: ");
            for(Map.Entry<String, Barbeiro> valor : barbeiros.entrySet()) {
                if (valor.getValue().getEspecialidades().containsKey(opcaoServico)) {
                    System.out.printf("(%s) %s\n", valor.getValue().getCpf(), valor.getValue().getNome());
                }
            }

            String opcaoBarbeiro = leitor.leIdentificadorUsuario();
            if (barbeiros.containsKey(opcaoBarbeiro)) {
                Barbeiro barbeiro = new Barbeiro(barbeiros.get(opcaoBarbeiro));

                Iterator<Disponibilidade> impressao = barbeiro.getDisponibilidade().iterator();

                System.out.println("* Selecione o horario desejado: ");
                while(impressao.hasNext()) {
                    Disponibilidade disponibilidade = impressao.next();
                    disponibilidade.exibirInformacoes();
                    System.out.println();
                }

                System.out.printf("- Digite o horario inicial para selecionar um dos horarios: ");
                LocalTime horario = LocalTime.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

                Iterator<Disponibilidade> iterador = barbeiro.getDisponibilidade().iterator();

                Disponibilidade disponibilidade = null;
                while(iterador.hasNext()) {
                    disponibilidade = iterador.next();
                    if (disponibilidade.getHoraInicio().equals(horario)) {
                        if (!(disponibilidade.isDisponivel())) {
                            throw new IllegalArgumentException(" o horario selecionado nao se encontra disponivel");
                        }
                        disponibilidade.setDisponivel(false);
                        break;
                    }
                }
                if (disponibilidade == null) {
                    throw new ExceptionObjetoInexistente(" Data e/ou horario indisponivel ou inexistente");
                }

                return new Agendamento(-1, cliente, barbeiro, servico, disponibilidade.getData(), horario);
            }else {
                throw new ExceptionObjetoInexistente(" O barbeiro selecionado nao esta registrado.");
            }
        }else {
            throw new ExceptionObjetoInexistente(" O servico selecionado nao esta registrado.");
        }
    }



    public void armazenarUsuario(Usuario usuario, String caminhoUsuario) throws Exception {
        usuario.setId(geradorId);

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoUsuario, true))) {

            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente)usuario;
                arquivo.write(cliente.toString());
                arquivo.newLine();
            }
            if (usuario instanceof Barbeiro) {
                Barbeiro barbeiro = (Barbeiro)usuario;
                arquivo.write(barbeiro.toString());
                arquivo.newLine();
            }
            System.out.println("................................................................................................................");
            System.out.println("@ Usuario cadastrado com sucesso!");
        }
    }



    public void armazenarDisponibilidade(List<Disponibilidade> disponibilidades, String identificadorBarbeiro) throws Exception {

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(EnumCaminho.DISPONIBILIDADES.getValue(), true))) {
            arquivo.write(identificadorBarbeiro);
            arquivo.newLine();

            Iterator<Disponibilidade> iterador = disponibilidades.iterator();

            while(iterador.hasNext()) {
                arquivo.write(iterador.next().toString());
                arquivo.newLine();
            }

            // Impressão de verificação para o programador;
            //System.out.println("Disponibilidades armazenadas com sucesso.");
        }
    }
    


    public void armazenarServico(Servico servico) throws Exception {
        servico.setId(geradorId);

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(EnumCaminho.SERVICOS.getValue(), true))) {
            arquivo.write(servico.toString());
            arquivo.newLine();

            System.out.println("................................................................................................................");
            System.out.println("@ Servico cadastrado com sucesso!");
        }
    }



    public void armazenarAgendamento(Agendamento agendamento) throws IOException {
        agendamento.setId(geradorId);

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(EnumCaminho.AGENDAMENTO.getValue(), true))) {
            arquivo.write(agendamento.toString());
            arquivo.newLine();

            System.out.println("................................................................................................................");
            System.out.println("@ Agendamento registrado com sucesso!");

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel registrar o agendamento.");
        }
    }

    public void armazenarEspecialidades(Barbeiro barbeiro) throws Exception {

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(EnumCaminho.DISPONIBILIDADES.getValue(), true))) {
            arquivo.write(barbeiro.getCpf());
            arquivo.newLine();

            TreeMap<Integer, Servico> lista = new TreeMap<>(barbeiro.getEspecialidades());

            for(Map.Entry<Integer, Servico> especialidades : lista.entrySet()) {
                if (especialidades.getKey().equals(lista.lastKey())) {
                    arquivo.write(Integer.toString(especialidades.getValue().getId()) + ";");
                }else {
                    arquivo.write(Integer.toString(especialidades.getValue().getId()));
                }
            }

            // Impressão de verificação para o programador;
            //System.out.println("Especialidade armazenadas com sucesso.");
        }
    }

    public void armazenarRecuros(Servico servico) throws Exception {

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(EnumCaminho.DISPONIBILIDADES.getValue(), true))) {
            arquivo.write(servico.getId());
            arquivo.newLine();

            TreeMap<Integer, Recurso> lista = new TreeMap<>(servico.getRecursos());

            for(Map.Entry<Integer, Recurso> recursos : lista.entrySet()) {
                if (recursos.getKey().equals(lista.lastKey())) {
                    arquivo.write(Integer.toString(recursos.getValue().getId()) + ";");
                }else {
                    arquivo.write(Integer.toString(recursos.getValue().getId()));
                }
            }

            // Impressão de verificação para o programador;
            //System.out.println("Recursos de servicos armazenados com sucesso.");
        }
    }


    public void editarLista(String local, int id) {
        Path caminho = Paths.get(local);

        try {
            List<String> conjuntoDados = Files.readAllLines(caminho);
            
            List<String> novoConjunto = new ArrayList<>();

            for (int c = 0; c < conjuntoDados.size(); c++) {
                String[] campos = conjuntoDados.get(c).split(";");
                
                if (!(campos[0].equals(Integer.toString(id)))) {
                    novoConjunto.add(conjuntoDados.get(c));
                }
            }
            Files.write(caminho, novoConjunto);
            System.out.println("................................................................................................................");
            System.out.println("@ Item removido com sucesso.");
        } catch (IOException f)  {
            System.out.println("* Caminho ou arquivo nao encontrado(s).");
        } 
    }
}
