import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        /*
         * Linha de Código que "limpa" o terminal e move o cursor para a primeira linha ao se executar novamente o programa;

         * OBS: Pode não funcionar em alguns consoles ou IDEs mais antigos(as);
         */
        System.out.print("\033[H\033[2J");
        System.out.flush();
        

        Map <String, Barbearia> barbearias = new HashMap<>();
        Set <Cliente> clientes = new HashSet<>();

        Leitor leitor = new Leitor();
        Gerenciador gerenciador = new Gerenciador();


        leitor.leDadosBarbearia(barbearias);
        leitor.leEndereco(barbearias, gerenciador.getCodigo());

        gerenciador.iniciarSistema(barbearias);
        gerenciador.executarPrograma(barbearias, clientes, leitor);

    }
}
