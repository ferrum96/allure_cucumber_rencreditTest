#language: ru
Функционал: Вклады

  @all @scenario1
  Сценарий: Расчет доходности по вкладу

    Когда выбрано меню "Вклады" и подменю "рубли"

    Когда заполняются поля:
    |Сумма вклада|300000|
    |Ежемесячное пополнение|50000|

    Когда выбран срок вклада "6 месяцев"
    Когда выполнено нажатие на кнопку "Ежемесячная капитализация"
    
    Тогда значение поля "К снятию" равно "549 774,53"

  @all @scenario2
  Сценарий: Расчет доходности по вкладу

    Когда выбрано меню "Вклады" и подменю "рубли"

    Когда заполняются поля:
      |Сумма вклада|500000|
      |Ежемесячное пополнение|70000|

    Когда выбран срок вклада "9 месяцев"
    Когда выполнено нажатие на кнопку "Ежемесячная капитализация"

    Тогда значение поля "К снятию" равно "1 088 175,53"


