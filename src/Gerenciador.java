import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Gerenciador {

    private final Scanner entrada = new Scanner(System.in);

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
            System.out.println("Selecione uma das opcoes abaixo:\n");
            System.out.println("[1] Cadastrar-se\t[2] Acessar area do cliente\n");
            
            opcao = leitor.leOpcoes();

            if (opcao == 1) {
                Cliente novo = registrador.cadastrarCliente();
                registrador.armazenarUsuario(novo, EnumCaminho.CLIENTES.getValue());
                    
            }else {
                System.out.printf("@ Login: ");
                String login = entrada.nextLine();

                System.out.printf("@ Senha: ");
                String senha = entrada.nextLine();

                if (barbearia.getClientes().containsKey(login)) {
                    Cliente cliente = new Cliente(barbearia.getClientes().get(login));
                
                    if ((opcao == 2) && (cliente.autenticar(login, senha))) {
                        Cliente.exibirMenu();
                        
                        switch (leitor.leOpcoes()) {
                            case 1:
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
                                break;
                            case 2:
                                System.out.printf("- Digite o numero do agendamento: ");
                                int identificadorAgendamento = Integer.parseInt(entrada.nextLine());

                                if (!(barbearia.getAgendamentos().isEmpty())){
                                    registrador.editarLista(EnumCaminho.AGENDAMENTO.getValue(), barbearia.getAgendamentos().get(identificadorAgendamento).getId());
                                }else {
                                    System.out.println("* Lista de Agendamentos vazia.");
                                }
                                break;
                            case 3:
                                if (barbearia.getClientes().containsKey(cliente.getCpf())) {
                                    cliente.exibirInformacoes();
                                }
                                break;
                                
                            case 4:
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

                            case 5:
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

                            case 6:
                                if (!(barbearia.getBarbeiros().isEmpty())) {
                                    consulta.exibirBarbeiros(barbearia.getBarbeiros());
                                }else {
                                    System.out.println("* Lista de prestadores de servicos vazia.");
                                }
                                break;

                            case 7:
                                cliente.alterarSenha();
                                Map<String, Cliente> clientes = new HashMap<>(barbearia.getClientes());
                                clientes.replace(cliente.getCpf(), cliente);
                                barbearia.adicionarClientes(clientes);
                                registrador.armazenarUsuario(cliente, senha);
                                break;
                            case 8:
                                if (!(barbearia.getClientes().isEmpty())) {
                                    if (barbearia.getClientes().containsKey(cliente.getCpf())) {
                                        registrador.editarLista(EnumCaminho.CLIENTES.getValue(), barbearia.getClientes().get(cliente.getCpf()).getId());
                                    }else {
                                        System.out.println("* Cliente nao encontrado/registrado.");
                                    }
                                }
                                break;
                            default:
                                throw new IllegalArgumentException(" pois a opcao nao e oferecida.");
                        }
                    }
                }
            }
        }else if (opcao == EnumOpcao.OPCAO_BARBEIRO.getValue()) {
            System.out.printf("@ Login (CPF): ");
            String login = entrada.nextLine();

            System.out.printf("@ Senha: ");
            String senha = entrada.nextLine();

            if (barbearia.getBarbeiros().containsKey(login)) {
                Map<String, Barbeiro> barbeirosAtualizado = new HashMap<>(barbearia.getBarbeiros());
                Barbeiro barbeiro = new Barbeiro(barbearia.getBarbeiros().get(login));

                if (barbeiro.autenticar(login, senha)) {
                    Barbeiro.exibirMenu();
                    

                    switch (leitor.leOpcoes()) {
                        case 1:
                            if (!(barbearia.getClientes().isEmpty())) {
                                if (!(barbearia.getServicos()).isEmpty()) {
                                    Agendamento agendamento = registrador.criarAgendamento(barbearia);
                                    registrador.armazenarAgendamento(agendamento);
                                } else {
                                    System.out.println("* Lista de servicos vazia.");
                                }
                            } else {
                                System.out.println("* Lista de clientes vazia.");
                            }
                            break;

                        case 2:
                            System.out.printf("- Digite o numero do agendamento: ");
                            int identificadorAgendamento = Integer.parseInt(entrada.nextLine());

                            if (!(barbearia.getAgendamentos().isEmpty())) {
                                if (barbearia.getAgendamentos().containsKey(identificadorAgendamento)) {
                                    Agendamento agendamento = barbearia.getAgendamentos().get(identificadorAgendamento);

                                    if (agendamento.getBarbeiro().getCpf().equals(barbeiro.getCpf())) {
                                        registrador.editarLista(EnumCaminho.AGENDAMENTO.getValue(), agendamento.getId());
                                    } else {
                                        System.out.println("* Este agendamento nao pertence a este barbeiro.");
                                    }
                                } else {
                                    System.out.println("* Agendamento nao encontrado.");
                                }
                            } else {
                                System.out.println("* Lista de Agendamentos vazia.");
                            }
                            break;

                        case 3:
                            // Adicionar especialidade

                            Barbeiro novoBarbeiro = new Barbeiro(registrador.cadastrarEspecialidade(barbeiro, barbearia.getServicos()));

                            registrador.armazenarEspecialidades(novoBarbeiro);

                            
                            barbeirosAtualizado.replace(novoBarbeiro.getCpf(), novoBarbeiro);
                            barbearia.adicionarBarbeiros(barbeirosAtualizado);

                            System.out.println("@ Especialidade adicionada com sucesso!");
                        
                            break;
                        case 4:
                            // Remover especialidade
                            if (barbeiro.getEspecialidades().isEmpty()) {
                                System.out.println("* Voce nao possui especialidades cadastradas.");
                                break;
                            }

                            System.out.println("* Suas especialidades:");
                            consulta.exibirServicos(barbeiro.getEspecialidades());

                            System.out.printf("- Digite o ID do servico que deseja remover: ");
                            int idRemover = Integer.parseInt(entrada.nextLine());

                            
                            if (barbeiro.getEspecialidades().containsKey(idRemover)) {
                                Map<Integer, Servico> novasEsp = new TreeMap<>(barbeiro.getEspecialidades());
                                novasEsp.remove(idRemover);

                                barbeiro.adicionarEspecialidades(novasEsp);

                                registrador.armazenarEspecialidades(barbeiro);

                                
                                barbeirosAtualizado.replace(barbeiro.getCpf(), barbeiro);
                                barbearia.adicionarBarbeiros(barbeirosAtualizado);

                                System.out.println("@ Especialidade removida com sucesso!");
                            } else {
                                System.out.println("* Especialidade nao encontrada.");
                            }
                            break;

                        case 5:
                            if (barbeiro.getEspecialidades().isEmpty()) {
                                System.out.println("* Lista de especialidades vazia.");
                            } else {
                                consulta.exibirServicos(barbeiro.getEspecialidades());
                            }
                            break;

                        case 6:
                            barbeiro.exibirInformacoes();
                            break;

                        case 7:
                            if (barbeiro.getDisponibilidade().isEmpty()) {
                                System.out.println("* Disponibilidades nao encontradas.");
                            } else {
                                consulta.exibirDisponibilidade(barbeiro.getDisponibilidade());
                            }
                            break;

                        case 8:
                            // Alterar disponibilidade (toggle)
                            if (barbeiro.getDisponibilidade().isEmpty()) {
                                System.out.println("* Disponibilidades nao encontradas.");
                                break;
                            }

                            System.out.println("* Informe o horario inicial (HH:mm) para alterar:");
                            LocalTime horarioAlt = java.time.LocalTime.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

                            List<Disponibilidade> listaDisp = new ArrayList<>(barbeiro.getDisponibilidade());
                            boolean achou = false;
                            
                            for (int i = 0; i < listaDisp.size(); i++) {
                                Disponibilidade d = listaDisp.get(i);
                                if (d.getHoraInicio().equals(horarioAlt)) {
                                    d.setDisponivel(!d.isDisponivel());
                                    achou = true;
                                    break;
                                }
                            }

                            if (achou) {
                                Barbeiro atualBarbeiro = new Barbeiro(barbeiro);
                                barbeirosAtualizado.replace(atualBarbeiro.getCpf(), atualBarbeiro);
                                barbearia.adicionarBarbeiros(barbeirosAtualizado);
                                registrador.armazenarDisponibilidade(listaDisp, atualBarbeiro.getCpf());

                                System.out.println("@ Disponibilidade atualizada com sucesso!");
                            } else {
                                System.out.println("* Horario nao encontrado.");
                            }
                            break;

                        case 9:
                            // Confirmar agendamento -> envia notificacao para o cliente do agendamento
                            System.out.printf("* Digite o numero do agendamento a confirmar: ");
                            int idAgConf = Integer.parseInt(entrada.nextLine());

                            if (barbearia.getAgendamentos().containsKey(idAgConf)) {
                                Agendamento ag = barbearia.getAgendamentos().get(idAgConf);
                                if (ag.getBarbeiro().getCpf().equals(barbeiro.getCpf())) {
                                    NotificacaoServico notif = new NotificacaoServico(-1, EnumTipoNotificacao.CONFIRMACAO_AGANDAMENTO,
                                            ag.getCliente().getCpf(), "PUSH", java.time.LocalDate.now(), java.time.LocalTime.now());
                                    notif.enviarPush();
                                    registrador.armazenarNotificacao(notif);
                                    System.out.println("@ Notificacao registrada em arquivo.");
                                } else {
                                    System.out.println("* Este agendamento nao pertence a este barbeiro.");
                                }
                            } else {
                                System.out.println("* Agendamento nao encontrado.");
                            }
                            break;

                        case 10:
                            barbeiro.alterarSenha();
                            Map<String, Barbeiro> barbeiros = new HashMap<>(barbearia.getBarbeiros());
                            barbeiros.replace(barbeiro.getCpf(), barbeiro);
                            barbearia.adicionarBarbeiros(barbeiros);
                            registrador.armazenarUsuario(barbeiro, senha);
                            break;

                        default:
                            throw new IllegalArgumentException(" pois a opcao nao e oferecida.");
                    }
                }
            } else {
                System.out.println("* Barbeiro nao encontrado/registrado.");
            }
        }else if (opcao == EnumOpcao.OPCAO_ADMINISTRADOR.getValue()) {
            Administrador administrador = leitor.lerAdministrador();

            System.out.printf("@ Login: ");
            String login = entrada.nextLine();

            System.out.printf("@ Senha: ");
            String senha = entrada.nextLine();

            if (administrador.autenticar(login, senha)) {
                Administrador.exibirMenu();

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
                        System.out.printf("- Digite o numero do agendamento: ");
                        int identificadorAgendamento = Integer.parseInt(entrada.nextLine());

                        if (!(barbearia.getAgendamentos().isEmpty())){
                            registrador.editarLista(EnumCaminho.AGENDAMENTO.getValue(), barbearia.getAgendamentos().get(identificadorAgendamento).getId());
                        }else {
                            System.out.println("* Lista de Agendamentos vazia.");
                        }
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
                            }else if (!(barbearia.getBarbeiros().isEmpty())) {
                                if (barbearia.getBarbeiros().containsKey(identificadorConsulta)) {
                                    barbearia.getBarbeiros().get(identificadorConsulta).exibirInformacoes();
                                }else {
                                    System.out.println("* Usuario nao encontrado.");
                                }
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
                                consulta.exibirClientes(barbearia.getClientes());
                            }else {
                                System.out.println("* Lista de clientes vazia.");
                            }
                        }else if (opcaoUsuario == EnumOpcao.OPCAO_BARBEIRO.getValue()){
                            if (!(barbearia.getBarbeiros().isEmpty())) {
                                consulta.exibirBarbeiros(barbearia.getBarbeiros());
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
                        // Enviar notificacoes de servico (ex: novos servicos)
                        System.out.println("* Selecione o tipo de notificacao:");
                        System.out.println("[1] Novos servicos	[2] Lembrete 24h	[3] Cancelamento	[4] Confirmacao de agendamento");

                        int tipoNotifOpcao = leitor.leOpcoes();
                        EnumTipoNotificacao tipoNotif;

                        if (tipoNotifOpcao == 1) {
                            tipoNotif = EnumTipoNotificacao.NOVOS_SERVICOS;
                        } else if (tipoNotifOpcao == 2) {
                            tipoNotif = EnumTipoNotificacao.LEMBRETE_24H;
                        } else if (tipoNotifOpcao == 3) {
                            tipoNotif = EnumTipoNotificacao.CANCELAMENTO;
                        } else if (tipoNotifOpcao == 4) {
                            tipoNotif = EnumTipoNotificacao.CONFIRMACAO_AGANDAMENTO;
                        } else {
                            throw new IllegalArgumentException("com opcao invalida para tipo de notificacao!");
                        }

                        System.out.println("* Selecione o canal de envio:");
                        System.out.println("[1] Email	[2] SMS	[3] Push");

                        int canalOpcao = leitor.leOpcoes();
                        String canal;

                        if (canalOpcao == 1) {
                            canal = "EMAIL";
                        } else if (canalOpcao == 2) {
                            canal = "SMS";
                        } else if (canalOpcao == 3) {
                            canal = "PUSH";
                        } else {
                            throw new IllegalArgumentException("com opcao invalida para canal!");
                        }

                        if (barbearia.getClientes().isEmpty()) {
                            System.out.println("* Lista de clientes vazia. Nao ha destinatarios.");
                            break;
                        }

                        System.out.println("* Enviando notificacoes para todos os clientes cadastrados...");
                        for (Map.Entry<String, Cliente> valor : barbearia.getClientes().entrySet()) {
                            Cliente cli = valor.getValue();
                            NotificacaoServico notif = new NotificacaoServico(-1, tipoNotif, cli.getCpf(), canal,
                                    java.time.LocalDate.now(), java.time.LocalTime.now());

                            if (canal.equals("EMAIL")) {
                                notif.enviarEmail();
                            } else if (canal.equals("SMS")) {
                                notif.enviarSMS();
                            } else {
                                notif.enviarPush();
                            }

                            registrador.armazenarNotificacao(notif);
                        }
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
