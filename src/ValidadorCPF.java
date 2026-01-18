public class ValidadorCPF {

    public boolean isValido(String cpf) {
        if (cpf == null) return false;

        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) return false;

        // rejeita CPFs com todos os dígitos iguais
        char first = digits.charAt(0);
        boolean allEqual = true;
        for (int i = 1; i < digits.length(); i++) {
            if (digits.charAt(i) != first) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) return false;

        int d1 = calculaDigito(digits, 9);
        int d2 = calculaDigito(digits, 10);

        return d1 == Character.getNumericValue(digits.charAt(9)) &&
               d2 == Character.getNumericValue(digits.charAt(10));
    }

    private int calculaDigito(String digits, int pos) {
        int sum = 0;
        int weight = pos + 1;

        for (int i = 0; i < pos; i++) {
            int n = Character.getNumericValue(digits.charAt(i));
            sum += n * weight--;
        }

        int mod = (sum * 10) % 11;
        return (mod == 10) ? 0 : mod;
    }
}
