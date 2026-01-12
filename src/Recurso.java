public class Recurso {
    private int id;
    private String nome;
    private String tipo;
    private boolean disponivel;

    public Recurso(int id, String nome, String tipo, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.disponivel = disponivel;
    }

    public Recurso(Recurso original) {
        this.id = original.id;
        this.nome = original.nome;
        this.tipo = original.tipo;
        this.disponivel = original.disponivel;
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

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean verificarDisponibilidade() {
        return this.disponivel;
    }

    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        System.out.println("- Nome: " + this.getNome());
        System.out.println("- Tipo: " + this.getTipo());
        System.out.println("- Disponível: " + (this.isDisponivel() ? "Sim" : "Não"));
    }
}