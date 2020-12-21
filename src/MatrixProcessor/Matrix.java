package MatrixProcessor;

public class Matrix {


    private int row, column;
    private double[][] matrix;

    Matrix() {
        row = 3;
        column = 3;
        fillMatrix();
    }

    Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        matrix = new double[this.row][this.column];
        fillMatrix();
    }

    Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.row = matrix.length;
        this.column = matrix[0].length;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    double[][] getMatrix() {
        return matrix;
    }

    public Matrix addition(Matrix adding) {
        double[][] result = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = matrix[i][j] + adding.getMatrix()[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix multiplyToConstant(double constant) {
        double[][] result = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = matrix[i][j] * constant;
            }
        }
        return new Matrix(result);
    }

    public Matrix multiplyMatrices(Matrix multiplicate) {
        double[][] result = new double[row][multiplicate.getColumn()];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < multiplicate.getColumn(); j++) {
                for (int k = 0; k < column; k++) {
                    result[i][j] += matrix[i][k] * multiplicate.getMatrix()[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose(int type) {
        double[][] result = new double[row][column];
        switch (type) {
            //Транспонирование относительно главной диагонали
            case 1:
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        result[i][j] = matrix[j][i];
                    }
                }
                break;
            //Транспонирование относительно обратной диагонали
            case 2:
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        result[i][j] = matrix[row - 1 - j][column - 1 - i];
                    }
                }
                break;
            //Транспонирование относительно вертикали
            case 3:
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        result[i][j] = matrix[i][column - 1 - j];
                    }
                }
                break;
            //Транспонирование относительно горизонтали
            case 4:
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        result[i][j] = matrix[row - 1 - i][j];
                    }
                }
                break;
        }
        return new Matrix(result);
    }

    public double determinant() {
        return calculateDeterminant(this.getMatrix(), this.getColumn());
    }

    public Matrix inverse() {
        double[][] cofactorMatrixArray = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cofactorMatrixArray[i][j] = Math.pow(-1.0, i + j) * calculateDeterminant(calculateMinorMatrix(this.getMatrix(),
                        this.getColumn(), i, j), this.getColumn() - 1);
            }
        }
        Matrix cofactorMatrix = new Matrix(cofactorMatrixArray);
        Matrix result = cofactorMatrix.transpose(1).multiplyToConstant(1 / this.determinant());
        return result;
    }

    private double calculateDeterminant(double[][] matrix, int size) {
        double result = 0;
        if (size == 1) {
            result = matrix[0][0];
            return result;
        } else if (size == 2) {
            result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            return result;
        } else {
            double[][] minorMatrix;
            for (int j = 0; j < size; j++) {
                minorMatrix = calculateMinorMatrix(matrix, size, 0, j);
                result += Math.pow(-1.0, 0.0 + j) * matrix[0][j] * calculateDeterminant(minorMatrix, size - 1);
            }
        }
        return result;
    }

    double[][] calculateMinorMatrix(double[][] m, int size, int deletedColumn) {
        double[][] minorMatrix = new double[size - 1][size - 1];
        for (int i = 0; i < size - 1; i++) {
            int k = 0;
            for (int j = 0; j < size; j++) {
                if (j == deletedColumn) continue;
                minorMatrix[i][k] = m[i + 1][j];
                k++;
            }
        }
        return minorMatrix;
    }

    double[][] calculateMinorMatrix(double[][] m, int size, int deletedRow, int deletedColumn) {
        int k = 0, n = 0;
        double[][] minorMatrix = new double[size - 1][size - 1];
        for (int i = 0; i < size; i++) {
            if (i == deletedRow) continue;
            for (int j = 0; j < size; j++) {
                if (j == deletedColumn) continue;
                minorMatrix[k][n] = m[i][j];
                n++;
            }
            n = 0;
            k++;
        }
        return minorMatrix;
    }

    private void printMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void fillMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = (int) (Math.random() * 11);
            }
        }
    }
}
