import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Consulta {
    public Consulta() {
    }

    public void consultarUsuario(Object usuarios) {
        if (usuarios instanceof Map) {
            Map<Integer, ?> mapeados  = (Map<Integer, ?>)usuarios;
            
            for (Object valor : mapeados.entrySet()) {
                if (valor instanceof Barbeiro) {
                    ((Barbeiro)valor).exibirInformacoes();
                }
            }
        }else if (usuarios instanceof Set){
            Set<?> mapeados = (Set<?>)usuarios;

            Iterator<?> iterador = mapeados.iterator();

            while(iterador.hasNext()) {
                Object valor = iterador.next();

                if (valor instanceof Cliente) {
                    ((Cliente)valor).exibirInformacoes();
                }
            }
        }
    }


    public void consultaServicos(Map<Integer, Servico> servicos) {
        for(Map.Entry<Integer, Servico> valor : servicos.entrySet()) {
            valor.getValue().exibirInformacoes();
        }
    }

    public void consultaDisponibilidade(List<Disponibilidade> disponibilidades) {
        Iterator<Disponibilidade> iterador = disponibilidades.iterator();

        while(iterador.hasNext()) {
            iterador.next().exibirInformacoes();
        }
    }
}
