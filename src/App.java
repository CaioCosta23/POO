import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        /*
         * Linha de Código que "limpa" o terminal e move o cursor para a primeira linha ao se executar novamente o programa;

         * OBS: Pode não funcionar em alguns consoles ou IDEs mais antigos(as);
         */
        System.out.print("\033[H\033[2J");
        System.out.flush();

        TimeUnit.SECONDS.sleep(2);
        

        Barbearia barbearia = null;
        Set <Cliente> clientes = new HashSet<>();

        Leitor leitor = new Leitor();
        Gerenciador gerenciador = new Gerenciador();

        
        barbearia = leitor.leDadosBarbearia(barbearia);
        leitor.leEndereco(barbearia);

        // Cria um tempo de espera (em segundos) no terminal antes de executar as próximas açõe;
        TimeUnit.SECONDS.sleep(6);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        gerenciador.iniciarSistema(barbearia);
        gerenciador.executarPrograma(barbearia, clientes, leitor);

    }
}
