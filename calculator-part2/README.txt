Теперь вам нужна поддержка скобок и функций в вашей программе.
Пример: 1+(2+3*(4+5-sin(45*cos(a))))/7

Кто-то бы решил эту задачу, гугля что-то про обратную польскую нотацию.
Кто-то бы загуглил рекурсивный спуск.

Вот ещё вопрос: учитывая, что вам нужно сделать поддержку функций sin, cos, tan, atan, log10, log2, sqrt - как вы будете в коде выбирать нужную ветку кода? через switch/case или как-то более хитро?

Есть вариант сделать что-то вроде HashMap<String, IAction>, где IAction - это интерфейс, имеющий метод (упрощённый пример)  double calculate(double number), соответственно ключами этой мэпы будут названия функций.
