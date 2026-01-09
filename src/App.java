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

        Leitor leitor = new Leitor();
        Gerenciador gerenciador = new Gerenciador();

        
        barbearia = leitor.leDadosBarbearia(barbearia);

        try {
            gerenciador.iniciarSistema(barbearia);
            gerenciador.executarPrograma(barbearia);
        } catch (ExceptionObjetoInexistente e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException s) {
            System.out.println("* Erro de leitura para os dados descritivos da barbearia.");
        }
    }
}
