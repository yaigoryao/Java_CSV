package csv;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.*;

public class CSV
{
    private String path = "\0";
    private FileWriter writer = null;
    private Boolean headerWritten = false;
    private Boolean isOpen = false;

    public CSV(){};
    /**
     * Открывает файл, если оне еще не открыт
     * @param path путь к файлу
     * @return void
     */
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

    /**
     * Записывает заголовок csv-файла, если файл открыт
     * @param header заголовок
     * @return void
     */
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

    /**
     * Записывает список строк данных, если файл открыт
     * @param data список типа Tuple<key, value> для записи
     * @return void
     */
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

    /**
     * Зкрывает файл, если файл открыт
     * 
     * @return void
     */
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

    /**
     * Записывает в файл path заголовок header и список строк data
     * @param path путь к файлу
     * @param header заголовок файла
     * @param data список строк
     * @return void
     */
    public <key, value> void fillFile(String path, String header, List<Tuple<key, value>> data)
    {
        this.open(path);
        this.writeHeader(header);
        this.write(data);
        this.close();
    }

    /**
     * Записывает в файл path словать с данными dict 
     * @param path путь к файлу
     * @param dict словарь с данными
     * @return void
     */
    public <key, value> void writeDict(String path, Map<key, List<value>> dict)
    {
        this.open(path);
        List<key> keys = new ArrayList<key>(dict.keySet());
        this.writeHeader(String.join(";", keys.stream().map(Object::toString).collect(Collectors.toList())));
        //List<List<value>> vals = dict.values().stream().collect(Collectors.toList());
        List<List<value>> vals = new ArrayList<List<value>>(dict.values());
        for (final Integer i[] = {0}; i[0] < vals.get(0).size(); i[0]++)
        {
            try 
            {
                List<value> rv = new ArrayList<value>();
                vals.forEach((List<value> e) -> { rv.add(e.get(i[0])); });
                this.writer.write(String.join(";", rv.stream().map(Object::toString).collect(Collectors.toList())) + "\n");
                writer.flush();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        this.close();
    }
}
