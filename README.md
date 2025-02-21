[![Code Smells][code_smells_badge]][code_smells_link]
[![Maintainability Rating][maintainability_rating_badge]][maintainability_rating_link]
[![Security Rating][security_rating_badge]][security_rating_link]
[![Bugs][bugs_badge]][bugs_link]
[![Vulnerabilities][vulnerabilities_badge]][vulnerabilities_link]
[![Duplicated Lines (%)][duplicated_lines_density_badge]][duplicated_lines_density_link]
[![Reliability Rating][reliability_rating_badge]][reliability_rating_link]
[![Quality Gate Status][quality_gate_status_badge]][quality_gate_status_link]
[![Technical Debt][technical_debt_badge]][technical_debt_link]
[![Lines of Code][lines_of_code_badge]][lines_of_code_link]

Мои лабораторные работы для BSUIR/БГУИР (белорусский государственный университет информатики и радиоэлектроники).

Предмет - KSiS/КСиС (компьютерные системы и сети).

## Условия

### Курсовая работа

Шахматы (клиенты и сервер) с GUI на Kotlin.

### Лабораторная работа 1

Написать программу, реализующую следующие функции:

* Отображение MAC-адреса компьютера (можно воспользоваться функцией netbios).
* Отображение всех рабочих групп, компьютеров в сети и их ресурсов (папок, открытых для общего доступа, принтеров).
  Воспользоваться функциями WNetXXX.

### Лабораторная работа 2

Программа измеряет скорость передачи информации по протоколам TCP и UDP, а так же количество потерянных (искаженных)
пакетов.
Трафик генерируется псевдослучайным образом (т.е. генерируется псевдослучайная последовательность данных, отсылается на
другой компьютер и там сравнивается с эталоном).

### Лабораторная работа 3

Написать программу, реализующую следующие функции клиента и сервера одного из протоколов:

* DayTime

Программа должна производить полную обработку команд запросов и ответов каждого из протоколов.

### Лабораторная работа 4

Написать программу, реализующую функции FTP-сервера. В главном окне сервера расположено поле типа memo, в котором
отображается весь протокол общения клиента с сервером, например:

* USER vasilisa
* 331 Password required for vasilisa.
* PASS abcd
* 230 User vasilisa logged in.
* PORT 140,252,13,34,4,150
* 200 PORT command successful.

Тестирование и подача программы-сервера производится при помощи любого стандартного FTP клиента.

## Приложения

В папке docs лежит официальная презентация, которую показывали на лекциях.
<!----------------------------------------------------------------------------->

[code_smells_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=code_smells

[code_smells_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[maintainability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=sqale_rating

[maintainability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[security_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=security_rating

[security_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[bugs_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=bugs

[bugs_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[vulnerabilities_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=vulnerabilities

[vulnerabilities_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[duplicated_lines_density_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=duplicated_lines_density

[duplicated_lines_density_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[reliability_rating_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=reliability_rating

[reliability_rating_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[quality_gate_status_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=alert_status

[quality_gate_status_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[technical_debt_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=sqale_index

[technical_debt_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks

[lines_of_code_badge]: https://sonarcloud.io/api/project_badges/measure?project=Hummel009_Computer-Systems-and-Networks&metric=ncloc

[lines_of_code_link]: https://sonarcloud.io/summary/overall?id=Hummel009_Computer-Systems-and-Networks
