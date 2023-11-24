Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - KSiS/КСиС (компьютерные системы и сети).

## Общая информация

Каждая папка в этом репозитории - проект Gradle, который должен быть открыт через IntelliJ IDEA. Использование Eclipse IDE не рекомендуется, так как там плохая поддержка языка Kotlin.

| Технология                                    | Версия    |
|-----------------------------------------------|-----------|
| Версия системы автоматической сборки Gradle   | 8.4       |
| Версия Java, используемая для запуска Gradle  | 17.0.9    |
| Версия Java, используемая для запуска проекта | 17.0.9    |
| Версия Java, используемая для сборки проекта  | 17.0.9    |
| Версия Kotlin                                 | 1.9.20    |

## Установка

Первым делом нужно скачать репозиторий и разархивировать его в любое место на диске. Если всё сделано правильно, вы должны увидеть несколько папок, в которых находятся файлы `build.gradle.kts`, `settings.gradle.kts` и другие. Полученные папки будем называть ***папками проектов***.

Запустите IntelliJ IDEA и откройте папку проекта: `File -> Open -> [Выбираете папку] -> OK`. Сразу после открытия начнётся установка среды. Если от вас потребуется разрешение на скачивание файлов, дайте его. Спустя некоторое время все необходимые файлы скачаются, и среда будет готова к работе.

Если на этом моменте что-то пошло не так и среда не установилась, значит, самое время проверить значения, указанные в таблице из первого раздела. Где их настроить:
* Java для запуска Gradle: переменные среды ОС или `File -> Settings -> Build -> Build Tools -> Gradle -> Gradle JVM`;
* Java для запуска проекта: `File -> Project Structure -> Project -> SDK`;
* Синтаксис Java: `File -> Project Structure -> Project -> Language Level`.

В каждом меню есть возможность скачать JDK. В конечном итоге, у вас должно быть скачано столько разных JDK, сколько указано в таблице.

После изменения этих значений необходимо перезагрузить проект Gradle. Это можно сделать в ***меню Gradle***: `View -> Tool Windows -> Gradle`, нажав на значок перезагрузки в появившемся справа меню.

## Основы работы

После установки среды весь необходимый инструментарий готов к работе. Вот самые важные команды, необходимые каждому разработчику:

* Запуск приложения: `Меню Gradle -> application -> run` (если такой команды нет, значит, файлы программы запускаются отдельно).
* Компиляция приложения в файл с расширением .jar: `Меню Gradle -> build -> build`. После компиляции ваше приложение появится в папке `папка_проекта/build/libs`. 

Примечания: 
* Команды запускаются двойным нажатием по ним.
* В IntelliJ IDEA все вышеуказанные команды содержатся в категории Gradle "Tools".

## Использование

* Скомпилировать приложение в файл с расширением .jar;
* Открыть консоль Windows в папке с вышеупомянутым файлом и выполнить команду `java -jar JarFileName.jar`. Курсовую работу можно запустить проще, двойным кликом ЛКМ.

## Условия

### Курсовая работа

Шахматы (клиенты и сервер) с GUI на Kotlin.

### Лабораторная работа 1

Написать программу, реализующую следующие функции: 

* Отображение MAC-адреса компьютера (можно воспользоваться функцией netbios). 
* Отображение всех рабочих групп, компьютеров в сети и их ресурсов (папок, открытых для общего доступа, принтеров). Воспользоваться функциями WNetXXX. 

### Лабораторная работа 2

Программа измеряет скорость передачи информации по протоколам TCP и UDP, а так же количество потерянных (искаженных) пакетов. 
Трафик генерируется псевдослучайным образом (т.е. генерируется псевдослучайная последовательность данных, отсылается на другой компьютер и там сравнивается с эталоном). 

### Лабораторная работа 3

Написать программу, реализующую следующие функции клиента и сервера одного из протоколов:

* DayTime

Программа должна производить полную обработку команд запросов и ответов каждого из протоколов.

### Лабораторная работа 4

Написать программу, реализующую функции FTP-сервера. В главном окне сервера расположено поле типа memo, в котором отображается весь протокол общения клиента с сервером, например: 

* USER vasilisa 
* 331 Password required for vasilisa. 
* PASS abcd 
* 230 User vasilisa logged in. 
* PORT 140,252,13,34,4,150 
* 200 PORT command successful. 

Тестирование и подача программы-сервера производится при помощи любого стандартного FTP клиента.
