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


public class Registrador {
    private static final Scanner entrada = new Scanner(System.in);

    public Registrador() {   
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

        System.out.printf("- Informe a data de nascimento (no formato dd/MM/yyyy): ");
        String nascimento = entrada.nextLine();
        LocalDate data = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.println("- Digite o valor do salario a ser recebido (em R$): ");
        Float salario = Float.valueOf(entrada.nextLine());

        //entrada.close();

        Barbeiro barbeiro = new Barbeiro(-1, nome, email, telefone, cpf, login, senha, data, salario);

        //barbeiro.getDisponibilidade();
        
        // O ID do barbeiro será registrado quando for feito o armazenamento do mesmo no arquivo;
        return barbeiro;
    }

    private List<Disponibilidade> criarDisponibilidade() {
        LocalTime horario = LocalTime.of(8, 0);
        LocalTime ultimoHorario = LocalTime.of(17, 20);

        List<Disponibilidade> disponibilidades = new ArrayList<>();

        while(horario.equals(ultimoHorario)) {

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

            agendamentos.put(-1, new Agendamento(-1, cliente, barbeiro, servico, disponibilidade.getData(), EnumStatusAgend.AGENDADO));
        } catch (NullPointerException d) {
            System.out.println("Erro! Disponibilidade nao encontrada.");
        }
    }



    public void armazenarUsuario(Usuario usuario, String caminhoUsuario) throws IOException {
        Path caminho = Path.of(caminhoUsuario);
        
        if (!caminho.toFile().exists()) {
            usuario.setId(1);
        }else {
            long qtdUsuarios = Files.lines(caminho).count();
            usuario.setId((int)qtdUsuarios + 1);
        }

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

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel realizar o cadastro.");
        }
    }
    

    public void armazenarServico(Servico servico, String caminhoServico) throws IOException {
        Path caminho = Path.of(caminhoServico);
        
        if (!caminho.toFile().exists()) {
            servico.setId(1);
        }else {
            long qtdUsuarios = Files.lines(caminho).count();
            servico.setId((int)qtdUsuarios + 1);
        }

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoServico, true))) {
            arquivo.write(servico.toString());
            arquivo.newLine();

            System.out.println("................................................................................................................");
            System.out.println("@ Servico cadastrado com sucesso!");

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel realizar o cadastro.");
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
