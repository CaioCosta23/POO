
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


        switch (leitor.leOpcoes()) {
            case 1:
                
                break;
            case 2:
                break;
            case 3:
                Administrador administrador = leitor.lerAdministrador();
                if (leitor.lerLoginSenhaUsuarios(administrador) != null) {
                    Servico servico = registrador.cadastrarServico();
                    registrador.armazenarServico(servico, caminho);
                }
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
                break;
            case 10:
                try {
                    barbearia.exibirInformacoes();
                } catch (NullPointerException p) {
                    System.out.println("* Informacoes nao encontradas.");
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
