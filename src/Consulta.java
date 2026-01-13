import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Consulta {
    public Consulta() {
    }

    public Cliente getCliente(Set<Cliente> clientes, String identificador) {
        Cliente cliente = null;

        Iterator<Cliente> iterator = clientes.iterator();

        while(iterator.hasNext()) {
            cliente = iterator.next();
            if (cliente.getCpf().equals(identificador)) {
                break;
            }
        }
        return cliente;
    }


    public void exibirClientes(Map<String, Cliente> clientes) {
            
        for (Map.Entry<String, Cliente> cliente : clientes.entrySet()) {
            cliente.getValue().exibirInformacoes();
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
    }



    public void exibirBarbeiros(Map<String, Barbeiro> barbeiros) {
            
        for (Map.Entry<String, Barbeiro> barbeiro : barbeiros.entrySet()) {
            barbeiro.getValue().exibirInformacoes();
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
    }

    

    public void exibirServicos(Map<Integer, Servico> servicos) {
        for(Map.Entry<Integer, Servico> valor : servicos.entrySet()) {
            valor.getValue().exibirInformacoes();
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
    }

    public void exibirDisponibilidade(List<Disponibilidade> disponibilidades) {
        Iterator<Disponibilidade> iterador = disponibilidades.iterator();

        while(iterador.hasNext()) {
            iterador.next().exibirInformacoes();
        }
    }

    public void exibirAgendamentos(Map<Integer, Agendamento> agendamentos) {
        for(Map.Entry<Integer, Agendamento> valor : agendamentos.entrySet()) {
            valor.getValue().exibirInformacoes();
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
    }
}
