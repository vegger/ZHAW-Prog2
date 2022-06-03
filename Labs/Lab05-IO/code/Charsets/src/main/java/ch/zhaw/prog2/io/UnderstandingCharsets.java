package ch.zhaw.prog2.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class UnderstandingCharsets {
    public static void main(String[] args) {


        /* Teilaufgabe a
         * In der Vorlesung haben Sie gelernt, dass Java-Klassen fuer Unicode entworfen wurden.
         * Nun ist Unicode aber nicht der einzige Zeichensatz und Java unterstuetz durchaus Alternativen.
         * Welche Zeichensaetze auf einem System konkret unterstuetzt werden haengt von der Konfiguration des Betriebssystems JVM ab.
         * Schreiben Sie ein Programm, welches alle Unterstuetzten Zeichensaetze auf der Konsole (System.out) ausgibt,
         * zusammen mit dem Standardzeichensatz.
         * https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html
         */

        // ToDo: Print default character set


        // Todo: Print all available character sets



        /* Ende Teilaufgabe a */


        /* Teilaufgabe b
         * Ergänzen Sie die Klasse so, dass sie einzelne Zeichen (also Zeichen für Zeichen) im Standardzeichensatz
         * von der Konsole einliest und in zwei Dateien schreibt einmal im Standardzeichensatz und einmal im
         * Zeichensatz `US-ASCII`.
         * Die Eingabe des Zeichens `q` soll das Program ordentlich beenden.
         * Die Dateien sollen `CharSetEvaluation_Default.txt` und `CharSetEvaluation_ASCII.txt` genannt und
         * werden entweder erzeugt oder, falls sie bereits existieren, geöffnet und der Inhalt überschrieben.
         * Testen Sie Ihr Program mit den folgenden Zeichen: a B c d € f ü _ q
         * Öffnen Sie die Textdateien nach Ausführung des Programs mit einem Texteditor und erklären Sie das Ergebnis.
         * Öffnen Sie die Dateien anschliessend mit einem HEX-Editor und vergleichen Sie.
         */




    }
}

