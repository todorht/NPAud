package mk.ukim.finki.aud0;

public class RefactoringExample {

    int countAllNumbersDivisibleWithDigitSum(int start, int end){
        int count=0;
        for(int i=start;i<=end;i++){
            if(i%getSumOfDigits(i)==0){
                count++;
            }
        }
        return count;
    }

    public int getSumOfDigits(int tmp) {
        int sumOfDigits = 0;
        while (tmp>0){
            sumOfDigits+=(tmp%10);
            tmp/=10;
        }
        return sumOfDigits;
    }
}
