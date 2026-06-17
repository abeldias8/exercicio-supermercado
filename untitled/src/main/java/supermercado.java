public class supermercado {
    public static void main(String[] args) {
        // Array de Strings (Itens)
        String[] itensCatalogo = {"Arroz", "Feijão", "Café"};

        // Array de floats (Preços) - Notar o uso obrigatório do sufixo 'f'
        float[] precosCatalogo = {25.50f, 8.90f, 15.00f};

        // Sincronizando dados pelo mesmo índice
        int indiceProduto = 1;
        System.out.println("O " + itensCatalogo[indiceProduto] + " custa R$ " + precosCatalogo[indiceProduto]);
        // Saída: O Feijão custa R$ 8.90
    }
}
