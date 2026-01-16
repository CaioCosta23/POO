import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
public class Barbearia{
    private String nome;
    protected Endereco endereco;
    private String telefone;
    private String email;
    protected static LocalTime abertura;
    protected static LocalTime fechamento;
    private String cnpj;
    //private PoliticaCancelamento politica;
    private final Map<String, Cliente> clientes = new HashMap<>();
    private final Map <String, Barbeiro> barbeiros = new HashMap<>();
    private final Map<Integer, Servico> servicos = new TreeMap<>();
    private final Map<Integer, Agendamento> agendamentos = new HashMap<>();
    private static Administrador administrador;
    
    public Barbearia(String nome, Endereco endereco, String telefone, String email, LocalTime abertura,
            LocalTime fechamento, String cnpj, Administrador administrador) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        Barbearia.abertura = abertura;
        Barbearia.fechamento = fechamento;
        this.cnpj = cnpj;
        //this.politica = null;
        Barbearia.administrador = administrador;
    }

    // Construtor para cópia profunda de TODOS os dados da Barbearia;
    public Barbearia(Barbearia original) {
        this.nome = original.nome;
        this.endereco = original.endereco;
        this.telefone = original.telefone;
        this.email = original.email;
        this.cnpj = original.cnpj;
        //this.politica = null;
        Barbearia.administrador = adicionarAdministrador(administrador);
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


    public static LocalTime getAbertura() {
        return Barbearia.abertura;
    }

    public static LocalTime getFechamento() {
        return Barbearia.fechamento;
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

    public Map<String, Cliente> getClientes() {
        return new HashMap<>(this.clientes);
    }

    public Map<String, Barbeiro> getBarbeiros() {
        return new HashMap<>(this.barbeiros);
    }
    
    public Map<Integer, Servico> getServicos() {
        return new HashMap<>(this.servicos);
    }

    public Map<Integer, Agendamento> getAgendamentos() {
        return new HashMap<>(this.agendamentos);
    }

    public void adicionarClientes(Map<String, Cliente>clientes) {
        this.clientes.clear();
        this.clientes.putAll(clientes);
    }

    public void adicionarBarbeiros(Map<String, Barbeiro>barbeiros) {
        this.barbeiros.clear();
        this.barbeiros.putAll(barbeiros);
    }

    public void adicionarServicos(Map<Integer, Servico>servicos) {
        this.servicos.clear();
        this.servicos.putAll(servicos);
    }

    public void adicionarAgendamentos(Map<Integer, Agendamento> agendamentos) {
        this.agendamentos.clear();
        this.agendamentos.putAll(agendamentos);
    }

    private static Administrador adicionarAdministrador(Administrador original) {
        return original;
    }
    
    public void exibirMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Menu de exibição;
        System.out.println("===============================================================================================================");
        System.out.println("\t\t\t\t\t" + this.getNome());
        System.out.println("===============================================================================================================");

        System.out.println("-> Bem-Vindo!\n");

        System.out.println("+ Selecione uma das opcoes abaixo:");

        System.out.println("[1] Acessar Area do Cliente\t\t [2] Acessar Area do prestador de Servicos");
        System.out.println("[3] Acessar Area do Administrador\t [4] Consultar informacoes do estabelecimento");

    }

    public void exibirInformacoes() {
        System.out.println("- Nome do Estabelecimento: " + this.getNome());
        System.out.println("- Telefone: " + this.getTelefone());
        System.out.println("- Email: " + getEmail());

        System.out.println("- Endereco:");
        this.getEndereco().imprimeEndereco();

        System.out.printf("- Horario de Funcionamento %02d:%02d - %02d:%02d\n", Barbearia.getAbertura().getHour(), Barbearia.getAbertura().getMinute(),
                                                                                      Barbearia.getFechamento().getHour(), Barbearia.getFechamento().getMinute());
        System.out.println("- CNPJ: " + this.getCnpj());
    }
}
