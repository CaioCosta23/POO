
public class Servico {
    private int id;
    private String nome;
    private String descricao;
    private float preco;
    private static final int DURACAO = 40;

    public Servico(int id, String nome, String descricao, float preco) {
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

    public static int getDuracao() {
        return DURACAO;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d;%d", this.getId(), this.getNome(), this.getDescricao(), (int)this.getPreco(), DURACAO);
    }

    public void exibirInformacoes() {
        System.out.println("\t#- ID: " + this.getId());
        System.out.println("\t# Nome: " + this.getNome());
        System.out.println("\t# Descricao: " + this.getDescricao());
        System.out.printf("\t# Duracao: %02d minutos\n",  DURACAO);
        System.out.printf("\t Preco: %.2f\n",  this.getPreco());
    }
}
