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

        System.out.printf("- Digite um nome de Login: ");
        String login = entrada.nextLine();

        System.out.printf("- Digite uma senha (com ao menos 5 caracteres): ");
        String senha = entrada.nextLine();

        System.out.printf("- Informe a data de nascimento (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();

        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        // O ID do cliente será registrado quando for feito o armazenamento do mesmo no arquivo;
        return new Cliente(-1, nome, email, telefone, cpf, login, senha, data);
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

        System.out.printf("- Digite um nome de Login: ");
        String login = entrada.nextLine();

        System.out.printf("- Digite uma senha (com ao menos 5 caracteres): ");
        String senha = entrada.nextLine();

        System.out.printf("- Informe a data de admissao (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();
        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.printf("- Digite o valor do salario a ser recebido (em R$): ");
        Float salario = Float.valueOf(entrada.nextLine());

        //entrada.close();

        Barbeiro barbeiro = new Barbeiro(-1, nome, email, telefone, cpf, login, senha, data, salario);

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
            barbeiro.adicionarServicos(especialidades);
        }else {
            System.out.println("* Servico nao disponivel no catalogo.");
        }
        return barbeiro;
    }



    public List<Disponibilidade> criarDisponibilidade() {
        LocalTime horario = LocalTime.of(8, 0);
        LocalTime ultimoHorario = LocalTime.of(18, 00);

        LocalDate data = LocalDate.of(2026, 06, 10);

        List<Disponibilidade> disponibilidades = new ArrayList<>();

        while(!(horario.isAfter(ultimoHorario))) {
            Disponibilidade disponibilidade = new Disponibilidade(data, horario, horario.plusMinutes(Servico.getDuracao()), true);
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



    public void criarAgendamento(Map<Integer, Agendamento> agendamentos, Barbearia barbearia, Cliente cliente) throws Exception {
        Map<String, Barbeiro> barbeiros = new HashMap<>(barbearia.getBarbeiros());
        Map<Integer, Servico> servicos = new HashMap<>(barbearia.getServicos());


        Leitor leitor = new Leitor();
        Servico servico;
        Barbeiro barbeiro;


        System.out.println("* Selecione o servico a ser prestado: ");
        for(Map.Entry<Integer, Servico> valor : servicos.entrySet()) {
            valor.getValue().exibirInformacoes();
            System.out.println();
        }
        servico = servicos.get((int)leitor.leOpcoes());

        if (servico == null) {
            throw new ExceptionObjetoInexistente("- Servico nao pertencente ao catalogo.");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");


        System.out.println("* Selecione o prestador de servico que desejado: ");
        for(Map.Entry<String, Barbeiro> valor : barbeiros.entrySet()) {
            System.out.printf("(%d)%s\n", (int)valor.getValue().getId(), valor.getValue().getNome());
        }
        barbeiro = barbeiros.get(leitor.leIdentificadorUsuario());

        if (barbeiro == null) {
            throw new ExceptionObjetoInexistente("- Prestador de servico nao registrado.");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");


        Iterator<Disponibilidade> impressao = barbeiro.getDisponibilidade().iterator();

        System.out.println("* Selecione o horario desejado: ");
        while(impressao.hasNext()) {
            Disponibilidade disponibilidade = impressao.next();
            disponibilidade.exibirInformacoes();
            System.out.println();
        }        
        System.out.println("+ Digite a data: ");
        LocalDate data = LocalDate.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.printf("+ Digite o horario inicial para selecionar um dos horarios: ");
        LocalTime horario = LocalTime.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        boolean disponivel = false;

        Iterator<Disponibilidade> iterador = barbeiro.getDisponibilidade().iterator();
        System.out.println("* Selecione o horario desejado: ");

        Disponibilidade disponibilidade = null;
        while(iterador.hasNext()) {
            disponibilidade = iterador.next();
            if ((disponibilidade.getData().equals(data)) && (disponibilidade.getHoraInicio().equals(horario))) {
                disponivel = true;
                disponibilidade.setDisponivel(disponivel);
                break;
            }
        }        
        if (!(disponivel)) {
            System.out.println("Data e/ou horario indisponivel ou inexistente");
        }
        try {
            if (disponibilidade == null) {
                throw new NullPointerException();
            }

            agendamentos.put(-1, new Agendamento(-1, cliente, barbeiro, servico, disponibilidade.getData()));
        } catch (NullPointerException d) {
            System.out.println("Erro! Disponibilidade nao encontrada.");
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



    public void armazenarAgendamento(Agendamento agendamento, String caminhoServico) throws IOException {
        Path caminho = Path.of(caminhoServico);
        
        if (!caminho.toFile().exists()) {
            agendamento.setId(1);
        }else {
            long qtdAgendamentos = Files.lines(caminho).count();
            agendamento.setId((int)qtdAgendamentos + 1);
        }

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoServico, true))) {
            arquivo.write(agendamento.toString());
            arquivo.newLine();

            System.out.println("................................................................................................................");
            System.out.println("@ Agendamento registrado com sucesso!");

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel registrar o agendamento.");
        }
    }


    public void editarLista(String local, int id) {
        Path caminho = Paths.get(local);

        try {
            List<String> conjuntoDados = Files.readAllLines(caminho);
            
            List<String> novoConjunto = new ArrayList<>();

            for (int c = 0; c < conjuntoDados.size(); c++) {
                if (c != (id - 1)) {
                    novoConjunto.add(conjuntoDados.get(c));
                }
            }
            Files.write(caminho, novoConjunto);
            System.out.println("* Usuario removido com sucesso");
        } catch (IOException f)  {
            System.out.println("* Caminho ou arquivo nao encontrado(s).");
        } 
    }
}
