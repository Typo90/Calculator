import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator implements ActionListener {
    private final String[] NUMBERS = new String[]{"%", "开方", "平方", "1/x", "CE", "C", "Backspace", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", " ", "0", ".", "="};
    private JButton[] numbers;
    private JTextField resultText;
    private double resultNum;
    private boolean isFirst;
    private String operate;

    public Calculator() {
        this.numbers = new JButton[this.NUMBERS.length];
        this.resultText = new JTextField("0");
        this.resultNum = 0.0D;
        this.isFirst = true;
        this.operate = "";
    }

    public void initializ() {
        Font num = new Font("圆体", 1, 25);
        Font sign = new Font("圆体", 1, 15);
        Font resultTe = new Font("圆体", 1, 50);
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(6, 4, 1, 1));

        for(int i = 0; i < this.NUMBERS.length; ++i) {
            this.numbers[i] = new JButton(this.NUMBERS[i]);
            numberPanel.add(this.numbers[i]);
            this.numbers[i].setBackground(Color.lightGray);
            this.numbers[i].setForeground(Color.black);
            this.numbers[i].setFont(sign);
            if (i == 8 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 16 || i == 17 || i == 18 || i == 21) {
                this.numbers[i].setBackground(Color.white);
                this.numbers[i].setFont(num);
            }
        }

        this.resultText.setFont(resultTe);
        this.resultText.setHorizontalAlignment(4);
        this.resultText.setEditable(false);
        this.resultText.setBackground(Color.white);
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", this.resultText);

        for(int i = 0; i < this.numbers.length; ++i) {
            this.numbers[i].addActionListener(this);
        }

        JFrame frame = new JFrame();
        frame.add("Center", numberPanel);
        frame.add("North", top);
        frame.setDefaultCloseOperation(3);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setTitle("计算器");
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if ("0123456789.".indexOf(label) >= 0) {
            this.handleNumbers(label);
        } else if (label.equals("Backspace")) {
            this.handleBackspace();
        } else if (label.equals("C")) {
            this.handleC();
        } else if (label.equals("CE")) {
            this.handleCE();
        } else if (label.equals("平方")) {
            this.resultNum = this.getNumberFromTextField();
            this.updateNumberToTextField(Math.pow(this.resultNum, 2.0D));
        } else if (label.equals("开方")) {
            this.resultNum = this.getNumberFromTextField();
            this.updateNumberToTextField(Math.sqrt(this.resultNum));
        } else if (label.equals("1/x")) {
            this.resultNum = this.getNumberFromTextField();
            if (this.resultNum == 0.0D) {
                this.resultText.setText("分母不能为零");
            } else {
                this.updateNumberToTextField(1.0D / this.resultNum);
            }
        } else if (label.equals("=")) {
            if (this.getNumberFromTextField() == 0.0D) {
                this.resultText.setText("分母不能为零");
            } else {
                this.handleOperator(label);
                this.updateNumberToTextField(this.resultNum);
            }
        } else {
            this.operate = label;
            this.resultNum = this.getNumberFromTextField();
            this.isFirst = true;
        }

    }

    public void handleNumbers(String key) {
        if (this.isFirst) {
            this.resultText.setText(key);
        } else if (key.equals(".") && this.resultText.getText().indexOf(".") < 0) {
            this.resultText.setText(this.resultText.getText() + ".");
        } else if (!key.equals(".")) {
            this.resultText.setText(this.resultText.getText() + key);
        }

        this.isFirst = false;
    }

    public void handleBackspace() {
        String text = this.resultText.getText();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            if (text.length() == 0) {
                this.resultText.setText("0");
                this.isFirst = true;
            } else {
                this.resultText.setText(text);
            }
        }

    }

    private void handleC() {
        this.resultText.setText("0");
        this.isFirst = true;
        this.resultNum = 0.0D;
    }

    private void handleCE() {
        this.resultText.setText("0");
    }

    private void handleOperator(String key) {
        if (this.operate.equals("+")) {
            this.resultNum += this.getNumberFromTextField();
        } else if (this.operate.equals("-")) {
            this.resultNum -= this.getNumberFromTextField();
        } else if (this.operate.equals("*")) {
            this.resultNum *= this.getNumberFromTextField();
        } else if (this.operate.equals("/")) {
            this.resultNum /= this.getNumberFromTextField();
        } else if (this.operate.equals("%")) {
            this.resultNum %= this.getNumberFromTextField();
        }

    }

    private double getNumberFromTextField() {
        double result = 0.0D;

        try {
            result = Double.valueOf(this.resultText.getText());
        } catch (NumberFormatException var4) {
        }

        return result;
    }

    private void updateNumberToTextField(double Num) {
        this.resultText.setText(String.valueOf(Num));
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        cal.initializ();
    }
}

