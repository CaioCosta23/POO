import java.time.LocalTime;
public class Barbearia {
    private String nome;
    protected Endereco endereco;
    private String telefone;
    private String email;
    protected LocalTime abertura;
    protected LocalTime fechamento;
    private String cnpj;
    private PoliticaCancelamento politica;


    public Barbearia(String nome, Endereco endereco, String telefone, String email, String cnpj) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cnpj = cnpj;
    }

    
    public Barbearia(String nome, Endereco endereco, String telefone, String email, LocalTime abertura,
            LocalTime fechamento, String cnpj, PoliticaCancelamento politica) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.cnpj = cnpj;
        this.politica = politica;
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


    public PoliticaCancelamento getPolitica() {
        return this.politica;
    }


    public void setPolitica(PoliticaCancelamento politica) {
        this.politica = politica;
    }

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
