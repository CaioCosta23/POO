import java.util.HashMap;
import java.util.Map;

public class Gerenciador {

    public Gerenciador() {
    }

    public void iniciarSistema(Barbearia barbearia)throws ExceptionObjetoInexistente {
        // Verifica se a barbearia existe dentro do sistema;
        if (barbearia == null) {
            throw new ExceptionObjetoInexistente("* Informacoes faltantes ou incompletos na criacao da barbearia. ");
        }
        Leitor leitor = new Leitor();
        leitor.leEndereco(barbearia);

        barbearia.exibirMenu();
    }


    public void executarPrograma(Barbearia barbearia) throws Exception {
        String caminho = "dados/servicos.txt";

        Leitor leitor = new Leitor();
        Registrador registrador = new Registrador();

        Map <String, Cliente> clientes;
        Map <Integer, Agendamento> agendamentos = new HashMap<>();

        //clientes = leitor.lerCliente();
        //barbearia.barbeiros = leitor.lerBarbeiro();
        //servicos = leitor.lerServico();

        int opcao = leitor.leOpcoes();

        if (opcao == EnumOpcao.OPCAO_CLIENTE.OPCAO) {

        }else if (opcao == EnumOpcao.OPCAO_BARBEIRO.OPCAO) {
            
        }else if (opcao == EnumOpcao.OPCAO_ADMINISTRADOR.OPCAO) {
            Administrador administrador = leitor.lerAdministrador();

            if (leitor.lerLoginSenhaUsuarios(administrador) != null) {
                administrador.exibirMenu();

                switch (leitor.leOpcoes()) {
                    case 1:
                        
                        
                        break;
                    case 2:
                        break;
                        
                    case 3:
                        Servico servico = registrador.cadastrarServico();
                        registrador.armazenarServico(servico, caminho);
                        break;

                    case 4:
                        
                        break;
                    case 5:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja cadastrar:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");
                        int opcaoAdicao = leitor.leOpcoes();

                        if (opcaoAdicao == 1) {
                            clientes = leitor.lerCliente();
                            Cliente novo = registrador.cadastrarCliente();
                            clientes.put(novo.getCpf(), novo);
                            registrador.armazenarUsuario(novo, "dados/clientes.txt");
                        }else if (opcaoAdicao == 2) {
                            Map<String, Barbeiro> barbeiros = new HashMap<>();
                            
                            barbearia.adicionarBarbeiros(leitor.lerBarbeiro());
                            Barbeiro novo = registrador.cadastrarBarbeiro();
                            barbeiros.put(novo.getCpf(), novo);
                            barbearia.adicionarBarbeiros(barbeiros);
                            registrador.armazenarUsuario(novo, "dados/clientes.txt");
                        }else {
                            throw new IllegalArgumentException("* Opcao Invalida ou inexistente!");
                        }
                        break;

                    case 6:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja excluir:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");
                        int opcaoRemocao = leitor.leOpcoes();

                        if (opcaoRemocao == 1) {
                            if (!(leitor.lerCliente().isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();
                                clientes = leitor.lerCliente();
                                if (clientes.containsKey(identificador)) {
                                    registrador.editarLista("dados/clientes.txt", clientes.get(identificador).getId());
                                }else {
                                    throw new ExceptionObjetoInexistente("Cliente nao encontrado/registrado.");
                                }
                            }else {
                                throw new IllegalArgumentException();
                            }
                            
                        }else if (opcaoRemocao == 2) {
                            if (!(leitor.lerBarbeiro().isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();
                                barbearia.adicionarBarbeiros(leitor.lerBarbeiro());
                                if (barbearia.getBarbeiros().containsKey(identificador)) {
                                    registrador.editarLista("dados/barbeiros.txt", barbearia.getBarbeiros().get(identificador).getId());
                                }else {
                                    throw new ExceptionObjetoInexistente("Cliente nao encontrado/registrado.");
                                }
                            }else {
                                throw new IllegalArgumentException();
                            }
                        }else {
                            throw new IllegalArgumentException("* Opcao Invalida ou inexistente!");
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        if (!(leitor.lerCliente().isEmpty())) {
                            clientes = leitor.lerCliente();
                            if (!(clientes.isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();
                                if (clientes.containsKey(identificador)) {
                                    clientes.get(identificador).exibirInformacoes();
                                }else {
                                    System.out.println("Prestador de servicos nao encontrado.");
                                }
                            }
                        }else if (!(leitor.lerBarbeiro().isEmpty())) {
                            barbearia.adicionarBarbeiros(leitor.lerBarbeiro());

                            if (!(barbearia.getBarbeiros().isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();
                                if (barbearia.getBarbeiros().containsKey(identificador)) {
                                    barbearia.getBarbeiros().get(identificador).exibirInformacoes();;
                                }else {
                                    System.out.println("Prestador de servicos nao encontrado.");
                                }
                            }
                        }else {
                            System.out.println("Nenhum usuario registrado ate o momento.");
                        }
                        break;
                    case 9:
                        if (!(leitor.lerServico().isEmpty())) {
                            barbearia.adicionarServicos(leitor.lerServico());
                            int opcaoServico = leitor.leOpcoes();
                            
                            if (barbearia.getServicos().containsKey(opcaoServico)) {
                                barbearia.getServicos().get(opcaoServico).exibirInformacoes();
                            }
                        }else {
                            System.out.println("* Lista de servicos vazia.");
                        }
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            
        }else if (opcao == EnumOpcao.OPCAO_INFORMACOES.OPCAO) {
            try {
                    barbearia.exibirInformacoes();
                } catch (NullPointerException p) {
                    System.out.println("* Informacoes nao encontradas.");
                }
        }else {
            throw new IllegalArgumentException();
        }
    }
}
