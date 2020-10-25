package mk.ukim.finki.aud1;

public class MatrixTest {

    public double sumOfMatrix(double[][] matrix){
        double sum = 0;
        for (int i = 0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                sum+=matrix[i][j];
            }
        }
        return sum;
    }

    public double averageOfMatrix(double[][] matrix){
        return sumOfMatrix(matrix)/(matrix.length*matrix[0].length);
    }

    public static void main(String[] args) {

    }
}
