PSZTJ
=====

Podstawy Sztucznej Inteligencji

@Maciej

Dane: SN2 http://galera.ii.pw.edu.pl/~zsz/pszt/SN2.txt
Ostatnia kolumna to wynik
Przyjmujemy, że wynik dodatni to true, ujemny to false
Dwa podzbiory:
Do uczenia - 80%
Do testowania - 20%
Ale najpierw dane przemieszac
Funkcje neuronów ukrytych - tanh

Funkcja celu algorytmu ewolucyjnego:
TP - Zgodność 1
TN - Zgodność 0
FP - Niezgodność
FN
0-100% precision
0-100% recall
Funkcja celu np. średnia z recall i precision

Dobrze by było gdyby można było podglądać proces uczenia.

Dokumentacja: wybór algorytmu, link do opisu, decyzje implementacyjne (np. jakieś szczególne parametry), wyniki algorytmu

@Łukasz

http://edu.pjwstk.edu.pl/wyklady/nai/scb/wyklad3/w3.htm - tutaj fajnie jest wyjaśnione jak należy poprawiać wagi i jak działa sieć 3 warstwowa.
Na końcu są też wskazówki (tak mi się wydaje) jaką funkcję należy minimalizować algorytmem ewolucyjnym tak, aby dobrać najoptymalniejsze wagi.

Ponieważ nie jestem dobry w takie klocki, to obgdajmy to we 3 na kolejnym spotkaniu. ;)

Nie wiecie, czy jeżeli powiedzieliśmy prowadzącemu, ze chcemy pisać w Javie, to musimy pisać to w 100% w javie? 
Patrząc na problemy z jakimi będziemy się spotykać, aż chce mi się użyć do ich rozwiązania modułów, które byłyby napisane w Scali.
Język ten jest o wiele bardziej ekspresywny od Javy,
jeżeli chodzi o pisanie matematycznych rzeczy w kodzie.
Ja znam Scalę na tyle dobrze, żeby jej użyć w projekcie (zrobiłem kurs na Coursera).
Nie będzie problemu z integracją scali z Javą (oba języki działają na JVM i nie potrzebują nic wiecej).
Scala zmniejszy nam ilość kodu i uczyni go czyściejszym. Pokażę wam ją na najbliższym spotkaniu.
