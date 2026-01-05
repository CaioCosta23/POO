public enum EnumTipoNotificacao {

    CONFIRMACAO_AGANDAMENTO {
        @Override
        public void notificacao() {
            System.out.println("Agendamento CONFIRMADO!");
        }
    },
    CANCELAMENTO {
        @Override
        public void notificacao() {
            System.out.println("Agendamento CANCELADO!");
        }
    },
    LEMBRETE_24H {
        @Override
        public void notificacao() {
            System.out.println("");
        }
    },
    NOVOS_SERVICOS {
        @Override
        public void notificacao() {
            System.out.println("");
        }
    };

    public abstract void notificacao();
}
