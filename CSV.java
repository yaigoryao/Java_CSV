package csv;
import java.io.IOException;
import java.util.*;
import java.io.*;
// public class csv_test
// {

public class CSV
{
    private String path = "\0";
    private FileWriter writer = null;
    private Boolean headerWritten = false;
    private Boolean isOpen = false;

    public CSV(){};
    public void open(String path)
    {
        if(this.path == "\0" && this.writer == null)
        {
            try 
            {
                this.writer = new FileWriter(path, false);
                this.path = path;
                this.isOpen = true;
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
        }
    }

    public void writeHeader(String header)
    {
        if(isOpen && !headerWritten && this.writer != null)
        {
            try 
            {
                this.writer.write(header);
                this.writer.write('\n');
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public <key, value> void write(List<Tuple<key, value>> data)
    {
        if(isOpen && this.writer != null)
        {
            try 
            {
                for(Tuple<key, value> t: data)
                {
                    this.writer.write(t.first.toString() + ";" + t.second.toString() + "\n");
                }
                writer.flush();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public void close()
    {
        if(this.path != "\0" && this.writer != null)
        {
            try 
            {
                this.writer.close();
                this.writer = null;
                this.headerWritten = false;
                this.path = "\0";
                this.isOpen = false;
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
        }
    }

    public <key, value> void fillFile(String path, String header, List<Tuple<key, value>> data)
    {
        this.open(path);
        this.writeHeader(header);
        this.write(data);
        this.close();
    }
}

//     public static void main(String[] args)
//     {
//         // класс безопасен, есть различные проверки, при использовании, как на примере, не сломается
//         List<Tuple<Integer, Double>> data = new ArrayList<Tuple<Integer, Double>>(); // <- данные в таком виде --> List<Tuple<T1, T2>> <--
//         data.add(new Tuple<Integer, Double>(10, 2.3));
//         data.add(new Tuple<Integer, Double>(20, 5.6));
//         data.add(new Tuple<Integer, Double>(30, 10.1));

//         CSV test = new CSV(); // <- создали объект
//         test.open("new_file.csv"); // <- открыли нужный файл (безопасно, проверка на уже открытый файл или на ошибку создания)
//         test.writeHeader("Count;Time"); // <- записали нужный заголовок (безопасно, не дублирует при многократном вызове)
//         test.write(data); // <- записали данные
//         test.close(); // <- закрыли (безопасно, не даст закрыть файл, если еще не открыли)
//     }
// }