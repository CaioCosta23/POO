public class NotificacaoServico {
    private int id;
    private EnumTipoNotificacao tipo;
    
    public NotificacaoServico(int id, EnumTipoNotificacao tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumTipoNotificacao getTipo() {
        return this.tipo;
    }

    public void setTipo(EnumTipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public void enviarEmail() {
        System.out.println();
    }

    public void enviarSMS() {
        System.out.println();
    }

    public void enviarPush() {
        System.out.println();
    }
}
