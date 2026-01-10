public class Servico implements Impressao {
    private int id;
    private String nome;
    private String descricao;
    private float preco;
    private static final int DURACAO = 40;
    
    public Servico(int id, String nome, String descricao, int duracao, float preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Servico(Servico original) {
        this.id = original.id;
        this.nome = original.nome;
        this.descricao = original.descricao;
        this.preco = original.preco;
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

    public float getPreco() {
        return this.preco;
    }

    public void setId(float preco) {
        this.preco = preco;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        System.out.println("- Nome: " + this.getNome());
        System.out.println("- Descricao: " + this.getDescricao());
        System.out.printf("- Duracao: %02d minutos\n",  DURACAO);
        System.out.printf("- Preco: %.2f\n",  this.getPreco());
    }
}
