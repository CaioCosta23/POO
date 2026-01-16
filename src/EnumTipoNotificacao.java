public enum EnumTipoNotificacao {

    CONFIRMACAO_AGANDAMENTO {
        @Override
        public void notificacao() {
            System.out.println("+ Agendamento CONFIRMADO!");
        }
    },
    CANCELAMENTO {
        @Override
        public void notificacao() {
            System.out.println("+ Agendamento CANCELADO!");
        }
    },
    LEMBRETE_24H {
        @Override
        public void notificacao() {
            System.out.println("+ Lembrete: Seu agendamento é em 24 horas!");
        }
    },
    NOVOS_SERVICOS {
        @Override
        public void notificacao() {
            System.out.println("+ Novos serviços disponíveis! Confira agora!");
        }
    };

    public abstract void notificacao();
}
