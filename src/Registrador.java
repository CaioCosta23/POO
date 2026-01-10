import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Registrador {
    public Registrador() {   
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


    public Barbeiro cadastrarBarbeiro() {
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
        
        System.out.println("- Digite o valor do salario a ser recebido (em R$): ");
        Float salario = Float.valueOf(entrada.nextLine());

        //entrada.close();
        
        // O ID do barbeiro será registrado quando for feito o armazenamento do mesmo no arquivo;
        return new Barbeiro(-1, nome, email, telefone, cpf, login, senha, data, salario);
    }

    public Servico cadastrarServico() {
        Scanner entrada = new Scanner(System.in);

        System.out.printf("- Informe o nome: ");
        String nome = entrada.nextLine();

        System.out.printf("- Insira um endereco de e-mail: ");
        String descricao = entrada.nextLine();
        //ValidacaoFormato.validacao(email, EnumFormato.EMAIL.FORMATO);

        System.out.printf("- Digite a duracao do servico (em minutos): ");
        int duracao = Integer.parseInt(entrada.nextLine());

        System.out.printf("- Digite o preco do servico: ");
        float preco = Float.parseFloat(entrada.nextLine());

        return new Servico(-1, nome, descricao, duracao, preco);
    }


    public void criarAgendamento(Map<Integer, Agendamento> agendamentos, Barbearia barbearia, Cliente cliente) throws Exception {
        Map<Integer, Barbeiro> barbeiros = barbearia.getBarbeiros();
        Map<Integer, Servico> servicos = barbearia.getServicos();

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
        for(Map.Entry<Integer, Barbeiro> valor : barbeiros.entrySet()) {
            System.out.printf("(%d)%s\n", (int)valor.getKey(), valor.getValue().getNome());
        }
        barbeiro = barbeiros.get((int)leitor.leOpcoes());

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
        Scanner entrada = new Scanner(System.in);
        
        System.out.println("+ Digite a data: ");
        LocalDate data = LocalDate.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.printf("+ Digite o horario inicial para selecionar um dos horarios: ");
        LocalTime horario = LocalTime.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        boolean disponivel = false;

        Iterator<Disponibilidade> iterador = barbeiro.getDisponibilidade().iterator();
        System.out.println("* Selecione o horario desejado: ");
        while(iterador.hasNext()) {
            Disponibilidade disponibilidade = iterador.next();
            if ((disponibilidade.getData().equals(data)) && (disponibilidade.getHoraInicio().equals(horario))) {
                disponivel = true;
                disponibilidade.setDisponivel(disponivel);
                break;
            }
        }        
        if (!(disponivel)) {
            System.out.println("Data e/ou horario indisponivel ou inexistente");
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
    
    
    public void armazenarServico(Servico servico, String caminhoUsuario) throws IOException {
        Path caminho = Path.of(caminhoUsuario);
        
        if (!caminho.toFile().exists()) {
            servico.setId(1);
        }else {
            long qtdUsuarios = Files.lines(caminho).count();
            servico.setId((int)qtdUsuarios + 1);
        }

        try (BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoUsuario, true))) {
            arquivo.write(servico.toString());
            arquivo.newLine();

            System.out.println("................................................................................................................");
            System.out.println("@ Servico cadastrado com sucesso!");

        }catch (IOException a) {
            System.out.println("Erro! Nao foi possivel realizar o cadastro.");
        }
    }
}
