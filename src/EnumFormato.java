public enum EnumFormato {
    /*
     * EXPRESSÃO REGULAR:
     *
     * Trata-se de uma expressão que define um padrão de escrita/validação de algum dado (geralmente também descritivo);
     * Não depende do objeto, sendo uma regra/expressão fixa, fazendo com que o proograma seja mais eficiente e sem a necessidade de instanciação de um objeto;
     * Não pode ser alterada, evitando bugs acidentais e garantindo consistência para o código;
     * 
     * 
     * Destrinchando as expressões abaixo (em si):
     * 
     * O "^" indica que não pode haver mais nada antes disso;
     * 
     * CPF, CEP, CNPJ:
     * "0-9" representa a possibilidade de haver todo e qualquer algarismo de 0 à 9;
     * 
     * 
     * EMAIL:
     * "A-Z" representa a possibilidade de haver toda e qualquer letra maiúscula do alfabeto;
     * "a-z" representa a possibilidade de haver toda e qualquer letra miniúscula do alfabeto;
     * * "+_.-" Indica os simbolos permitidos em um endereço de e-mail;
     * "+" indica que pode haver um ou mais caracteres depois;
     * "@" indica que ele tem que estar obrigatóriamente nos dados;
     * "\\." indica que o ponto também é obrigatório (o "\" é necessário para "escapar" o caractere, uma vez que o ponto tem um significado especial em expressões regulares);
     * "{2,}" indica que deve haver no mínimo 2 caracteres após o ponto (como em .com, .br, .org, etc.);
     * "$" indica que não pode haver mais nenhum caractere após isso;
     * 
     * 
     * HORARIO:
     * "01" representa a possibilidade de haver o algarismo  0 ou 1(na primeira posição no caso do horário);
     * "|2": Indica que obrigatóriamente caso haja outro algarismo na primeira posição, além de 0 e 1, ele deve ser o número 2;
     * "0-3" representa a possibilidade de haver todo e qualquer algarismo de 0 à 3;
     * ":" Indica que ele tem que estar obrigatóriamente nos dados;
     * "0-5" representa a possibilidade de haver todo e qualquer algarismo de 0 à 5;
     * 
     * OBS: Geralmente essa variável é nomeada como "REGEX";
     */
    CPF("^([0-9][0-9][0-9])([0-9][0-9][0-9])([0-9][0-9][0-9])([0-9][0-9])$"),
    CNPJ("^([0-9][0-9]).([0-9][0-9][0-9]).([0-9][0-9][0-9])/([0-9][0-9][0-9][0-9])-([0-9][0-9])$"),
    CEP("^([0-9][0-9][0-9][0-9][0-9])-([0-9][0-9][0-9])$"),
    EMAIL("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[A-Za-z]{2,}$"),
    HORARIO("^([01][0-9]|2[0-3]):[0-5][0-9]$");

    public final String FORMATO;

    private EnumFormato(String formato) {
        this.FORMATO = formato;
    }
}
