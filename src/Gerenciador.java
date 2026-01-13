
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
        barbearia.adicionarAgendamentos(leitor.lerAgendamento(barbearia));

        barbearia.exibirMenu();
    }



    public void executarPrograma(Barbearia barbearia) throws Exception {

        Leitor leitor = new Leitor();
        Registrador registrador = new Registrador();
        Consulta consulta = new Consulta();

        int opcao = leitor.leOpcoes();

        if (opcao == EnumOpcao.OPCAO_CLIENTE.getValue()) {

        }else if (opcao == EnumOpcao.OPCAO_BARBEIRO.getValue()) {
            
        }else if (opcao == EnumOpcao.OPCAO_ADMINISTRADOR.getValue()) {
            Administrador administrador = leitor.lerAdministrador();

            if (leitor.lerLoginSenhaUsuarios(administrador) != null) {
                administrador.exibirMenu();

                switch (leitor.leOpcoes()) {
                    case 1:
                        if (!(barbearia.getClientes().isEmpty())) { 
                            if (!(barbearia.getBarbeiros()).isEmpty()){
                                if (!(barbearia.getServicos()).isEmpty()) {
                                    Agendamento agendamento = registrador.criarAgendamento(barbearia);
                                    registrador.armazenarAgendamento(agendamento);
                                }else {
                                    System.out.println("* Lista de servicos vazia.");
                                }
                            }else {
                                System.out.println("* Lista de prestadores de servicos vazia.");
                            }
                        }else {
                            System.out.println("* Lista de clientes vazia.");
                        }
                        break;
                    case 2:
                        break;
                        
                    case 3:
                        System.out.println("* Informe o numero do agendamento:");
                        int numeroAgendamento = leitor.leOpcoes();

                        if (!(barbearia.getAgendamentos().isEmpty())){
                            if (!(barbearia.getClientes().isEmpty())) { 
                                if (!(barbearia.getBarbeiros()).isEmpty()){
                                    if (!(barbearia.getServicos()).isEmpty()) {
                                        if (barbearia.getAgendamentos().containsKey(numeroAgendamento)) {
                                            barbearia.getAgendamentos().get(numeroAgendamento).exibirInformacoes();
                                        }else {
                                            System.out.println("Agendamento nao encontrado.");
                                        }
                                    }else {
                                        System.out.println("* Lista de servicos vazia.");
                                    }
                                }else {
                                    System.out.println("* Lista de prestadores de servicos vazia.");
                                }
                            }else {
                                System.out.println("* Lista de clientes vazia.");
                            }
                        }else {
                            System.out.println("* Lista de agendamentos vazia.");
                        }
                        break;
                    case 4:
                        Servico servico = registrador.cadastrarServico();
                        registrador.armazenarServico(servico);
                        break;
                    case 5:
                        System.out.println("Escolha e insira o ID de um dos servicos que deseja excluir:");
                        consulta.exibirServicos(barbearia.getServicos());

                        int opcaoServicoExcluido = leitor.leOpcoes();
                        if (barbearia.getServicos().containsKey(opcaoServicoExcluido)) {
                            registrador.editarLista(EnumCaminho.SERVICOS.getValue(), barbearia.getServicos().get(opcaoServicoExcluido).getId());
                        }else {
                            System.out.println("*Servico nao existente ou excluido do(no) catalogo de servicos da barbearia");
                        }
                        
                        break;

                    case 6:
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
                        
                    case 7:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja cadastrar:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");

                        int opcaoAdicao = leitor.leOpcoes();

                        if (opcaoAdicao == EnumOpcao.ADICIONAR_CLIENTE.getValue()) {
                            Cliente novo = registrador.cadastrarCliente();
                            registrador.armazenarUsuario(novo, EnumCaminho.CLIENTES.getValue());
                        }else if (opcaoAdicao == EnumOpcao.ADICIONAR_BARBEIRO.getValue()) {
                            Barbeiro novo = registrador.cadastrarBarbeiro();
                            registrador.armazenarUsuario(novo, EnumCaminho.BARBEIROS.getValue());
                            registrador.armazenarDisponibilidade(novo.getDisponibilidade(), novo.getCpf());
                        }else {
                            throw new IllegalArgumentException("com opcao invalida ou inexistente!");
                        }
                        break;
                        
                    case 8:
                        System.out.println("* Selecione um dos tipos de usuario abaixo que deseja excluir:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");

                        int opcaoRemocao = leitor.leOpcoes();
                        String identificadorUsuario = leitor.leIdentificadorUsuario();

                        if (opcaoRemocao == EnumOpcao.REMOVER_CLIENTE.getValue()) {
                            if (!(barbearia.getClientes().isEmpty())) {
                                if (barbearia.getClientes().containsKey(identificadorUsuario)) {
                                    registrador.editarLista(EnumCaminho.CLIENTES.getValue(), barbearia.getClientes().get(identificadorUsuario).getId());
                                }else {
                                    System.out.println("* Cliente nao encontrado/registrado.");
                                }
                            }else {
                                System.out.println("* Lista de clientes vazia.");
                            }
                        }else if (opcaoRemocao == EnumOpcao.REMOVER_BARBEIRO.getValue()) {
                            if (!(barbearia.getBarbeiros().isEmpty())) {
                                if (barbearia.getBarbeiros().containsKey(identificadorUsuario)) {
                                    registrador.editarLista(EnumCaminho.BARBEIROS.getValue(), barbearia.getBarbeiros().get(identificadorUsuario).getId());
                                }else {
                                    System.out.println("* Prestador de servicos nao encontrado/registrado.");
                                }
                            }else {
                                System.out.println("* Lista de prestadores de servicos vazia.");
                            }
                        }else {
                            throw new IllegalArgumentException("com opcao invalida ou inexistente!");
                        }
                        break;

                    case 9:
                        String identificadorConsulta = leitor.leIdentificadorUsuario();

                        if (!(barbearia.getClientes().isEmpty())) {
                            if (barbearia.getClientes().containsKey(identificadorConsulta)){
                                barbearia.getClientes().get(identificadorConsulta).exibirInformacoes();
                            }
                        }else if (!(barbearia.getBarbeiros().isEmpty())) {
                            if (barbearia.getBarbeiros().containsKey(identificadorConsulta)) {
                                barbearia.getBarbeiros().get(identificadorConsulta).exibirInformacoes();
                            }else {
                                System.out.println("* Usuario nao encontrado.");
                            }
                        }else {
                            System.out.println("Lista de usuarios (clientes e/ou prestadores de servicos) vazia.");
                        }
                        break;

                    case 10:
                        System.out.println("* Selecione um dos tipos de usuarios do qual deseja ver a lista:");
                        System.out.println("[1] Cliente\t[2] Barbeiro");

                        int opcaoUsuario = leitor.leOpcoes();

                        if (opcaoUsuario == EnumOpcao.OPCAO_CLIENTE.getValue()) {
                            if (!(barbearia.getClientes().isEmpty())) {
                                consulta.exibirUsuarios(barbearia.getClientes());
                            }else {
                                System.out.println("* Lista de clientes vazia.");
                            }
                        }else if (opcaoUsuario == EnumOpcao.OPCAO_BARBEIRO.getValue()){
                            if (!(barbearia.getBarbeiros().isEmpty())) {
                                consulta.exibirUsuarios(barbearia.getBarbeiros());
                            }else {
                                System.out.println("* Lista de prestadores de servicos vazia.");
                            }
                        }
                        break;
                    case 11:
                        if (!(barbearia.getServicos().isEmpty())) {
                            consulta.exibirServicos(barbearia.getServicos());
                        }else {
                            System.out.println("Catalogo/Lista de servicos vazia.");
                        }
                        break;
                    case 12:
                        break;
                    default:
                        throw new IllegalArgumentException(" pois a opcao nao e oferecida.");
                }
            }
            
        }else if (opcao == EnumOpcao.OPCAO_INFORMACOES.getValue()) {
            try {
                    barbearia.exibirInformacoes();
                } catch (NullPointerException p) {
                    System.out.println("* Informacoes nao encontradas.");
                }
        }else {
            throw new IllegalArgumentException("pois a opcao digitada nao existe no sistema.");
        }
    }
}
