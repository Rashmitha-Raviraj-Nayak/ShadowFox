package gui;

import calculator.conversion.CurrencyConverter;
import calculator.conversion.LengthConverter;
import calculator.conversion.TemperatureConverter;
import calculator.operations.BasicOperations;
import calculator.operations.ScientificOperations;
import calculator.operations.StatisticalOperations;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CalculatorFrame extends JFrame {

    private JTextField displayField;
    private JTextField num1Field;
    private JTextField num2Field;
    private JComboBox<String> modeBox;
    private JComboBox<String> operationBox;
    private JTextArea historyArea;
    private List<String> calculationHistory;
    
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);

    public CalculatorFrame() {
        setTitle("🧮 Enhanced Calculator Pro");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        calculationHistory = new ArrayList<>();
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterTabbedPane(), BorderLayout.CENTER);
        mainPanel.add(createHistoryPanel(), BorderLayout.EAST);
        
        add(mainPanel);
        
        // Add global keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleGlobalKeyPress(e);
            }
        });
        
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Advanced Calculator Suite");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Basic • Scientific • Conversion");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(189, 195, 199));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        displayField = new JTextField();
        displayField.setFont(new Font("Consolas", Font.BOLD, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(true);
        displayField.setText("0");
        displayField.setBackground(Color.WHITE);
        displayField.setForeground(new Color(0, 0, 0));
        displayField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        displayField.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 2));
        displayField.setFocusable(true);
        
        // Add keyboard support to display field
        displayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performCalculation();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    displayField.setText("0");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String text = displayField.getText();
                    if (text.length() > 1) {
                        displayField.setText(text.substring(0, text.length() - 1));
                    } else {
                        displayField.setText("0");
                    }
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Allow numbers, operators, decimal point, and backspace
                if (Character.isDigit(c) || c == '.' || c == '-' || c == '+' 
                    || c == '*' || c == '/' || c == '(' || c == ')' || c == '\b' || c == '\n') {
                    if (displayField.getText().equals("0") && Character.isDigit(c)) {
                        displayField.setText("");
                    }
                    // Allow the character
                } else {
                    e.consume(); // Prevent invalid characters
                }
            }
        });
        
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(15));
        header.add(displayField);
        
        return header;
    }

    private JTabbedPane createCenterTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        tabbedPane.addTab("📊 Basic Calculator", createBasicCalculatorTab());
        tabbedPane.addTab("🔬 Scientific", createScientificTab());
        tabbedPane.addTab("🌡️ Temperature", createTemperatureTab());
        tabbedPane.addTab("💱 Currency", createCurrencyTab());
        tabbedPane.addTab("📏 Length", createLengthTab());
        tabbedPane.addTab("📈 Statistics", createStatisticsTab());
        
        return tabbedPane;
    }

    private JPanel createBasicCalculatorTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Display field at top
        num1Field = createStyledTextField();
        num1Field.setFont(new Font("Consolas", Font.BOLD, 32));
        num1Field.setEditable(true);
        num1Field.setHorizontalAlignment(JTextField.RIGHT);
        num1Field.setForeground(new Color(0, 0, 0));
        num1Field.setBackground(Color.WHITE);
        
        num1Field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleBasicCalculatorEnter();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    num1Field.setText("0");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String text = num1Field.getText();
                    if (text.length() > 1) {
                        num1Field.setText(text.substring(0, text.length() - 1));
                    } else {
                        num1Field.setText("0");
                    }
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '-' && c != '+' 
                    && c != '*' && c != '/' && c != '%' && c != '\b' && c != '\n') {
                    e.consume();
                }
            }
        });
        
        // Calculator grid
        JPanel calcPanel = new JPanel(new BorderLayout(10, 10));
        calcPanel.setBackground(BACKGROUND_COLOR);
        
        calcPanel.add(num1Field, BorderLayout.NORTH);
        
        // Button grid - 4x4 layout for calculator style
        JPanel gridPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        gridPanel.setBackground(BACKGROUND_COLOR);
        
        String[][] buttonLabels = {
            {"%", "CE", "C", "←"},
            {"1/x", "x²", "√x", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"+/-", "0", ".", "="}
        };
        
        operationBox = new JComboBox<>();  // Hidden operation box for internal use
        num2Field = new JTextField();  // Hidden field for second number
        
        for (String[] row : buttonLabels) {
            for (String label : row) {
                JButton btn = createCalculatorButton(label, num1Field);
                gridPanel.add(btn);
            }
        }
        
        calcPanel.add(gridPanel, BorderLayout.CENTER);
        panel.add(calcPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void handleBasicCalculatorEnter() {
        String text = num1Field.getText().trim();
        if (text.isEmpty() || text.equals("0")) return;
        
        try {
            // Simple expression evaluation
            double result = evaluateExpression(text);
            num1Field.setText(String.valueOf(result));
            addToHistory(text + " = " + result);
        } catch (Exception ex) {
            showError("Invalid expression");
        }
    }
    
    private double evaluateExpression(String expr) throws Exception {
        // Remove spaces
        expr = expr.replaceAll("\\s+", "");
        
        // Normalize visual operator characters to JS-friendly operators
        expr = expr.replace("×", "*")
                   .replace("÷", "/")
                   .replace("−", "-")
                   .replace("—", "-");

        // Handle percentage tokens like 50% -> (50/100)
        expr = expr.replaceAll("(\\d+\\.?\\d*)%", "($1/100)");

        // Trim trailing operators (avoid JS eval errors)
        expr = expr.replaceAll("[+\\-*/. ]+$", "");

        // Try using script engine for evaluation (if available)
        javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
        javax.script.ScriptEngine engine = manager.getEngineByName("JavaScript");
        if (engine == null) engine = manager.getEngineByName("nashorn");
        if (engine != null) {
            Object result = engine.eval(expr);
            if (result instanceof Double) return (Double) result;
            if (result instanceof Number) return ((Number) result).doubleValue();
        }

        // Fallback: simple shunting-yard evaluator (supports + - * / and parentheses)
        return evaluateWithShuntingYard(expr);
    }

    private double evaluateWithShuntingYard(String expr) throws Exception {
        if (expr == null || expr.isEmpty()) throw new Exception("Empty expression");

        // Handle unary minus: prefix with 0 when necessary
        if (expr.startsWith("-")) expr = "0" + expr;
        expr = expr.replace("(-", "(0-");

        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) continue;

            if (ch == '(') {
                ops.push(ch);
            } else if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            } else if (ch == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') applyOp(values, ops.pop());
                if (ops.isEmpty() || ops.pop() != '(') throw new Exception("Mismatched parentheses");
            } else if (isOperator(ch)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(ch)) {
                    applyOp(values, ops.pop());
                }
                ops.push(ch);
            } else {
                throw new Exception("Invalid character: " + ch);
            }
        }

        while (!ops.isEmpty()) applyOp(values, ops.pop());

        if (values.size() != 1) throw new Exception("Invalid calculation");
        return values.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    private void applyOp(Stack<Double> values, char op) throws Exception {
        if (values.size() < 2) throw new Exception("Invalid calculation");
        double b = values.pop();
        double a = values.pop();
        double res;
        switch (op) {
            case '+' -> res = a + b;
            case '-' -> res = a - b;
            case '*' -> res = a * b;
            case '/' -> {
                if (b == 0) throw new Exception("Division by zero");
                res = a / b;
            }
            default -> throw new Exception("Unknown operator: " + op);
        }
        values.push(res);
    }
    
    private JButton createCalculatorButton(String label, JTextField display) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 2));
        btn.setMargin(new java.awt.Insets(10, 10, 10, 10));
        btn.setPreferredSize(new Dimension(80, 60));
        btn.setMinimumSize(new Dimension(70, 50));
        
        // Color coding for different button types (foreground set to dark for readability)
        if (label.equals("=")) {
            btn.setBackground(SUCCESS_COLOR);
        } else if (label.equals("C") || label.equals("CE") || label.equals("←")) {
            btn.setBackground(ERROR_COLOR);
        } else if ("÷×-+%".contains(label) || "1/x√".contains(label)) {
            btn.setBackground(SECONDARY_COLOR);
        } else {
            btn.setBackground(new Color(220, 220, 220));
        }
        // Make all button text bold and dark for better visibility
        btn.setForeground(TEXT_COLOR);

        // Add hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(btn.getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (label.equals("=")) {
                    btn.setBackground(SUCCESS_COLOR);
                } else if (label.equals("C") || label.equals("CE") || label.equals("←")) {
                    btn.setBackground(ERROR_COLOR);
                } else if ("÷×-+%".contains(label) || "1/x√".contains(label)) {
                    btn.setBackground(SECONDARY_COLOR);
                } else {
                    btn.setBackground(new Color(220, 220, 220));
                }
            }
        });
        
        // Button actions
        btn.addActionListener(e -> handleCalculatorButton(label, display));
        
        return btn;
    }
    
    private void handleCalculatorButton(String label, JTextField display) {
        String currentText = display.getText();
        
        if (currentText.equals("0") && Character.isDigit(label.charAt(0))) {
            display.setText(label);
        } else {
            switch (label) {
                case "=" -> handleBasicCalculatorEnter();
                case "C" -> display.setText("0");
                case "CE" -> display.setText("0");
                case "←" -> {
                    if (currentText.length() > 1) {
                        display.setText(currentText.substring(0, currentText.length() - 1));
                    } else {
                        display.setText("0");
                    }
                }
                case "+/-" -> {
                    try {
                        double val = Double.parseDouble(currentText);
                        display.setText(String.valueOf(-val));
                    } catch (NumberFormatException ex) {
                        // If it's an expression, append +/- operator
                        display.setText(currentText + "*-1");
                    }
                }
                case "√x" -> {
                    try {
                        double val = Double.parseDouble(currentText);
                        BigDecimal bd = new BigDecimal(val);
                        BigDecimal result = BasicOperations.squareRoot(bd);
                        display.setText(result.toString());
                        addToHistory("√(" + val + ") = " + result);
                    } catch (Exception ex) {
                        showError("Invalid input for square root");
                    }
                }
                case "x²" -> {
                    try {
                        double val = Double.parseDouble(currentText);
                        BigDecimal bd = new BigDecimal(val);
                        BigDecimal result = BasicOperations.square(bd);
                        display.setText(result.toString());
                        addToHistory(val + "² = " + result);
                    } catch (Exception ex) {
                        showError("Invalid input for square");
                    }
                }
                case "1/x" -> {
                    try {
                        double val = Double.parseDouble(currentText);
                        BigDecimal bd = new BigDecimal(val);
                        BigDecimal result = BasicOperations.inverse(bd);
                        display.setText(result.toString());
                        addToHistory("1/" + val + " = " + result);
                    } catch (Exception ex) {
                        showError("Cannot divide by zero");
                    }
                }
                case "%" -> {
                    try {
                        double val = Double.parseDouble(currentText);
                        BigDecimal bd = new BigDecimal(val);
                        BigDecimal result = BasicOperations.percentage(bd);
                        display.setText(result.toString());
                    } catch (Exception ex) {
                        showError("Invalid percentage");
                    }
                }
                default -> display.setText(currentText + label);
            }
        }
    }

    private JPanel createScientificTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        JTextField sciNum1 = createStyledTextField();
        JTextField sciNum2 = createStyledTextField();
        
        // Add Enter key support
        sciNum1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sciNum2.requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    sciNum2.requestFocus();
                    e.consume();
                }
            }
        });
        
        sciNum2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performScientificCalculation(sciNum1, sciNum2, (JComboBox<String>) inputPanel.getComponent(5));
                }
            }
        });
        
        addLabeledTextField(inputPanel, "Number:", sciNum1);
        addLabeledTextField(inputPanel, "Power/Value:", sciNum2);
        
        JComboBox<String> sciOpBox = new JComboBox<>(new String[]{"Square Root", "Power", "Sine (degrees)", "Cosine (degrees)", "Tangent (degrees)", "Logarithm (log10)", "Natural Log (ln)", "Exponential (e^x)", "Absolute Value", "Factorial"});
        addLabeledComponent(inputPanel, "Operation:", sciOpBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton sciCalcBtn = createStyledButton("Calculate", SUCCESS_COLOR);
        JButton sciClearBtn = createStyledButton("Clear", ERROR_COLOR);
        
        sciCalcBtn.addActionListener(e -> performScientificCalculation(sciNum1, sciNum2, sciOpBox));
        
        sciClearBtn.addActionListener(e -> {
            sciNum1.setText("");
            sciNum2.setText("");
            displayField.setText("0");
        });
        
        buttonPanel.add(sciCalcBtn);
        buttonPanel.add(sciClearBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void performScientificCalculation(JTextField sciNum1, JTextField sciNum2, JComboBox<String> sciOpBox) {
        try {
            BigDecimal num = new BigDecimal(sciNum1.getText());
            String operation = (String) sciOpBox.getSelectedItem();
            BigDecimal result = null;
            String displayText = "";
            
            switch (operation) {
                case "Square Root" -> {
                    result = ScientificOperations.squareRoot(num);
                    displayText = "√(" + num + ") = " + result;
                }
                case "Power" -> {
                    int exponent = Integer.parseInt(sciNum2.getText());
                    result = ScientificOperations.power(num, exponent);
                    displayText = num + "^" + exponent + " = " + result;
                }
                case "Sine (degrees)" -> {
                    result = ScientificOperations.sine(num);
                    displayText = "sin(" + num + "°) = " + result;
                }
                case "Cosine (degrees)" -> {
                    result = ScientificOperations.cosine(num);
                    displayText = "cos(" + num + "°) = " + result;
                }
                case "Tangent (degrees)" -> {
                    result = ScientificOperations.tangent(num);
                    displayText = "tan(" + num + "°) = " + result;
                }
                case "Logarithm (log10)" -> {
                    result = ScientificOperations.logarithm(num);
                    displayText = "log(" + num + ") = " + result;
                }
                case "Natural Log (ln)" -> {
                    result = ScientificOperations.naturalLog(num);
                    displayText = "ln(" + num + ") = " + result;
                }
                case "Exponential (e^x)" -> {
                    result = ScientificOperations.exponential(num);
                    displayText = "e^" + num + " = " + result;
                }
                case "Absolute Value" -> {
                    result = ScientificOperations.absolute(num);
                    displayText = "|" + num + "| = " + result;
                }
                case "Factorial" -> {
                    int n = num.intValue();
                    result = ScientificOperations.factorial(n);
                    displayText = n + "! = " + result;
                }
            }
            
            if (result != null) {
                displayField.setText(displayText);
                addToHistory(operation + ": " + num + " → " + result);
            }
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers. Factorial requires an integer.");
        } catch (ArithmeticException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Calculation error: " + ex.getMessage());
        }
    }

    private JPanel createTemperatureTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        JTextField tempInput = createStyledTextField();
        JComboBox<String> tempOpBox = new JComboBox<>(new String[]{"Celsius to Fahrenheit", "Fahrenheit to Celsius"});
        
        // Add Enter key support
        tempInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performTemperatureConversion(tempInput, tempOpBox);
                }
            }
        });
        
        addLabeledTextField(inputPanel, "Temperature:", tempInput);
        addLabeledComponent(inputPanel, "Conversion:", tempOpBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton tempCalcBtn = createStyledButton("Convert", SUCCESS_COLOR);
        JButton tempClearBtn = createStyledButton("Clear", ERROR_COLOR);
        
        tempCalcBtn.addActionListener(e -> performTemperatureConversion(tempInput, tempOpBox));
        
        tempClearBtn.addActionListener(e -> {
            tempInput.setText("");
            displayField.setText("0");
        });
        
        buttonPanel.add(tempCalcBtn);
        buttonPanel.add(tempClearBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void performTemperatureConversion(JTextField tempInput, JComboBox<String> tempOpBox) {
        try {
            BigDecimal temp = new BigDecimal(tempInput.getText());
            String operation = (String) tempOpBox.getSelectedItem();
            BigDecimal result = null;
            
            if (operation.equals("Celsius to Fahrenheit")) {
                result = TemperatureConverter.celsiusToFahrenheit(temp);
                displayField.setText(temp + "°C → " + result + "°F");
            } else {
                result = TemperatureConverter.fahrenheitToCelsius(temp);
                displayField.setText(temp + "°F → " + result + "°C");
            }
            addToHistory(operation + ": " + temp + " → " + result);
        } catch (Exception ex) {
            showError("Please enter valid temperature");
        }
    }

    private JPanel createCurrencyTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        JTextField currencyInput = createStyledTextField();
        JComboBox<String> currencyOpBox = new JComboBox<>(new String[]{"USD to INR", "INR to USD"});
        
        // Add Enter key support
        currencyInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performCurrencyConversion(currencyInput, currencyOpBox);
                }
            }
        });
        
        addLabeledTextField(inputPanel, "Amount:", currencyInput);
        addLabeledComponent(inputPanel, "Conversion:", currencyOpBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton currCalcBtn = createStyledButton("Convert", SUCCESS_COLOR);
        JButton currClearBtn = createStyledButton("Clear", ERROR_COLOR);
        
        currCalcBtn.addActionListener(e -> performCurrencyConversion(currencyInput, currencyOpBox));
        
        currClearBtn.addActionListener(e -> {
            currencyInput.setText("");
            displayField.setText("0");
        });
        
        buttonPanel.add(currCalcBtn);
        buttonPanel.add(currClearBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void performCurrencyConversion(JTextField currencyInput, JComboBox<String> currencyOpBox) {
        try {
            BigDecimal amount = new BigDecimal(currencyInput.getText());
            String operation = (String) currencyOpBox.getSelectedItem();
            BigDecimal result = null;
            
            if (operation.equals("USD to INR")) {
                result = CurrencyConverter.usdToInr(amount);
                displayField.setText("$" + amount + " → ₹" + result);
            } else {
                result = CurrencyConverter.inrToUsd(amount);
                displayField.setText("₹" + amount + " → $" + result);
            }
            addToHistory(operation + ": " + amount + " → " + result);
        } catch (Exception ex) {
            showError("Please enter valid amount");
        }
    }

    private JPanel createLengthTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        JTextField lengthInput = createStyledTextField();
        JComboBox<String> lengthOpBox = new JComboBox<>(new String[]{
            "Meter to Feet", "Feet to Meter", "Kilometer to Mile", "Mile to Kilometer",
            "Centimeter to Inch", "Inch to Centimeter"
        });
        
        // Add Enter key support
        lengthInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLengthConversion(lengthInput, lengthOpBox);
                }
            }
        });
        
        addLabeledTextField(inputPanel, "Length:", lengthInput);
        addLabeledComponent(inputPanel, "Conversion:", lengthOpBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton lengthCalcBtn = createStyledButton("Convert", SUCCESS_COLOR);
        JButton lengthClearBtn = createStyledButton("Clear", ERROR_COLOR);
        
        lengthCalcBtn.addActionListener(e -> performLengthConversion(lengthInput, lengthOpBox));
        lengthClearBtn.addActionListener(e -> {
            lengthInput.setText("");
            displayField.setText("0");
        });
        
        buttonPanel.add(lengthCalcBtn);
        buttonPanel.add(lengthClearBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private void performLengthConversion(JTextField lengthInput, JComboBox<String> lengthOpBox) {
        try {
            BigDecimal length = new BigDecimal(lengthInput.getText());
            String operation = (String) lengthOpBox.getSelectedItem();
            BigDecimal result = null;
            
            switch (operation) {
                case "Meter to Feet" -> {
                    result = LengthConverter.meterToFeet(length);
                    displayField.setText(length + " m → " + result + " ft");
                }
                case "Feet to Meter" -> {
                    result = LengthConverter.feetToMeter(length);
                    displayField.setText(length + " ft → " + result + " m");
                }
                case "Kilometer to Mile" -> {
                    result = LengthConverter.kilometerToMile(length);
                    displayField.setText(length + " km → " + result + " mi");
                }
                case "Mile to Kilometer" -> {
                    result = LengthConverter.mileToKilometer(length);
                    displayField.setText(length + " mi → " + result + " km");
                }
                case "Centimeter to Inch" -> {
                    result = LengthConverter.centimeterToInch(length);
                    displayField.setText(length + " cm → " + result + " in");
                }
                case "Inch to Centimeter" -> {
                    result = LengthConverter.inchToCentimeter(length);
                    displayField.setText(length + " in → " + result + " cm");
                }
            }
            
            if (result != null) {
                addToHistory(operation + ": " + length + " → " + result);
            }
        } catch (Exception ex) {
            showError("Please enter valid length");
        }
    }

    private JPanel createStatisticsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel instructionLabel = new JLabel("Enter comma-separated numbers:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        instructionLabel.setForeground(TEXT_COLOR);
        
        JTextField statsInput = createStyledTextField();
        statsInput.setText("1,2,3,4,5");
        
        JComboBox<String> statsOpBox = new JComboBox<>(new String[]{
            "Sum", "Mean (Average)", "Median", "Standard Deviation", 
            "Maximum", "Minimum", "Count"
        });
        
        // Add Enter key support
        statsInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performStatisticsCalculation(statsInput, statsOpBox);
                }
            }
        });
        
        JPanel statsOpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statsOpPanel.setBackground(BACKGROUND_COLOR);
        JLabel opLabel = new JLabel("Operation:");
        opLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        opLabel.setForeground(TEXT_COLOR);
        statsOpPanel.add(opLabel);
        statsOpPanel.add(statsOpBox);
        
        inputPanel.add(instructionLabel, BorderLayout.NORTH);
        inputPanel.add(statsInput, BorderLayout.CENTER);
        inputPanel.add(statsOpPanel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton statsCalcBtn = createStyledButton("Calculate", SUCCESS_COLOR);
        JButton statsClearBtn = createStyledButton("Clear", ERROR_COLOR);
        
        statsCalcBtn.addActionListener(e -> performStatisticsCalculation(statsInput, statsOpBox));
        statsClearBtn.addActionListener(e -> {
            statsInput.setText("1,2,3,4,5");
            displayField.setText("0");
        });
        
        buttonPanel.add(statsCalcBtn);
        buttonPanel.add(statsClearBtn);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private void performStatisticsCalculation(JTextField statsInput, JComboBox<String> statsOpBox) {
        try {
            String input = statsInput.getText().trim();
            String[] numbers = input.split(",");
            java.util.List<BigDecimal> numberList = new java.util.ArrayList<>();
            
            for (String num : numbers) {
                numberList.add(new BigDecimal(num.trim()));
            }
            
            String operation = (String) statsOpBox.getSelectedItem();
            String result = "";
            
            switch (operation) {
                case "Sum" -> {
                    BigDecimal sum = StatisticalOperations.sum(numberList);
                    result = "Sum: " + sum;
                }
                case "Mean (Average)" -> {
                    BigDecimal mean = StatisticalOperations.mean(numberList);
                    result = "Mean: " + mean;
                }
                case "Median" -> {
                    BigDecimal median = StatisticalOperations.median(numberList);
                    result = "Median: " + median;
                }
                case "Standard Deviation" -> {
                    BigDecimal stdDev = StatisticalOperations.standardDeviation(numberList);
                    result = "Std Dev: " + stdDev;
                }
                case "Maximum" -> {
                    BigDecimal max = StatisticalOperations.max(numberList);
                    result = "Max: " + max;
                }
                case "Minimum" -> {
                    BigDecimal min = StatisticalOperations.min(numberList);
                    result = "Min: " + min;
                }
                case "Count" -> {
                    int count = StatisticalOperations.count(numberList);
                    result = "Count: " + count;
                }
            }
            
            displayField.setText(result);
            addToHistory(operation + " of [" + input + "] = " + result);
        } catch (NumberFormatException ex) {
            showError("Please enter valid comma-separated numbers (e.g., 1,2,3,4,5)");
        } catch (ArithmeticException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Calculation error: " + ex.getMessage());
        }
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
                "📜 Calculation History",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                SECONDARY_COLOR
        ));
        panel.setPreferredSize(new Dimension(250, 0));
        
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 10));
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JButton clearHistoryBtn = createStyledButton("Clear History", ERROR_COLOR);
        clearHistoryBtn.addActionListener(e -> {
            calculationHistory.clear();
            historyArea.setText("");
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(clearHistoryBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private void performCalculation() {
        try {
            BigDecimal a = new BigDecimal(num1Field.getText().trim());
            BigDecimal b = num2Field.getText().trim().isEmpty()
                    ? BigDecimal.ZERO
                    : new BigDecimal(num2Field.getText().trim());
            
            String operation = (String) operationBox.getSelectedItem();
            BigDecimal result = null;
            String expression = "";
            
            switch (operation) {
                case "Add" -> {
                    result = BasicOperations.add(a, b);
                    expression = a + " + " + b + " = " + result;
                }
                case "Subtract" -> {
                    result = BasicOperations.subtract(a, b);
                    expression = a + " - " + b + " = " + result;
                }
                case "Multiply" -> {
                    result = BasicOperations.multiply(a, b);
                    expression = a + " × " + b + " = " + result;
                }
                case "Divide" -> {
                    result = BasicOperations.divide(a, b);
                    expression = a + " ÷ " + b + " = " + result;
                }
            }
            
            displayField.setText(expression);
            addToHistory(expression);
            num1Field.setText(result.toString());
            num2Field.setText("");
            
        } catch (Exception ex) {
            showError("Invalid input! Please check your numbers.");
        }
    }

    private void clearFields() {
        num1Field.setText("");
        num2Field.setText("");
        displayField.setText("0");
    }

    private void addToHistory(String calculation) {
        calculationHistory.add(calculation);
        updateHistoryDisplay();
    }

    private void updateHistoryDisplay() {
        StringBuilder hist = new StringBuilder();
        for (int i = calculationHistory.size() - 1; i >= Math.max(0, calculationHistory.size() - 15); i--) {
            hist.append("• ").append(calculationHistory.get(i)).append("\n");
        }
        historyArea.setText(hist.toString());
    }

    private void handleGlobalKeyPress(KeyEvent e) {
        // Handle common keyboard shortcuts
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            clearFields();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            performCalculation();
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            clearFields();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_COLOR);
        
        // Add keyboard listener for enhanced input
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performCalculation();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    field.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String text = field.getText();
                    if (!text.isEmpty()) {
                        field.setText(text.substring(0, text.length() - 1));
                    }
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                // Allow only numeric input, decimal point, and operators
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '-' && c != '+' 
                    && c != '*' && c != '/' && c != '\b' && c != '\n') {
                    e.consume(); // Prevent invalid characters
                }
            }
        });
        
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setOpaque(true);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }

    private void addLabeledTextField(JPanel panel, String label, JTextField field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(TEXT_COLOR);
        panel.add(lbl);
        panel.add(field);
    }

    private void addLabeledComponent(JPanel panel, String label, JComponent component) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(TEXT_COLOR);
        panel.add(lbl);
        panel.add(component);
    }
}
