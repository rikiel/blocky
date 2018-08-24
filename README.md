# Bločky

## 0. Úloha branche ORM
Úlohou tejto branche bolo vyskúšať si použitie rôznych nástrojov pri práci s DB.
Nástroje sa využívajú iba v module service, zvyšné časti sú rovnaké s pôvodnou master branchou.
Odporúčam pozrieť si hlavne testy, z ktorých je vidieť použitie nástrojov - hibernate/jdbc/jdbcTemplate/MyBatis.

## 1. Úloha programu
Hlavnou úlohou aplikácie Bločky je spravovať rôzne výdaje jedného užívateľa,
alebo spoločné výdaje viacerých užívateľov.

Zložité skladovanie bločkov z nákupov je vďaka tejto aplikácií minulosťou.
Stačí iba vytvoriť záznam nákupu, odfotografovať bloček z nákupu a obrázok
nahrať do aplikácie, ktorá sa postará o ich uloženie a skladovanie.

Okrem obrázkov je možné nahrať do aplikácie ľubovoľný súbor.
Aplikácia podporuje náhľad príloh pre obrázkové a textové súbory. Ostatné typy súborov sa dajú jednoducho stiahnuť a je možné s nimi ďalej pracovať mimo aplikáciu.

## 2. Používanie programu
V tejto časti sa budeme venovať popisu programu, obrazoviek a funkčností,
ktoré sú implementované. Informácie ku kompilácií a prvému štartu programu
sú rozpísané v ďalších kapitolách.


### Prehľad bločkov

Základnou obrazovkou je obrazovka prehľadu bločkov. Jej hlavnou funkciou
je zobraziť zoznam bločkov uložených v databáze. Položky v zozname sú rozklikávateľné,
teda po kliknutí na jednotlivé riadky zobrazia všetky dostupné informácie, aj tie,
ktoré v riadku zobrazené neboli (napríklad prílohy). Jednotlivé položky môžeme
upravovať a mazať vďaka tlačítkam v detaile.

Program ponúka možnosť hromadného mazania položiek vďaka tomu, že riadky sú
zaškrtávacie a po vybraní aspoň jednej položky sa sprístupní tlačítko na odstránenie
vybraných bločkov.

Okrem mazania je z tejto obrazovky prístupná aj funkčnosť vytvorenia nového záznamu.

#### Dostupné akcie
* Akcie dostupné priamo z obrazovky
  * Vytvorenie novej položky
  * Hromadné mazanie položiek
* Akcie dostupné z detailu položky
  * Zmena položky
  * Zmazanie položky

### Vytvorenie bločku
Na prehľadovej obrazovke je dostupné tlačítko Pridať na vytvorenie nového bločku.
Po kliknutí na tlačidlo sa zobrazí formulár umožňujúci vytváranie novej položky.

Bločky potrebujú mať vyplnený názov a kategóriu do ktorej spadajú. Dostupné kategórie
sa načítavajú z databáze, aby bolo jednoduché ich zoznam doplniť o nové položky.
Ďalšou položkou, ktorú môžeme vyplniť je detail položky. Okrem neho môžeme nahrať
aj ľubovoľný počet príloh (napríklad odfotografované bločky z obchodu, faktúry,
nákupné zoznamy a podobne).

Po vyplnení potrebných polí kliknutím na tlačítko Potvrdiť vytvoríme novú položku.
O (ne)úspechu informuje zobrazená správa. V prípade, že nie sú vyplnené povinné polia,
kliknutie na tlačítko Potvrdiť zvýrazni polia, ktoré je potrebné doplniť.

Tlačítkom Späť sa dostaneme na prehľadovú obrazovku bez vytvárania novej položky.

### Úprava bločku
Z detailu bločku na prehľadovej obrazovke je dostupné tlačítko Upraviť, ktoré nás
presmeruje na obrazovku rovnakú ako obrazovka vytvorenia bločku, s tým, že polia na
obrazovke sú vyplnené podľa aktuálneho bločku. Rovnako sú zobrazené všetky jeho prílohy.
Po úprave a kliknutí na tlačítko Potvrdiť sa zmena prepíše do databáze.

Z obrazovky pre úpravu bločku je dostupná funkcia stiahnutia jednotlivých príloh.

### Hromadné mazanie bločkov
V prípade, že na prehľadovej obrazovke vyberieme niekoľko položiek a klikneme na tlačítko
Odstrániť vybrané, alebo v detaile položky klikneme na tlačítko Zmazať, zobrazí sa
obrazovka pre mazanie bločkov. Je zobrazený ich zoznam s dostupnými akciami Späť a Zmazať.

## 3. Inštalácia

### Prerekvizity
* Stiahnuté zdrojové kódy v adresáry $APP_DIR
* Maven verzie 3
* Java verzie 8
* Databáza nieje nutná, testovať sa dá so zapnutým maven profilom mock

### Inštalácia
Program sa skompiluje sériou nasledujúcich príkazov v konzole:
```bash
$ cd $APP_DIR
$ mvn clean install
```
V prípade, že chceme spustiť program bez inštalácie a konfigurácie databáze,
je možné program skompilovať so zapnutým mock profilom:
```bash
$ mvn clean install -P mock
```
Mock profil zabezpečí, že sa spustí in-memory databáza, do ktorej sa pridá niekoľko
testovacích bločkov a dá sa otestovať funkčnosť programu.

### Spustenie
Program sa spustí nasledovne
```bash
$ cd $APP_DIR/view
$ mvn spring-boot:run
```
V prípade, že používame mock profil, namiesto druhého príkazu spustíme
```bash
$ mvn spring-boot:run -P mock
```

## 4. Použité knižnice

### Vaadin
Užívateľské rozhranie je postavené na frameworky Vaadin verzie 8. Keďže je aplikácia
iba menšieho rozsahu, využívame iba základné komponenty, rovnako aj téma valo je
postačujúca a nebolo potrebné v nej nič meniť.

### Spring-boot
Aby bolo možné program jednoducho spustiť, používame framework spring-boot, ktorý sa
postará o veľké množstvo drobností, aby program bežal bez problémov. Je v ňom zabudovaný
aj tomcat server, takže na spustenie webovej aplikácie nieje potrebné nič konfigurovať
a stačí ju iba skompilovať a priamo spustiť.

### Testovanie - TestNG, JMockit
Ako testovací framework sme použili TestNG. Mockovanie jednotlivých databázových operácií
pri testoch zabezpečuje Jmockit.

## 5. Moduly
Program pozostáva zo 5 modulov – common, model, service, service-mock, view.
Význam jednotlivých modulov je nasledovný:

### Common
Modul obsahuje časti využívané vo všetkých častiach aplikácie. Obsahuje napríklad
definície aspektov alebo validácie

### Model
Modul obsahuje definície modelov ktoré sa načítavajú z databáze – Bloček (Invoice),
Príloha (Attachment) a ďalšie.

### Service
Modul obsahuje rozhranie s databázou. Existujú tu repozitáre pre každú tabuľku z databáze
a okrem nad nimi je vrstva ktorá jednotlivé repozitáre spája a tvorí službu ako takú.

### Service-mock
Použije sa iba ak je zapnutý mock profil. Zabezpečí vyplnenie in-memory databáze
nejakými hodnotami.

### View
Modul obsahuje implementáciu užívateľského rozhrania.