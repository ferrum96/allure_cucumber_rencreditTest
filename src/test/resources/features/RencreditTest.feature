#language: ru

Функционал: Вклады

  @scenario1 @all
  Сценарий: Расчет доходности по вкладу

    Когда выбрано меню "Вклады" и подменю "рубли"

    Когда заполняются поля:
    |Сумма вклада|300000|
    |Ежемесячное пополнение|50000|

    Когда выбран срок вклада "6 месяцев"
    Когда выполнено нажатие на кнопку "Ежемесячная капитализация"
    
    Тогда значение поля "К снятию" равно "549 774,53"