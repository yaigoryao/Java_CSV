Установка пакета
1.	В папку с проектом перемещаем файлы CSV.java и Tuple.java
2.	Пишем следующие команды 
  javac -d . CSV.java
  javac -d . Tuple.java
  
4.	В проекте подключаем с помощью
  import csv.CSV;
  import csv.Tuple;

Пример использования
import csv.CSV;
import csv.Tuple;
import java.util.*;

public class csv_test
{

    public static void main(String[] args)
    {
    // класс безопасен, есть различные проверки, при использовании, как на примере, не сломается
        List<Tuple<Integer, Double>> data = new ArrayList<Tuple<Integer, Double>>(); // <- данные в таком виде --> List<Tuple<T1, T2>> <--
        data.add(new Tuple<Integer, Double>(10, 2.3));
        data.add(new Tuple<Integer, Double>(20, 5.6));
        data.add(new Tuple<Integer, Double>(30, 10.1));

        CSV test = new CSV(); // <- создали объект
        test.fillFile("new_file.csv", "Count;Time", data);
        //ИЛИ
        test.open("new_file.csv"); // <- открыли нужный файл (безопасно, проверка на уже открытый файл или на ошибку создания)
        test.writeHeader("Count;Time"); // <- записали нужный заголовок (безопасно, не дублирует при многократном вызове)
        test.write(data); // <- записали данные
        test.close(); // <- закрыли (безопасно, не даст закрыть файл, если еще не открыли)
    }
}

