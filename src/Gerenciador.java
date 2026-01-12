import java.util.HashMap;
import java.util.Map;

public class Gerenciador {

    public Gerenciador() {
    }


    public void iniciarSistema(Barbearia barbearia)throws Exception {
        // Verifica se a barbearia existe dentro do sistema;
        if (barbearia == null) {
            throw new ExceptionObjetoInexistente(" Informacoes faltantes ou incompletos na criacao da barbearia. ");
        }
        // Realiza a leitura do arquivo que contém o endereco da barbearia;
        Leitor leitor = new Leitor();

        barbearia.adicionarClientes(leitor.lerCliente());
        barbearia.adicionarBarbeiros(leitor.lerBarbeiro());
        barbearia.adicionarServicos(leitor.lerServico());

        barbearia.exibirMenu();
    }



    public void executarPrograma(Barbearia barbearia) throws Exception {

        Leitor leitor = new Leitor();
        Registrador registrador = new Registrador();

        Map <Integer, Agendamento> agendamentos = new HashMap<>();

        //clientes = leitor.lerCliente();
        //barbearia.barbeiros = leitor.lerBarbeiro();
        //servicos = leitor.lerServico();

        int opcao = leitor.leOpcoes();

        if (opcao == EnumOpcao.OPCAO_CLIENTE.getValue()) {

        }else if (opcao == EnumOpcao.OPCAO_BARBEIRO.getValue()) {
            
        }else if (opcao == EnumOpcao.OPCAO_ADMINISTRADOR.getValue()) {
            Administrador administrador = leitor.lerAdministrador();

            if (leitor.lerLoginSenhaUsuarios(administrador) != null) {
                administrador.exibirMenu();

                switch (leitor.leOpcoes()) {
                    case 1:
                        /* 
                        if (!(leitor.lerCliente().isEmpty())) {
                            clientes = leitor.lerCliente();
                            String identificador = leitor.leIdentificadorUsuario();

                            if (clientes.containsKey(identificador)) {
                                Cliente cliente = new Cliente(clientes.get(identificador));

                                if (!(leitor.lerServico().isEmpty())) {
                                    System.out.println("* Digite o codigo de um dos servicos abaixo: ");
                                    barbearia.adicionarServicos(leitor.lerServico());
                                    
                                    Consulta consulta = new Consulta();
                                    consulta.exibirServicos(barbearia.getServicos());
                                    
                                    int opcaoServico = leitor.leOpcoes();

                                    if (barbearia.getServicos().containsKey(opcaoServico)) {
                                        Servico servico = new Servico(barbearia.getServicos().get(opcaoServico));

                                        if (!(leitor.lerBarbeiro().isEmpty())) {
                                            barbearia.adicionarBarbeiros(leitor.lerBarbeiro());
                                            consulta.exibirUsuarios(barbearia.getBarbeiros());

                                            String opcaoBarbeiro = leitor.leIdentificadorUsuario();
                                            //if ((barbearia.getServicos().containsKey(opcaoServico))) {

                                                Barbeiro barbeiro = new Barbeiro(barbearia.getBarbeiros().get(opcaoBarbeiro));

                                                //if (barbeiro.getServicos().containsKey(servico.getId())) {
                                                    //System.out.println("* Selecione um dos horarios disponiveis:");

                                                    //int opcaoDisponibilidade = leitor.leOpcoes();
                                                    //if (barbeiro.getDisponibilidade().get(opcaoDisponibilidade).getId() == opcaoDisponibilidade) {
                                                        //Disponibilidade disponibilidade = barbeiro.getDisponibilidade().get(opcaoDisponibilidade);
                                                        Agendamento agendamento = new Agendamento(1, cliente, barbeiro, servico, LocalDate.of(2026, 01, 10));
                                                        agendamentos.put(1, agendamento);
                                                        registrador.armazenarAgendamento(agendamento, "dados/agendamentos.txt");

                                                        System.out.println("* Agendamento realizado com SUCESSO!");
                                                    //}else {
                                                        //throw new IllegalAccessException("* Data/horario indisponiveis.");
                                                    //}
                                                //}else {
                                                    //throw new ExceptionObjetoInexistente("* Desculpe, o servico escolhido nao e oferecido pelo barbeiro.");
                                                //}
                                            //}else {
                                                //throw new ExceptionObjetoInexistente("* Prestador de servico inexistente.");
                                            //}
                                        }else {
                                            throw new ExceptionObjetoInexistente("Nenhum prestador de servicos dsponivel no momento.");
                                        }

                                    }else {
                                        //throw new IllegalAccessException();
                                    }
                                }else {
                                    throw new ExceptionObjetoInexistente("Nenhum servico disponivel no momento.");
                                }
                            }else {
                                throw new ExceptionObjetoInexistente("Cliente nao cadastrado. Realize o cadastro para fazer um agendamento.");
                            }
                        }
                            */
                        break;
                    case 2:
                        break;
                        
                    case 3:
                        Servico servico = registrador.cadastrarServico();
                        registrador.armazenarServico(servico);
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja cadastrar:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");

                        int opcaoAdicao = leitor.leOpcoes();

                        if (opcaoAdicao == EnumOpcao.ADICIONAR_CLIENTE.getValue()) {
                            Cliente novo = registrador.cadastrarCliente();
                            registrador.armazenarUsuario(novo, EnumCaminho.CLIENTES.getValue());
                        }else if (opcaoAdicao == EnumOpcao.ADICIONAR_BARBEIRO.getValue()) {
                            Barbeiro novo = registrador.cadastrarBarbeiro();
                            registrador.armazenarUsuario(novo, EnumCaminho.BARBEIROS.getValue());
                        }else {
                            throw new IllegalArgumentException("com opcao invalida ou inexistente!");
                        }
                        break;
                    case 6:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja excluir:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");

                        int opcaoRemocao = leitor.leOpcoes();

                        if (opcaoRemocao == EnumOpcao.REMOVER_CLIENTE.getValue()) {
                            if (!(barbearia.getClientes().isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();

                                if (barbearia.getClientes().containsKey(identificador)) {
                                    registrador.editarLista(EnumCaminho.CLIENTES.getValue(), barbearia.getClientes().get(identificador).getId());
                                }else {
                                    System.out.println("* Cliente nao encontrado/registrado.");
                                }
                            }
                        }else if (opcaoRemocao == EnumOpcao.REMOVER_CLIENTE.getValue()) {
                            if (!(barbearia.getBarbeiros().isEmpty())) {
                                String identificador = leitor.leIdentificadorUsuario();

                                if (barbearia.getBarbeiros().containsKey(identificador)) {
                                    registrador.editarLista(EnumCaminho.BARBEIROS.getValue(), barbearia.getBarbeiros().get(identificador).getId());
                                }else {
                                    System.out.println("* Prestador de servicos nao encontrado/registrado.");
                                }
                            }
                        }else {
                            throw new IllegalArgumentException("com opcao invalida ou inexistente!");
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        String identificador = leitor.leIdentificadorUsuario();

                        if (!(barbearia.getClientes().isEmpty())) {
                            if (barbearia.getClientes().containsKey(identificador)){
                                barbearia.getClientes().get(identificador).exibirInformacoes();
                            }else if (!(barbearia.getBarbeiros().isEmpty())) {
                                if (barbearia.getBarbeiros().containsKey(identificador)) {
                                    barbearia.getBarbeiros().get(identificador).exibirInformacoes();
                                }else {
                                    System.out.println("* Usuario nao encontrado.");
                                }
                            }else {
                                System.out.println("Lista de usuarios (clientes e prestadores de servicos) vazia.");
                            }
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
            
        }else if (opcao == EnumOpcao.OPCAO_INFORMACOES.getValue()) {
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
