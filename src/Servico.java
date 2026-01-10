public class Servico implements Menu {
    private int id;
    private String nome;
    private String descricao;
    private int duracao;
    private float preco;
    
    public Servico(int id, String nome, String descricao, int duracao, float preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.duracao = duracao;
        this.preco = preco;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public float getPreco() {
        return this.preco;
    }

    public void setId(float preco) {
        this.preco = preco;
    }

    @Override
    public void exibirMenu() {
        // Vazio (Por enquanto);
    }

    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        System.out.println("- Nome: " + this.getNome());
        System.out.println("- Descricao: " + this.getDescricao());
        System.out.printf("- Duracao: %02d minutos" + this.getDuracao());
        System.out.printf("- Preco %.2f: " + this.getPreco());
    }
}
