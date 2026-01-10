import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        Set <Cliente> clientes = new HashSet<>();
        Map<String, Barbeiro> barbeiros = new HashMap<>();
        Map<Integer, Servico> servicos = new HashMap<>();
        Map <Integer, Agendamento> agendamentos = new HashMap<>();

        //clientes = leitor.lerCliente();
        barbearia.barbeiros = leitor.lerBarbeiro();
        servicos = leitor.lerServico();

        int opcao = leitor.leOpcoes();

        if (opcao == EnumOpcao.OPCAO_CLIENTE.OPCAO) {

        }else if (opcao == EnumOpcao.OPCAO_BARBEIRO.OPCAO) {
            
        }else if (opcao == EnumOpcao.OPCAO_ADMINISTRADOR.OPCAO) {
            Administrador administrador = leitor.lerAdministrador();

            if (leitor.lerLoginSenhaUsuarios(administrador) != null) {
                administrador.exibirMenu();

                switch (leitor.leOpcoes()) {
                    case 1:
                        clientes = leitor.lerCliente();
                        barbearia.barbeiros = leitor.lerBarbeiro();
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
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        servicos = leitor.lerServico();
                        if (!(servicos.isEmpty())) {
                            int opcaoServico = leitor.leOpcoes();
                            if (servicos.containsKey(opcaoServico)) {
                                servicos.get(opcaoServico).exibirInformacoes();
                            }
                        }
                        break;
                    case 10:
                        throw new IllegalArgumentException();
                    default:
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
