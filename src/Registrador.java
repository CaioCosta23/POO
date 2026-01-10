import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
