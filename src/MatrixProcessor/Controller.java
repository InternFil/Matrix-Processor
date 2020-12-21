package MatrixProcessor;

import java.util.*;

public class Controller {
    private Scanner scanner = new Scanner(System.in);
    private Queue<Matrix> listMatrix = new LinkedList<>();
    private Matrix m1, m2;

    public void go() {
        while (true) {
            //Выбор операции
            System.out.print("1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit\n" +
                    "Your choice: > ");
            int action = 0;
            action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 0:
                    System.exit(0);
                case 1:
                    inputTwoMatrices();
                    Matrix m1 = listMatrix.poll();
                    Matrix m2 = listMatrix.poll();
                    if (!checkConditionAdding(m1, m2)) {
                        System.out.println("Wrong size of matrix");
                        break;
                    }
                    Controller.print(m1.addition(m2));
                    listMatrix.clear();
                    break;
                case 2:
                    double constant;
                    inputSingeMatrix();
                    m1 = listMatrix.poll();

                    System.out.println("Enter constant: > ");
                    constant = scanner.nextDouble();
                    Controller.print(m1.multiplyToConstant(constant));
                    listMatrix.clear();
                    break;
                case 3:
                    inputTwoMatrices();
                    m1 = listMatrix.poll();
                    m2 = listMatrix.poll();
                    if (!checkConditionMultiply(m1, m2)) {
                        System.out.println("Wrong size of matrix");
                        break;
                    }
                    Controller.print(m1.multiplyMatrices(m2));
                    listMatrix.clear();
                    break;
                case 4:
                    System.out.println("1. Main diagonal\n" +
                            "2. Side diagonal\n" +
                            "3. Vertical line\n" +
                            "4. Horizontal line");
                    System.out.print("Your choice: > ");
                    int typeOfTranspose = scanner.nextInt();
                    scanner.nextLine();
                    inputSingeMatrix();
                    m1 = listMatrix.poll();
                    Controller.print(m1.transpose(typeOfTranspose));
                    listMatrix.clear();
                    break;
                case 5:
                    inputSingeMatrix();
                    m1 = listMatrix.poll();
                    if (!checkIsSquareMatrix(m1)) {
                        System.out.println("Wrong size of matrix, input square matrix");
                        break;
                    }
                    System.out.println(m1.determinant());
                    listMatrix.clear();
                    break;
                case 6:
                    inputSingeMatrix();
                    m1 = listMatrix.poll();
                    if (m1.determinant() == 0) {
                        System.out.println("Determinant equals zero, can't get inverse matrix");
                        break;
                    }
                    Controller.print(m1.inverse());
                    listMatrix.clear();
                    break;
                default:
                    System.out.println("Please, choose an action");
            }
        }
    }

    private void inputSingeMatrix() {
        String inputLine;
        String[] stringMatrix;
        int inputRow, inputColumn;
        double[][] parsed;

        System.out.println("Enter size of matrix: >");
        inputLine = scanner.nextLine();
        Scanner sc_1 = new Scanner(inputLine);
        inputRow = sc_1.nextInt();
        inputColumn = sc_1.nextInt();
        stringMatrix = new String[inputRow];
        System.out.println("Enter matrix:");
        for (int i = 0; i < inputRow; i++) {
            inputLine = scanner.nextLine();
            stringMatrix[i] = inputLine;
        }
        parsed = parseToDouble(stringMatrix, inputRow, inputColumn);
        listMatrix.offer(new Matrix(parsed));
    }

    private void inputTwoMatrices() {

        String inputLine;
        String[] stringMatrix;
        int inputRow, inputColumn;
        double[][] parsed;

        System.out.print("Enter size of first matrix: > ");
        inputLine = scanner.nextLine();
        Scanner sc_1 = new Scanner(inputLine);
        inputRow = sc_1.nextInt();
        inputColumn = sc_1.nextInt();
        stringMatrix = new String[inputRow];
        System.out.println("Enter first matrix:");
        for (int i = 0; i < inputRow; i++) {
            inputLine = scanner.nextLine();
            stringMatrix[i] = inputLine;
        }
        parsed = parseToDouble(stringMatrix, inputRow, inputColumn);
        listMatrix.offer(new Matrix(parsed));

        System.out.print("Enter size of second matrix: > ");
        inputLine = scanner.nextLine();
        Scanner sc_2 = new Scanner(inputLine);
        inputRow = sc_2.nextInt();
        inputColumn = sc_2.nextInt();
        stringMatrix = new String[inputRow];
        System.out.println("Enter second matrix:");
        for (int i = 0; i < inputRow; i++) {
            inputLine = scanner.nextLine();
            stringMatrix[i] = inputLine;
        }
        parsed = parseToDouble(stringMatrix, inputRow, inputColumn);
        listMatrix.offer(new Matrix(parsed));
        sc_1.close();
        sc_2.close();
    }

    private double[][] parseToDouble(String[] inputArray, int row, int column) {
        double[][] parsedInt = new double[row][column];
        Scanner sc_2;
        for (int i = 0; i < row; i++) {
            sc_2 = new Scanner(inputArray[i]);
            for (int j = 0; j < column; j++) {
                parsedInt[i][j] = sc_2.nextDouble();
            }
        }
        return parsedInt;
    }

    private boolean checkConditionAdding(Matrix m1, Matrix m2) {
        return m1.getRow() == m2.getRow() && m1.getColumn() == m2.getColumn();
    }

    private boolean checkConditionMultiply(Matrix m1, Matrix m2) {
        return m1.getColumn() == m2.getRow();
    }

    private boolean checkIsSquareMatrix(Matrix m) {
        return m.getRow() == m.getColumn();
    }

    private static void print(String input) {
        System.out.println(input);
    }

    static void print(Matrix in) {
        for (int i = 0; i < in.getRow(); i++) {
            for (int j = 0; j < in.getColumn(); j++) {
                System.out.printf("% .3f ", in.getMatrix()[i][j]);
            }
            System.out.println();
        }
    }
}
