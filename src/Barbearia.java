import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
public class Barbearia implements Impressao{
    private String nome;
    protected Endereco endereco;
    private String telefone;
    private String email;
    protected LocalTime abertura;
    protected LocalTime fechamento;
    private String cnpj;
    //private PoliticaCancelamento politica;
    Map <Integer, Barbeiro> barbeiros = new HashMap<>();
    
    public Barbearia(String nome, Endereco endereco, String telefone, String email, LocalTime abertura,
            LocalTime fechamento, String cnpj) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.cnpj = cnpj;
        //this.politica = null;
    }


    public String getNome() {
        return this.nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public Endereco getEndereco() {
        return this.endereco;
    }


    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


    public String getTelefone() {
        return this.telefone;
    }


    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public LocalTime getAbertura() {
        return this.abertura;
    }


    public void setAbertura(LocalTime abertura) {
        this.abertura = abertura;
    }


    public LocalTime getFechamento() {
        return this.fechamento;
    }


    public void setFechamento(LocalTime fechamento) {
        this.fechamento = fechamento;
    }


    public String getCnpj() {
        return this.cnpj;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

/* 
    public PoliticaCancelamento getPolitica() {
        return this.politica;
    }


    public void setPolitica(PoliticaCancelamento politica) {
        this.politica = politica;
    }
        */

    public Map<Integer, Barbeiro> getBarbeiros() {
        return new HashMap<>(this.barbeiros);
    }
    
    public void exibirMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
        // Menu de exibição;
        System.out.println("===============================================================================================================");
        System.out.println("\t\t\t\t\t" + this.getNome());
        System.out.println("===============================================================================================================");

        System.out.println("-> Bem-Vindo!\n");

        System.out.println("# Informe o servico do qual deseja acessar:\n");

        System.out.println("[1] Criar agendamento\t[2] Excluir Agendamento");
        System.out.println("[4] Adicionar Servico\t[4] Remover Servico");
        System.out.println("[5] Adicioanar Usuario\t[6] Excluir Usuario");
        System.out.println("[7] Consultar agendamento\t[8] Consultar Dados Usuarios ");
        System.out.println("[9] Buscar Servico\t[10] Informacoes do estabelecimento ");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- Nome do Estabelecimento: " + this.getNome());
        System.out.println("- Telefone: " + this.getTelefone());
        System.out.println("- Email: " + getEmail());

        System.out.println("- Endereco:");
        this.getEndereco().imprimeEndereco();

        System.out.printf("- Horario de Funcionamento %02d:%02d - %02d:%02d\n", this.getAbertura().getHour(), this.getAbertura().getMinute(),
                                                                                      this.getFechamento().getHour(), this.getFechamento().getMinute());
        System.out.println("- CNPJ: " + this.getCnpj());
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
}
