#language: ru
@All
Функционал: : поиск на авито

  Сценарий: : Найдем самые дорогие принтеры на авито
    Пусть открыт ресурс Авито
    И в выпадающем списке категорий выбрана ОРГТЕХНИКА
    И в поле поиска введено значение принтер
    Тогда кликнуть по выпадающему списку региона
    Тогда в поле регион ввести значение Владивосток
    И нажать кнопка показать объявления
    Тогда открылась страница результаты по запросу принтер
    И активирован чекбокс только с фотографией
    И в выпадающем списке сортировка выбрано значение ДОРОЖЕ
    И в консоль выведено значение название и цены 3 первых товаров