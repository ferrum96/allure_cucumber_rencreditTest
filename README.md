Запуск Allure : mvn test -Dtest=RencreditTest / mvn test -Dtest=RencreditTest2
                
Запуск Cucumber: mvn test -DTAGS=@scenario1 / mvn test -DTAGS=@scenario2

Сборка html отчета: mvn allure:serve
