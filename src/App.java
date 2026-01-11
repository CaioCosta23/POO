import java.io.FileNotFoundException;
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
        

        Leitor leitor = new Leitor();
        Gerenciador gerenciador = new Gerenciador();

        try {
            Barbearia barbearia = leitor.leDadosBarbearia(EnumCaminho.BARBEARIA.getValue());

            gerenciador.iniciarSistema(barbearia);
            gerenciador.executarPrograma(barbearia);
        } catch (FileNotFoundException a) {
            System.out.println("* Erro na leitura do arquivo! Um dos arquivos de confinguracao e leitura nao foi encontrado.");
        } catch (NullPointerException v) {
            System.out.println("Erro! Dado(s) nao encontrado(s) ou nao lidos.");
        }catch (ExceptionObjetoInexistente o) {
            System.out.println("Erro no registro/obtencao de dados!" + o.getMessage());
        } catch (IllegalArgumentException s) {
            System.out.println("* Erro de leitura para os dados descritivos da barbearia.");
        }
    }
}
