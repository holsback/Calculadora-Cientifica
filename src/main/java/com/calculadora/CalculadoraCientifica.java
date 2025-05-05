package main.java.com.calculadora;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraCientifica extends JFrame implements ActionListener {

    private JTextField display;
    private String currentInput = "";
    private String currentOperator = "";
    private double firstOperand = 0;
    private JPanel painelCientifico;
    private boolean cientificoVisivel = false;

    // Paleta de cores escuras com toque futurista
    private Color backgroundColor = new Color(30, 30, 30);
    private Color buttonColor = new Color(60, 60, 60);
    private Color operatorColor = new Color(80, 80, 180);
    private Color specialButtonColor = new Color(100, 100, 100);
    private Color foregroundColor = Color.WHITE;
    private Font buttonFont = new Font("Arial", Font.BOLD, 16);
    private Font displayFont = new Font("Consolas", Font.PLAIN, 30);

    public CalculadoraCientifica() {
        setTitle("Calculadora Científica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600); // Definido para 600x600
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        // Criar o display com fonte maior
        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(backgroundColor.darker());
        display.setForeground(foregroundColor);
        display.setFont(displayFont);
        display.setBorder(new EmptyBorder(10, 10, 20, 10));
        add(display, BorderLayout.NORTH);

        // Painel principal para botões básicos (CENTER)
        JPanel painelBasico = new JPanel(new GridLayout(5, 4, 5, 5));
        painelBasico.setBackground(backgroundColor);
        String[] botoesBasicos = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C"
        };
        for (String textoBotao : botoesBasicos) {
            painelBasico.add(criarBotao(textoBotao));
        }
        add(painelBasico, BorderLayout.CENTER);

        // Botão para mostrar/esconder funções científicas (WEST)
        JButton botaoMenuCientifico = new JButton("≡"); // Ícone de menu simples
        botaoMenuCientifico.addActionListener(e -> {
            cientificoVisivel = !cientificoVisivel;
            painelCientifico.setVisible(cientificoVisivel);
            pack(); // Podemos manter o pack aqui para ajustar ao mostrar/esconder
        });
        botaoMenuCientifico.setFont(buttonFont);
        botaoMenuCientifico.setForeground(foregroundColor);
        botaoMenuCientifico.setBackground(specialButtonColor);
        botaoMenuCientifico.setFocusPainted(false);

        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelMenu.setBackground(backgroundColor);
        painelMenu.add(botaoMenuCientifico);
        add(painelMenu, BorderLayout.WEST);

        // Painel para botões científicos (inicialmente oculto)
        painelCientifico = new JPanel(new GridLayout(4, 3, 5, 5));
        painelCientifico.setBackground(backgroundColor);
        String[] botoesCientificos = {
                "sin", "cos", "tan",
                "log", "√", "^",
                "ln", "e^x", "10^x",
                "abs", "1/x"
        };
        for (String textoBotao : botoesCientificos) {
            painelCientifico.add(criarBotao(textoBotao));
        }
        painelCientifico.setVisible(false); // Inicialmente oculto
        add(painelCientifico, BorderLayout.EAST); // Adicionado à direita e inicialmente oculto

        // pack(); // Remova ou comente esta linha
        setVisible(true);
    }

    // Método para criar botões com estilo consistente
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.addActionListener(this);
        botao.setFont(buttonFont);
        botao.setForeground(foregroundColor);
        botao.setBackground(buttonColor);
        botao.setFocusPainted(false);

        if (texto.equals("C") || texto.equals("=")) {
            botao.setBackground(specialButtonColor);
        } else if (texto.equals("+") || texto.equals("-") || texto.equals("×") || texto.equals("÷") || texto.equals("^")) {
            botao.setBackground(operatorColor);
        } else if (texto.equals("sin") || texto.equals("cos") || texto.equals("tan") || texto.equals("log") || texto.equals("√") || texto.equals("ln") || texto.equals("e^x") || texto.equals("10^x") || texto.equals("abs") || texto.equals("1/x")) {
            botao.setBackground(specialButtonColor.darker());
        }
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraCientifica());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
            case ".":
                currentInput += comando;
                display.setText(currentInput);
                break;
            case "+": case "-": case "×": case "÷": case "^":
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    currentOperator = (comando.equals("×") ? "*" : (comando.equals("÷") ? "/" : comando));
                    currentInput = "";
                    display.setText("");
                }
                break;
            case "=":
                if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = calculate(firstOperand, secondOperand, currentOperator);
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                    currentOperator = "";
                    firstOperand = 0;
                }
                break;
            case "C":
                currentInput = "";
                currentOperator = "";
                firstOperand = 0;
                display.setText("");
                break;
            case "sin": case "cos": case "tan": case "log": case "√": case "ln": case "e^x": case "10^x": case "abs": case "1/x":
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    double result = 0;
                    switch (comando) {
                        case "sin":
                            result = Math.sin(Math.toRadians(value));
                            break;
                        case "cos":
                            result = Math.cos(Math.toRadians(value));
                            break;
                        case "tan":
                            result = Math.tan(Math.toRadians(value));
                            break;
                        case "log":
                            if (value > 0) {
                                result = Math.log10(value);
                            } else {
                                display.setText("Erro: Log de número não positivo");
                                currentInput = "";
                                return;
                            }
                            break;
                        case "√":
                            if (value >= 0) {
                                result = Math.sqrt(value);
                            } else {
                                display.setText("Erro: Raiz de número negativo");
                                currentInput = "";
                                return;
                            }
                            break;
                        case "ln":
                            if (value > 0) {
                                result = Math.log(value);
                            } else {
                                display.setText("Erro: Log natural de número não positivo");
                                currentInput = "";
                                return;
                            }
                            break;
                        case "e^x":
                            result = Math.exp(value);
                            break;
                        case "10^x":
                            result = Math.pow(10, value);
                            break;
                        case "abs":
                            result = Math.abs(value);
                            break;
                        case "1/x":
                            if (value != 0) {
                                result = 1 / value;
                            } else {
                                display.setText("Erro: Divisão por zero");
                                currentInput = "";
                                return;
                            }
                            break;
                    }
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                }
                break;
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    display.setText("Erro: Divisão por zero");
                    return Double.NaN;
                }
            case "^":
                return Math.pow(num1, num2);
            default:
                return num2;
        }
    }
}