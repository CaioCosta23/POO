import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        /*
         * Linha de Código que "limpa" o terminal e move o cursor para a primeira linha ao se executar novamente o programa;

         * OBS: Pode não funcionar em alguns consoles ou IDEs mais antigos(as);
         */
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Cria um tempo de espera na saída;
        TimeUnit.SECONDS.sleep(2);
        

        Leitor leitor = new Leitor();
        Gerenciador gerenciador = new Gerenciador();
        
        try {
            Barbearia barbearia = leitor.leDadosBarbearia();

            gerenciador.iniciarSistema(barbearia);
            gerenciador.executarPrograma(barbearia);
        } catch (FileNotFoundException a) {
            System.out.println("* Erro na leitura do arquivo! Um dos arquivos de confinguracao e leitura nao foi encontrado.");
        } catch (NullPointerException v) {
            System.out.format("Erro! Dado(s) de", v.getMessage(), " nao encontrado(s) ou nao lidos ou incompletos.\n");
        }catch (ExceptionObjetoInexistente o) {
            System.out.println("Erro no registro/obtencao de dados!" + o.getMessage());
        }catch(ExceptionFormato f) {
            System.out.format("Erro! Formato do(s) ", f.getMessage(), " incompativel para a leitura/armazenamento das informacoes.");
        } catch (IllegalArgumentException a) {
            System.out.println("* Erro de leitura dos dados " + a.getMessage());
        } catch(NoSuchElementException q) {
            System.out.println("\n................................................................................................................");
            System.out.println("$ Programa encerrado prematuramente.\n");
            // Faz com que o programa não execute o finally por lançar uma InterruptedException;
            TimeUnit.SECONDS.sleep(1);
        } finally {
            System.out.println("................................................................................................................");
            System.out.printf("+ Finalizando");
            TimeUnit.SECONDS.sleep(1);
            System.out.printf(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.printf(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.printf(".\n");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("................................................................................................................");
            System.out.println("+ ENCERRADO.");
        }
    }
}
