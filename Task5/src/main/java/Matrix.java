import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alexandr on 20.12.15.
 */
public class Matrix<T>{
    private List<T> matrixList;
    private int colNumber = 0;
    private int rowNumber = 0;
    //static final int DEFAULT_COL_NUMBER = 5;

    public class IncorrectMatrixFileException extends Exception{
        IncorrectMatrixFileException(String fileName){
            super("In file \"" + fileName + "\": Rows in matrix should has the same length");
        }
    }

    Matrix(int colNumber,int rowNumber){
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        matrixList = new ArrayList<>(colNumber*rowNumber);
        for (int i = 0;i < colNumber*rowNumber;++i){
            matrixList.add(null);
        }
    }



    Matrix(String fileName, FromStringCreator<T> creator) throws IOException, IncorrectMatrixFileException{
        matrixList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while((line=reader.readLine())!=null)

            {
                rowNumber++;
                String[] stringArr = line.split(" ");
                if (colNumber == 0)
                    colNumber = stringArr.length;
                else if (colNumber != stringArr.length) {
                    reader.close();
                    throw new IncorrectMatrixFileException(fileName);
                }
                for (String elem : stringArr)
                    matrixList.add(creator.create(elem));
            }
        }
        catch(IOException e){
            throw new IOException("File \"" + fileName + "\" can't open to read.");
        }
    }

    void set(int rowIndex,int colIndex,T element){
        if (isIndexOutOfBound(rowIndex,colIndex))
            throw new IndexOutOfBoundsException();
        matrixList.set(rowIndex*colNumber + colIndex,element);
    }

    T get(int rowIndex,int colIndex){
        if (isIndexOutOfBound(rowIndex,colIndex))
            throw new IndexOutOfBoundsException();
        return matrixList.get(rowIndex*colNumber + colIndex);
    }

    int getColNumber(){
        return colNumber;
    }

    int getRowNumber(){
        return rowNumber;
    }

    boolean isCellHaveValue(int rowIndex, int colIndex){
        if (isIndexOutOfBound(rowIndex,colIndex))
            throw new IndexOutOfBoundsException();
        return (matrixList.get(rowIndex*colNumber + colIndex) != null);
    }

    boolean isIndexOutOfBound(int rowIndex, int colIndex){
        return rowIndex >= rowNumber || colIndex >= colNumber;
    }

    void print (String fileName) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            StringBuilder builder;
            for (int rowIndex = 0; rowIndex < rowNumber; ++rowIndex) {
                builder = new StringBuilder();
                for (int colIndex = 0; colIndex < colNumber; ++colIndex) {
                    builder.append(get(rowIndex, colIndex)).append(' ');
                }
                writer.write(builder.toString());
                writer.newLine();
            }
        } catch(IOException e){
            throw new IOException("File \"" + fileName + "\" can't open to write.");
        }
    }
}
