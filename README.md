[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=code_smells)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=bugs)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=duplicated_lines_density)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=sqale_index)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=ncloc)](https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks)

Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - KSiS/КСиС (компьютерные системы и сети).

## Общая информация

Этот репозиторий - проект Gradle, который должен быть открыт через IntelliJ IDEA.

| Технология                             | Версия |
|----------------------------------------|--------|
| Система автоматической сборки Gradle   | 8.5    |
| Java, используемая для запуска Gradle  | 8+     |
| Java, используемая для сборки проекта  | 8+     |
| Java, используемая для запуска проекта | 8+     |
| Kotlin                                 | 1.9.22 |

## Установка

Установка моих проектов Gradle и основы работы с ними примерно одинаковы, так что
смотрите [общую инструкцию](https://github.com/Hummel009/The-Rings-of-Power#readme).

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
